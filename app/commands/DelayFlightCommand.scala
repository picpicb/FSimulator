package commands


import models.Flight
import events.FlightEvent
import services.FlightServiceImpl

class DelayFlightCommand  extends Command[Flight] {
  val flightEvent = new FlightEvent
  val flightService = new FlightServiceImpl()

  override def execute(context: Flight, flight: Flight): Flight = {
    val f = flight.setStatus("DELAYED")
    val contextFlight =
    print(flightEvent.updatedFlightEvent(f))
    return f
  }
}
