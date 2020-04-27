package events

import models.Flight
import net.liftweb.json._
import net.liftweb.json.Serialization.write

case class Event(name: String, flight_id: String, payload: String )
class FlightEvent {


  def createdFlightEvent(flight: Flight): String = {
    implicit val formats = DefaultFormats
    val jsonPayload = write(flight)
    val e = Event("CreatedFlight",flight.flight_number,jsonPayload)
    return write(e)
  }

  def updatedFlightEvent(flight: Flight, context: Flight, command: String): String ={
    implicit val formats = DefaultFormats

    val originalSet = Map("flight_status" -> context.flight_status, "arrival_date" -> context.arrival_date.toString(),"departure_date" -> context.departure_date.toString(),"dest_airport" -> context.dest_airport,"from_airport" -> context.from_airport)
    val modifiedSet = Map("flight_status" -> flight.flight_status, "arrival_date" -> flight.arrival_date.toString(),"departure_date" -> flight.departure_date.toString(),"dest_airport" -> flight.dest_airport,"from_airport" -> flight.from_airport)

    val jsonPayload = write((originalSet.toSet diff modifiedSet.toSet).toMap)
    val e = Event(command,flight.flight_number,jsonPayload)
    return write(e)
  }
}
