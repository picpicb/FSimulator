package models

import java.util.UUID

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import reactivemongo.play.json._
import play.api.libs.json._
import play.api.libs.json.JodaReads
import play.api.libs.json.JodaWrites
import reactivemongo.bson.BSONObjectID
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Writes}

case class Flight(
                   _id: Option[BSONObjectID],
                   flight_number: String,
                   flight_status: String,
                   from_airport: String,
                   dest_airport: String,
                   departure_date: DateTime,
                   arrival_date: DateTime
                 ){

  def setStatus(status: String) = new Flight(_id,flight_number,status,from_airport,dest_airport,departure_date,arrival_date)
}

object Flight {

  val jodaDateReads: Reads[DateTime] = Reads[DateTime](js =>
    js.validate[String].map[DateTime](dtString =>
      DateTime.parse(dtString, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")) /**2020-04-01T05:06:07.000+02:00**/
    )
  )

  val jodaDateWrites: Writes[DateTime] = new Writes[DateTime] {
    def writes(dateTime: DateTime): JsValue = JsString(dateTime.toString())
  }

  val flightWrites: Writes[Flight] = (
    (JsPath \ "_id").writeNullable[BSONObjectID] and
      (JsPath \ "flight_number").write[String] and
      (JsPath \ "flight_status").write[String] and
      (JsPath \ "from_airport").write[String] and
      (JsPath \ "dest_airport").write[String] and
      (JsPath \ "departure_date").write[DateTime](jodaDateWrites) and
      (JsPath \ "arrival_date").write[DateTime](jodaDateWrites)
    )(unlift(Flight.unapply))

  implicit val flightReads: Reads[Flight] = (
    (JsPath \ "_id").readNullable[BSONObjectID] and
      (JsPath \ "flight_number").read[String] and
      (JsPath \ "flight_status").read[String] and
      (JsPath \ "from_airport").read[String] and
      (JsPath \ "dest_airport").read[String] and
      (JsPath \ "departure_date").read[DateTime](jodaDateReads) and
      (JsPath \ "arrival_date").read[DateTime](jodaDateReads)
    )(Flight.apply _)

  implicit val flightFormat: Format[Flight] = Format(flightReads, flightWrites)

  implicit val flightOFormat = new OFormat[Flight] {
    override def reads(json: JsValue): JsResult[Flight] = flightFormat.reads(json)
    override def writes(o: Flight): JsObject = flightFormat.writes(o).asInstanceOf[JsObject]
  }

//
//  implicit object FlightWrites extends OWrites[Flight] {
//    def writes(flight: Flight): JsObject = Json.obj(
//      "id" -> Option(BSONObjectID.generate().stringify),
//      "flight_number" -> flight.flight_number,
//      "flight_status" -> flight.flight_status,
//      "from_airport" -> flight.from_airport,
//      "dest_airport" -> flight.dest_airport,
//      "departure_date" -> flight.departure_date.fold(-1L)(_.getMillis),
//      "arrival_date" -> flight.arrival_date.fold(-1L)(_.getMillis))
//  }
//
//  implicit object FlightReads extends Reads[Flight] {
//    def reads(json: JsValue): JsResult[Flight] = json match {
//      case obj: JsObject => try {
//        val id = (obj \ "id").asOpt[String]
//        val flight_number = (obj \ "flight_number").as[String]
//        val flight_status = (obj \ "flight_status").as[String]
//        val from_airport = (obj \ "from_airport").as[String]
//        val dest_airport = (obj \ "dest_airport").as[String]
//        val departure_date = (obj \ "departure_date").asOpt[DateTime]
//        val arrival_date = (obj \ "arrival_date").asOpt[DateTime]
//
//        JsSuccess(Flight(id, flight_number, flight_status, from_airport, dest_airport,departure_date.map(new DateTime(_)),arrival_date.map(new DateTime(_))))
//
//      } catch {
//        case cause: Throwable => JsError(cause.getMessage)
//      }
//
//      case _ => JsError("expected.jsObject")
//    }
//  }
}