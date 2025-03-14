package com.shayan.journalyt.serializers;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.shayan.journalyt.entity.JournalEntry;

public class JournalEntryIdListSerializer extends JsonSerializer<List<JournalEntry>> {

    // Reuse your ObjectIdSerializer to output each id in the desired format.
    private final ObjectIdSerializer objectIdSerializer = new ObjectIdSerializer();

    @Override
    public void serialize(List<JournalEntry> journalEntries, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartArray();
        if (journalEntries != null) {
            for (JournalEntry entry : journalEntries) {
                ObjectId id = entry.getId();
                if (id == null) {
                    gen.writeNull();
                } else {
                    objectIdSerializer.serialize(id, gen, serializers);
                }
            }
        }
        gen.writeEndArray();
    }
}
