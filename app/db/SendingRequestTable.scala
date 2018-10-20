package db

import models.{Item, SendingRequest}
import slick.jdbc.H2Profile.api._
import slick.lifted.Tag

//SendingRequest(id: Long, origin: String, destination: String, items: List[Item], isFragile: Boolean, refrigerationLevel: String)
class SendingRequestTable(tag: Tag) extends Table[SendingRequest](tag, "sendingRequests") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def origin = column[String]("origin")
  def destination = column[String]("destination")
  def isFragile = column[Boolean]("isFragile")
  def items = column[List[Item]]("items")
  def refrigerationLevel = column[String]("refrigerationLevel")

  def * = (id, origin, destination, isFragile, items, refrigerationLevel) <> ((SendingRequest.apply _).tupled, SendingRequest.unapply)
}
