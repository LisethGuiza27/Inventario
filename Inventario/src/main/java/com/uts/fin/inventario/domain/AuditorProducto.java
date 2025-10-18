package com.uts.fin.inventario.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.util.logging.Logger;

/**
 * Observa EventoProductoCreado y registra auditoría en el log.
 * Verificar en server.log al crear un producto.
 */
@ApplicationScoped
public class AuditorProducto {
    private static final Logger LOG = Logger.getLogger(AuditorProducto.class.getName());

    public void onCreado(@Observes EventoProductoCreado e) {
        LOG.info(() -> "Auditor: producto creado codigo=" + e.getCodigo()
                + ", precio=" + e.getPrecio());
    }
}
