package com.uts.fin.inventario.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.annotation.Resource;
import javax.sql.DataSource;

/**
 * Producer CDI para exponer el DataSource:
 * - @Resource usa el JNDI creado en GlassFish (jdbc/inventarioPool)
 * - @Produces lo hace inyectable con @Inject DataSource
 */
@ApplicationScoped
public class DataSourceProducer {

    @Resource(lookup = "jdbc/inventarioPool")
    private DataSource ds;

    @Produces
    public DataSource produceDataSource() {
        return ds;
    }
}
