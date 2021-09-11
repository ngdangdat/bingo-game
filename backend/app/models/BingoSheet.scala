package models

import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._

import java.util.UUID

case class BingoSheet(id: Long, sheetId: Int, gameId: UUID, cellsStr: String)

class BingoSheetTableDef(tag: Tag) extends Table[BingoSheet](tag, "sheets") {
  def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def sheetId: Rep[Int] = column[Int]("sheet_id")
  def gameId: Rep[UUID] = column[UUID]("game_id")
  def cellsStr: Rep[String] = column[String]("cells_str")

  override def * = (id, sheetId, gameId, cellsStr) <> (BingoSheet.tupled, BingoSheet.unapply)
}

class BingoSheetDAL @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {
  var bingoSheetDAL = TableQuery[BingoSheetTableDef]

  def add(sheet: BingoSheet): Future[String] = {
    dbConfig.db
      .run(bingoSheetDAL += sheet)
      .map(_ => "successfully added sheet")
      .recover {
        case ex: Exception => {
          printf(ex.getMessage)
          ex.getMessage
        }
      }
  }

  def addMultiple(sheets: Seq[BingoSheet]): Future[String] = {
    dbConfig.db
      .run(bingoSheetDAL ++= sheets)
      .map(_ => "successfully added sheets")
      .recover {
        case ex: Exception => {
          printf(ex.getMessage)
          ex.getMessage
        }
      }
  }

  def get(gameId: UUID, sheetId: Int): Future[Option[BingoSheet]] = {
    dbConfig.db
      .run(bingoSheetDAL.filter(e => (e.gameId === gameId && e.sheetId === sheetId)).result.headOption)
  }

  def getAll(gameId: UUID): Future[Seq[BingoSheet]] = {
    dbConfig.db.run(bingoSheetDAL.filter(_.gameId === gameId).result)
  }
}
