<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Inventario - Productos</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<div class="container">

  <!-- Header -->
  <div class="header">
    <div class="brand">
      <a href="${pageContext.request.contextPath}/" class="brand-badge" title="Inicio"></a>
      <h1>Productos</h1>
      <span class="badge">Inventario</span>
    </div>
    <div class="header-actions">
      <a class="btn btn-secondary" href="${pageContext.request.contextPath}/">Inicio</a>
      <a class="btn btn-primary" href="#form-nuevo">Nuevo producto</a>
    </div>
  </div>


  <c:if test="${param.msg == 'creado'}"><div class="alert success">Producto creado correctamente.</div></c:if>
  <c:if test="${param.msg == 'eliminado'}"><div class="alert success">Producto eliminado correctamente.</div></c:if>
  <c:if test="${param.msg == 'actualizado'}"><div class="alert success">Producto actualizado correctamente.</div></c:if>
  <c:if test="${not empty info}"><div class="alert info">${info}</div></c:if>
  <c:if test="${not empty error}"><div class="alert error"><strong>Error:</strong> ${error}</div></c:if>


  <div class="card">
    <div class="card-header">
      <div class="card-title">Listado de productos</div>


      <form method="get" action="${pageContext.request.contextPath}/productos" class="toolbar">
        <input class="input grow" type="text" name="codigo" placeholder="Buscar por código"
               value="${param.codigo != null ? param.codigo : preferenciasBean.filtros['codigo']}" minlength="3">
        <select name="size" class="input">
          <c:set var="sz" value="${size != null ? size : 10}"/>
          <option value="5"  <c:if test="${sz==5}">selected</c:if>>5</option>
          <option value="10" <c:if test="${sz==10}">selected</c:if>>10</option>
          <option value="20" <c:if test="${sz==20}">selected</c:if>>20</option>
        </select>
        <button class="btn btn-primary" type="submit">Buscar</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/productos">Limpiar</a>
      </form>
    </div>

    <div class="card-content">
      <div class="table-wrap">
        <table>
          <thead>
          <tr>
            <th>Código</th>
            <th>Nombre</th>
            <th>Categoría</th>
            <th style="text-align:right;">Precio</th>
            <th style="text-align:right;">Stock</th>
            <th>Activo</th>
            <th style="width:160px;">Acciones</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${lista}" var="p">
            <tr>
              <td>${p.codigo}</td>
              <td>${p.nombre}</td>
              <td>${p.categoria}</td>
              <td style="text-align:right;">${p.precio}</td>
              <td style="text-align:right;">${p.stock}</td>
              <td><c:out value="${p.activo ? 'Sí' : 'No'}"/></td>
              <td class="actions">
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/productos?edit=${p.id}">Editar</a>
                <a class="btn btn-danger"
                   href="${pageContext.request.contextPath}/productos?del=${p.id}"
                   onclick="return confirm('¿Eliminar el producto ${p.codigo}?');">Eliminar</a>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty lista}">
            <tr><td colspan="7" style="text-align:center; color:#7a89a6;">Sin resultados</td></tr>
          </c:if>
          </tbody>
        </table>
      </div>


      <div class="pagination">
        <c:set var="pageNum" value="${page != null ? page : 1}"/>
        <c:set var="pageSize" value="${size != null ? size : 10}"/>
        <a class="page-link"
           href="${pageContext.request.contextPath}/productos?page=${pageNum > 1 ? pageNum-1 : 1}&size=${pageSize}">« Anterior</a>
        <span>Página ${pageNum}</span>
        <a class="page-link"
           href="${pageContext.request.contextPath}/productos?page=${pageNum+1}&size=${pageSize}">Siguiente »</a>
      </div>
    </div>
  </div>


  <div class="card" id="form-nuevo" style="margin-top:18px;">
    <div class="card-header">
      <div class="card-title">
        <c:choose>
          <c:when test="${not empty editar}">Editar producto</c:when>
          <c:otherwise>Agregar producto</c:otherwise>
        </c:choose>
      </div>
    </div>

    <div class="card-content">
      <form method="post" action="${pageContext.request.contextPath}/productos" class="form-grid">
        <c:if test="${not empty editar}">
          <input type="hidden" name="op" value="update"/>
          <input type="hidden" name="id" value="${editar.id}"/>
        </c:if>

        <div class="field">
          <label>Código</label>
          <input class="input" name="codigo" required minlength="3" maxlength="32"
                 value="${not empty editar ? editar.codigo : ''}">
        </div>

        <div class="field">
          <label>Nombre</label>
          <input class="input" name="nombre" required minlength="5" maxlength="120"
                 value="${not empty editar ? editar.nombre : ''}">
        </div>

        <div class="field">
          <label>Categoría</label>
          <select class="input" name="categoria" required>
            <c:forEach items="${categorias}" var="c">
              <option value="${c}" <c:if test="${not empty editar and editar.categoria==c}">selected</c:if>>${c}</option>
            </c:forEach>
          </select>
        </div>

        <div class="field">
          <label>Precio</label>
          <input class="input" name="precio" type="number" step="0.01" min="0.01" required
                 value="${not empty editar ? editar.precio : ''}">
        </div>

        <div class="field">
          <label>Stock</label>
          <input class="input" name="stock" type="number" min="0" required
                 value="${not empty editar ? editar.stock : ''}">
        </div>

        <div class="field" style="align-items:flex-start">
          <label>Activo</label>
          <input style="width:auto; transform:scale(1.2);" name="activo" type="checkbox"
                 <c:if test="${empty editar or editar.activo}">checked</c:if>/>
        </div>

        <div class="form-actions" style="grid-column:1/-1; margin-top:6px;">
          <button class="btn btn-primary" type="submit">
            <c:choose>
              <c:when test="${not empty editar}">Actualizar</c:when>
              <c:otherwise>Guardar</c:otherwise>
            </c:choose>
          </button>
          <c:if test="${not empty editar}">
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/productos">Cancelar</a>
          </c:if>
        </div>
      </form>
    </div>
  </div>

</div>
</body>
</html>



