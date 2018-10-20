package models

import play.api.libs.json.Json

case class SendingRequest(id: Long, origin: String, destination: String, items: List[Item], isFragile: Boolean, refrigerationLevel: String)
//owner: User, refrigerationLevel: RefrigerationLevel

object SendingRequest {
  implicit val sendinRequestFormat = Json.format[SendingRequest]
}