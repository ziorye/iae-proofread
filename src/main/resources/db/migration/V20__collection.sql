# --- schema ---
CREATE TABLE `collection`
(
    `id`                bigint unsigned NOT NULL AUTO_INCREMENT,
    `title`             varchar(255) NOT NULL,
    `title_translation` varchar(255)   DEFAULT NULL COMMENT '翻译后的标题 [proofread-specific]',
    `slug`              varchar(255)   DEFAULT NULL,
    `type`              char(20) NOT NULL DEFAULT 'doc' COMMENT 'doc|video',
    `content`           text,
    `video`             varchar(255)   DEFAULT NULL COMMENT 'video link',
    `duration`          int unsigned NOT NULL DEFAULT '0',
    `cover`             varchar(255)   DEFAULT NULL,
    `description`       varchar(512)   DEFAULT NULL,
    `published`      tinyint(1) NOT NULL DEFAULT '1',
    `free`           tinyint(1) NOT NULL DEFAULT '0',
    `price`             decimal(10, 2) DEFAULT NULL,
    `view_count`        int unsigned NOT NULL DEFAULT '0',
    `seo_title`         varchar(255)   DEFAULT NULL,
    `seo_description`   varchar(255)   DEFAULT NULL,
    `completed`      tinyint(1) NOT NULL DEFAULT '1',
    `proofread`        tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已经校对过 [proofread-specific]',
    `user_id`           bigint unsigned NOT NULL,
    `created_at`        timestamp NULL DEFAULT NULL,
    `updated_at`        timestamp NULL DEFAULT NULL,
    `deleted_at`        timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `collection_slug_unique` (`slug`)
);


# --- data ---
INSERT INTO `collection` (`title`, `title_translation`, `slug`, `type`, `content`, `video`, `duration`, `cover`, `description`, `published`, `free`, `price`, `view_count`, `seo_title`, `seo_description`, `completed`, `proofread`, `user_id`, `created_at`, `updated_at`, `deleted_at`)
VALUES
	('Docker 快速入门文档', NULL, 'docker-get-started__doc', 'doc', NULL, NULL, 0, 'https://bianxuebianzuo.oss-cn-shenzhen.aliyuncs.com/uploads/cover/1/xQkug4WdwX1628302147.png', 'Docker 官方入门文档不完全翻译。介绍了什么是容器、什么是镜像、以及如何创建镜像、运行容器、构建镜像最佳实践等 Docker 基础知识。', 1, 1, 0.00, 0, NULL, NULL, 1, 0, 1, now(), NULL, NULL);
