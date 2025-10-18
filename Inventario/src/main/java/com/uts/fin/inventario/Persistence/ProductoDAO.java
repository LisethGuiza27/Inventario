package com.uts.fin.inventario.Persistence;

import com.uts.fin.inventario.Model.Producto;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * DAO: Acceso a datos con JDBC puro.
 * - Inyecta DataSource (producido por DataSourceProducer)
 * - Cada método abre y cierra su conexión (try-with-resources)
 */
@Dependent
public class ProductoDAO {

    @Inject
    private DataSource ds; // Resuelto vía JNDI -> Producer CDI

    /** Lista todos los productos ordenados por nombre */
    public List<Producto> listar() throws SQLException {
        String sql = "SELECT id,codigo,nombre,categoria,precio,stock,activo FROM productos ORDER BY nombre";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Producto> out = new ArrayList<>();
            while (rs.next()) out.add(mapRow(rs));
            return out;
        }
    }

    /** Lista paginado (LIMIT/OFFSET) */
    public List<Producto> listarPaginado(int limit, int offset) throws SQLException {
        String sql = "SELECT id,codigo,nombre,categoria,precio,stock,activo FROM productos ORDER BY nombre LIMIT ? OFFSET ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Math.max(1, limit));
            ps.setInt(2, Math.max(0, offset));
            try (ResultSet rs = ps.executeQuery()) {
                List<Producto> out = new ArrayList<>();
                while (rs.next()) out.add(mapRow(rs));
                return out;
            }
        }
    }

    /** Busca por id */
    public Optional<Producto> buscarPorId(int id) throws SQLException {
        String sql = "SELECT id,codigo,nombre,categoria,precio,stock,activo FROM productos WHERE id=?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }
        }
    }

    /** Busca por código */
    public Optional<Producto> buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT id,codigo,nombre,categoria,precio,stock,activo FROM productos WHERE codigo=?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }
        }
    }

    /** Inserta y devuelve el id generado */
    public void insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO productos (codigo,nombre,categoria,precio,stock,activo) VALUES (?,?,?,?,?,?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getCategoria());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setBoolean(6, Boolean.TRUE.equals(p.getActivo()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getInt(1));
            }
        }
    }

    /** Actualiza por id */
    public void actualizar(Producto p) throws SQLException {
        String sql = "UPDATE productos SET codigo=?, nombre=?, categoria=?, precio=?, stock=?, activo=? WHERE id=?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getCategoria());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setBoolean(6, Boolean.TRUE.equals(p.getActivo()));
            ps.setInt(7, p.getId());
            ps.executeUpdate();
        }
    }

    /** Elimina por id */
    public void eliminarPorId(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id=?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /** Convierte la fila actual del ResultSet en un Producto */
    private Producto mapRow(ResultSet rs) throws SQLException {
        return new Producto(
            rs.getInt("id"),
            rs.getString("codigo"),
            rs.getString("nombre"),
            rs.getString("categoria"),
            rs.getDouble("precio"),
            rs.getInt("stock"),
            rs.getBoolean("activo")
        );
    }
}
