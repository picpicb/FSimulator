package kafka

import java.util
import java.util.Properties

import models.Flight
import org.apache.kafka.clients.consumer._

object KafkaConsumerFlight {

  import scala.collection.JavaConverters._

  val bootstrapServer = "127.0.0.1:9092"
  val groupId = "flight-id"
  val topic = "flight-event"

  val props: Properties = {
    val prop = new Properties()
    prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
    prop.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    prop
  }

  def main(args: Array[String]): Unit = {
    ConsumeFromKafka()
  }

  def ConsumeFromKafka(): Unit = {

    val consumer = new KafkaConsumer[String, Flight](props)
    consumer.subscribe(util.Collections.singletonList(this.topic))

    println("Starting pooling...")

    while (true) {
      val records = consumer.poll(10000)
      if (records.isEmpty) {
        println("Nothing to poll")
      } else {
        for (record <- records.asScala) {
          println("Message: (key: " + record.key() +
            ", with value: " + record.value() +
            ") at on partition " + record.partition() +
            " at offset " + record.offset())
        }
      }
      consumer.commitSync()
    }

  }
}