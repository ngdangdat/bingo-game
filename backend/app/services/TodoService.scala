package services

import com.google.inject.Inject
import models.{Todo, TodoList}

import scala.concurrent.Future

class TodoService @Inject() (items: TodoList){
  def addItem(item: Todo): Future[String] = {
    items.add(item)
  }

  def deleteItem(id: Long): Future[Int] = {
    items.delete(id)
  }

  def getItem(id: Long): Future[Option[Todo]] = {
    items.get(id)
  }

  def updateItem(item: Todo): Future[Int] = {
    items.update(item)
  }

  def listAll(): Future[Seq[Todo]] = items.listAll()
}
