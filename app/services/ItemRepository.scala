package services

import db.ItemTable
import javax.inject.{Inject, Singleton}
import models.Item
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ItemRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  private val items = TableQuery[ItemTable]

  import dbConfig._
  import profile.api._

  private def find(id:Long) = items.filter(_.id === id)

  def create(name: String, weight: Long, size: Long): Future[Item] = db run {
    (items.map(item => (item.name, item.weight, item.size))
      returning items.map(_.id)
      into ((tupleValues, id) => Item(id, tupleValues._1, tupleValues._2, tupleValues._3))
      ) += (name, weight, size)
  }

  def list(): Future[Seq[Item]] = db.run(items.result)

  def get(id : Long): Future[Option[Item]] = db.run(find(id).result.headOption)

  def delete(id:Long): Future[Int] = db.run(find(id).delete)

}