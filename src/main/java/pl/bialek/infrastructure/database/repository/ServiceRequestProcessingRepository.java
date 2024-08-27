package pl.bialek.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.bialek.business.dao.ServiceRequestProcessingDAO;
import pl.bialek.domain.CarServiceRequest;
import pl.bialek.domain.ServiceMechanic;
import pl.bialek.domain.ServicePart;
import pl.bialek.infrastructure.database.entity.CarServiceRequestEntity;
import pl.bialek.infrastructure.database.entity.PartEntity;
import pl.bialek.infrastructure.database.entity.ServiceMechanicEntity;
import pl.bialek.infrastructure.database.entity.ServicePartEntity;
import pl.bialek.infrastructure.database.repository.jpa.CarServiceRequestJpaRepository;
import pl.bialek.infrastructure.database.repository.jpa.PartJpaRepository;
import pl.bialek.infrastructure.database.repository.jpa.ServiceMechanicJpaRepository;
import pl.bialek.infrastructure.database.repository.jpa.ServicePartJpaRepository;
import pl.bialek.infrastructure.database.repository.mapper.ServiceMechanicMapper;
import pl.bialek.infrastructure.database.repository.mapper.ServicePartMapper;

import java.util.Objects;

@Repository
@AllArgsConstructor
public class ServiceRequestProcessingRepository implements ServiceRequestProcessingDAO {

    private final ServiceMechanicJpaRepository serviceMechanicJpaRepository;
    private final CarServiceRequestJpaRepository carServiceRequestJpaRepository;
    private final PartJpaRepository partJpaRepository;
    private final ServicePartJpaRepository servicePartJpaRepository;
    private final ServiceMechanicMapper serviceMechanicMapper;
    private final ServicePartMapper servicePartMapper;

    @Override
    public void process(
            CarServiceRequest carServiceRequest,
            ServiceMechanic serviceMechanic,
            ServicePart servicePart) {

        PartEntity partEntity = partJpaRepository.findById(servicePart.getServicePartId()).orElseThrow();
        ServicePartEntity servicePartEntity =  servicePartMapper.mapToEntity(servicePart);
        servicePartEntity.setPart(partEntity);
        servicePartJpaRepository.saveAndFlush(servicePartEntity);
        process(carServiceRequest,serviceMechanic);
    }
    @Override
    public void process(
            CarServiceRequest carServiceRequest,
            ServiceMechanic serviceMechanic) {
        ServiceMechanicEntity serviceMechanicEntity = serviceMechanicMapper.mapToEntity(serviceMechanic);
        serviceMechanicJpaRepository.saveAndFlush(serviceMechanicEntity);

        if (Objects.nonNull(carServiceRequest.getCompletedDateTime())) {
            CarServiceRequestEntity carServiceRequestEntity = carServiceRequestJpaRepository
                    .findById(carServiceRequest.getCarServiceRequestId())
                    .orElseThrow();
            carServiceRequestEntity.setCompletedDateTime(carServiceRequest.getCompletedDateTime());
            carServiceRequestJpaRepository.saveAndFlush(carServiceRequestEntity);
        }
    }



}
