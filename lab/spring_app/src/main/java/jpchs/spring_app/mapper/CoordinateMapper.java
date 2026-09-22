package jpchs.spring_app.mapper;

import jpchs.spring_app.dto.inner.CoordinatesDTO;
import jpchs.spring_app.entity.Coordinates;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class CoordinateMapper {

    public CoordinatesDTO toDTO(Coordinates entity) {
        if (isNull(entity)) {
            return null;
        }
        return new CoordinatesDTO(
                entity.getX(),
                entity.getY()
        );
    }

    public Coordinates fromDTO(CoordinatesDTO dto) {
        if (isNull(dto)) {
            return null;
        }
        return new Coordinates(
                dto.x(),
                dto.y()
        );
    }
}
