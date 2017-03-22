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
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '',
  `last_name` VARCHAR(40) NOT NULL COMMENT '',
  `name` VARCHAR(30) NOT NULL COMMENT '',
  `patronymic` VARCHAR(40) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8
COMMENT = 'person data';


-- -----------------------------------------------------
-- Table `faculty`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `faculty`.`user` (
  `person_id` BIGINT(20) NOT NULL COMMENT '',
  `username` VARCHAR(45) NOT NULL COMMENT '',
  `password` VARCHAR(100) NOT NULL COMMENT '',
  `role` ENUM('STUDENT','TEACHER','ADMIN') NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`person_id`)  COMMENT '',
  UNIQUE INDEX `username_UNIQUE` (`username` ASC)  COMMENT '',
  INDEX `fk_user_person_idx` (`person_id` ASC)  COMMENT '',
  CONSTRAINT `fk_user_person`
    FOREIGN KEY (`person_id`)
    REFERENCES `faculty`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `faculty`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `faculty`.`student` (
  `user_person_id` BIGINT(20) NOT NULL COMMENT '',
  `form` ENUM('FULL_TAME','EXTRAMURAL','EVENING','DISTANCE') NULL DEFAULT NULL COMMENT '',
  `contract` ENUM('STATE_ORDER','CONTRACT') NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`user_person_id`)  COMMENT '',
  CONSTRAINT `fk_student_user1`
    FOREIGN KEY (`user_person_id`)
    REFERENCES `faculty`.`user` (`person_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `faculty`.`teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `faculty`.`teacher` (
  `user_person_id` BIGINT(20) NOT NULL COMMENT '',
  `position` ENUM('PROFESSOR','ASSOCIATE_PROFESSOR','ASSISTANT') NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`user_person_id`)  COMMENT '',
  CONSTRAINT `fk_teacher_user1`
    FOREIGN KEY (`user_person_id`)
    REFERENCES `faculty`.`user` (`person_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `faculty`.`subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `faculty`.`subject` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '',
  `title` VARCHAR(150) NOT NULL COMMENT '',
  `teacher_user_person_id` BIGINT(20) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_subject_teacher1_idx` (`teacher_user_person_id` ASC)  COMMENT '',
  CONSTRAINT `fk_subject_teacher1`
    FOREIGN KEY (`teacher_user_person_id`)
    REFERENCES `faculty`.`teacher` (`user_person_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `faculty`.`evaluation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `faculty`.`evaluation` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '',
  `subject_id` BIGINT(20) NOT NULL COMMENT '',
  `student_user_person_id` BIGINT(20) NOT NULL COMMENT '',
  `status` ENUM('DECLARED','CONFIRMED','REJECTED','DELETED') NOT NULL COMMENT '',
  `rating` ENUM('A','B','C','D','E','Fx','F') NULL DEFAULT NULL COMMENT '',
  `feedback` TEXT NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_evaluation_student1_idx` (`student_user_person_id` ASC)  COMMENT '',
  INDEX `fk_evaluation_subject1` (`subject_id` ASC)  COMMENT '',
  CONSTRAINT `fk_evaluation_student1`
    FOREIGN KEY (`student_user_person_id`)
    REFERENCES `faculty`.`student` (`user_person_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_evaluation_subject1`
    FOREIGN KEY (`subject_id`)
    REFERENCES `faculty`.`subject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `faculty`.`period`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `faculty`.`period` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '',
  `status` ENUM('DISTRIBUTION','LEARNING') NULL DEFAULT NULL COMMENT '',
  `date` DATE NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;