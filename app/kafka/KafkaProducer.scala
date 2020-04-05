package kafka

import java.util.Properties
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerConfig, ProducerRecord, RecordMetadata}

object KafkaProducer {

  val bootstrapServer = "127.0.0.1:9092"
  val groupId = "flight-example"
  val topic = "flight-sourcing"

  val props: Properties = {
    val prop = new Properties()
    prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
    prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    prop.put(ProducerConfig.ACKS_CONFIG, "all")
    prop
  }

  def main(args: Array[String]): Unit = {

    val callback = new Callback {
      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
        println("Callback" + metadata.toString)
      }
    }
    val producer = new KafkaProducer[String, String](props)

    for (k <- 1 to 10) {
      producer.send(new ProducerRecord(topic, s"key ${k}", "value"), callback)
    }
    producer.close()
  }
}