package controllers

import javax.inject._
import play.api.libs.json.Json
import services.SendingRequestRepository
import play.api.mvc._
import formValidations._
import models.{Item, SendingRequest}

import scala.concurrent.{ExecutionContext, Future}

class SendingRequestController @Inject()(repo: SendingRequestRepository,
                               cc: MessagesControllerComponents
                              )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def index = Action { implicit request =>
    Ok("items")
  }

  def addSendingRequest(): Action[AnyContent] = Action.async { implicit request =>
    SendingRequestForm.sendingRequestForm.bindFromRequest.fold(
      error => {
        Future.successful(Ok(error.errorsAsJson))
      },
      sendingRequestForm => {
        repo.createRequestWithItems(
          SendingRequest(sendingRequestForm.name, sendingRequestForm.origin, sendingRequestForm.origin),
          sendingRequestForm.items.map(i => Item(i.name, i.quantity, i.weight, i.size, i.isFragile, i.refrigerationLevel))
        ).map(res => {
          Ok(Json.toJson(res))
        })
      }
    )
  }

  def getSendingRequests: Action[AnyContent] = Action.async { implicit request =>
    repo.list().map { res =>
      Ok(Json.toJson(res))
    }
  }

  def getSendingRequest(id:Long): Action[AnyContent] = Action.async { implicit request =>
    repo.get(id).map { res =>
      Ok(Json.toJson(res))
    }
  }

  def deleteSendingRequest(id:Long): Action[AnyContent] = Action.async(implicit request => repo.delete(id).map(_ => Ok("borrado: " + id)))
}