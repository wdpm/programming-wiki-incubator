CREATE DATABASE IF NOT EXISTS `mario_sample` DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user`
(
    `id`       int(10)     NOT NULL AUTO_INCREMENT,
    `name`     varchar(50) NOT NULL,
    `age`      tinyint(4)  NOT NULL,
    `birthday` datetime    NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;


insert into `t_user`(`id`, `name`, `age`, `birthday`)
values (1, 'jack', 20, '1990-07-01 15:51:06'),
       (2, 'rose', 18, '1993-12-26 15:51:21');
