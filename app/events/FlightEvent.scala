package events

import models.Flight
import net.liftweb.json._
import net.liftweb.json.Serialization.write

class FlightEvent {

  case class Event(name: String, flight_id: String, payload: String )

  def createdFlightEvent(flight: Flight): String = {
    implicit val formats = DefaultFormats
    val jsonPayload = write(flight)
    val e = Event("CreatedFlight",flight.flight_number,jsonPayload)
    return write(e)
  }

  def updatedFlightEvent(flight: Flight, context: Flight): String ={
    implicit val formats = DefaultFormats
    val originalSet = Set(context.flight_status, context.arrival_date, context.departure_date, context.dest_airport, context.from_airport)
    val modifiedSet = Set(flight.flight_status, flight.arrival_date, flight.departure_date, flight.dest_airport, flight.from_airport)

    val diffSet = originalSet.diff(modifiedSet)
    for(elem <- diffSet) print(elem)
    //val jsonPayload = write(flight)
    //val e = Event("CreatedFlight",flight.flight_number,jsonPayload)
    return "toto"
  }
}
