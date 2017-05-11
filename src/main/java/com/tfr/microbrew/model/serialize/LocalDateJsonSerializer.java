package com.tfr.microbrew.model.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.time.LocalDate;

import java.io.IOException;

/**
 * Created by Erik on 5/10/2017.
 */
public class LocalDateJsonSerializer extends StdSerializer<LocalDate> {


    protected LocalDateJsonSerializer() {
        super(LocalDate.class);
    }

    @Override
    public void serialize(LocalDate localDate,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        String formattedDate = localDate.toString("MM-dd-yyyy");
        jsonGenerator.writeString(formattedDate);
    }
}
