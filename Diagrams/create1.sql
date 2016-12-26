SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema faculty
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `faculty` DEFAULT CHARACTER SET utf8 ;
USE `faculty` ;

-- -----------------------------------------------------
-- Table `faculty`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `faculty`.`person` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `last_name` VARCHAR(40) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `patronymic` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'person data';

-- -----------------------------------------------------
-- Table `faculty`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `faculty`.`user` (
  `person_id` BIGINT(20) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `role` ENUM('STUDENT', 'TEACHER', 'ADMIN') NOT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `faculty`.`teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `faculty`.`teacher` (
  `user_person_id` BIGINT(20) NOT NULL,
  `position` ENUM('PROFESSOR', 'ASSOCIATE_PROFESSOR', 'ASSISTANT') NOT NULL,
  PRIMARY KEY (`user_person_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `faculty`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `faculty`.`student` (
  `user_person_id` BIGINT(20) NOT NULL,
  `form` ENUM('FULL_TAME', 'EXTRAMURAL', 'EVENING', 'DISTANCE') NOT NULL,
  `contract` ENUM('STATE_ORDER', 'CONTRACT') NOT NULL,
  PRIMARY KEY (`user_person_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `faculty`.`subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `faculty`.`subject` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(150) NOT NULL,
  `teacher_user_person_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `faculty`.`evaluation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `faculty`.`evaluation` (
  `subject_id` BIGINT(20) NOT NULL,
  `student_user_person_id` BIGINT(20) NOT NULL,
  `status` ENUM('DECLARED', 'CONFIRMED', 'REJECTED', 'DELETED') NOT NULL,
  `rating` ENUM('A', 'B', 'C', 'D', 'E', 'Fx', 'F') NOT NULL,
  `feedback` TEXT NULL,
  PRIMARY KEY (`subject_id`, `student_user_person_id`))
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
