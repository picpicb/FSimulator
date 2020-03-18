package commands

import models.{Flight, FlightStatus}

class ScheduleFlightCommand extends Command[Flight]{
  override def execute(t: Flight) : Flight = {
    t.flight_status = FlightStatus.SCHEDULED
    return t
  }

}
