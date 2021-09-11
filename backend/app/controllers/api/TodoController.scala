package controllers.api

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.{Todo, TodoForm}

import services.TodoService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TodoController @Inject()(cc: ControllerComponents,
                               todoSvc: TodoService,
                              ) extends AbstractController(cc){
  implicit val todoFormat: OFormat[Todo] = Json.format[Todo]
  def getAll: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    todoSvc.listAll map { items => Ok(Json.toJson(items)) }
  }

  def getById(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    todoSvc.getItem(id) map { item => Ok(Json.toJson(item)) }
  }

  def add(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    TodoForm.form.bindFromRequest.fold(
      err => {
        err.errors.foreach(println)
        Future.successful(BadRequest("error"))
      },
      data => {
        val item = Todo(0, data.name, data.isComplete)
        todoSvc.addItem(item).map(_ => Redirect(routes.TodoController.getAll))
      }
    )
  }

  def update(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    TodoForm.form.bindFromRequest.fold(
      err => {
        err.errors.foreach(println)
        Future.successful(BadRequest("error"))
      },
      data => {
        val item: Todo = Todo(id, data.name, data.isComplete)
        todoSvc.updateItem(item).map(_ => Redirect(routes.TodoController.getAll))
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    todoSvc.deleteItem(id).map(_ => Redirect(routes.TodoController.getAll))
  }
}
