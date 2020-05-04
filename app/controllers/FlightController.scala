package controllers

import commands.{DelayFlightCommand, ScheduleFlightCommand}
import javax.inject.Inject
import models.Flight
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}
import services.FlightServiceImpl

import scala.concurrent.{ExecutionContext, Future}

class FlightController @Inject()(implicit ec: ExecutionContext, cc: ControllerComponents, flightServiceImpl: FlightServiceImpl, delayCommand: DelayFlightCommand, scheduleFlightCommand: ScheduleFlightCommand) extends AbstractController(cc) {

  def index = Action.async {
    flightServiceImpl.findAll().map(flights => Ok(Json.toJson(flights)))
  }

  def read(flight_number: String) = Action.async {
    flightServiceImpl.find(flight_number).map { flight =>
      flight.map { flight =>
        Ok(Json.toJson(flight))
      }.getOrElse(NotFound("NOT_FOUND"))
    }
  }

  def create = Action.async(parse.json) {
    _.body.validate[Flight]
      .map {
        flight =>
          scheduleFlightCommand.execute(flight)
          Future.successful(Ok(Json.toJson(flight)))
      }.getOrElse(Future.successful(BadRequest("INVALID_FORMAT")))
  }

  def update(flight_number: String) = Action.async(parse.json) {
    _.body.validate[Flight].map {
      flight =>
        flight.flight_status match {
          case "DELAYED" => delayCommand.execute(flight)
          //case "BOARDING" =>
          //case "CANCELED" =>
          //case "LANDED" =>
          //case "FLYING" =>
        }
        Future.successful(Ok(Json.toJson(flight)))
    }.getOrElse(Future.successful(BadRequest("INVALID_FORMAT")))
  }

  def remove(flight_number: String) = Action.async {
    flightServiceImpl.remove(flight_number).map {
      case Some(flight) => Ok(Json.toJson(flight))
      case _ => NotFound("NOT_FOUND")
    }
  }
}