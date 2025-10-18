# Inventario
Inventario de productos

Liseth Xiomara Guiza Gonzalez
Sebastian Arley Jacome Orduz

## Taller #0
CRUD mínimo de productos con arquitectura en capas (Modelo, DAO, Fachada, Servlet/JSP).

## Investigación 01
- ¿Qué problema resuelve un Pool JDBC y por qué es crítico en producción?
- Mantiene un conjunto de conexiones abiertas y reutilizables hacia la base de datos, evitando crear y cerrar una conexión por cada petición.
Esto reduce drásticamente el consumo de recursos y el tiempo de respuesta,
- ¿Por qué separar DAO y Fachada mejora el mantenimiento y las pruebas?
- Permite aislar el acceso a datos de la lógica empresarial, cambios en SQL no afectan reglas de negocio, reutilización del DAO
- Defina responsabilidades de:
- Servlet: Controla el flujo entre vista y negocio; recibe peticiones, invoca la fachada y reenvía resultados al JSP.
- JSP: Presenta la información al usuario final usando etiquetas y datos del request (sin lógica de negocio).
- DAO: Ejecuta operaciones CRUD directas sobre la base de datos mediante JDBC.
- Fachada: Contiene la lógica de negocio, valida reglas y orquesta los DAO antes de llegar al controlador.

## Investigación 02
-¿Qué es un Managed Bean y en qué se diferencia de un JavaBean?
Managed Bean es una clase Java gestionada por el contenedor de una aplicación Jakarta EE que permite inyección de dependencias, control de ciclo de vida. JavaBean es simplemente una clase Java con atributos privados, constructores públicos, métodos get/set y sin lógica especial.
-¿Para qué sirven @Named, @ApplicationScoped, @RequestScoped, @SessionScoped y @Dependent?
-@Named:Expone el bean al lenguaje de expresiones (EL) para que pueda usarse en JSP, JSF o CDI con #{nombreBean}. Ejemplo: @Named("productoFacade") permite acceder como #{productoFacade}.
-@ApplicationScoped:El bean se crea una sola vez y se comparte entre todos los usuarios y sesiones. Ideal para servicios, fachadas o recursos únicos.
-@RequestScoped:El bean vive mientras dure la sesión del usuario. Guarda información personalizada, como preferencias o autenticación.
-@SessionScoped:Se crea y destruye en cada solicitud. Perfecto para manejar formularios, mensajes o respuestas de un único request.
-@Dependent:Es el scope por defecto; el ciclo de vida del bean depende completamente del componente que lo inyecta. Útil para validadores, DAO u objetos pequeños sin estado.

## GlassFish
   - Agregar `mysql-connector-j` a `glassfish/lib`.
   - Crear Pool `InventarioPool` y Recurso JNDI `jdbc/inventarioPool`.
   - Prueba del pool e hizo “Ping succeeded”

## NetBeans
   - Crear proyecto Web Java con GlassFish y Java EE 8.
   - Estructura de paquetes sugerida:

com.uts.fin.inventario.Controller     (servlets y filtros)
com.uts.fin.inventario.Facade         (reglas de negocio, fachada)
com.uts.fin.inventario.Model          (entidades/POJOs)
com.uts.fin.inventario.Persistence    (acceso a datos, JDBC/DAO)

src/main/webapp
- index.jsp (página de entrada muy simple).
- productos.jsp (vista base para validar JSP/JSTL y despliegue).




























