package com.java.bigdata.utils.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

/**
 * @author brahim bahri
 */
public class KafkaAccess {
    final static Logger logger = Logger.getLogger(KafkaAccess.class.getName());

    /**
     * This method Sends a String message  to the a kafka topic
     * @param topicName : Name of the topic to which the message has to be sent
     * @param id: Kafka message key
     * @param msg: The message to be sent
     */
    public static void sendToKafka(String topicName, String id, String msg){

            /*Send the full list to Kafka topic*/
        KafkaProducerConnection.getKafkaProducer().send(new ProducerRecord<String, String>(topicName,id,msg));
    }

  }

