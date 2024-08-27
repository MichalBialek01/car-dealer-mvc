package pl.bialek.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.bialek.business.dao.ServiceDAO;
import pl.bialek.domain.Service;
import pl.bialek.infrastructure.database.repository.jpa.ServiceJpaRepository;
import pl.bialek.infrastructure.database.repository.mapper.ServiceMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ServiceRepository implements ServiceDAO {
    private final ServiceJpaRepository serviceJpaRepository;
    private final ServiceMapper serviceMapper;
    @Override
    public Optional<Service> findByServiceCode(String serviceCode) {
        return serviceJpaRepository.findByServiceCode(serviceCode)
                .map(serviceMapper::mapFromEntity);
    }
}
