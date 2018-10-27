package models

import play.api.libs.json.{Json, OFormat}

//id: Long, origin: String, destination: String, items: Seq[Item]
case class RequestWithItems(sendingRequest: SendingRequest, items: Seq[Item])

object RequestWithItems {
  implicit val requestWithItemsFormat: OFormat[RequestWithItems] = Json.format[RequestWithItems]
}