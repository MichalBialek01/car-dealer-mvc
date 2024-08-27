package pl.bialek.business.dao;

import pl.bialek.domain.Service;

import java.util.Optional;

public interface ServiceDAO {
    Optional<Service> findByServiceCode(String serviceCode);

}
