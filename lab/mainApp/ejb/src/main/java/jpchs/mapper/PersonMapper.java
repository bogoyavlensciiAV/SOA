package jpchs.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jpchs.dto.inner.PersonDTO;
import jpchs.entity.Person;

import static java.util.Objects.isNull;

@ApplicationScoped
public class PersonMapper {

    @Inject
    private LocationMapper locationMapper;

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
