package com.nitendragautam.kafkastreamingjob.main


import java.nio.file.Paths

import akka.NotUsed
import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.stream.ActorMaterializer
import akka.stream.alpakka.file.scaladsl.FileTailSource
import akka.stream.scaladsl.Source
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

import scala.concurrent.duration._


/**
  * Created by nitendra on 3/13/2017.
  */
object Boot extends App{
  //Define Actor System

  implicit val system = ActorSystem("KafkaActorSystem")
  implicit val materializer =ActorMaterializer()
  implicit val executionContext = system.dispatcher

  //Access Logs File Path which is streamed line by line and send it to Kafka
  val filePath="D:\\App\\KafkaStreamingProducerJob\\Data\\KafkaStreaming.log"
  val logFilePath= Paths.get(filePath);


  val config = ConfigFactory.load()
  val producerConfig =config.getConfig("kafka.producer")
  val kafkaTopic =producerConfig.getString("topic")

  //producer Settings
  val producerSettings =
    ProducerSettings(system,new StringSerializer,new StringSerializer)
      .withBootstrapServers(producerConfig.getString("bootstrap.servers"))
  //Create Kafka Producer
  val kafkaProducer = producerSettings.createKafkaProducer()

  /*
  Creates a source of lines
   */
  val lines :Source[String,NotUsed] =FileTailSource.lines(
    path=logFilePath,
    maxLineSize = 1000,
    pollingInterval = 5000.millis

  )

  lines.runForeach(line => {
    //Send Kafka Record for each Lines
    kafkaProducer.send(new ProducerRecord[String,String](kafkaTopic,line))


  }


  )



}