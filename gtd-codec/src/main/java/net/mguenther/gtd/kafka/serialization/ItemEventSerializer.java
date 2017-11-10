package net.mguenther.gtd.kafka.serialization;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Simple {@code Serializer} that operates on {@code AvroItemEvent}. This {@code Serializer} does
 * not support multiple versions of the same schema.
 *
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public class ItemEventSerializer implements Serializer<AvroItemEvent> {

    private final DatumWriter<AvroItemEvent> eventWriter = new SpecificDatumWriter<>(AvroItemEvent.class);

    @Override
    public void configure(Map<String, ?> map, boolean b) {
        // nothing to do here
    }

    @Override
    public byte[] serialize(final String s, final AvroItemEvent event) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            final BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            eventWriter.write(event, encoder);
            encoder.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Unable to serialize Event to byte[].", e);
        }
    }

    @Override
    public void close() {
        // nothing to do here
    }
}
