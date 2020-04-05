package commands

import models.Flight

class ScheduleFlightCommand extends Command[Flight] {
  override def execute(flight: Flight): Flight = {
    flight.setStatus("SCHEDULED")
  }
}
