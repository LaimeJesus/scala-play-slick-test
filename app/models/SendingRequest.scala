package models

import play.api.libs.json.{Json, OFormat}

case class SendingRequest(name: String, origin: String, destination: String, id: Long = 0L)

object SendingRequest {
  implicit val sendinRequestFormat: OFormat[SendingRequest] = Json.format[SendingRequest]
}