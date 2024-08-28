package pl.bialek.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.bialek.api.dto.MechanicDTO;
import pl.bialek.domain.Mechanic;

@Mapper(componentModel = "spring")
public interface MechanicsMapper {
    MechanicDTO mapToDTO(final Mechanic mechanic);

}
