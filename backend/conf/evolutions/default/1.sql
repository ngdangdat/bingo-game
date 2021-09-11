# Bingo game schema schema
# --- !Ups

CREATE TABLE IF NOT EXISTS `games` (
    `id` CHAR(36) NOT NULL,
    `currSeq` SMALLINT,
    PRIMARY KEY (`id`));

# --- !Downs
drop table 'games'
