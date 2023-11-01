# 表 user
# ------------------------------------------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `enabled`  bit(1) NOT NULL,
    `name`     varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


# 表 role
# ------------------------------------------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `sort`        int    NOT NULL,
    PRIMARY KEY (`id`)
);


# 表 permission
# ------------------------------------------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `sort`        int    NOT NULL,
    PRIMARY KEY (`id`)
);


# 表 user_role
# ------------------------------------------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL,
    KEY       `FKa68196081fvovjhkek5m97n3y` (`role_id`),
    KEY       `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
    CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);


# 表 role_permission
# ------------------------------------------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`
(
    `role_id`       bigint NOT NULL,
    `permission_id` bigint NOT NULL,
    KEY             `FKf8yllw1ecvwqy3ehyxawqa1qp` (`permission_id`),
    KEY             `FKa6jx8n8xkesmjmv6jqug6bg68` (`role_id`),
    CONSTRAINT `FKa6jx8n8xkesmjmv6jqug6bg68` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
    CONSTRAINT `FKf8yllw1ecvwqy3ehyxawqa1qp` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
);
