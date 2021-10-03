# --- !Ups
CREATE TABLE IF NOT EXISTS `rolls` (
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `rolls_str` TEXT NOT NULL,
    `is_enabled` TINYINT(4) NULL DEFAULT NULL,
    `game_id` CHAR(36) NOT NULL,
    CONSTRAINT fk_roll_game_id
      FOREIGN KEY (game_id)
      REFERENCES games(id)
) ENGINE = INNODB;

# --- !Downs
drop table 'rolls'
