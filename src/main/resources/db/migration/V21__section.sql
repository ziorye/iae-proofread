CREATE TABLE `section`
(
    `id`                bigint unsigned NOT NULL AUTO_INCREMENT,
    `slug`              varchar(255)          DEFAULT NULL,
    `title`             varchar(255) NOT NULL,
    `title_translation` varchar(255)          DEFAULT NULL,
    `description`       varchar(512)          DEFAULT NULL,
    `sort_order`        int          NOT NULL DEFAULT '0',
    `collection_id`     bigint unsigned NOT NULL,
    `created_at`        timestamp NULL DEFAULT NULL,
    `updated_at`        timestamp NULL DEFAULT NULL,
    `deleted_at`        timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `sections_slug_unique` (`slug`)
);


# --- data ---
INSERT INTO `section` (`slug`, `title`, `title_translation`, `description`, `sort_order`, `collection_id`, `created_at`, `updated_at`, `deleted_at`)
VALUES
	(NULL, 'Docker 初体验', NULL, '', 0, 1, now(), NULL, NULL),
	(NULL, '创建、更新、分享 Docker 镜像', NULL, '', 0, 1, now(), NULL, NULL),
	(NULL, '进一步了解 Docker', NULL, '', 0, 1, now(), NULL, NULL),
	(NULL, '小结', NULL, '', 0, 1, now(), NULL, NULL);