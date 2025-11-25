# Repositorio para las prácticas de AADD 25/26

Integrantes:
  - Manuel Chica Piñera
  - Emilio González Fernández-Piqueras

# Trabajo
La segunda parte del proyecto consiste en la implementación de la capa de presentación haciendo uso del framework JSF, vistas Facelets y la biblioteca de componentes PrimeFaces. Las interfaces desarrolladas deben permitir a un usuario realizar la siguientes acciones:

## Historias de usuario
- Acceso a la aplicación: el usuario debe poder iniciar sesión usando sus credenciales (email y clave). Esta interfaz requiere crear una nueva funcionalidad en ServicioUsuarios que reciba el email y la clave, y retorne los datos del usuario si existe.
- Cerrar sesión: si un usuario ha iniciado sesión, debe poder cerrarla.
- Crear un producto: un usuario autenticado debe poder crear un producto para ponerlo a la venta. La interfaz le debe permitir introducir todos los datos necesarios para crear el producto.
- Ver productos creados: un usuario autenticado debe poder consultar un listado de los productos que ha puesto a la venta. Este listado deberá mostrar al menos título, precio, estado, fecha de publicación, categoría y número de visualizaciones. Esta interfaz requiere crear una nueva funcionalidad en ServicioProductos que reciba el identificador de un vendedor y devuelva sus productos puestos a la venta.
- Editar producto: un usuario autenticado debe poder modificar precio y/o descripción de sus productos a la venta.
- Buscador de productos: un usuario debe poder buscar productos disponibles filtrando por categoría, descripción, estado y precio máximo. El listado resultante debe mostrar, al menos, el título, precio, estado y categoría de cada producto.
- Ver detalle de un producto: un usuario autenticado debe poder consultar el detalle de un producto a la venta. En el detalle podrá ver la ficha del producto donde aparecerá título, descripción, precio, estado, fecha de publicación, categoría, disponibilidad de envío, descripción del lugar de recogida (si existe) y nombre del vendedor. Acceder a esta vista incrementará en uno el número de visualizaciones del producto.

El número de vistas .xhtml no necesariamente debe coincidir con el número de funcionalidades aquí descritas. Algunas pueden implementarse conjuntamente en una misma vista o integrarse en menús o cabeceras compartidas.

## Requisitos de diseño
En todo el trabajo se deberán seguir los principios de diseño vistos en la asignatura.

Todas las vistas harán uso de la librería de componentes PrimeFaces y de las librerías de componentes de JSF.

Se valorará la amigabilidad de la interfaz, el aprovechamiento de los componentes y funcionalidades de JSF y PrimeFaces, y las posibilidades de navegación implementadas.

La capa de presentación desarrollada en este entregable debe apoyarse en los servicios implementados en el entregable anterior.

## Documentación
El alumno deberá entrega un breve manual de usuario que describa el uso de las vistas implementadas. Este manual debe ser lo más breve posible y puede ir acompañado de capturas de pantalla. De forma adicional, el alumno puede usar este documento para comentar cualquier decisión de diseño tomada o aspecto que considere relevante de su trabajo. Es importante que este documento sea breve, claro y preciso, además de en perfecto castellano.

Entrega del trabajo
Se trabajará sobre el mismo proyecto utilizado en la entrega anterior.

La entrega del trabajo será realizada en un repositorio Git en GitHub y en una tarea de Aula Virtual. En el caso de la documentación, es suficiente con subirla a la tarea del Aula Virtual.

El repositorio Git debe ser utilizado desde el comienzo del desarrollo del proyecto y con la participación de los integrantes del grupo. No será aceptada una entrega que simplemente aloje el código del proyecto sin el historial de trabajo (commits) previo.

Los proyectos Java Maven deben estar configurados con todas las dependencias necesarias.

Fecha de entrega
10 de diciembre de 2025.
