package services

import db.DatabaseSchema
import javax.inject.{Inject, Singleton}
import models.{Item, RequestWithItems, SendingRequest}
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.{ExecutionContext, Future}

//name: String, quantity: Long, weight: Long, size: Long, isFragile: Boolean, refrigerationLevel: String, sendingRequestId: Long
@Singleton
class SendingRequestRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseSchema {

  import slick.jdbc.H2Profile
  private val dbConfig = dbConfigProvider.get[H2Profile]
  import dbConfig._
  import profile.api._

  private def find(id:Long) = sendingRequests.filter(_.id === id)
  private def fetchRequestWithItems() = {
    for {
      (sr, i) <- sendingRequests join items on ((sr, i) => sr.id === i.requestId)
    } yield (sr, i)
  }

  def create(sendingRequest: SendingRequest): Future[Long] = db run {
    (sendingRequests returning sendingRequests.map(_.id) += sendingRequest)
    .map(reqId => {
      reqId
    })
  }

  def createRequestWithItems(sendingRequest: SendingRequest, reqItems: Seq[Item]): Future[Long] = {
    val insertRequest = (sendingRequests returning sendingRequests.map(_.id)) += sendingRequest
    db.run(insertRequest).map(id => {
      val insertions = DBIO.seq(
        items ++= reqItems.map(item => item.copy(requestId = id))
      )
      db.run(insertions)
      id
    })
  }

  def list(): Future[Seq[RequestWithItems]] = {
    var resMap: scala.collection.mutable.Map[Long, RequestWithItems] = scala.collection.mutable.Map[Long, RequestWithItems]()
    val query = fetchRequestWithItems()
    query.result.statements.foreach(println)
    db.run(query.result).map(res => {
      res.map(par => {
          if (!resMap.contains(key = par._1.id)) {
            val toInsert: (Long, RequestWithItems) = (par._1.id, RequestWithItems(par._1, Seq(par._2)))
            resMap = resMap + toInsert
          } else {
            val prevVal = resMap.get(par._1.id).get
            val toInsert = (par._1.id, prevVal.copy(items = prevVal.items :+ par._2))
            resMap = resMap + toInsert
          }
        }
    )}).map(_ => resMap.values.toSeq)
  }

  def get(id : Long): Future[Option[RequestWithItems]] = {
    val query = fetchRequestWithItems().filter(_._1.id === id)
    query.result.statements.foreach(println)
    var resMap: scala.collection.mutable.Map[Long, RequestWithItems] = scala.collection.mutable.Map[Long, RequestWithItems]()
    db.run(query.result).map(res => {
      res.map(par => {
        if (!resMap.contains(key = par._1.id)) {
          val toInsert: (Long, RequestWithItems) = (par._1.id, RequestWithItems(par._1, Seq(par._2)))
          resMap = resMap + toInsert
        } else {
          val prevVal = resMap.get(par._1.id).get
          val toInsert = (par._1.id, prevVal.copy(items = prevVal.items :+ par._2))
          resMap = resMap + toInsert
        }
      }
      )})
    .map(_ => {
        resMap.get(id)
    })
  }

  def delete(id:Long): Future[Int] = db.run(find(id).delete)

}