package pl.bialek.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.bialek.api.dto.SalesmanDTO;
import pl.bialek.domain.Salesman;

@Mapper(componentModel = "spring")
public interface SalesmanMapper {
    SalesmanDTO mapToDTO(final Salesman salesman);
}
