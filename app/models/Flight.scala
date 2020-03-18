package models

import java.sql.Date

import models.FlightStatus.FlightStatus

class Flight(
  var flight_number  : String,
  var flight_status  : FlightStatus,
  var from_airport   : String,
  var dest_airport   : String,
  var departure_date : Date,
  var arrival_date   : Date){

  def changeStatus(status: FlightStatus) {
    flight_status = status
  }


}

