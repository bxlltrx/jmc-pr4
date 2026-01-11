package com.example.libraryrest.web;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

public class XmlUtil {

    private static final XmlMapper XML_MAPPER = new XmlMapper();

    static {
        // поддержка LocalDate/LocalDateTime
        XML_MAPPER.registerModule(new JavaTimeModule());
        // чтобы даты были "YYYY-MM-DD", а не timestamp-массив
        XML_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private XmlUtil() {}

    public static String toXmlWithXsl(Object value, String xslHref) {
        try {
            String xmlBody = XML_MAPPER.writeValueAsString(value);

            // добавляем xml-stylesheet
            String stylesheet = "<?xml-stylesheet type=\"text/xsl\" href=\"" + xslHref + "\"?>\n";

            if (xmlBody.startsWith("<?xml")) {
                int end = xmlBody.indexOf("?>");
                if (end >= 0) {
                    String first = xmlBody.substring(0, end + 2);
                    String rest = xmlBody.substring(end + 2).trim();
                    return first + "\n" + stylesheet + rest;
                }
            }

            // если вдруг декларации нет
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + stylesheet + xmlBody;

        } catch (Exception e) {
            throw new RuntimeException("XML serialization error: " + e.getMessage(), e);
        }
    }
}
