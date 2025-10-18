package com.uts.fin.inventario.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

/**
 * Preferencias del usuario (sesión):
 * - Guarda filtros como "codigo" y "size" para recordar la vista
 * - Accesible en JSP como #{preferenciasBean}
 */
@Named("preferenciasBean")
@SessionScoped
public class PreferenciasBean implements Serializable {
    private String idioma = "es";
    private Map<String, Object> filtros = new HashMap<>();

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Map<String, Object> getFiltros() { return filtros; }
    public void setFiltros(Map<String, Object> filtros) { this.filtros = filtros; }

    public void setFiltro(String clave, Object valor) { filtros.put(clave, valor); }
    public Object getFiltro(String clave) { return filtros.get(clave); }
}
