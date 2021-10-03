# --- !Ups
CREATE TABLE IF NOT EXISTS `sheets` (
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `sheet_id` SMALLINT NOT NULL,
    `cells_str` TEXT NOT NULL,
    `game_id` CHAR(36) NOT NULL,
    CONSTRAINT fk_sheet_game_id
      FOREIGN KEY (game_id)
      REFERENCES games(id)
) ENGINE = INNODB;

# --- !Downs
drop table 'sheets'
