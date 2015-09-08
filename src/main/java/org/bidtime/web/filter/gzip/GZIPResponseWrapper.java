package com.utils.filter.gzip1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GZIPResponseWrapper extends HttpServletResponseWrapper implements Serializable {

	private static final long serialVersionUID = -2284995811939858218L;
	private int statusCode = SC_OK;
    private String contentType;
    private final Map<String, List<Serializable>> headersMap = new TreeMap<String, List<Serializable>>(String.CASE_INSENSITIVE_ORDER);
    private final List<Cookie> cookies = new ArrayList<Cookie>();
    private final ServletOutputStream outstr;
    private PrintWriter writer;
    private boolean disableFlushBuffer = true;

    /**
     * Creates a GenericResponseWrapper
     */
    public GZIPResponseWrapper(final HttpServletResponse response, final OutputStream outstr) {
        super(response);
        this.outstr = new GZIPResponseStream(outstr);
    }

    /**
     * Gets the OutputStream.
     */
    public ServletOutputStream getOutputStream() {
        return outstr;
    }

    /**
     * Sets the status code for this response.
     */
    public void setStatus(final int code) {
        statusCode = code;
        super.setStatus(code);
    }

    /**
     * Send the error. If the response is not ok, most of the logic is bypassed and the error is sent raw
     * Also, the content is not cached.
     *
     * @param i      the status code
     * @param string the error message
     * @throws IOException
     */
    public void sendError(int i, String string) throws IOException {
        statusCode = i;
        super.sendError(i, string);
    }

    /**
     * Send the error. If the response is not ok, most of the logic is bypassed and the error is sent raw
     * Also, the content is not cached.
     *
     * @param i the status code
     * @throws IOException
     */
    public void sendError(int i) throws IOException {
        statusCode = i;
        super.sendError(i);
    }

    /**
     * Send the redirect. If the response is not ok, most of the logic is bypassed and the error is sent raw.
     * Also, the content is not cached.
     *
     * @param string the URL to redirect to
     * @throws IOException
     */
    public void sendRedirect(String string) throws IOException {
        statusCode = HttpServletResponse.SC_MOVED_TEMPORARILY;
        super.sendRedirect(string);
    }

    /**
     * Sets the status code for this response.
     */
    public void setStatus(final int code, final String msg) {
        statusCode = code;
        //LOG.warn("Discarding message because this method is deprecated.");
        super.setStatus(code);
    }

    /**
     * Returns the status code for this response.
     */
    public int getStatus() {
        return statusCode;
    }

    /**
     * Sets the content type.
     */
    public void setContentType(final String type) {
        this.contentType = type;
        super.setContentType(type);
    }

    /**
     * Gets the content type.
     */
    public String getContentType() {
        return contentType;
    }


    /**
     * Gets the print writer.
     */
    public PrintWriter getWriter() throws IOException {
        if (writer == null) {
            writer = new PrintWriter(new OutputStreamWriter(outstr, getCharacterEncoding()), true);
        }
        return writer;
    }



    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#addHeader(java.lang.String, java.lang.String)
     */
    @Override
    public void addHeader(String name, String value) {
        List<Serializable> values = this.headersMap.get(name);
        if (values == null) {
            values = new LinkedList<Serializable>();
            this.headersMap.put(name, values);
        }
        values.add(value);

        super.addHeader(name, value);
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#setHeader(java.lang.String, java.lang.String)
     */
    @Override
    public void setHeader(String name, String value) {
        final LinkedList<Serializable> values = new LinkedList<Serializable>();
        values.add(value);
        this.headersMap.put(name, values);

        super.setHeader(name, value);
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#addDateHeader(java.lang.String, long)
     */
    @Override
    public void addDateHeader(String name, long date) {
        List<Serializable> values = this.headersMap.get(name);
        if (values == null) {
            values = new LinkedList<Serializable>();
            this.headersMap.put(name, values);
        }
        values.add(date);

        super.addDateHeader(name, date);
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#setDateHeader(java.lang.String, long)
     */
    @Override
    public void setDateHeader(String name, long date) {
        final LinkedList<Serializable> values = new LinkedList<Serializable>();
        values.add(date);
        this.headersMap.put(name, values);

        super.setDateHeader(name, date);
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#addIntHeader(java.lang.String, int)
     */
    @Override
    public void addIntHeader(String name, int value) {
        List<Serializable> values = this.headersMap.get(name);
        if (values == null) {
            values = new LinkedList<Serializable>();
            this.headersMap.put(name, values);
        }
        values.add(value);

        super.addIntHeader(name, value);
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#setIntHeader(java.lang.String, int)
     */
    @Override
    public void setIntHeader(String name, int value) {
        final LinkedList<Serializable> values = new LinkedList<Serializable>();
        values.add(value);
        this.headersMap.put(name, values);

        super.setIntHeader(name, value);
    }

    /**
     * Adds a cookie.
     */
    public void addCookie(final Cookie cookie) {
        cookies.add(cookie);
        super.addCookie(cookie);
    }

    /**
     * Flushes buffer and commits response to client.
     */
    public void flushBuffer() throws IOException {
        flush();

        // doing this might leads to response already committed exception
        // when the PageInfo has not yet built but the buffer already flushed
        // Happens in Weblogic when a servlet forward to a JSP page and the forward
        // method trigger a flush before it forwarded to the JSP
        // disableFlushBuffer for that purpose is 'true' by default
        // EHC-447
        if (!disableFlushBuffer) {
            super.flushBuffer();
        }
    }

    /**
     * Resets the response.
     */
    public void reset() {
        super.reset();
        cookies.clear();
        headersMap.clear();
        statusCode = SC_OK;
        contentType = null;
    }

    /**
     * Flushes all the streams for this response.
     */
    public void flush() throws IOException {
        if (writer != null) {
            writer.flush();
        }
        outstr.flush();
    }

    /**
     * Set if the wrapped reponse's buffer flushing should be disabled.
     */
    public void setDisableFlushBuffer() {
        this.disableFlushBuffer = true;
    }
}
