package pl.bialek.business;

import lombok.AllArgsConstructor;
import pl.bialek.business.dao.ServiceDAO;
import pl.bialek.domain.Service;

import java.util.Optional;
@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceCatalogService {
    private final ServiceDAO serviceDAO;

    public Service findService(String serviceCode) {
        Optional<Service> service = serviceDAO.findByServiceCode(serviceCode);
        if (service.isEmpty()) {
            throw new RuntimeException("Provided Service with serviceCode: [%s] doesn't exist".formatted(serviceCode));
        }
        return service.get();
    }
}

