package org.bidtime.web.filter.gzip;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class GZIPResponseStream extends ServletOutputStream {

    private final OutputStream stream;

    /**
     * Creates a FilterServletOutputStream.
     */
    public GZIPResponseStream(final OutputStream stream) {
        this.stream = stream;
    }

    /**
     * Writes to the stream.
     */
    public void write(final int b) throws IOException {
        stream.write(b);
    }

    /**
     * Writes to the stream.
     */
    public void write(final byte[] b) throws IOException {
        stream.write(b);
    }

    /**
     * Writes to the stream.
     */
    public void write(final byte[] b, final int off, final int len) throws IOException {
        stream.write(b, off, len);
    }

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener arg0) {
	}
	
}
