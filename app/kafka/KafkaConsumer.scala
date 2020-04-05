package kafka

import java.util.{Collections, Properties}

import org.apache.kafka.clients.consumer._

object KafkaConsumer {

  import scala.collection.JavaConverters._

  val bootstrapServer = "127.0.0.1:9092"
  val groupId = "flight-example"
  val topic = "flight-sourcing"

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
    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(Collections.singletonList(this.topic))

    while (true) {
      val records = consumer.poll(1000).asScala

      for (record <- records) {
        println("Message: (key: " + record.key() +
          ", with value: " + record.value() +
          ") at on partition " + record.partition() +
          " at offset " + record.offset())
      }
    }
  }
}
