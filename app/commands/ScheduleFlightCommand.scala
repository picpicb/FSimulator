package commands

import models.Flight
import events.FlightEvent
class ScheduleFlightCommand extends Command[Flight] {
  val flightEvent = new FlightEvent
  override def execute(flight: Flight): Flight = {
    val f = flight.setStatus("SCHEDULED")
    print(flightEvent.createdFlightEvent(f))
    return f
  }
}
