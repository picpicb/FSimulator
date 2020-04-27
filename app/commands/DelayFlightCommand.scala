package commands


import models.Flight
import events.FlightEvent
import services.FlightServiceImpl

class DelayFlightCommand  extends Command[Flight] {
  val flightEvent = new FlightEvent
  //val flightService = new FlightServiceImpl()

  override def execute(flight: Flight): Flight = {
    val f = flight.setStatus("DELAYED")
    //print(flightEvent.updatedFlightEvent(f))
    return f
  }


}
