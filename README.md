App para registro y control de consumo energético

-- Esquema para crear la base de datos energia_sostenible

CREATE DATABASE IF NOT EXISTS `energia_sostenible`;

USE `energia_sostenible`;

CREATE TABLE `users` ( -- con ésta tabla se almacenan los usuarios el único campo no obligatorio es el nombre
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `energy_device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL, -- This user_id might refer to the users table
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `energy_devices` (
  `device_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  `avg_consumption` decimal(10,4) DEFAULT NULL,
  PRIMARY KEY (`device_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `energy_devices_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `sustainability_goal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `completed` bit(1) NOT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `target_value` double NOT NULL,
  `user_id` bigint(20) DEFAULT NULL, -- This user_id might refer to the users table
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `sustainability_goals` (
  `goal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `target_kwh` decimal(10,4) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `status` varchar(20) DEFAULT 'IN_PROGRESS',
  PRIMARY KEY (`goal_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `sustainability_goals_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `energy_reading` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` double DEFAULT NULL,
  `device_id` bigint(20) DEFAULT NULL,
  `consumption` double NOT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL, -- This user_id might refer to the users table
  PRIMARY KEY (`id`),
  KEY `FK14n4cmanp5us1k09ndbmq431a` (`device_id`),
  CONSTRAINT `FK14n4cmanp5us1k09ndbmq431a` FOREIGN KEY (`device_id`) REFERENCES `energy_device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `energy_readings` (
  `reading_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `device_id` bigint(20) DEFAULT NULL,
  `consumption_kwh` decimal(10,4) NOT NULL,
  `timestamp` timestamp NOT NULL,
  PRIMARY KEY (`reading_id`),
  KEY `timestamp` (`timestamp`),
  KEY `user_id` (`user_id`),
  KEY `FKmvcg815s5t5sy33q933y66cbp` (`device_id`),
  CONSTRAINT `FKmvcg815s5t5sy33q933y66cbp` FOREIGN KEY (`device_id`) REFERENCES `energy_device` (`id`),
  CONSTRAINT `energy_readings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `energy_readings_ibfk_2` FOREIGN KEY (`device_id`) REFERENCES `energy_devices` (`device_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--Insert para poblamiento de algunas tablas
INSERT INTO db_energia_sostenible.sustainability_goal (id, description, name, completed, end_date, start_date, target_value, user_id) VALUES(1, 'casa', NULL, 0, '2025-07-06', '2025-06-06', 120.0, 1);
INSERT INTO db_energia_sostenible.sustainability_goal (id, description, name, completed, end_date, start_date, target_value, user_id) VALUES(3, 'casa', NULL, 1, '2025-05-06', '2025-04-06', 121.0, 1);
INSERT INTO db_energia_sostenible.sustainability_goal (id, description, name, completed, end_date, start_date, target_value, user_id) VALUES(4, 'casa', NULL, 1, '2025-04-06', '2025-03-06', 131.0, 1);
INSERT INTO db_energia_sostenible.sustainability_goal (id, description, name, completed, end_date, start_date, target_value, user_id) VALUES(6, 'casa', NULL, 1, '2025-02-06', '2025-02-06', 120.0, 1);
INSERT INTO db_energia_sostenible.sustainability_goal (id, description, name, completed, end_date, start_date, target_value, user_id) VALUES(10, 'casa', NULL, 0, '2024-07-06', '2024-06-06', 120.0, 1);
INSERT INTO db_energia_sostenible.sustainability_goal (id, description, name, completed, end_date, start_date, target_value, user_id) VALUES(14, 'casa', NULL, 1, '2025-01-06', '2025-01-06', 100.0, 1);
INSERT INTO db_energia_sostenible.sustainability_goal (id, description, name, completed, end_date, start_date, target_value, user_id) VALUES(15, 'casa', NULL, 1, '2024-12-02', '2024-11-06', 150.0, 1);
INSERT INTO db_energia_sostenible.sustainability_goal (id, description, name, completed, end_date, start_date, target_value, user_id) VALUES(18, 'casa', NULL, 1, '2024-06-06', '2024-05-06', 100.0, 1);


INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(1, 1, NULL, 140.0000, '2025-02-02 20:10:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(2, 1, NULL, 160.0000, '2024-07-06 23:12:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(3, 1, NULL, 157.0000, '2025-06-09 00:13:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(4, 1, NULL, 110.0000, '2024-08-09 00:14:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(5, 1, NULL, 135.0000, '2023-05-10 00:15:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(6, 1, NULL, 130.0000, '2023-07-07 00:14:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(65, 1, NULL, 150.5000, '2024-01-15 10:00:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(66, 1, NULL, 120.0000, '2024-02-10 11:30:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(67, 1, NULL, 210.8000, '2024-03-20 09:15:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(68, 1, NULL, 105.2000, '2024-04-05 14:00:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(69, 1, NULL, 180.3000, '2024-05-25 16:45:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(70, 1, NULL, 95.7000, '2024-06-01 08:00:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(71, 1, NULL, 150.5000, '2025-01-15 10:00:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(72, 1, NULL, 120.0000, '2025-02-10 11:30:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(73, 1, NULL, 210.8000, '2025-03-20 09:15:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(74, 1, NULL, 105.2000, '2025-04-05 14:00:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(75, 1, NULL, 180.3000, '2025-05-25 16:45:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(76, 1, NULL, 95.7000, '2025-06-01 08:00:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(77, 1, NULL, 150.5000, '2023-01-15 10:00:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(78, 1, NULL, 120.0000, '2023-03-10 11:30:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(79, 1, NULL, 210.8000, '2023-04-20 09:15:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(80, 1, NULL, 105.2000, '2023-05-05 14:00:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(81, 1, NULL, 180.3000, '2023-07-25 16:45:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(82, 1, NULL, 95.7000, '2023-09-01 08:00:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(83, 1, NULL, 150.5000, '2023-01-15 10:00:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(84, 1, NULL, 120.0000, '2022-03-10 11:30:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(85, 1, NULL, 210.8000, '2023-04-20 09:15:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(86, 1, NULL, 105.2000, '2022-05-05 14:00:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(87, 1, NULL, 180.3000, '2023-07-25 16:45:00');
INSERT INTO db_energia_sostenible.energy_readings (reading_id, user_id, device_id, consumption_kwh, `timestamp`) VALUES(88, 1, NULL, 95.7000, '2023-09-01 08:00:00');


INSERT INTO db_energia_sostenible.energy_device (id, location, name, active, `type`, user_id) VALUES(2, 'cll 25', 'Casa', 1, 'Energía', 1);
INSERT INTO db_energia_sostenible.energy_device (id, location, name, active, `type`, user_id) VALUES(3, 'cll98', 'Empresa', 1, 'Energía', 1);
INSERT INTO db_energia_sostenible.energy_device (id, location, name, active, `type`, user_id) VALUES(4, 'cll25 13', 'Local', 0, 'Energía', 1);
