-- 1. Crear la base de datos y usarla
DROP DATABASE IF EXISTS carmotors;
CREATE DATABASE carmotors;

USE carmotors;

-- 2. Proveedores
CREATE TABLE proveedor (
  id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  nit VARCHAR(30) NOT NULL UNIQUE,
  contacto VARCHAR(100),
  frecuencia_visita INT COMMENT 'Días entre visitas típicas'
) ENGINE=InnoDB;

-- 3. Clientes
CREATE TABLE cliente (
  id_cliente INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  identificacion VARCHAR(50) NOT NULL UNIQUE,
  telefono VARCHAR(20),
  correo VARCHAR(100)
) ENGINE=InnoDB;

-- 4. Vehículos
CREATE TABLE vehiculo (
  id_vehiculo INT AUTO_INCREMENT PRIMARY KEY,
  id_cliente INT NOT NULL,
  marca ENUM('Renault', 'Chevrolet', 'Mazda', 'Kia', 'Toyota', 'Nissan', 'Ford', 'Hyundai', 'Suzuki', 'Volkswagen') NOT NULL,
  modelo INT NOT NULL,
  placa VARCHAR(20) NOT NULL,
  tipo ENUM('Automóvil', 'SUV', 'Motocicleta', 'Otro') DEFAULT 'Automóvil',
  FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 5. Técnicos
  CREATE TABLE tecnico (
    id_tecnico INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    especialidad VARCHAR(100),
    telefono VARCHAR(20)
  ) ENGINE=InnoDB;

-- 6. Repuestos e Inventario
CREATE TABLE repuesto (
  id_repuesto INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  tipo ENUM('Mecánico', 'Eléctrico', 'Carrocería', 'Consumo') NOT NULL,
  marca ENUM('Renault', 'Chevrolet', 'Mazda', 'Kia', 'Toyota', 'Nissan', 'Ford', 'Hyundai', 'Suzuki', 'Volkswagen'),
  modelo INT,
  id_proveedor INT,
  cantidad_stock INT DEFAULT 0,
  nivel_minimo INT DEFAULT 0,
  fecha_ingreso DATE,
  vida_util_meses INT COMMENT 'Vida útil estimada en meses',
  estado ENUM('Disponible', 'Reservado para trabajo', 'Fuera de servicio') DEFAULT 'Disponible',
  FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 7. Lotes y trazabilidad
CREATE TABLE lote (
  id_lote INT AUTO_INCREMENT PRIMARY KEY,
  id_repuesto INT NOT NULL,
  fecha_ingreso DATE NOT NULL,
  id_proveedor INT NOT NULL,
  fecha_caducidad DATE,
  FOREIGN KEY (id_repuesto) REFERENCES repuesto(id_repuesto) ON DELETE CASCADE,
  FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 8. Órdenes de compra
CREATE TABLE orden_compra (
  id_orden INT AUTO_INCREMENT PRIMARY KEY,
  id_proveedor INT NOT NULL,
  fecha_orden DATE NOT NULL,
  estado ENUM('Pendiente', 'Enviado', 'Recibido', 'Cancelado') DEFAULT 'Pendiente',
  FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE orden_compra_detalle (
  id_detalle INT AUTO_INCREMENT PRIMARY KEY,
  id_orden INT NOT NULL,
  id_repuesto INT NOT NULL,
  cantidad INT NOT NULL,
  precio_unitario DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (id_orden) REFERENCES orden_compra(id_orden) ON DELETE CASCADE,
  FOREIGN KEY (id_repuesto) REFERENCES repuesto(id_repuesto) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 9. Servicios y uso de repuestos
CREATE TABLE servicio (
  id_servicio INT AUTO_INCREMENT PRIMARY KEY,
  tipo ENUM('Preventivo', 'Correctivo') NOT NULL,  
  id_vehiculo INT NOT NULL,
  id_tecnico INT NOT NULL,
  descripcion TEXT,
  tiempo_estimado DECIMAL(5,2) COMMENT 'Horas',
  costo_manoobra DECIMAL(10,2),
  estado ENUM('pendiente', 'en_proceso', 'completado', 'entregado') DEFAULT 'pendiente',
  fecha_inicio DATE,
  fecha_fin DATE,  
  FOREIGN KEY (id_vehiculo) REFERENCES vehiculo(id_vehiculo) ON DELETE CASCADE,
  FOREIGN KEY (id_tecnico) REFERENCES tecnico(id_tecnico) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE servicio_repuesto (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_servicio INT NOT NULL,
  id_repuesto INT NOT NULL,
  id_lote INT,
  cantidad INT NOT NULL,
  FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio) ON DELETE CASCADE,
  FOREIGN KEY (id_repuesto) REFERENCES repuesto(id_repuesto) ON DELETE CASCADE,
  FOREIGN KEY (id_lote) REFERENCES lote(id_lote) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 10. Facturación electrónica
CREATE TABLE factura (
  id_factura INT AUTO_INCREMENT PRIMARY KEY,
  id_servicio INT NOT NULL,
  numero_factura VARCHAR(50) NOT NULL UNIQUE,
  fecha_emision DATE NOT NULL,
  cufe VARCHAR(64),
  total DECIMAL(12,2) NOT NULL,
  FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE factura_detalle (
  id_detalle INT AUTO_INCREMENT PRIMARY KEY,
  id_factura INT NOT NULL,
  descripcion VARCHAR(255) NOT NULL,
  cantidad INT NOT NULL,
  precio_unitario DECIMAL(10,2) NOT NULL,
  subtotal DECIMAL(12,2) NOT NULL,
  FOREIGN KEY (id_factura) REFERENCES factura(id_factura) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 11. Actividades especiales: campañas e inspecciones
CREATE TABLE campana (
  id_campana INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion TEXT,
  fecha_inicio DATE,
  fecha_fin DATE
) ENGINE=InnoDB;

CREATE TABLE campana_cliente (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_campana INT NOT NULL,
  id_cliente INT NOT NULL,
  fecha_asignacion DATE,
  FOREIGN KEY (id_campana) REFERENCES campana(id_campana) ON DELETE CASCADE,
  FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE inspeccion (
  id_inspeccion INT AUTO_INCREMENT PRIMARY KEY,
  id_servicio INT,
  id_tecnico INT NOT NULL,
  tipo_inspeccion VARCHAR(100),
  fecha_inspeccion DATE,
  resultado ENUM('Aprobado', 'Requiere reparaciones', 'Rechazado') DEFAULT 'Aprobado',
  fecha_proxima DATE,
  FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio) ON DELETE CASCADE,
  FOREIGN KEY (id_tecnico) REFERENCES tecnico(id_tecnico) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE evaluacion_proveedor (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_proveedor INT NOT NULL,
  puntualidad INT CHECK (puntualidad BETWEEN 1 AND 5),
  calidad INT CHECK (calidad BETWEEN 1 AND 5),
  costo INT CHECK (costo BETWEEN 1 AND 5),
  fecha DATE,
  FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE detalle_orden_compra (
  id_detalle INT AUTO_INCREMENT PRIMARY KEY,
  id_orden INT,
  id_repuesto INT,
  cantidad INT,
  FOREIGN KEY (id_orden) REFERENCES orden_compra(id_orden) ON DELETE CASCADE,
  FOREIGN KEY (id_repuesto) REFERENCES repuesto(id_repuesto) ON DELETE CASCADE
);