<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Inventario - Inicio</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
  <div class="container">
    <div class="header">
      <div class="brand">
        <div class="brand-badge"></div>
        <h1>Inventario</h1>
      </div>
      <div class="header-actions">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/productos">Ir a productos</a>
      </div>
    </div>

    <div class="hero">
      <h2>Control de inventario en tonos azules</h2>
      <p>Administra productos, busca por código y registra nuevas entradas desde una interfaz limpia y rápida.</p>
      <a class="btn btn-primary" href="${pageContext.request.contextPath}/productos">Entrar a productos</a>
    </div>
  </div>
</body>
</html>


