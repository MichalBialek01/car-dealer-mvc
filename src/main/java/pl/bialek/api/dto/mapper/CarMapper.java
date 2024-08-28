package pl.bialek.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.bialek.api.dto.CarToBuyDTO;
import pl.bialek.domain.CarToBuy;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarToBuyDTO mapToDTO(final CarToBuy car);
}
