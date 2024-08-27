package pl.bialek.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.bialek.business.dao.CarToBuyDAO;
import pl.bialek.domain.CarToBuy;
import pl.bialek.infrastructure.database.repository.jpa.CarToBuyJpaRepository;
import pl.bialek.infrastructure.database.repository.mapper.CarToBuyMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarToBuyRepository implements CarToBuyDAO {
    private final CarToBuyJpaRepository carToBuyJpaRepository;
    private final CarToBuyMapper carToBuyEntityMapper;
    @Override
    public Optional<CarToBuy> findCarToBuyByVin(String vin) {
        return carToBuyJpaRepository.findByVin(vin)
                .map(carToBuyEntityMapper::mapFromEntity);
    }
}
