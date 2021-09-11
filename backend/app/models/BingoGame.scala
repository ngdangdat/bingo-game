package models

import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._

import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._

import java.util.UUID

case class BingoGame(id: UUID, currSeq: Int)
case class BingoGameStatusUpdateFormData(currSeq: Int)

object BingoGameStatusUpdateForm {
  val form: Form[BingoGameStatusUpdateFormData] = Form(
    mapping(
      "currSeq" -> number,
    )(BingoGameStatusUpdateFormData.apply)(BingoGameStatusUpdateFormData.unapply)
  )
}

class BingoGameTableDef(tag: Tag) extends Table[BingoGame](tag, "games") {
  def id: Rep[UUID] = column[UUID]("id", O.PrimaryKey)
  def currSeq: Rep[Int] = column[Int]("currSeq")

  override def * = (id, currSeq) <> (BingoGame.tupled, BingoGame.unapply)
}

class BingoGameDAL @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit executionContext: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile] {
  var bingoGameDAL = TableQuery[BingoGameTableDef]
  def add(game: BingoGame): Future[String] = {
    dbConfig.db
      .run(bingoGameDAL += game)
      .map(_ => "successfully added game")
      .recover {
        case ex: Exception => {
          printf(ex.getMessage)
          ex.getMessage
        }
      }
  }

  def get(id: UUID): Future[Option[BingoGame]] = {
    dbConfig.db
      .run(bingoGameDAL.filter(_.id === id).result.headOption)
  }

  def updateSequence(game: BingoGame): Future[Int] = {
    dbConfig.db
      .run(bingoGameDAL.filter(_.id === game.id)
        .map(x => x.currSeq)
        .update(game.currSeq)
      )
  }
}
