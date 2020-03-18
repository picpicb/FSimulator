package models

object FlightStatus extends Enumeration {
  type FlightStatus = Value
  val SCHEDULED, CANCELLED, DELAYED,BOARDING, LANDED, FLYING, CREATED = Value
}
