package com.booking.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dto.Suite;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class SuiteStrategy extends JsonDeserializer<Suite>{

    @Autowired
    private Map<String, Suite> strategies;

    public Suite getSuite(String suiteType) {
        return strategies.get(suiteType);
    }

    @Override
    public Suite deserialize(JsonParser parser, DeserializationContext context) throws IOException, 
    JacksonException {
        JsonNode node = parser.readValueAsTree();
        Suite suite = strategies.get(node.asText());

        if (suite == null) {
            suite = strategies.get(node.get("name").asText()+"Suite");
        }

        return suite;
    }

}