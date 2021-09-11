package models

import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._

import java.util.UUID

case class BingoGameRoll(id: Long, gameId: UUID, rollsStr: String, isEnabled: Boolean)

class BingoGameRollTableDef @Inject()(tag: Tag) extends Table[BingoGameRoll](tag, "rolls") {
  def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def gameId: Rep[UUID] = column[UUID]("game_id")

  def rollsStr: Rep[String] = column[String]("rolls_str")

  def isEnabled: Rep[Boolean] = column[Boolean]("is_enabled")

  override def * = (id, gameId, rollsStr, isEnabled) <> (BingoGameRoll.tupled, BingoGameRoll.unapply)
}

class BingoGameRollDAL @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {
  var bingoGameRollDAL = TableQuery[BingoGameRollTableDef]
  def add(roll: BingoGameRoll): Future[String] = {
    dbConfig.db
      .run(bingoGameRollDAL += roll)
      .map(_ => "successfully added roll")
      .recover {
        case ex: Exception => {
          printf(ex.getMessage)
          ex.getMessage
        }
      }
  }

  def disableAll(gameId: UUID): Future[Int] = {
    dbConfig.db
      .run(bingoGameRollDAL.filter(_.gameId === gameId)
        .map(x => x.isEnabled)
        .update(false)
      )
  }

  def getFirstEnabled(gameId: UUID): Future[Option[BingoGameRoll]] = {
    dbConfig.db
      .run(bingoGameRollDAL.filter(e => e.gameId === gameId && e.isEnabled === true).result.headOption)
  }

}
