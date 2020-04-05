package repositories

import javax.inject.Inject
import kafka.KafkaProducerFlight
import models.Flight
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

class FlightRepositoryImpl @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) extends FlightRepository {

  private def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("flights"))

  override def findAll(limit: Int = 100)(implicit ec: ExecutionContext): Future[Seq[Flight]] =
    collection.flatMap(_.find(BSONDocument()).cursor[Flight](ReadPreference.primary).collect[Seq](limit, Cursor.FailOnError[Seq[Flight]]()))

  override def findByFlightNumber(flight_number: String)(implicit ec: ExecutionContext): Future[Option[Flight]] =
    collection.flatMap(_.find(BSONDocument("flight_number" -> flight_number)).one[Flight])

  override def save(flight: Flight)(implicit ec: ExecutionContext): Future[WriteResult] =
    collection.flatMap(_.insert(flight))

  override def update(flight_number: String,flight: Flight)(implicit ec: ExecutionContext): Future[Option[Flight]] =
    collection.flatMap(_.findAndUpdate(BSONDocument("flight_number" -> flight_number), BSONDocument(
    f"$$set" -> BSONDocument(
      "flight_number" -> flight.flight_number,
      "flight_status" -> flight.flight_status,
      "from_airport" -> flight.from_airport,
      "dest_airport" -> flight.dest_airport,
      "departure_date" -> flight.departure_date.toString(),
      "arrival_date" -> flight.arrival_date.toString()
    )
  ),
    true
  ).map(_.result[Flight])
  )

  override def remove(flight_number: String)(implicit ec: ExecutionContext): Future[Option[Flight]] =
    collection.flatMap(_.findAndRemove(BSONDocument("flight_number" -> flight_number)).map(_.result[Flight]))
}
