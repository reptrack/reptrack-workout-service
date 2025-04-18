package com.reptrack.api.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addSerializer(GrantedAuthority.class, new GrantedAuthoritySerializer());
        module.addDeserializer(SimpleGrantedAuthority.class, new SimpleGrantedAuthorityDeserializer());
        module.addDeserializer(Collection.class, new AuthoritiesDeserializer());

        mapper.registerModule(module);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.addMixIn(GrantedAuthority.class, SimpleGrantedAuthority.class);

        return mapper;
    }

    private static class GrantedAuthoritySerializer extends StdSerializer<GrantedAuthority> {
        public GrantedAuthoritySerializer() {
            super(GrantedAuthority.class);
        }

        @Override
        public void serialize(GrantedAuthority value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(value.getAuthority());
        }
    }

    private static class SimpleGrantedAuthorityDeserializer extends StdDeserializer<SimpleGrantedAuthority> {
        public SimpleGrantedAuthorityDeserializer() {
            super(SimpleGrantedAuthority.class);
        }

        @Override
        public SimpleGrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String authority = p.getValueAsString();
            return new SimpleGrantedAuthority(authority);
        }
    }

    private static class AuthoritiesDeserializer extends StdDeserializer<Collection<?>> {
        public AuthoritiesDeserializer() {
            super(Collection.class);
        }

        @Override
        public Collection<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            
            if (node.isArray()) {
                for (JsonNode authorityNode : node) {
                    String authority = authorityNode.isObject() 
                        ? authorityNode.get("authority").asText() 
                        : authorityNode.asText();
                    authorities.add(new SimpleGrantedAuthority(authority));
                }
            }
            
            return authorities;
        }
    }
} 