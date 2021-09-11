# --- !Ups
CREATE TABLE IF NOT EXISTS `sheets` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `sheet_id` SMALLINT NOT NULL,
    `game_id` CHAR(36) NOT NULL,
    `cells_str` TEXT NOT NULL,
    PRIMARY KEY (`id`));

# --- !Downs
drop table 'sheets'
