-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server versie:                10.2.9-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Versie:              9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Databasestructuur van persondb wordt geschreven
DROP DATABASE IF EXISTS `persondb`;
CREATE DATABASE IF NOT EXISTS `persondb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `persondb`;

-- Structuur van  tabel persondb.person wordt geschreven
DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `iq` int(11) NOT NULL,
  `metabolism` int(11) NOT NULL,
  `speed` int(11) NOT NULL,
  `stamina` int(11) NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `sprite` int(11) NOT NULL,
  `age` int(11) NOT NULL,
  `hunger` int(11) NOT NULL,
  `tiredness` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumpen data van tabel persondb.person: ~1 rows (ongeveer)
DELETE FROM `person`;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` (`id`, `iq`, `metabolism`, `speed`, `stamina`, `gender`, `sprite`, `age`, `hunger`, `tiredness`) VALUES
	(1, 0, 0, 0, 0, NULL, 0, 0, 0, 0),
	(2, 0, 0, 0, 0, NULL, 0, 0, 0, 0),
	(3, 0, 0, 0, 0, NULL, 0, 0, 0, 0);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
