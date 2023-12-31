package raf.microservice.components.notificationservice.mapper;

import org.springframework.stereotype.Component;
import raf.microservice.components.notificationservice.dto.TypeDto;
import raf.microservice.components.notificationservice.model.Type;

@Component
public class TypeMapper {


    public TypeDto typeToTypeDto(Type type) {
        TypeDto typeDto = new TypeDto();

        typeDto.setName(type.getName());
        typeDto.setFormat(type.getFormat());

        return typeDto;
    }
}