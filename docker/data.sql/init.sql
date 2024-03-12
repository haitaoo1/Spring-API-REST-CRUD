-- creating tables
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ;

CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` int NOT NULL,
  `description` varchar(100) NOT NULL,
  `sku` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ;

CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_roles_idx` (`role_id`),
  CONSTRAINT `FK_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FK_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);



-- Initial Roles
INSERT INTO `roles` (`id`, `name`) VALUES ('1', 'ROLE_ADMIN'),
                                            ('2', 'ROLE_USER');

-- Initial Products
INSERT INTO `products` (`id`, `name`, `price`, `description`, `sku`) VALUES ('1', 'Ordenador Asus', '600', '1TB Memoria, P-i7, Nvidia 2060', '1234'),
                                                                                           ('2', 'Smartphone Samsung', '400', '128GB Memoria, 6GB RAM, Pantalla AMOLED', '5678'),
                                                                                           ('3', 'Tablet Lenovo', '300', '64GB Memoria, 4GB RAM, Pantalla HD', '91011'),
                                                                                           ('4', 'Smartwatch Apple', '200', 'Pantalla Retina, GPS Integrado, Resistente al Agua', '121314'),
                                                                                           ('5', 'Cámara Canon', '800', 'Sensor Full Frame, Grabación 4K, Wi-Fi Integrado', '151617');

-- Initial Users
INSERT INTO `users` (`id`, `username`, `password`, `enabled`) VALUES ('1', 'Admin', '12345', '1'),
                                                                                    ('2', 'Juan', '12345', '1'),
                                                                                    ('3', 'Maria', '12345', '1'),
                                                                                    ('4', 'Pedro', '12345', '1');

-- Initial USER_ROLES
INSERT INTO`users_roles` (`user_id`, `role_id`) VALUES ('1', '2'),
                                                                      ('1', '1'),
                                                                      ('2', '2'),
                                                                      ('3', '2'),
                                                                      ('4', '2');
