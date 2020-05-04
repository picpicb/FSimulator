package repositories


import models.Flight
import reactivemongo.api.commands.WriteResult

import scala.concurrent.{ExecutionContext, Future}

trait FlightRepository {

  def findAll(limit: Int)(implicit ec: ExecutionContext): Future[Seq[Flight]]

  def findByFlightNumber(flight_number: String)(implicit ec: ExecutionContext): Future[Option[Flight]]

  def save(flight: Flight)(implicit ec: ExecutionContext): Future[WriteResult]

  def update(flight_number: String, flight: Flight)(implicit ec: ExecutionContext): Future[Option[Flight]]

  def remove(flight_number: String)(implicit ec: ExecutionContext): Future[Option[Flight]]
}
