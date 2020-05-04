package commands


import models.Flight
import events.FlightEvent
import javax.inject.Inject
import kafka.KafkaProducerFlight
import play.api.mvc.ControllerComponents
import services.FlightServiceImpl

import scala.concurrent.{ExecutionContext, Future}

class DelayFlightCommand  @Inject()( implicit ec: ExecutionContext , flightServiceImpl: FlightServiceImpl) extends Command[Flight] {
  val flightEvent = new FlightEvent

  override def execute(modifiedFlight: Flight): Flight = {

    flightServiceImpl.find(modifiedFlight.flight_number).map {
      case Some(flight) => flightServiceImpl.save(modifiedFlight).map {
        _ => KafkaProducerFlight.sendToKafka(flightEvent.updatedFlightEvent(modifiedFlight, flight, "DelayedFlight"))
      }
      case _ => null
    }

    return modifiedFlight
  }


}
