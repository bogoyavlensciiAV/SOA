package jpchs.spring_app.mapper;

import jpchs.spring_app.dto.inner.PersonDTO;
import jpchs.spring_app.entity.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.util.Objects.isNull;

@Component
@AllArgsConstructor
public class PersonMapper {

    private final LocationMapper locationMapper;

    public PersonDTO toDTO(Person entity) {
        if (isNull(entity)) {
            return null;
        }
        return new PersonDTO(
                entity.getName(),
                entity.getWeight(),
                entity.getPassportID(),
                locationMapper.toDTO(entity.getLocation())
        );
    }

    public Person fromDTO(PersonDTO dto) {
        if (isNull(dto)) {
            return null;
        }
        return new Person(
                null,
                dto.name(),
                dto.weight(),
                dto.passportID(),
                locationMapper.fromDTO(dto.location())
        );
    }
}
