package com.uis.authorization.exception;

public class ValidationException extends RuntimeException{
    private static final long serialVersionUID = 1312880275761233179L;

    public ValidationException() {
        super("Validación no exitosa");
    }

    public ValidationException(String aMensaje) {
        super(aMensaje);
    }

    public ValidationException(String aMensaje, boolean aPosibilidadContinuar) {
        super(aMensaje, aPosibilidadContinuar ? new Throwable("true") : null);
    }

    public ValidationException(String aMensaje, Throwable aCausa) {
        super(aMensaje, aCausa);
    }

    public ValidationException(String aMensaje, Exception aException) {
        super(aMensaje, aException);
    }

    public ValidationException(String aMensaje, Throwable aCausa, boolean enableSuppression, boolean writableStackTrace) {
        super(aMensaje, aCausa, enableSuppression, writableStackTrace);
    }
}
