package services

import com.google.inject.Inject
import models.{BingoGame, BingoGameDAL, BingoGameRoll, BingoGameRollDAL, BingoSheet, BingoSheetDAL}

import java.util.UUID
import scala.concurrent.Future

class BingoService @Inject() (games: BingoGameDAL,
                              rolls: BingoGameRollDAL,
                              sheets: BingoSheetDAL)
{
  // Games
  def addGame(game: BingoGame): Future[String] = {
    games.add(game)
  }

  def getGame(id: UUID): Future[Option[BingoGame]] = {
    games.get(id)
  }

  def updateGameSequence(game: BingoGame): Future[Int] = {
    games.updateSequence(game)
  }

  // Sheets
  def addSheet(sheet: BingoSheet): Future[String] = {
    sheets.add(sheet)
  }

  def addSheets(sheetsToAdd: Seq[BingoSheet]): Future[String] = {
    sheets.addMultiple(sheetsToAdd)
  }

  def getSheet(gameId: UUID, sheetId: Int): Future[Option[BingoSheet]] = {
    sheets.get(gameId, sheetId)
  }

  def getAllSheets(gameId: UUID): Future[Seq[BingoSheet]] = {
    sheets.getAll(gameId)
  }

  // Rolls
  def addRoll(roll: BingoGameRoll): Future[String] = {
    rolls.add(roll)
  }

  def disableAllRolls(gameId: UUID): Future[Int] = {
    rolls.disableAll(gameId)
  }

  def getFirstEnabledRoll(gameId: UUID): Future[Option[BingoGameRoll]] = {
    rolls.getFirstEnabled(gameId)
  }
}
