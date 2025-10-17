package com.uts.fin.inventario.domain;

import jakarta.enterprise.context.Dependent;

@Dependent
public class ValidadorProducto {
    public void validarCodigo(String codigo) throws Exception {
        if (codigo == null || codigo.trim().length() < 3)
            throw new Exception("El código debe tener al menos 3 caracteres");
    }
    public void validarNombre(String nombre) throws Exception {
        if (nombre == null || nombre.trim().length() < 5)
            throw new Exception("El nombre debe tener al menos 5 caracteres");
    }
    public void validarPrecio(Double precio) throws Exception {
        if (precio == null || precio <= 0)
            throw new Exception("El precio debe ser mayor a 0");
    }
    public void validarStock(Integer stock) throws Exception {
        if (stock == null || stock < 0)
            throw new Exception("El stock no puede ser negativo");
    }
}
