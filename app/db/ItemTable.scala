package db

import models.{Item, Person}
//import slick.jdbc.MySQLProfile.api._
import slick.jdbc.H2Profile.api._
import slick.lifted.Tag

//case class Item (id: Long, name: String, weight: Long, size: Long)
class ItemTable(tag: Tag) extends Table[Item](tag, "items") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def weight = column[Long]("weight")
  def size = column[Long]("size")

  def * = (id, name, weight, size) <> ((Item.apply _).tupled, Item.unapply)
}
