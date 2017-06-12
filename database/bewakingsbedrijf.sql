-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 11, 2017 at 09:22 PM
-- Server version: 5.7.18-0ubuntu0.16.04.1
-- PHP Version: 7.0.18-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bewakingsbedrijf`
--

-- --------------------------------------------------------

--
-- Table structure for table `gebruikers`
--

CREATE TABLE `gebruikers` (
  `id` int(11) NOT NULL,
  `achternaam` varchar(255) DEFAULT NULL,
  `voornaam` varchar(255) DEFAULT NULL,
  `adres` varchar(255) DEFAULT NULL,
  `woonplaats` varchar(255) DEFAULT NULL,
  `idnummer` varchar(255) DEFAULT NULL,
  `geslacht` varchar(1) DEFAULT NULL,
  `geboortedatum` date DEFAULT NULL,
  `rol_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `gebruikers`
--

INSERT INTO `gebruikers` (`id`, `achternaam`, `voornaam`, `adres`, `woonplaats`, `idnummer`, `geslacht`, `geboortedatum`, `rol_id`) VALUES
(1, 'Pawirodinomo', 'Mitchel', 'Para', 'Para', 'FO 003090', 'M', '1996-09-01', 1),
(2, 'Amatbrahim', 'Anfernee', 'Para', '', NULL, NULL, NULL, 1),
(3, 'Oldenstam', 'Jonathan', 'Parbo', '', NULL, NULL, NULL, 1),
(4, 'Janna', 'Toeti', 'Para', NULL, NULL, NULL, NULL, NULL),
(5, 'Admin', 'Admin', 'Para', 'Domburg', 'FO 003090', 'M', '1996-09-01', 1);

-- --------------------------------------------------------

--
-- Table structure for table `posten`
--

CREATE TABLE `posten` (
  `id` int(11) NOT NULL,
  `locatie` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `posten`
--

INSERT INTO `posten` (`id`, `locatie`) VALUES
(1, 'Bakkerij'),
(2, 'Poort #3'),
(3, 'Poort #2');

-- --------------------------------------------------------

--
-- Table structure for table `rollen`
--

CREATE TABLE `rollen` (
  `id` int(11) NOT NULL,
  `naam` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rollen`
--

INSERT INTO `rollen` (`id`, `naam`) VALUES
(1, 'Bewaker'),
(2, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `roosters`
--

CREATE TABLE `roosters` (
  `id` int(11) NOT NULL,
  `gebruiker_id` int(11) DEFAULT NULL,
  `post_id` int(11) DEFAULT NULL,
  `shift_id` int(11) DEFAULT NULL,
  `datum` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roosters`
--

INSERT INTO `roosters` (`id`, `gebruiker_id`, `post_id`, `shift_id`, `datum`) VALUES
(12, 1, 1, 1, '2017-01-12'),
(14, 1, 1, 2, '2017-06-06'),
(15, 1, 2, 2, '2017-06-11');

-- --------------------------------------------------------

--
-- Table structure for table `shiften`
--

CREATE TABLE `shiften` (
  `id` int(11) NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  `begintijd` time DEFAULT NULL,
  `eindtijd` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shiften`
--

INSERT INTO `shiften` (`id`, `type`, `begintijd`, `eindtijd`) VALUES
(1, 'Dagshift', '08:00:00', '16:00:00'),
(2, 'Nachtshift', '16:00:00', '24:00:00'),
(3, 'Ochtenshift', '24:00:00', '08:00:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `gebruikers`
--
ALTER TABLE `gebruikers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_rol_id_idx` (`rol_id`);

--
-- Indexes for table `posten`
--
ALTER TABLE `posten`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rollen`
--
ALTER TABLE `rollen`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `roosters`
--
ALTER TABLE `roosters`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_gebruiker_id_idx` (`gebruiker_id`),
  ADD KEY `fk_post_id_idx` (`post_id`),
  ADD KEY `fk_shift_id_idx` (`shift_id`);

--
-- Indexes for table `shiften`
--
ALTER TABLE `shiften`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `gebruikers`
--
ALTER TABLE `gebruikers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `posten`
--
ALTER TABLE `posten`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `rollen`
--
ALTER TABLE `rollen`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `roosters`
--
ALTER TABLE `roosters`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `shiften`
--
ALTER TABLE `shiften`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `gebruikers`
--
ALTER TABLE `gebruikers`
  ADD CONSTRAINT `fk_rol_id` FOREIGN KEY (`rol_id`) REFERENCES `rollen` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `roosters`
--
ALTER TABLE `roosters`
  ADD CONSTRAINT `fk_gebruiker_id` FOREIGN KEY (`gebruiker_id`) REFERENCES `gebruikers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_post_id` FOREIGN KEY (`post_id`) REFERENCES `posten` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_shift_id` FOREIGN KEY (`shift_id`) REFERENCES `shiften` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
