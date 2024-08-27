package pl.bialek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.bialek.business.dao.CarToBuyDAO;
import pl.bialek.business.dao.CarToServiceDAO;
import pl.bialek.domain.CarHistory;
import pl.bialek.domain.CarToBuy;
import pl.bialek.domain.CarToService;

import java.util.Optional;
@Service
@AllArgsConstructor
@Slf4j
public class CarService {

    private final CarToServiceDAO carToServiceDAO;
    private final CarToBuyDAO carToBuyDAO;

    public CarToBuy findCarToBuy(String vin) {
        Optional<CarToBuy> carToBuyByVin = carToBuyDAO.findCarToBuyByVin(vin);
        if (carToBuyByVin.isEmpty()) {
            throw new RuntimeException("Provided car with vin: [%s] doesn't exist".formatted(vin));
        }
        return carToBuyByVin.get();
    }

    public Optional<CarToService> findCarToService(String vin) {
        return carToServiceDAO.findCarToServiceByVin(vin);
    }

    public CarToService saveCarToService(CarToBuy carToBuy) {
        CarToService carToService = CarToService.builder()
                .vin(carToBuy.getVin())
                .brand(carToBuy.getBrand())
                .model(carToBuy.getModel())
                .year(carToBuy.getYear())
                .build();
        return carToServiceDAO.saveCarToService(carToService);
    }

    public CarToService saveCarToService(CarToService carToService) {
        return carToServiceDAO.saveCarToService(carToService);
    }


    public void printCarHistory(String vinNumber) {
        CarHistory carHistoryByVin = carToServiceDAO.findCarHistoryByVin(vinNumber);
        log.info("Car History for car with VIN: [{}]", vinNumber);
        carHistoryByVin.getCarServiceRequests().forEach(this::printServiceRequest);
    }

    private void printServiceRequest(CarHistory.CarServiceRequest carServiceRequest) {
        log.info("Service request [{}]", carServiceRequest);
        carServiceRequest.getServices().forEach(service -> log.info("Service: [{}]", service));
        carServiceRequest.getParts().forEach(part -> log.info("Part: [{}]", part));
    }

}
