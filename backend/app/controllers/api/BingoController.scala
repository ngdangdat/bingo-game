package controllers.api

import helpers.GameHelper
import models.{BingoGame, BingoGameRoll, BingoGameStatusUpdateForm, BingoSheet}
import play.api.mvc.{AbstractController, ControllerComponents}
import services.BingoService
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.ExecutionContext.Implicits.global
import java.util.UUID
import javax.inject.Inject
import scala.concurrent.Future

class BingoController @Inject() (cc: ControllerComponents,
                                 bingoSvc: BingoService) extends AbstractController(cc){

  implicit val bingoSheetFormat: OFormat[BingoSheet] = Json.format[BingoSheet]
  // POST
  def createBingoGame(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val id: UUID = UUID.randomUUID()
    val game: BingoGame = BingoGame(id, -1)
    val sheets: List[BingoSheet] = (1 to 50).toList
      .map(e => (e, GameHelper.generateSheet().mkString(",")))
      .map(z => BingoSheet(0, z._1, id, z._2))
    val roll: BingoGameRoll = BingoGameRoll(
      0, id, GameHelper.generateRoll().mkString(","), isEnabled = true)
    val c: Future[Any] = for {
      c1 <- bingoSvc.addGame(game)
      c2 <- bingoSvc.addSheets(sheets)
      c3 <- bingoSvc.addRoll(roll)
    } yield (c1, c2, c3)
    c.map {_ => {
      Ok(Json.toJson(game)(Json.format[BingoGame]))
    }}
  }

  // GET
  def getGame(uuid: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    bingoSvc.getGame(UUID.fromString(uuid)) map {
      case None => NotFound(Json.obj("message" -> "game not found"))
      case Some(g) => Ok(Json.toJson(g)(Json.format[BingoGame]))
    }
  }

  def updateGameStatus(uuid: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    BingoGameStatusUpdateForm.form.bindFromRequest.fold(
      err => {
        err.errors.foreach(println)
        Future.successful(BadRequest("error"))
      },
      data => {
        bingoSvc.getGame(UUID.fromString(uuid))
          .map(game => {
            if (game.isDefined) {
              for {
                c <- bingoSvc.updateGameSequence(BingoGame(game.get.id, data.currSeq))
              } yield c
              Ok
            } else NotFound
          })
      }
    )
  }

  // GET
  def getRoll(uuid: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    bingoSvc.getFirstEnabledRoll(UUID.fromString(uuid)) map {
      case Some(roll) => Ok(Json.toJson(roll)(Json.format[BingoGameRoll]))
      case None => NotFound(Json.obj("message" -> "game not found"))
    }
  }
  // POST
  def reroll(uuid: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val gameId: UUID = UUID.fromString(uuid)
    val roll: BingoGameRoll = BingoGameRoll(0, gameId, GameHelper.generateRoll().mkString(","), isEnabled = true)
    val gameFuture = for { g <- bingoSvc.getGame(gameId) } yield g
    gameFuture.map { game =>
      if (game.isDefined) {
        for {
          result <- bingoSvc.disableAllRolls(gameId)
          .map(_ => bingoSvc.addRoll(roll))
          .map(_ => bingoSvc.updateGameSequence(BingoGame(gameId, -1)))
        } yield result
        Ok(Json.toJson(roll) (Json.format[BingoGameRoll]))
      } else {
        NotFound(Json.obj("message" -> "game not found"))
      }
    }
  }

  // GET
  def getSheets(uuid: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    bingoSvc.getAllSheets(UUID.fromString(uuid)) map { sheets =>
      if (sheets.nonEmpty) Ok(Json.toJson(sheets))
      else NotFound(Json.obj("message" -> "sheets not found"))
    }
  }

  // GET
  def getSheet(uuid: String, sheetId: Int): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    bingoSvc.getSheet(UUID.fromString(uuid), sheetId) map {
      case Some(sheet) => Ok(Json.toJson(sheet))
      case None => NotFound(Json.obj("message" -> "sheet not found"))
    }
  }

}
