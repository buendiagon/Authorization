package com.uis.authorization.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDTO {
    @JsonProperty("status_code")
    private Integer statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("tipo")
    private String tipoError;
    @JsonProperty("uri")
    private String uriRequested;
    @JsonProperty("posibilidad_continuar")
    private boolean posibilidadContinuar;

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public String getMessage() {
        return this.message;
    }

    public String getTipoError() {
        return this.tipoError;
    }

    public String getUriRequested() {
        return this.uriRequested;
    }

    public boolean isPosibilidadContinuar() {
        return this.posibilidadContinuar;
    }

    public ErrorDTO(final Integer statusCode, final String message, final String tipoError, final String uriRequested, final boolean posibilidadContinuar) {
        this.statusCode = statusCode;
        this.message = message;
        this.tipoError = tipoError;
        this.uriRequested = uriRequested;
        this.posibilidadContinuar = posibilidadContinuar;
    }
}
