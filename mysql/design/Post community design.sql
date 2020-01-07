CREATE TABLE `user` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(255),
  `avatar` varchar(255),
  `description` varchar(255),
  `created_date` date,
  `password` varchar(255),
  `email` varchar(255),
  `wechat_openid` varchar(255),
  `phone_number` varchar(255),
  `role` ENUM ('ADMIN', 'DEVELOPER', 'CONTRIBUTOR', 'NORMAL')
);

CREATE TABLE `topic` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) UNIQUE
);

CREATE TABLE `category` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) UNIQUE
);

CREATE TABLE `post` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(255),
  `content` text,
  `create_datetime` datetime,
  `last_edit_datetime` datetime,
  `user_id` id
);

CREATE TABLE `post_category` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `post_id` int,
  `category_id` int,
  PRIMARY KEY (`post_id`, `category_id`)
);

CREATE TABLE `post_topic` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `post_id` int,
  `topic_id` int,
  PRIMARY KEY (`post_id`, `topic_id`)
);

CREATE TABLE `comment` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `content` text,
  `user_id` int,
  `post_id` int,
  `parent_comment_id` int
);

ALTER TABLE `post` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `comment` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `comment` ADD FOREIGN KEY (`post_id`) REFERENCES `post` (`id`);

ALTER TABLE `comment` ADD FOREIGN KEY (`parent_comment_id`) REFERENCES `comment` (`id`);

ALTER TABLE `post_category` ADD FOREIGN KEY (`post_id`) REFERENCES `post` (`id`);

ALTER TABLE `post_category` ADD FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

ALTER TABLE `post_topic` ADD FOREIGN KEY (`post_id`) REFERENCES `post` (`id`);

ALTER TABLE `post_topic` ADD FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`);

CREATE UNIQUE INDEX `user_index_0` ON `user` (`email`);

CREATE UNIQUE INDEX `user_index_1` ON `user` (`wechat_openid`);

CREATE UNIQUE INDEX `user_index_2` ON `user` (`phone_number`);

CREATE INDEX `post_index_3` ON `post` (`user_id`);

CREATE INDEX `comment_index_4` ON `comment` (`user_id`);

CREATE INDEX `comment_index_5` ON `comment` (`post_id`);

CREATE INDEX `comment_index_6` ON `comment` (`parent_comment_id`);
