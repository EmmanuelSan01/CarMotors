# 🚗 CarMotors

**CarMotors** es un sistema integral de gestión para talleres automotrices. Desarrollado en Java con Swing y arquitectura MVC, permite gestionar de manera eficiente clientes, vehículos, servicios, técnicos, inventario, proveedores, facturas, campañas promocionales, inspecciones y órdenes de compra.

---

## 📌 Tabla de Contenidos

- [🎯 Objetivo](#-objetivo)
- [🧱 Estructura del Proyecto](#-estructura-del-proyecto)
- [🛠️ Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [📁 Descripción de Paquetes](#-descripción-de-paquetes)
- [📋 Funcionalidades del Sistema](#-funcionalidades-del-sistema)
- [🚀 Cómo Ejecutar el Proyecto](#-cómo-ejecutar-el-proyecto)
- [🖼️ Capturas y Diagramas UML](#-capturas-y-diagramas-uml)
- [📌 Estado del Proyecto](#-estado-del-proyecto)
- [📄 Licencia](#-licencia)
- [👨‍💻 Autor](#-autor)

---

## 🎯 Objetivo

CarMotors busca digitalizar y optimizar la operación diaria de un taller mecánico. Permite tener un control ordenado y centralizado de todas las áreas operativas: atención al cliente, servicios técnicos, logística, inventario y facturación.

---

## 🧱 Estructura del Proyecto

El proyecto está estructurado por capas, bajo el patrón arquitectónico MVC (Modelo-Vista-Controlador):

📦 CarMotors
├── controlador/ → Controladores (conexión lógica entre modelo y vista)
├── dao/ → Acceso a datos y consultas (CRUD)
├── model/ → Entidades del dominio (POJOs)
├── vista/ → Interfaces gráficas (Swing)
└── DatabaseConnection/ → Clase de conexión a la base de datos.


---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 8+
- **Framework de UI:** Java Swing
- **Persistencia:** JDBC
- **Base de Datos:** MySQL (adaptable a SQLite o PostgreSQL)
- **IDE sugerido:** NetBeans
- **Diagramación:** UML

---

## 📁 Descripción de Paquetes

### 1. `model`
Contiene las entidades que representan el dominio del sistema:

- `Cliente`, `Vehiculo`, `Servicio`, `Tecnico`, `Factura`, `Repuesto`, `Proveedor`, `Lote`, `Campana`, `Inspeccion`, `OrdenCompra`, `Order`, `OrderDetail`

### 2. `dao`
Contiene las clases encargadas del acceso a datos:

- Cada entidad tiene su respectiva clase DAO: `ClienteDAO`, `VehiculoDAO`, `ServicioDAO`, etc.
- Realizan operaciones: `insertar`, `listar`, `actualizar`, `eliminar`.

### 3. `controlador`
Define la lógica de negocio y se comunica entre DAO y vistas:

- Ejemplos: `ClientController`, `ServiceController`, `InventoryController`, etc.
- Controla las acciones del usuario en las vistas.

### 4. `vista`
Componentes Swing como `JFrame`, uno por cada módulo:

- `ClientView`, `ServiceView`, `FacturaView`, `InventoryView`, `ProveedorFrame`, `MainFrame`, etc.
- Cada vista implementa funciones como: `mostrarFormulario()`, `refrescarLista()`, `cargarDatos()`.

### 5. `DatabaseConnection`
Clase utilitaria para la conexión a la base de datos usando JDBC.

---

## 📋 Funcionalidades del Sistema

### Clientes y Vehículos
- Registro de clientes
- Asociar múltiples vehículos por cliente
- Edición y eliminación

### Servicios Técnicos
- Ingreso y ejecución de servicios
- Asignación de técnicos
- Seguimiento por estado (pendiente, en progreso, finalizado)

### Técnicos
- Gestión por especialidad
- Asignación a servicios

### Inventario y Repuestos
- Gestión de stock
- Ingreso de repuestos
- Asociación con órdenes y servicios

### Facturación
- Generación de facturas
- Vista previa y cálculos automáticos

### Proveedores
- Alta y detalles de proveedores
- Evaluación por servicio

### Campañas
- Gestión de campañas promocionales

### Inspección de Vehículos
- Registro de estado inicial del vehículo
- Estimación de costos

### Reportes
- Generación de informes por módulo

---

## 🚀 Cómo Ejecutar el Proyecto

### 1. Clonar el repositorio
```bash
git clone https://github.com/tuusuario/CarMotors.git
cd CarMotors
