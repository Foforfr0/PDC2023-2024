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
-- Table ``.`Periodo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Periodo` (
  `IdPeriodo` INT NOT NULL AUTO_INCREMENT,
  `Inicio` DATE NOT NULL,
  `Fin` DATE NOT NULL,
  PRIMARY KEY (`IdPeriodo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`ExperienciaEducativa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`ExperienciaEducativa` (
  `IdExperienciaEducativa` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripcion` VARCHAR(45) NOT NULL,
  `idPeriodo` INT NOT NULL,
  PRIMARY KEY (`IdExperienciaEducativa`),
  INDEX `IDExperienciaEducativa_Periodo_idx` (`idPeriodo` ASC) VISIBLE,
  CONSTRAINT `IDExperienciaEducativa_Periodo`
    FOREIGN KEY (`idPeriodo`)
    REFERENCES `Eminus5`.`Periodo` (`IdPeriodo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Proyecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Proyecto` (
  `IdProyecto` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `NumIntegrantes` INT NULL,
  `Descripcion` TEXT NOT NULL,
  `FechaInicio` DATE NULL,
  `FechaFin` DATE NULL,
  `IdExperienciaEducativa` INT NOT NULL,
  PRIMARY KEY (`IdProyecto`),
  INDEX `IDExperienciaEducativa_Proyectos_idx` (`IdExperienciaEducativa` ASC) VISIBLE,
  CONSTRAINT `IDExperienciaEducativa_Proyectos`
    FOREIGN KEY (`IdExperienciaEducativa`)
    REFERENCES `Eminus5`.`ExperienciaEducativa` (`IdExperienciaEducativa`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`RolSistema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`RolSistema` (
  `IdRolSistema` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IdRolSistema`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Usuario` (
  `IDUsuario` INT NOT NULL AUTO_INCREMENT,
  `Usuario` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `ApellidoPaterno` VARCHAR(45) NOT NULL,
  `ApellidoMaterno` VARCHAR(45) NOT NULL,
  `CorreoPersonal` VARCHAR(45) NULL,
  `CorreoInstitucional` VARCHAR(45) NULL,
  `Semestre` INT NULL,
  `IdRolSistema` INT NOT NULL,
  `IdProyecto` INT NULL,
  `IdExperienciaEducativa` INT NOT NULL,
  UNIQUE INDEX `Matricula_UNIQUE` (`Usuario` ASC) VISIBLE,
  UNIQUE INDEX `CorreoPersonal_UNIQUE` (`CorreoPersonal` ASC) VISIBLE,
  UNIQUE INDEX `CorreoInstitucional_UNIQUE` (`CorreoInstitucional` ASC) VISIBLE,
  INDEX `IDProyecto_idx` (`IdProyecto` ASC) VISIBLE,
  PRIMARY KEY (`IDUsuario`),
  INDEX `IDRolSistema_Usuario_idx` (`IdRolSistema` ASC) VISIBLE,
  INDEX `IDExperienciaEducativa_Usuarios_idx` (`IdExperienciaEducativa` ASC) VISIBLE,
  CONSTRAINT `IDProyecto_Usuarios`
    FOREIGN KEY (`IdProyecto`)
    REFERENCES `Eminus5`.`Proyecto` (`IdProyecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDRolSistema_Usuario`
    FOREIGN KEY (`IdRolSistema`)
    REFERENCES `Eminus5`.`RolSistema` (`IdRolSistema`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDExperienciaEducativa_Usuarios`
    FOREIGN KEY (`IdExperienciaEducativa`)
    REFERENCES `Eminus5`.`ExperienciaEducativa` (`IdExperienciaEducativa`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Bitacora`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Bitacora` (
  `IdBitacora` INT NOT NULL AUTO_INCREMENT,
  `NumBitacora` INT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripción` TEXT NOT NULL,
  `IdDesarrollador` INT NOT NULL,
  PRIMARY KEY (`IdBitacora`),
  INDEX `IDDesarrollador_idx` (`IdDesarrollador` ASC) VISIBLE,
  CONSTRAINT `IDDesarrollador_Bitacoras`
    FOREIGN KEY (`IdDesarrollador`)
    REFERENCES `Eminus5`.`Usuario` (`IDUsuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`TipoActividad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`TipoActividad` (
  `IdTipoActividad` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IdTipoActividad`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Estado` (
  `IdEstado` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IdEstado`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Actividad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Actividad` (
  `IdActividad` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripcion` TEXT NOT NULL,
  `Asignado` TINYINT NULL,
  `Estado` INT NULL,
  `Tipo` INT NOT NULL,
  `FechaInicio` DATE NULL,
  `FechaFin` DATE NULL,
  `IdProyecto` INT NOT NULL,
  `IdDesarrollador` INT NULL,
  PRIMARY KEY (`IdActividad`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC) VISIBLE,
  INDEX `IDProyecto_idx` (`IdProyecto` ASC) VISIBLE,
  INDEX `IDDesarrollador_idx` (`IdDesarrollador` ASC) VISIBLE,
  INDEX `IDTipoActividad_idx` (`Tipo` ASC) VISIBLE,
  INDEX `IDEstado_idx` (`Estado` ASC) VISIBLE,
  CONSTRAINT `IDProyecto_Actividades`
    FOREIGN KEY (`IdProyecto`)
    REFERENCES `Eminus5`.`Proyecto` (`IdProyecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDDesarrollador_Actividades`
    FOREIGN KEY (`IdDesarrollador`)
    REFERENCES `Eminus5`.`Usuario` (`IDUsuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDTipoActividad_Actividad`
    FOREIGN KEY (`Tipo`)
    REFERENCES `Eminus5`.`TipoActividad` (`IdTipoActividad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDEstado_Actividad`
    FOREIGN KEY (`Estado`)
    REFERENCES `Eminus5`.`Estado` (`IdEstado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Defecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Defecto` (
  `IdDefecto` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  `Descripcion` VARCHAR(45) NULL,
  `IdEstado` INT NULL,
  `Esfuerzo` INT NULL,
  `FechaEncontrado` DATE NULL,
  `FechaSolucionado` DATE NULL,
  `IdTipo` INT NULL,
  `IdProyecto` INT NOT NULL,
  PRIMARY KEY (`IdDefecto`),
  INDEX `IDTIpo_idx` (`IdTipo` ASC) VISIBLE,
  INDEX `IDEstado_idx` (`IdEstado` ASC) VISIBLE,
  INDEX `IDProyecto_Defectos_idx` (`IdProyecto` ASC) VISIBLE,
  CONSTRAINT `IDTIpo_Defecto`
    FOREIGN KEY (`IdTipo`)
    REFERENCES `Eminus5`.`TipoActividad` (`IdTipoActividad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDEstado_Defecto`
    FOREIGN KEY (`IdEstado`)
    REFERENCES `Eminus5`.`Estado` (`IdEstado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDProyecto_Defectos`
    FOREIGN KEY (`IdProyecto`)
    REFERENCES `Eminus5`.`Proyecto` (`IdProyecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`SolicitudCambio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`SolicitudCambio` (
  `IdSolicitud` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  `Descripcion` VARCHAR(45) NULL,
  `Razon` VARCHAR(45) NULL,
  `Impacto` VARCHAR(45) NULL,
  `AccionPropuesta` VARCHAR(45) NULL,
  `FechaCreacion` DATE NULL,
  `FechaAceptada` DATE NULL,
  `IdDefecto` INT NOT NULL,
  PRIMARY KEY (`IdSolicitud`),
  UNIQUE INDEX `IDDefecto_UNIQUE` (`IdDefecto` ASC) VISIBLE,
  CONSTRAINT `IDDefecto_Solicitud`
    FOREIGN KEY (`IdDefecto`)
    REFERENCES `Eminus5`.`Defecto` (`IdDefecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Cambio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Cambio` (
  `IdCambio` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripcion` VARCHAR(45) NOT NULL,
  `IdDesarrollador` INT NULL,
  `IdSolicitud` INT NULL,
  PRIMARY KEY (`IdCambio`),
  INDEX `IDDesarrollador_idx` (`IdDesarrollador` ASC) VISIBLE,
  UNIQUE INDEX `IDSolicitud_UNIQUE` (`IdSolicitud` ASC) VISIBLE,
  CONSTRAINT `IDDesarrollador_Cambio`
    FOREIGN KEY (`IdDesarrollador`)
    REFERENCES `Eminus5`.`Usuario` (`IDUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDSolicitud_Cambio`
    FOREIGN KEY (`IdSolicitud`)
    REFERENCES `Eminus5`.`SolicitudCambio` (`IdSolicitud`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

/*ROLES SISTEMA--------------------------------------------------------------------------------------------------------------------*/
INSERT INTO RolSistema (Nombre) VALUES ('Responsable');
INSERT INTO RolSistema (Nombre) VALUES ('Desarrollador');
/*ESTADOS--------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Estado (Nombre) VALUES ('Sin asignar');
INSERT INTO Estado (Nombre) VALUES ('Asignado');
INSERT INTO Estado (Nombre) VALUES ('Iniciado');
INSERT INTO Estado (Nombre) VALUES ('Terminado');
INSERT INTO Estado (Nombre) VALUES ('Entregado');
/*TIPOS----------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO TipoActividad (Nombre) VALUES ('Frontend');
INSERT INTO TipoActividad (Nombre) VALUES ('Backend');
INSERT INTO TipoActividad (Nombre) VALUES ('Base de datos');
INSERT INTO TipoActividad (Nombre) VALUES ('Controlador');
INSERT INTO TipoActividad (Nombre) VALUES ('JavaScript');
/*PERIODO-------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Periodo (Inicio, Fin)
VALUES ('2023-06-01', '2024-01-01');
INSERT INTO Periodo (Inicio, Fin)
VALUES ('2024-01-02', '2024-05-30');
/*EXPERIENCIA EDUCATIVA-------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO ExperienciaEducativa (Nombre, Descripcion, idPeriodo)
VALUES ('Proyecto guiado', 'EE proyecto guiado', 1);
INSERT INTO ExperienciaEducativa(Nombre, Descripcion, idPeriodo)
VALUES ('Experiencia recepcional', 'EE experiencia recepcional', 2);
/*PROYECTO-------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Proyecto (Nombre, NumIntegrantes, Descripcion, FechaInicio, FechaFin, idExperienciaEducativa)
VALUES ('SPGER', 10, 'Descripcion de ejemplo del proyecto SPGER', '2023-06-01', '2024-01-01', 1);
/*USUARIO-------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Usuario (Usuario, Password, Nombre, ApellidoPaterno, ApellidoMaterno, CorreoPersonal, CorreoInstitucional, Semestre, IdRolSistema,  IdProyecto, IdExperienciaEducativa)
VALUES ('P12345678', 'PapuPro', 'Rodolfo', 'Fernandez', 'Rodriguez', 'Rodolfo@ejemplo.hotmail.com', 'P12345678@UV.MX', 7, 1, 1, 1);

INSERT INTO Usuario (Usuario, Password, Nombre, ApellidoPaterno, ApellidoMaterno, CorreoPersonal, CorreoInstitucional, Semestre, IdRolSistema,  IdProyecto, IdExperienciaEducativa)
VALUES('S12345678', 'saijiki', 'Abraham', 'Vazquez', 'Quinto', 'Abraham@ejemplo.hotmail.com', 'S12345678@UV.MX', 7, 2, 1, 1);
