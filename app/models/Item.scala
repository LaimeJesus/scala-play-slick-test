package models

import play.api.libs.json.{Json, OFormat}

case class Item (name: String, quantity: Long, weight: Long, size: Long, isFragile: Boolean, refrigerationLevel: String, requestId: Long = 0L, id: Long = 0L)

object Item {
  implicit val itemFormat: OFormat[Item] = Json.format[Item]
}
