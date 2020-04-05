package kafka

import java.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import models.Flight
import org.apache.kafka.common.serialization.Deserializer

class CustomDeserializer extends Deserializer[Flight] {

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def deserialize(topic: String, bytes: Array[Byte]): Flight = {

    try {
      val mapper = new ObjectMapper
      mapper.registerModule(DefaultScalaModule)
      mapper.readValue(bytes, classOf[Flight])
    } catch {
      case ex: Exception => throw new Exception(ex.getMessage)
    }
  }

  override def close(): Unit = {}
}
