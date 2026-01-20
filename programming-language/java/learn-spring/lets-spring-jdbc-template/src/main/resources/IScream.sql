CREATE TABLE `ingredient`
(
    `id`              integer        NOT NULL auto_increment,
    `ingredient`      varchar(100)   NULL,
    `ingredient_type` varchar(50)    NOT NULL,
    `unit_price`      decimal(19, 4) NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `ingredient_type`
(
    `ingredient_type` varchar(50) NOT NULL,
    PRIMARY KEY (`ingredient_type`)
);
CREATE TABLE `purchase`
(
    `id`               integer        NOT NULL auto_increment,
    `create_timestamp` datetime       NOT NULL default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
    `total_price`      decimal(19, 4) NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `purchase_line_item`
(
    `id`            integer NOT NULL auto_increment,
    `purchase_id`   integer NULL,
    `ingredient_id` integer NULL,
    `units`         integer NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `ingredient`
    ADD CONSTRAINT `fk_ingredient_type` FOREIGN KEY (`ingredient_type`) REFERENCES `ingredient_type` (`ingredient_type`);
ALTER TABLE `purchase_line_item`
    ADD CONSTRAINT `fk_ingredient_id` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`id`);
ALTER TABLE `purchase_line_item`
    ADD CONSTRAINT `fk_purchase_id` FOREIGN KEY (`purchase_id`) REFERENCES `purchase` (`id`);

insert into `ingredient_type`
values ('ICE_CREAM');
insert into `ingredient_type`
values ('TOPPING');
insert into `ingredient`(`ingredient`, `ingredient_type`, `unit_price`)
values ('ICE_CREAM_1', 'ICE_CREAM', 2.50);
insert into `ingredient`(`ingredient`, `ingredient_type`, `unit_price`)
values ('ICE_CREAM_2', 'ICE_CREAM', 1.50);
insert into `ingredient`(`ingredient`, `ingredient_type`, `unit_price`)
values ('TOPPING_1', 'TOPPING', 2.00);
insert into `ingredient`(`ingredient`, `ingredient_type`, `unit_price`)
values ('TOPPING_2', 'TOPPING', 4.50);

