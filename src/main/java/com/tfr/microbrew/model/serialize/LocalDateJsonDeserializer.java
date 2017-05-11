package com.tfr.microbrew.model.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 *
 * Created by Erik on 5/10/2017.
 */
public class LocalDateJsonDeserializer extends StdDeserializer<LocalDate> {


    protected LocalDateJsonDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String json = jsonParser.getText();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM-dd-yyyy");
        return LocalDate.parse(json, dateTimeFormatter);
    }
}
