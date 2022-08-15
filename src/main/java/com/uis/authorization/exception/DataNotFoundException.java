package com.uis.authorization.exception;

public class DataNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -3058744460908898864L;

    public DataNotFoundException() {
        super("No se encontró el registro en la base de datos");
    }

    public DataNotFoundException(String aMensaje) {
        super(aMensaje);
    }

    public DataNotFoundException(String aMensaje, boolean aPosibilidadContinuar) {
        super(aMensaje, aPosibilidadContinuar ? new Throwable("true") : null);
    }

    public DataNotFoundException(String aMensaje, Throwable aCausa) {
        super(aMensaje, aCausa);
    }

    public DataNotFoundException(String aMensaje, Exception aException) {
        super(aMensaje, aException);
    }

    public DataNotFoundException(String aMensaje, Throwable aCausa, boolean enableSuppression, boolean writableStackTrace) {
        super(aMensaje, aCausa, enableSuppression, writableStackTrace);
    }
}
