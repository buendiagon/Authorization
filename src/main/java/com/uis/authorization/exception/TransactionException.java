package com.uis.authorization.exception;

public class TransactionException extends RuntimeException{
    private static final long serialVersionUID = 453162048279590402L;

    public TransactionException() {
        super("Transacción no exitosa");
    }

    public TransactionException(String aMensaje) {
        super(aMensaje);
    }

    public TransactionException(String aMensaje, boolean aPosibilidadContinuar) {
        super(aMensaje, aPosibilidadContinuar ? new Throwable("true") : null);
    }

    public TransactionException(String aMensaje, Throwable aCausa) {
        super(aMensaje, aCausa);
    }

    public TransactionException(String aMensaje, Exception aException) {
        super(aMensaje, aException);
    }

    public TransactionException(String aMensaje, Throwable aCausa, boolean enableSuppression, boolean writableStackTrace) {
        super(aMensaje, aCausa, enableSuppression, writableStackTrace);
    }
}
