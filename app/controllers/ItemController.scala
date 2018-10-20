package controllers

import javax.inject._
import play.api.libs.json.Json
import services.ItemRepository
import play.api.mvc._
import formValidations._
import scala.concurrent.{ExecutionContext, Future}

class ItemController @Inject()(repo: ItemRepository,
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  // mapping


  def index = Action { implicit request =>
    Ok("items")
  }

  def addItem(): Action[AnyContent] = Action.async { implicit request =>
    ItemForm.itemForm.bindFromRequest.fold(
      _ => {
        Future.successful(Ok("error creating item"))
      },
      itemFromForm => {
        repo.create(itemFromForm.name, itemFromForm.weight, itemFromForm.size).map { resItem =>
          Ok(Json.toJson(resItem))
        }
      }
    )
  }

  def getItems: Action[AnyContent] = Action.async { implicit request =>
    repo.list().map { items =>
      Ok(Json.toJson(items))
    }
  }

  def getItem(id:Long): Action[AnyContent] = Action.async(implicit request => repo.get(id).map(item => Ok(Json.toJson(item))))

  def deleteItem(id:Long): Action[AnyContent] = Action.async(implicit request => repo.delete(id).map(_ => Ok("borrado: " + id)))
}