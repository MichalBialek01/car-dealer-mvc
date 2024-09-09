package pl.bialek.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.bialek.api.dto.CarServiceCustomerRequestDTO;
import pl.bialek.domain.Address;
import pl.bialek.domain.CarServiceRequest;
import pl.bialek.domain.CarToService;
import pl.bialek.domain.Customer;

@Mapper(componentModel = "spring")
public interface CarServiceRequestDtoMapper {
    default CarServiceRequest mapFromDTO(CarServiceCustomerRequestDTO dto) {
        if (dto.isNewCar()) {
            return CarServiceRequest.builder()
                    .customer(Customer.builder()
                            .name(dto.getCustomerName())
                            .surname(dto.getCustomerSurname())
                            .phone(dto.getCustomerPhone())
                            .email(dto.getCustomerEmail())
                            .address(Address.builder()
                                    .country(dto.getCustomerAddressCountry())
                                    .city(dto.getCustomerAddressCity())
                                    .postalCode(dto.getCustomerAddressPostalCode())
                                    .address(dto.getCustomerAddressStreet())
                                    .build())
                            .build())
                    .car(CarToService.builder()
                            .vin(dto.getCarVin())
                            .brand(dto.getCarBrand())
                            .model(dto.getCarModel())
                            .year(dto.getCarYear())
                            .build())
                    .customerComment(dto.getCustomerComment())
                    .build();
        } else {
            return CarServiceRequest.builder()
                    .customer(Customer.builder()
                            .email(dto.getExistingCustomerEmail())
                            .build())
                    .car(CarToService.builder()
                            .vin(dto.getExistingCarVin())
                            .build())
                    .customerComment(dto.getCustomerComment())
                    .build();
        }
    }
}
