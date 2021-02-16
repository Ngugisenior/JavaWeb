package org.mik.FirstMaven.export;

public class XMLSerializationException extends RuntimeException {
    public XMLSerializationException() {
        super();
    }

    public XMLSerializationException(String message) {
        super(message);
    }

    public XMLSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLSerializationException(Throwable cause) {
        super(cause);
    }

    protected XMLSerializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
