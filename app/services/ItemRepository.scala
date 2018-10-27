package services

import db.DatabaseSchema
import javax.inject.{Inject, Singleton}
import models.Item
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ItemRepository @Inject() (dbConfigProvider: DatabaseConfigProvider) (implicit ec: ExecutionContext) extends DatabaseSchema {

  import slick.jdbc.H2Profile
  private val dbConfig = dbConfigProvider.get[H2Profile]
  import dbConfig._
  import profile.api._

  private def find(id:Long) = items.filter(_.id === id)

  def create(item: Item): Future[Long] = db run {
    (items returning items.map(_.id) += item).map(itemId => itemId)
  }

  def list(): Future[Seq[Item]] = db.run(items.result)

  def get(id : Long): Future[Option[Item]] = db.run(find(id).result.headOption)

  def delete(id:Long): Future[Int] = db.run(find(id).delete)

}