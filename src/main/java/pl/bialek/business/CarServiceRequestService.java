package pl.bialek.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bialek.business.dao.CarServiceRequestDAO;
import pl.bialek.domain.CarServiceRequest;
import pl.bialek.domain.CarToBuy;
import pl.bialek.domain.CarToService;
import pl.bialek.domain.Customer;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarServiceRequestService {
//    private final DataPreparationService dataPreparationService;
//    private final CarService carService;
//    private final CustomerService customerService;
//    private final CarServiceRequestDAO carServiceRequestDAO;
//
//    public void requestService() {
//        // We distinguish 2 cases: 1 if car to service and client already exist is on database, and second if client and car are external guests.
//        // Distinguish bases on if got car parameters  (vin&year&brand) aren't exisitng. if - true = this car doesn't exist
//
//        Map<Boolean, List<CarServiceRequest>> serviceRequests = dataPreparationService.createServiceRequests()
//                .stream().collect(Collectors.groupingBy(element -> element.getCar().shouldExistInCarToBuy()));
//
//
//        serviceRequests.get(true).forEach(this::saveServiceRequestsForExistingCar);
//        serviceRequests.get(false).forEach(this::saveServiceRequestForNewCar);
//
//    }
//
//    private void saveServiceRequestsForExistingCar(CarServiceRequest request) {
//        CarToService car = carService.findCarToService(request.getCar().getVin())
//                .orElse(findInCarToBuyAndSaveInCarToService(request.getCar()));
//        Customer customer = customerService.findCustomer(request.getCustomer().getEmail());
//
//        CarServiceRequest carServiceRequest = buildCarServiceRequest(request, car, customer);
//
//        Set<CarServiceRequest> existingCarServiceRequests = customer.getCarServiceRequests();
//        existingCarServiceRequests.add(carServiceRequest);
//        customerService.saveServiceRequest(customer);
//    }
//
//    private CarToService findInCarToBuyAndSaveInCarToService(CarToService car) {
//        CarToBuy carToBuy = carService.findCarToBuy(car.getVin());
//        return carService.saveCarToService(carToBuy);
//    }
//
//    //Basing on above creating caToService and connecting it with customer
//
//    private CarServiceRequest buildCarServiceRequest(
//            CarServiceRequest request,
//            CarToService car,
//            Customer customer
//    ) {
//        OffsetDateTime when = OffsetDateTime.of(2027, 1, 10, 10, 2, 10, 0, ZoneOffset.UTC);
//        return CarServiceRequest.builder()
//                .carServiceRequestNumber(generateCarServiceRequestNumber(when))
//                .receivedDateTime(when)
//                .customerComment(request.getCustomerComment())
//                .customer(customer)
//                .car(car)
//                .build();
//    }
//
//    private String generateCarServiceRequestNumber(OffsetDateTime when) {
//        return "%s.%s.%s-%s.%s.%s.%s".formatted(
//                when.getYear(),
//                when.getMonth().ordinal(),
//                when.getDayOfMonth(),
//                when.getHour(),
//                when.getMinute(),
//                when.getSecond(),
//                randomInt(10, 100)
//        );
//    }
//
//    private Object randomInt(int min, int max) {
//        return new Random().nextInt(max - min) + min;
//    }
//
//    private void saveServiceRequestForNewCar(CarServiceRequest request) {
//
//        CarToService car = carService.saveCarToService(request.getCar());
//        Customer customer = customerService.saveCustomer(request.getCustomer());
//
//        CarServiceRequest carToServiceRequest = buildCarServiceRequest(request, car, customer);
//        Set<CarServiceRequest> existingCarServiceRequests = customer.getCarServiceRequests();
//        existingCarServiceRequests.add(carToServiceRequest);
//        customerService.saveServiceRequest(customer);
//    }
//
//    public CarServiceRequest findAnyActiveRequest(String carVin) {
//        Set<CarServiceRequest> serviceRequests = carServiceRequestDAO.findActiveServiceRequestsByCarVin(carVin);
//        return serviceRequests.stream()
//                .findAny()
//                .orElseThrow(() -> new RuntimeException("Could not find service request for car with VIN number: [%s] ".formatted(carVin)));
//    }
}
