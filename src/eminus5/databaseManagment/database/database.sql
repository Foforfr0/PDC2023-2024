-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Eminus5
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Eminus5
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Eminus5` DEFAULT CHARACTER SET utf8 ;
USE `Eminus5` ;

-- -----------------------------------------------------
-- Table `Eminus5`.`Proyecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Proyecto` (
  `IDProyecto` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `NumIntegrantes` INT NULL,
  `Descripcion` TEXT NOT NULL,
  `FechaInicio` DATE NULL,
  `FechaFin` DATE NULL,
  PRIMARY KEY (`IDProyecto`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Usuario` (
  `IDUsuario` INT NOT NULL AUTO_INCREMENT,
  `Matricula` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `ApellidoPaterno` VARCHAR(45) NOT NULL,
  `ApellidoMaterno` VARCHAR(45) NOT NULL,
  `CorreoPersonal` VARCHAR(45) NULL,
  `CorreoInstitucional` VARCHAR(45) NULL,
  `Semestre` INT NULL,
  `RolSistema` VARCHAR(45) NOT NULL,
  `IDProyecto` INT NULL,
  UNIQUE INDEX `Matricula_UNIQUE` (`Matricula` ASC) VISIBLE,
  UNIQUE INDEX `CorreoPersonal_UNIQUE` (`CorreoPersonal` ASC) VISIBLE,
  UNIQUE INDEX `CorreoInstitucional_UNIQUE` (`CorreoInstitucional` ASC) VISIBLE,
  INDEX `IDProyecto_idx` (`IDProyecto` ASC) VISIBLE,
  PRIMARY KEY (`IDUsuario`),
  CONSTRAINT `IDProyecto_Usuarios`
    FOREIGN KEY (`IDProyecto`)
    REFERENCES `Eminus5`.`Proyecto` (`IDProyecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Bitacora`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Bitacora` (
  `IDBitacora` INT NOT NULL AUTO_INCREMENT,
  `IDDesarrollador` INT NOT NULL,
  PRIMARY KEY (`IDBitacora`),
  INDEX `IDDesarrollador_idx` (`IDDesarrollador` ASC) VISIBLE,
  CONSTRAINT `IDDesarrollador_Bitacoras`
    FOREIGN KEY (`IDDesarrollador`)
    REFERENCES `Eminus5`.`Usuario` (`IDUsuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`TipoActividad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`TipoActividad` (
  `IDTipoActividad` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IDTipoActividad`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Estado` (
  `IDEstado` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IDEstado`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Actividad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Actividad` (
  `IDActividad` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripcion` TEXT NOT NULL,
  `Asignado` VARCHAR(45) NULL,
  `Estado` INT NULL,
  `Tipo` INT NOT NULL,
  `FechaInicio` DATE NULL,
  `FechaFin` DATE NULL,
  `IDProyecto` INT NOT NULL,
  `IDDesarrollador` INT NULL,
  PRIMARY KEY (`IDActividad`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC) VISIBLE,
  INDEX `IDProyecto_idx` (`IDProyecto` ASC) VISIBLE,
  INDEX `IDDesarrollador_idx` (`IDDesarrollador` ASC) VISIBLE,
  INDEX `IDTipoActividad_idx` (`Tipo` ASC) VISIBLE,
  INDEX `IDEstado_idx` (`Estado` ASC) VISIBLE,
  CONSTRAINT `IDProyecto_Actividades`
    FOREIGN KEY (`IDProyecto`)
    REFERENCES `Eminus5`.`Proyecto` (`IDProyecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDDesarrollador_Actividades`
    FOREIGN KEY (`IDDesarrollador`)
    REFERENCES `Eminus5`.`Usuario` (`IDUsuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDTipoActividad_Actividad`
    FOREIGN KEY (`Tipo`)
    REFERENCES `Eminus5`.`TipoActividad` (`IDTipoActividad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDEstado_Actividad`
    FOREIGN KEY (`Estado`)
    REFERENCES `Eminus5`.`Estado` (`IDEstado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Defecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Defecto` (
  `IDDefecto` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  `Descripcion` VARCHAR(45) NULL,
  `Estado` INT NULL,
  `Esfuerzo` INT NULL,
  `FechaEncontrado` DATE NULL,
  `FechaSolucionado` DATE NULL,
  `Tipo` INT NULL,
  `IDActividad` INT NOT NULL,
  PRIMARY KEY (`IDDefecto`),
  INDEX `IDTIpo_idx` (`Tipo` ASC) VISIBLE,
  INDEX `IDEstado_idx` (`Estado` ASC) VISIBLE,
  INDEX `IDActividad_idx` (`IDActividad` ASC) VISIBLE,
  CONSTRAINT `IDTIpo_Defecto`
    FOREIGN KEY (`Tipo`)
    REFERENCES `Eminus5`.`TipoActividad` (`IDTipoActividad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDEstado_Defecto`
    FOREIGN KEY (`Estado`)
    REFERENCES `Eminus5`.`Estado` (`IDEstado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDActividad_Defectos`
    FOREIGN KEY (`IDActividad`)
    REFERENCES `Eminus5`.`Actividad` (`IDActividad`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Cambio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Cambio` (
  `IDCambio` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripcion` VARCHAR(45) NOT NULL,
  `Tipo` INT NOT NULL,
  `IDProyecto` INT NOT NULL,
  `IDDesarrollador` INT NULL,
  `IDDefecto` INT NOT NULL,
  PRIMARY KEY (`IDCambio`),
  INDEX `IDTipo_idx` (`Tipo` ASC) VISIBLE,
  INDEX `IDDesarrollador_idx` (`IDDesarrollador` ASC) VISIBLE,
  INDEX `IDProyecto_idx` (`IDProyecto` ASC) VISIBLE,
  INDEX `IDDefecto_idx` (`IDDefecto` ASC) VISIBLE,
  CONSTRAINT `IDTipo_Cambio`
    FOREIGN KEY (`Tipo`)
    REFERENCES `Eminus5`.`TipoActividad` (`IDTipoActividad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDDesarrollador_Cambio`
    FOREIGN KEY (`IDDesarrollador`)
    REFERENCES `Eminus5`.`Usuario` (`IDUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDProyecto_Cambios`
    FOREIGN KEY (`IDProyecto`)
    REFERENCES `Eminus5`.`Proyecto` (`IDProyecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDDefecto_Cambios`
    FOREIGN KEY (`IDDefecto`)
    REFERENCES `Eminus5`.`Defecto` (`IDDefecto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`SolicitudCambio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`SolicitudCambio` (
  `IDSolicitud` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  `Descripcion` VARCHAR(45) NULL,
  `Razon` VARCHAR(45) NULL,
  `Impacto` VARCHAR(45) NULL,
  `AccionPropuesta` VARCHAR(45) NULL,
  `FechaCreacion` DATE NULL,
  `FechaAceptada` DATE NULL,
  PRIMARY KEY (`IDSolicitud`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

USE Eminus5;
INSERT INTO Proyecto (Nombre, NumIntegrantes, Descripcion, FechaInicio, FechaFin) 
VALUES ('SPGER', 10, 'Descripción de ejemplo del proyecto SPGER', '2023-06-01', '2024-01-01');
SELECT * FROM Proyecto;

INSERT INTO Usuario (Matricula, Password, Nombre, ApellidoPaterno, ApellidoMaterno, CorreoPersonal, CorreoInstitucional, Semestre, RolSistema, IDproyecto) 
VALUES('S21013908', '1234', 'Rodolfo', 'Fernández', 'Rodríguez', 
'foforfr007@gmail.com', 'zs21013908@estudiantes.uv.mx', 6, 'Desarrollador', 1);
INSERT INTO Usuario (Matricula, Password, Nombre, ApellidoPaterno, ApellidoMaterno, CorreoPersonal, CorreoInstitucional, Semestre, RolSistema, IDproyecto) 
VALUES('P21013908', '1234', 'Rodolfo', 'Fernández', 'Rodríguez', 
'foforfr008@gmail.com', 'zp21013908@estudiantes.uv.mx', null, 'Responsable', 1);
SELECT * FROM Usuario;

SELECT u.IDUsuario, u.RolSistema, p.IDProyecto 
FROM Usuario u LEFT JOIN Proyecto p ON u.IDProyecto = p.IDProyecto 
WHERE Matricula = 'P21013908' AND Password = '1234';
SELECT IDUsuario, RolSistema, Proyecto FROM Usuario WHERE Matricula = 'P21013908' AND Password = '1234';