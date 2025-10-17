package com.uts.fin.inventario.web;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("mensajeBean")
@RequestScoped
public class MensajeBean {
    private String textoError;
    private String textoInfo;

    public String getTextoError() { return textoError; }
    public void setTextoError(String textoError) { this.textoError = textoError; }
    public String getTextoInfo() { return textoInfo; }
    public void setTextoInfo(String textoInfo) { this.textoInfo = textoInfo; }

    public void limpiar() { this.textoError = null; this.textoInfo = null; }
}
