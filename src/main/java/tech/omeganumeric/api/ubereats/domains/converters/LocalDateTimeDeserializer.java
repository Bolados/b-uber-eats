/**
 *
 */
package tech.omeganumeric.api.ubereats.domains.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author BSCAKO
 *
 */
public class LocalDateTimeDeserializer extends StdScalarDeserializer<LocalDateTime> {

    public final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public final static String FIELD_DATE = "date";
    public final static String FIELD_TIME = "time";
    private static final long serialVersionUID = 566040662033097980L;
    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public LocalDateTimeDeserializer() {
        super(LocalDateTime.class);
    }

    protected LocalDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml
     * .jackson.core.JsonParser,
     * com.fasterxml.jackson.databind.DeserializationContext)
     */
    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {

        JsonNode node = jp.getCodec().readTree(jp);

        String dateStr = node.get(FIELD_DATE).asText();
        String timeStr = node.get(FIELD_TIME).asText();

        if (dateStr == null) {
            dateStr = DATE_FORMAT.format(LocalDateTime.now());
        }
        if (timeStr == null) {
            timeStr = TIME_FORMAT.format(LocalDateTime.now());
        }
        return LocalDateTime.parse(dateStr + " " + timeStr, DATETIME_FORMAT);
    }


}
