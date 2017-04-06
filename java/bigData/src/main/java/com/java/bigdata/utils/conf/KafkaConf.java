package com.java.bigdata.utils.conf;

/**
 * Created by SMA on 21/10/2016.
 */
public class KafkaConf {
    public String broker_list;
    public String producer_batch_size;
    public String producer_buffer_memory;

    public String toString(String indent) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%sbroker_list: %s\n", indent, broker_list));
        sb.append(String.format("%sproducer_batch_size: %s\n", indent, producer_batch_size));
        sb.append(String.format("%sproducer_buffer_memory: %s\n", indent, producer_buffer_memory));

        return sb.toString();
    }
}
