# 表 user
# ------------------------------------------------------------
INSERT INTO `user` (`id`, `name`, `password`, `enabled`)
VALUES
	(1,'admin','$2a$10$X/uMNuiis.fyO47cxbta3OSs2sllSeLcwVfC0.ghyxeVVZRmAbzk2',b'1'),
	(2,'user','$2a$10$X/uMNuiis.fyO47cxbta3OSs2sllSeLcwVfC0.ghyxeVVZRmAbzk2',b'1');


# 表 role
# ------------------------------------------------------------
INSERT INTO `role` (`id`, `name`, `description`, `sort`)
VALUES
	(1,'admin','管理员',0),
	(2,'user','普通用户',0);


# 表 permission
# ------------------------------------------------------------
INSERT INTO `permission` (`id`, `name`, `description`, `sort`)
VALUES
	(1,'Dashboard','/backend/dashboard',0),
	(2,'Empty','/backend/empty',0);


# 表 user_role
# ------------------------------------------------------------
INSERT INTO `user_role` (`role_id`, `user_id`)
VALUES
	(1,1),
	(2,2);


# 表 role_permission
# ------------------------------------------------------------
INSERT INTO `role_permission` (`role_id`, `permission_id`)
VALUES
	(1,1),
	(1,2);
