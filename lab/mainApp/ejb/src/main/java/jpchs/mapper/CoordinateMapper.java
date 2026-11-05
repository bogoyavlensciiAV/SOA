package jpchs.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jpchs.dto.inner.CoordinatesDTO;
import jpchs.entity.Coordinates;

import static java.util.Objects.isNull;

@ApplicationScoped
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
