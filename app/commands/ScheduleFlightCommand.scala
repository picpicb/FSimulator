package commands

import models.Flight
import events.FlightEvent
import javax.inject.Inject
import kafka.KafkaProducerFlight
import services.FlightServiceImpl

import scala.concurrent.ExecutionContext
class ScheduleFlightCommand @Inject()( implicit ec: ExecutionContext , flightServiceImpl: FlightServiceImpl) extends Command[Flight] {
  val flightEvent = new FlightEvent
  override def execute(flight: Flight): Flight = {

    val f = flight.setStatus("SCHEDULED")
    flightServiceImpl.save(f).map {
      _ => KafkaProducerFlight.sendToKafka(flightEvent.createdFlightEvent(f))
    }
    return f
  }
}
