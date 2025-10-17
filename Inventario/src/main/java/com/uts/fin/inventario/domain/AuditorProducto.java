package com.uts.fin.inventario.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.util.logging.Logger;

@ApplicationScoped
public class AuditorProducto {
    private static final Logger LOG = Logger.getLogger(AuditorProducto.class.getName());

    public void onCreado(@Observes EventoProductoCreado e) {
        LOG.info(() -> "Auditor: producto creado codigo=" 
                + e.getCodigo() + ", precio=" + e.getPrecio());
    }
}
