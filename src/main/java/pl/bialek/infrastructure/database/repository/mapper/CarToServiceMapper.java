package pl.bialek.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.bialek.domain.CarHistory;
import pl.bialek.domain.CarToService;
import pl.bialek.domain.Part;
import pl.bialek.domain.Service;
import pl.bialek.infrastructure.database.entity.CarToServiceEntity;
import pl.bialek.infrastructure.database.entity.ServiceMechanicEntity;
import pl.bialek.infrastructure.database.entity.ServicePartEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarToServiceMapper {
    @Mapping(target = "carServiceRequests", ignore = true)
    CarToService mapFromEntity(CarToServiceEntity entity);

    default CarHistory mapFromEntity(String vin, CarToServiceEntity carToServiceEntity){
        return CarHistory.builder()
                .carVin(vin)
                .carServiceRequests(carToServiceEntity.getCarServiceRequests().stream()
                        .map(carServiceRequest -> CarHistory.CarServiceRequest.builder()
                                .carServiceRequestNumber(carServiceRequest.getCarServiceRequestNumber())
                                .receivedDateTime(carServiceRequest.getReceivedDateTime())
                                .completedDateTime(carServiceRequest.getCompletedDateTime())
                                .customerComment(carServiceRequest.getCustomerComment())

                                .parts(carServiceRequest.getServiceParts().stream()
                                        .map(ServicePartEntity::getPart)
                                        .map(service -> Part.builder()
                                                .serialNumber(service.getSerialNumber())
                                                .description(service.getDescription())
                                                .price(service.getPrice())
                                                .build())
                                        .toList())

                                .services(carServiceRequest.getServiceMechanics().stream()
                                        .map(ServiceMechanicEntity::getService)
                                        .map(service -> Service.builder()
                                                .serviceCode(service.getServiceCode())
                                                .description(service.getDescription())
                                                .price(service.getPrice())
                                                .build())
                                        .toList())

                                .build())
                        .toList())
                .build();
    }

    CarToServiceEntity mapToEntity(CarToService car);

};


