-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-06-2019 a las 10:52:57
-- Versión del servidor: 10.1.9-MariaDB
-- Versión de PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `planetario`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `planeta`
--

CREATE TABLE `planeta` (
  `nombre` varchar(50) NOT NULL,
  `nucleo` varchar(50) NOT NULL,
  `diametro` int(50) NOT NULL,
  `satelites` int(50) NOT NULL,
  `id_sistema` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `planeta`
--

INSERT INTO `planeta` (`nombre`, `nucleo`, `diametro`, `satelites`, `id_sistema`) VALUES
('Jupiter', 'silicio', 6000, 12, 0),
('Marte', 'tierra', 300, 50, 0),
('Planus', 'tierra', 5000, 1, 1),
('Plutona', 'rocoso', 600, 1, 2),
('Saturno', 'silicio', 5, 5, 0),
('Terraria', 'lava', 2650, 2, 2),
('Tierra', 'lava', 255, 4000, 0),
('Varus', 'desc', 0, 0, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sistema`
--

CREATE TABLE `sistema` (
  `id` int(50) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `sistema`
--

INSERT INTO `sistema` (`id`, `nombre`) VALUES
(0, 'Sistema solar'),
(1, 'Sistema lunar'),
(2, 'Sistema estelar'),
(3, 'Sistema ocaso');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `planeta`
--
ALTER TABLE `planeta`
  ADD PRIMARY KEY (`nombre`),
  ADD KEY `id_sistema` (`id_sistema`);

--
-- Indices de la tabla `sistema`
--
ALTER TABLE `sistema`
  ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
