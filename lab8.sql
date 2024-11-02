-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';



-- -----------------------------------------------------
-- Schema lab8gtics
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `lab8gtics`;
CREATE SCHEMA IF NOT EXISTS `lab8gtics` DEFAULT CHARACTER SET utf8mb3 ;
USE `lab8gtics` ;
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `lab8gtics`.`eventos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lab8gtics`.`eventos` (
  `ideventos` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `fecha` DATE NULL DEFAULT NULL,
  `categoria` VARCHAR(45) NULL DEFAULT NULL,
  `capacidad` INT NULL DEFAULT NULL,
  `reservas` INT NULL DEFAULT NULL,
  PRIMARY KEY (`ideventos`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `lab8gtics`.`reservas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lab8gtics`.`reservas` (
  `idreservas` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `correo` VARCHAR(45) NULL,
  `cupos` VARCHAR(45) NULL,
  `ideventos` INT NOT NULL,
  PRIMARY KEY (`idreservas`),
  INDEX `fk_reservas_eventos_idx` (`ideventos` ASC) VISIBLE,
  CONSTRAINT `fk_reservas_eventos`
    FOREIGN KEY (`ideventos`)
    REFERENCES `lab8gtics`.`eventos` (`ideventos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `lab8gtics`.`eventos` (`nombre`, `fecha`, `categoria`, `capacidad`, `reservas`) 
VALUES 
('Concierto Rock en Lima', '2024-04-15', 'Música', 5000, 3200),
('Comic Con Perú', '2024-05-20', 'Entretenimiento', 2000, 1800),
('Festival Gastronómico', '2024-06-10', 'Gastronomía', 1500, 950),
('Conferencia Tech 2024', '2024-07-01', 'Tecnología', 800, 600),
('Encuentros Cercanos del Tercer Tipo', '2024-08-01', 'Entretenimiento', 800, 600),
('Festival de Teatro', '2024-08-15', 'Arte', 300, 250);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
