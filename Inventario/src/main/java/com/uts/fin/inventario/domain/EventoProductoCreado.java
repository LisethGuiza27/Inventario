package com.uts.fin.inventario.domain;

public class EventoProductoCreado {
    private final String codigo;
    private final double precio;

    public EventoProductoCreado(String codigo, double precio) {
        this.codigo = codigo;
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "EventoProductoCreado{codigo='" + codigo + "', precio=" + precio + "}";
    }
}
