package jpchs.spring_app.mapper;

import jpchs.spring_app.dto.inner.LocationDTO;
import jpchs.spring_app.entity.Location;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class LocationMapper {

    public LocationDTO toDTO(Location entity) {
        if (isNull(entity)) {
            return null;
        }
        return new LocationDTO(
                entity.getX(),
                entity.getY(),
                entity.getName()
        );
    }

    public Location fromDTO(LocationDTO dto) {
        if (isNull(dto)) {
            return null;
        }
        return new Location(
                dto.x(),
                dto.y(),
                dto.name()
        );
    }
}
