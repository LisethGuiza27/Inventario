package com.uts.fin.inventario.domain;

/**
 * Evento CDI: se dispara al crear un producto.
 * Nota: clase normal (no record) para compatibilidad Java 11.
 */
public class EventoProductoCreado {
    private final String codigo;
    private final double precio;

    public EventoProductoCreado(String codigo, double precio) {
        this.codigo = codigo;
        this.precio = precio;
    }

    public String getCodigo() { return codigo; }
    public double getPrecio() { return precio; }

    @Override
    public String toString() {
        return "EventoProductoCreado{codigo='"+codigo+"', precio="+precio+"}";
    }
}
