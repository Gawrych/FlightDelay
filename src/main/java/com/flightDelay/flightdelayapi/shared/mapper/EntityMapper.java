package com.flightDelay.flightdelayapi.shared.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.flightDelay.flightdelayapi.shared.exception.importData.ProcessingJsonDataFailedException;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public abstract class EntityMapper {

    public <T> List<T> jsonArrayToList(String json, Class<T> elementClass, ObjectMapper objectMapper) {
        try {
            CollectionType listType =
                    objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);

            return objectMapper.readValue(json, listType);

        } catch (JsonProcessingException e) {
            throw new ProcessingJsonDataFailedException(elementClass.getName());
        }
    }
}
