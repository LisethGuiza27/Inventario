package com.uts.fin.inventario.Controller;

import com.uts.fin.inventario.Facade.ProductoFacade;
import com.uts.fin.inventario.Model.Producto;
import com.uts.fin.inventario.web.MensajeBean;
import com.uts.fin.inventario.web.PreferenciasBean;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Servlet controlador:
 * - GET: Eliminar (si param del), cargar edición (si param edit), listar o buscar
 * - POST: Crear o Actualizar (op=update)
 * - Usa PreferenciasBean para recordar filtros (código/size) entre requests
 * - PRG (Post/Redirect/Get) para no re-enviar formularios
 */
@WebServlet(name="ProductoServlet", urlPatterns={"/productos"})
public class ProductoServlet extends HttpServlet {

    @Inject private ProductoFacade facade;
    @Inject private PreferenciasBean preferencias;
    @Inject private MensajeBean mensajeBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1) Eliminar si viene ?del= (cambia estado -> redirige)
        String delParam = req.getParameter("del");
        if (delParam != null && !delParam.isBlank()) {
            try {
                int delId = Integer.parseInt(delParam);
                facade.eliminar(delId);
                resp.sendRedirect(req.getContextPath() + "/productos?msg=eliminado");
                return; // Importante: cortar el flujo tras redirección
            } catch (NumberFormatException nfe) {
                req.setAttribute("error", "ID inválido para eliminación.");
            } catch (Exception e) {
                req.setAttribute("error", "No se pudo eliminar: " + e.getMessage());
            }
        }

        // 2) Filtros y paginación (usa preferencias de sesión si no hay params)
        String codigo = req.getParameter("codigo");
        if (codigo == null) {
            Object prev = preferencias.getFiltro("codigo");
            codigo = prev != null ? String.valueOf(prev) : null;
        }
        String sizeParam = req.getParameter("size");
        if (sizeParam == null) {
            Object prev = preferencias.getFiltro("size");
            sizeParam = prev != null ? String.valueOf(prev) : "10";
        }
        String pageParam = req.getParameter("page");

        int page = pageParam != null ? Math.max(1, Integer.parseInt(pageParam)) : 1;
        int size = Math.max(1, Integer.parseInt(sizeParam));
        int offset = (page - 1) * size;

        // Guardar preferencias
        preferencias.setFiltro("codigo", codigo);
        preferencias.setFiltro("size", size);

        // 3) Cargar para edición si viene ?edit=
        String editParam = req.getParameter("edit");
        if (editParam != null && !editParam.isBlank()) {
            try {
                int editId = Integer.parseInt(editParam);
                facade.buscarPorId(editId).ifPresent(prod -> req.setAttribute("editar", prod));
            } catch (Exception e) {
                req.setAttribute("error", "No se pudo cargar para edición: " + e.getMessage());
            }
        }

        // 4) Listar o buscar (por código)
        try {
            if (codigo != null && !codigo.isBlank()) {
                Optional<Producto> uno = facade.buscarPorCodigo(codigo.trim());
                if (uno.isPresent()) {
                    req.setAttribute("lista", List.of(uno.get()));
                } else {
                    req.setAttribute("lista", List.of());
                    req.setAttribute("info", "No se encontró el código: " + codigo);
                }
            } else {
                req.setAttribute("lista", facade.listarPaginado(size, offset));
            }
            req.setAttribute("categorias", facade.categoriasDisponibles());
            req.setAttribute("page", page);
            req.setAttribute("size", size);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        // 5) Mensajes (desde la fachada, copiados al request)
        if (mensajeBean.getTextoInfo() != null) req.setAttribute("info", mensajeBean.getTextoInfo());
        if (mensajeBean.getTextoError() != null) req.setAttribute("error", mensajeBean.getTextoError());

        // 6) Forward a la vista JSP
        req.getRequestDispatcher("/productos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ¿Es actualización?
        String op = req.getParameter("op");
        if ("update".equals(op)) {
            try {
                Integer id = Integer.valueOf(req.getParameter("id"));
                String codigo = req.getParameter("codigo");
                String nombre = req.getParameter("nombre");
                String categoria = req.getParameter("categoria");
                Double precio = Double.valueOf(req.getParameter("precio"));
                Integer stock = Integer.valueOf(req.getParameter("stock"));
                Boolean activo = req.getParameter("activo") != null;

                Producto p = new Producto(id, codigo, nombre, categoria, precio, stock, activo);
                facade.actualizar(p);

                // PRG con mensaje
                resp.sendRedirect(req.getContextPath() + "/productos?msg=actualizado");
                return;
            } catch (Exception e) {
                // Mostrar error y volver a GET (mantiene estado)
                req.setAttribute("error", e.getMessage());
                doGet(req, resp);
                return;
            }
        }

        // Si no, crear
        try {
            String codigo = req.getParameter("codigo");
            String nombre = req.getParameter("nombre");
            String categoria = req.getParameter("categoria");
            Double precio = Double.valueOf(req.getParameter("precio"));
            Integer stock = Integer.valueOf(req.getParameter("stock"));
            Boolean activo = req.getParameter("activo") != null;

            Producto p = new Producto(codigo, nombre, categoria, precio, stock, activo);
            facade.crear(p);

            // PRG con mensaje
            resp.sendRedirect(req.getContextPath() + "/productos?msg=creado");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }
}
