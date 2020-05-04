package kafka

import java.util.Properties

import com.goyeau.kafka.streams.circe.CirceSerdes
import events.FlightEvent
import models.Flight
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerConfig, ProducerRecord, RecordMetadata}
import org.joda.time.DateTime
import reactivemongo.bson.BSONObjectID

object KafkaProducerFlight {

  val bootstrapServer = "127.0.0.1:9092"
  val groupId = "flight-id"
  val topic = "flight"

  val props: Properties = {
    val prop = new Properties()
    prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
    prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    prop.put(ProducerConfig.ACKS_CONFIG, "all")
    prop
  }

  def sendToKafka(event: String): Unit = {

    val callback = new Callback {
      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
        println("Callback" + metadata.toString)
      }
    }
    val producer = new KafkaProducer[String, String](props)

    producer.send(new ProducerRecord(topic, BSONObjectID.generate().stringify, event), callback)
    producer.close()
  }

//  def main(args: Array[String]): Unit = {
//
//    val callback = new Callback {
//      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
//        println("Callback" + metadata.toString)
//      }
//    }
//
//    val flight = Flight(_id = Option(BSONObjectID.generate()),flight_number="ABCDE",flight_status="CREATED",from_airport="JFK",dest_airport="CDG",departure_date = new DateTime("2020-04-01T05:06:07.000+02:00"),arrival_date = new DateTime("2020-04-01T05:06:07.000+02:00"))
//    val producer = new KafkaProducer[String, Flight](props)
//
//    for (k <- 1 to 2) {
//      producer.send(new ProducerRecord(topic, s"key ${k}", flight), callback)
//    }
//    producer.close()
//  }
}
