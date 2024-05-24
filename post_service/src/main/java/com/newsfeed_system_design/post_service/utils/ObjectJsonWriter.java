package com.newsfeed_system_design.post_service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ObjectJsonWriter {
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public static String writeObjectToString(Object targetObject) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(targetObject);
    }
}
