package commands


import events.FlightEvent
import javax.inject.Inject
import kafka.KafkaProducerFlight
import models.Flight
import services.FlightServiceImpl

import scala.concurrent.ExecutionContext

class DelayFlightCommand @Inject()(implicit ec: ExecutionContext, flightServiceImpl: FlightServiceImpl) extends Command[Flight] {
  val flightEvent = new FlightEvent

  override def execute(modifiedFlight: Flight): Flight = {

    flightServiceImpl.find(modifiedFlight.flight_number).map {
      case Some(flight) => flightServiceImpl.update(modifiedFlight.flight_number, modifiedFlight).map {
        _ => KafkaProducerFlight.sendToKafka(flightEvent.updatedFlightEvent(modifiedFlight, flight, "DelayedFlight"))
      }
      case _ => null
    }

    return modifiedFlight
  }


}
