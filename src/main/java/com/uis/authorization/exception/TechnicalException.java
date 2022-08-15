package com.uis.authorization.exception;

public class TechnicalException extends RuntimeException{
    private static final long serialVersionUID = 7627891784764517269L;

    public TechnicalException() {
        super("Problemas técnicos en el servidor");
    }

    public TechnicalException(String aMensaje) {
        super(aMensaje);
    }

    public TechnicalException(String aMensaje, boolean aPosibilidadContinuar) {
        super(aMensaje, aPosibilidadContinuar ? new Throwable("true") : null);
    }

    public TechnicalException(String aMensaje, Throwable aCausa) {
        super(aMensaje, aCausa);
    }

    public TechnicalException(String aMensaje, Exception aException) {
        super(aMensaje, aException);
    }

    public TechnicalException(String aMensaje, Throwable aCausa, boolean enableSuppression, boolean writableStackTrace) {
        super(aMensaje, aCausa, enableSuppression, writableStackTrace);
    }
}
