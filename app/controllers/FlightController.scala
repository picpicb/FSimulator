package controllers

import java.util.Date

import javax.inject.{Inject, Singleton}
import models.{Flight, FlightStatus}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class FlightController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def create = TODO

  def index = Action {
    val flight = new Flight("1","AIR52",FlightStatus.CREATED.toString,"CDG","JFK", new Date(), new Date())
    Ok(Json.toJson(flight))
  }

  def read(id: String) = TODO
}