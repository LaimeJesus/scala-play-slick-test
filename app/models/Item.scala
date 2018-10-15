package models

import play.api.libs.json.Json

case class Item (id: Long, name: String, weight: Long, size: Long)

object Item {
  implicit val itemFormat = Json.format[Item]
}