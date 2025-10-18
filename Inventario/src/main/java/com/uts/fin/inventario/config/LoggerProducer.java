package com.uts.fin.inventario.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

import java.util.logging.Logger;

/**
 * Producer CDI de Logger:
 * - Permite @Inject Logger log; en cualquier clase
 * - El nombre del logger es el nombre de la clase donde se inyecta
 */
@ApplicationScoped
public class LoggerProducer {
    @Produces
    public Logger produce(InjectionPoint ip) {
        String name = ip.getMember().getDeclaringClass().getName();
        return Logger.getLogger(name);
    }
}
