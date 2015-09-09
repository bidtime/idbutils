/*
 * GZipFilter
 * 过滤压缩流功能
 */
package org.bidtime.web.filter.gzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GZIPFilter implements Filter {

	private static final Logger LOG = LoggerFactory
			.getLogger(GZIPFilter.class);

    /**
     * Performs initialisation.
     *
     * @param filterConfig
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * A template method that performs any Filter specific destruction tasks. Called from {@link #destroy()}
     */
    public void destroy() {
        // noop
    }

    /**
     * Performs the filtering for a request.
     */
    public final void doFilter(final ServletRequest sRequest, final ServletResponse sResponse, final FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) sRequest;
        HttpServletResponse response = (HttpServletResponse) sResponse;

        if (!isIncluded(request) && headerContainsAcceptEncodingGzip(request) && !response.isCommitted()) {
            // Client accepts zipped content
            //if (LOG.isDebugEnabled()) {
            //    LOG.debug(request.getRequestURL() + ". Writing with gzip compression");
           // }

            // Create a gzip stream
            final ByteArrayOutputStream compressed = new ByteArrayOutputStream();
            final GZIPOutputStream gzout = new GZIPOutputStream(compressed);

            // Handle the request
            final GZIPResponseWrapper wrapper = new GZIPResponseWrapper(response, gzout);
            wrapper.setDisableFlushBuffer();
            chain.doFilter(request, wrapper);
            wrapper.flush();

            gzout.close();

            // double check one more time before writing out
            // repsonse might have been committed due to error
            if (response.isCommitted()) {
                return;
            }

            // return on these special cases when content is empty or unchanged
            switch (wrapper.getStatus()) {
                case HttpServletResponse.SC_NO_CONTENT:
                case HttpServletResponse.SC_RESET_CONTENT:
                case HttpServletResponse.SC_NOT_MODIFIED:
                    return;
                default:
            }



            // Saneness checks
            byte[] compressedBytes = compressed.toByteArray();
            boolean shouldGzippedBodyBeZero = shouldGzippedBodyBeZero(compressedBytes, request);
            boolean shouldBodyBeZero = shouldBodyBeZero(request, wrapper.getStatus());
            if (shouldGzippedBodyBeZero || shouldBodyBeZero) {
                // No reason to add GZIP headers or write body if no content was written or status code specifies no
                // content
                response.setContentLength(0);
                return;
            }

            // Write the zipped body
            addGzipHeader(response);

            response.setContentLength(compressedBytes.length);

            response.getOutputStream().write(compressedBytes);

        } else {
            // Client does not accept zipped content - don't bother zipping
            if (LOG.isDebugEnabled()) {
                LOG.debug(request.getRequestURL() + ". Writing without gzip compression because the request does not accept gzip.");
            }
            chain.doFilter(request, response);
        }
    }

    /**
     * Checks if the request uri is an include. These cannot be gzipped.
     */
    private boolean isIncluded(final HttpServletRequest request) {
        final String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
        final boolean includeRequest = !(uri == null);

        if (includeRequest && LOG.isDebugEnabled()) {
            LOG.debug(request.getRequestURL() + " resulted in an include request. This is unusable, because"
                    + "the response will be assembled into the overrall response. Not gzipping.");
        }
        return includeRequest;
    }








    /**
     * Checks if request contains the header value.
     */
    @SuppressWarnings("rawtypes")
	private boolean headerContainsAcceptEncodingGzip(final HttpServletRequest request) {

        final Enumeration accepted = request.getHeaders("Accept-Encoding");
        while (accepted.hasMoreElements()) {
            final String headerValue = (String) accepted.nextElement();
            if (headerValue.indexOf("gzip") != -1) {
                return true;
            }
        }
        return false;
    }

    private static final int EMPTY_GZIPPED_CONTENT_SIZE = 20;

    /**
     * Checks whether a gzipped body is actually empty and should just be zero.
     * When the compressedBytes is {@link #EMPTY_GZIPPED_CONTENT_SIZE} it should be zero.
     *
     * @param compressedBytes the gzipped response body
     * @param request         the client HTTP request
     * @return true if the response should be 0, even if it is isn't.
     */
    private static boolean shouldGzippedBodyBeZero(byte[] compressedBytes, HttpServletRequest request) {

        //Check for 0 length body
        if (compressedBytes.length == EMPTY_GZIPPED_CONTENT_SIZE) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(request.getRequestURL() + " resulted in an empty response.");
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * Performs a number of checks to ensure response saneness according to the rules of RFC2616:
     * <ol>
     * <li>If the response code is {@link javax.servlet.http.HttpServletResponse#SC_NO_CONTENT} then it is illegal for the body
     * to contain anything. See http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.5
     * <li>If the response code is {@link javax.servlet.http.HttpServletResponse#SC_NOT_MODIFIED} then it is illegal for the body
     * to contain anything. See http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.5
     * </ol>
     *
     * @param request         the client HTTP request
     * @param responseStatus         the responseStatus
     * @return true if the response should be 0, even if it is isn't.
     */
    private static boolean shouldBodyBeZero(HttpServletRequest request, int responseStatus) {

        //Check for NO_CONTENT
        if (responseStatus == HttpServletResponse.SC_NO_CONTENT) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(request.getRequestURL() + " resulted in a " + HttpServletResponse.SC_NO_CONTENT
                        + " response. Removing message body in accordance with RFC2616.");
            }
            return true;
        }

        //Check for NOT_MODIFIED
        if (responseStatus == HttpServletResponse.SC_NOT_MODIFIED) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(request.getRequestURL() + " resulted in a " + HttpServletResponse.SC_NOT_MODIFIED
                        + " response. Removing message body in accordance with RFC2616.");
            }
            return true;
        }

        return false;
    }

    /**
     * Adds the gzip HTTP header to the response. This is need when a gzipped body is returned so that browsers can properly decompress it.
     * <p/>
     * @param response the response which will have a header added to it. I.e this method changes its parameter
     * from a {@link javax.servlet.RequestDispatcher#include(javax.servlet.ServletRequest, javax.servlet.ServletResponse)}
     * method and the set set header is ignored.
     */
    private static void addGzipHeader(final HttpServletResponse response) {
        response.setHeader("Content-Encoding", "gzip");
    }

}
