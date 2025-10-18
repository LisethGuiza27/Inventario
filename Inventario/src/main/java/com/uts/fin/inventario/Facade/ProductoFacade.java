package com.uts.fin.inventario.Facade;

import com.uts.fin.inventario.Model.Producto;
import com.uts.fin.inventario.Persistence.ProductoDAO;
import com.uts.fin.inventario.domain.EventoProductoCreado;
import com.uts.fin.inventario.domain.ValidadorProducto;
import com.uts.fin.inventario.web.MensajeBean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Fachada (servicio de negocio) para Productos:
 * - Valida reglas
 * - Orquesta el DAO
 * - Publica mensajes a la vista (MensajeBean)
 * - Dispara un evento CDI al crear
 */
@Named("productoFacade")
@ApplicationScoped
public class ProductoFacade {

    @Inject private ValidadorProducto validador;     // Reglas atómicas
    @Inject private MensajeBean mensaje;             // Mensajes a JSP (request)
    @Inject private ProductoDAO dao;                 // Acceso a datos
    @Inject private Event<EventoProductoCreado> evt; // Evento CDI (auditoría)

    // Catálogo de categorías válidas
    private static final Set<String> CATEGORIAS =
            Set.of("Electronicos","Accesorios","Muebles","Ropa");

    /** Exponer categorías a la vista */
    public Set<String> categoriasDisponibles(){ return CATEGORIAS; }

    /** Reglas de validación de negocio */
    private void validar(Producto p) throws Exception {
        if (p == null) throw new Exception("Producto nulo");
        validador.validarCodigo(p.getCodigo());
        validador.validarNombre(p.getNombre());
        if (!CATEGORIAS.contains(p.getCategoria()))
            throw new Exception("Categoría inválida ("+p.getCategoria()+")");
        validador.validarPrecio(p.getPrecio());
        validador.validarStock(p.getStock());
    }

    // Casos de uso (delegan en el DAO)
    public List<Producto> listar() throws Exception { return dao.listar(); }
    public List<Producto> listarPaginado(int limit,int offset) throws Exception { return dao.listarPaginado(limit,offset); }
    public Optional<Producto> buscarPorCodigo(String codigo) throws Exception { return dao.buscarPorCodigo(codigo); }
    public Optional<Producto> buscarPorId(int id) throws Exception { return dao.buscarPorId(id); }

    /** Crea validando y dispara evento CDI */
    public void crear(Producto p) throws Exception {
        validar(p);
        if (dao.buscarPorCodigo(p.getCodigo()).isPresent())
            throw new Exception("Ya existe un producto con código: " + p.getCodigo());
        dao.insertar(p);

        // Mensaje para la vista
        if (mensaje != null) mensaje.setTextoInfo("Producto creado: " + p.getCodigo());

        // Evento CDI (para auditoría/logs)
        if (evt != null) evt.fire(new EventoProductoCreado(p.getCodigo(), p.getPrecio()));
    }

    /** Actualiza validando */
    public void actualizar(Producto p) throws Exception {
        if (p.getId()==null) throw new Exception("ID requerido para actualizar");
        validar(p);
        dao.actualizar(p);
        if (mensaje != null) mensaje.setTextoInfo("Producto actualizado: " + p.getCodigo());
    }

    /** Elimina por id */
    public void eliminar(int id) throws Exception {
        dao.eliminarPorId(id);
        if (mensaje != null) mensaje.setTextoInfo("Producto eliminado (ID=" + id + ")");
    }
}
