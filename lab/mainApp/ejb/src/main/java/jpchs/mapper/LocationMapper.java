package jpchs.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jpchs.dto.inner.LocationDTO;
import jpchs.entity.Location;

import static java.util.Objects.isNull;

@ApplicationScoped
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
