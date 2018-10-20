package db

import models.{Item, SendingRequest}
import slick.jdbc.H2Profile.api._
import slick.lifted.Tag

class ItemTable(tag: Tag) extends Table[Item](tag, "items") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def weight = column[Long]("weight")
  def size = column[Long]("size")
  def sendingRequest = foreignKey("sending_request_FK", id, TableQuery[SendingRequest])(_.id)

  def * = (id, name, weight, size) <> ((Item.apply _).tupled, Item.unapply)
}
