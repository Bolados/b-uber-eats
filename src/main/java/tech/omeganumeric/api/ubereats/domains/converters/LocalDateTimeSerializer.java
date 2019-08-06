/**
 *
 */
package tech.omeganumeric.api.ubereats.domains.converters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author BSCAKO
 *
 */
public class LocalDateTimeSerializer extends StdScalarSerializer<LocalDateTime> {
    private static final long serialVersionUID = 1241235894149832599L;

    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public LocalDateTimeSerializer() {
        super(LocalDateTime.class);
    }

    /**
     * @param t
     * @param dummy
     */
    public LocalDateTimeSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    /**
     * @param t
     */
    public LocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    /* (non-Javadoc)
     * @see com.fasterxml.jackson.databind.ser.std.StdSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
     */
    @Override
    public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("date", DATE_FORMAT.format(value));
        jgen.writeStringField("time", TIME_FORMAT.format(value));
        jgen.writeEndObject();
    }


}
