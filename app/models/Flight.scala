package models

import java.util.Date

import reactivemongo.bson.BSONObjectID
import models.FlightStatus.FlightStatus
import play.api.libs.json.Json

case class Flight(
                   var _id : String = BSONObjectID.generate.toString(),
                   var flight_number  : String,
                   var flight_status  : String,
                   var from_airport   : String,
                   var dest_airport   : String,
                   var departure_date : Date,
                   var arrival_date   : Date
                 )

  object Flight {
    implicit val flightFormat = Json.format[Flight]
  }

//  def changeStatus(status: FlightStatus) {
//  flight_status = status
//}

