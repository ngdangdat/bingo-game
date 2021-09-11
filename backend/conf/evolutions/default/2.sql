# --- !Ups
CREATE TABLE IF NOT EXISTS `rolls` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `game_id` CHAR(36) NOT NULL,
    `rolls_str` TEXT NOT NULL,
    `is_enabled` TINYINT(4) NULL DEFAULT NULL,
    PRIMARY KEY (`id`));

# --- !Downs
drop table 'rolls'
