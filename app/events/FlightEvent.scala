package events

import models.Flight


abstract class FlightEvent {

  def createdFlightEvent(flight: Flight): Unit ={
  }

  def updatedFlightEvent()
}
