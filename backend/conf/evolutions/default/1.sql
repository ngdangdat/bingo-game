# Bingo game schema schema
# --- !Ups

CREATE TABLE IF NOT EXISTS `games` (
    `id` CHAR(36) NOT NULL PRIMARY KEY,
    `currSeq` SMALLINT
);

# --- !Downs
drop table 'games'
