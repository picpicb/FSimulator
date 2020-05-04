package services

import commands.{Command, ScheduleFlightCommand}

import javax.inject.Inject
import kafka.KafkaProducerFlight
import models.Flight
import play.api.libs.json._
import reactivemongo.api.commands.WriteResult
import repositories.FlightRepositoryImpl

import scala.concurrent.{ExecutionContext, Future}

class FlightServiceImpl @Inject()(implicit ec: ExecutionContext, flightRepository: FlightRepositoryImpl,scheduleFlightCommand: ScheduleFlightCommand) {

  def findAll(): Future[Seq[Flight]] =
    flightRepository.findAll()

  def find(flight_number: String): Future[Option[Flight]] =
    flightRepository.findByFlightNumber(flight_number = flight_number)

  def save(flight: Flight): Future[WriteResult] = {
    flightRepository.save(flight)
  }

  def update(flight_number: String,flight: Flight): Future[Option[Flight]] =
    flightRepository.update(flight_number = flight_number,flight = flight)

  def remove(flight_number: String):Future[Option[Flight]] =
    flightRepository.remove(flight_number = flight_number)
}
