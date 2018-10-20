package services


import db.{ItemTable, SendingRequestTable}
import javax.inject.{Inject, Singleton}
import models.{Item, SendingRequest}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SendingRequestRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  private val sendingRequests = TableQuery[SendingRequestTable]

  import dbConfig._
  import profile.api._

  private def find(id:Long) = sendingRequests.filter(_.id === id)

  def create(origin: String, destination: String, items: List[Item], isFragile: Boolean, refrigerationLevel: String): Future[SendingRequest] = db run {
    (sendingRequests.map(sendingRequest => (sendingRequest.origin, sendingRequest.destination, sendingRequest.items, sendingRequest.isFragile, sendingRequest.refrigerationLevel))
      returning sendingRequests.map(_.id)
      into ((tupleValues, id) => SendingRequest(id, tupleValues._1, tupleValues._2, tupleValues._3, tupleValues._4, tupleValues._5))
      ) += (origin, destination, items, isFragile, refrigerationLevel)
  }

  def list(): Future[Seq[Item]] = db.run(items.result)

  def get(id : Long): Future[Option[Item]] = db.run(find(id).result.headOption)

  def delete(id:Long): Future[Int] = db.run(find(id).delete)

}