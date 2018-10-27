package db

import models.{Item, SendingRequest}
import slick.lifted.Tag
import slick.jdbc.H2Profile.api._

trait DatabaseSchema {

  class ItemTable(tag: Tag) extends Table[Item](tag, "items") {
    def id = column[Long]("item_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def quantity = column[Long]("quantity")
    def weight = column[Long]("weight")
    def size = column[Long]("size")
    def isFragile = column[Boolean]("is_fragile")
    def refrigerationLevel = column[String]("refrigeration_level")
    def requestId = column[Long]("request_id")
    def * = (name, quantity, weight, size, isFragile, refrigerationLevel, requestId, id) <> ((Item.apply _).tupled, Item.unapply)

    def sendingRequest = foreignKey("sending_request_FK", requestId, sendingRequests)(_.id, onDelete=ForeignKeyAction.Cascade)
  }

  val items = TableQuery[ItemTable]

  class SendingRequestTable(tag: Tag) extends Table[SendingRequest](tag, "sending_requests") {
    def id = column[Long]("sending_request_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def origin = column[String]("origin")
    def destination = column[String]("destination")

    def * = (name, origin, destination, id) <> ((SendingRequest.apply _).tupled, SendingRequest.unapply)
  }

  val sendingRequests = TableQuery[SendingRequestTable]

  items.schema.create.statements.foreach(println)
  items.schema.drop.statements.foreach(println)

  sendingRequests.schema.create.statements.foreach(println)
  sendingRequests.schema.drop.statements.foreach(println)
}
