package io.geekidea.springbootplus.framework.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.text.DecimalFormat;

public class JsonSerializeUtils extends JsonSerializer<String> {

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(s != null) {
            jsonGenerator.writeString(StringEscapeUtils.unescapeHtml4(s));
        }
    }
}
