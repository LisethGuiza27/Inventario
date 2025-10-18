package com.uts.fin.inventario.Model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entidad de dominio "Producto":
 * - POJO serializable con getters/setters
 * - equals/hashCode por "codigo" (clave de negocio)
 */
public class Producto implements Serializable {
    private Integer id;
    private String codigo;
    private String nombre;
    private String categoria;
    private Double precio;
    private Integer stock;
    private Boolean activo;

    public Producto() {}

    // Constructor completo (incluye id)
    public Producto(Integer id, String codigo, String nombre, String categoria,
                    Double precio, Integer stock, Boolean activo) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.activo = activo;
    }

    // Constructor sin id (para crear)
    public Producto(String codigo, String nombre, String categoria,
                    Double precio, Integer stock, Boolean activo) {
        this(null, codigo, nombre, categoria, precio, stock, activo);
    }

    // Getters y setters (requeridos por JSP/EL y JDBC)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto that = (Producto) o;
        return Objects.equals(codigo, that.codigo);
    }
    @Override public int hashCode() { return Objects.hash(codigo); }
    @Override public String toString() {
        return "Producto{id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + "}";
    }
}

