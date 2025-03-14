package com.shayan.journalyt.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ObjectIdSerializer extends JsonSerializer<ObjectId> {

    @Override
    public void serialize(ObjectId objectId, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        // Write the "object" part with timestamp and date
        gen.writeObjectFieldStart("object");
        gen.writeNumberField("timestamp", objectId.getTimestamp());
        // Convert the timestamp (in seconds) to a Date and format it
        Date date = new Date(objectId.getTimestamp() * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String formattedDate = sdf.format(date);
        gen.writeStringField("date", formattedDate);
        gen.writeEndObject();

        // Write the "string" representation
        gen.writeStringField("string", objectId.toHexString());

        gen.writeEndObject();
    }
}
