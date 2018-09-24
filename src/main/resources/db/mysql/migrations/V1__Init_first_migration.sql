ALTER DATABASE CHARACTER SET utf8 COLLATE utf8_general_ci;
SET collation_connection     = utf8_general_ci;
SET character_set_results    = utf8;
SET character_set_connection = utf8;
SET character_set_client     = utf8;

CREATE TABLE IF NOT EXISTS `app_user` (
  `id` varchar(255) NOT NULL,
  `confirmation_token` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1j9d9a06i600gd43uu3km82jw` (`email`)
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `role` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `task_category` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `app_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoa2tr0u8wb5rx7bbcka1msmr1` (`app_user_id`),
  CONSTRAINT `FKoa2tr0u8wb5rx7bbcka1msmr1` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `task` (
  `id` varchar(255) NOT NULL,
  `actual_end_time` datetime NOT NULL,
  `actual_start_time` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `planned_end_time` datetime NOT NULL,
  `planned_start_time` datetime NOT NULL,
  `task_comment` text,
  `task_importance` varchar(255) NOT NULL,
  `task_status` varchar(255) NOT NULL,
  `app_user_id` varchar(255) NOT NULL,
  `task_category_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhlnuoprbtqmjwm2a3se5km00i` (`app_user_id`),
  KEY `FK8nbmbrk24s1dkt7h3fsfl6mg8` (`task_category_id`),
  CONSTRAINT `FK8nbmbrk24s1dkt7h3fsfl6mg8` FOREIGN KEY (`task_category_id`) REFERENCES `task_category` (`id`),
  CONSTRAINT `FKhlnuoprbtqmjwm2a3se5km00i` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKg7fr1r7o0fkk41nfhnjdyqn7b` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
