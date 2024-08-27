package pl.bialek.business;

import lombok.AllArgsConstructor;
import pl.bialek.business.dao.ServiceRequestProcessingDAO;
import pl.bialek.business.menagement.DataPreparationService;
import pl.bialek.business.menagement.Keys;
import pl.bialek.domain.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
@org.springframework.stereotype.Service
@AllArgsConstructor
public class CarServiceProcessingService {
    private final DataPreparationService dataPreparationService;
    private final MechanicService mechanicService;
    private final CarService carService;
    private final CarServiceRequestService carToServiceRequestService;
    private final ServiceCatalogService catalogService;
    private final PartCatalogService partCatalogService;
    private final ServiceRequestProcessingDAO serviceRequestProcessingDAO;

    public void process() {
        List<CarServiceProcessingInputData> toProcess = dataPreparationService.prepareServiceRequestToProcess();
        toProcess.forEach(this::processRequest);
    }

    private void processRequest(CarServiceProcessingInputData request) {
        Mechanic mechanic = mechanicService.findMechanic(request.getMechnicPesel());
        //Checking that carToService is existing
        carService.findCarToService(request.getCarVin()).orElseThrow();
        CarServiceRequest carServiceRequest = carToServiceRequestService.findAnyActiveRequest(request.getCarVin());

        Service service = catalogService.findService(request.getServiceCode());

        ServiceMechanic serviceMechanic = buildServiceMechanic(request, mechanic, carServiceRequest, service);

        if (Keys.Constant.FINISHED.toString().equals(request.getDone())) {
            carServiceRequest = carServiceRequest.withCompletedDateTime(OffsetDateTime.now());
        }

        //Case if no parts are required
        if (Objects.isNull(request.getPartSerialNumber()) || Objects.isNull(request.getPartQuantity())) {
            serviceRequestProcessingDAO.process(carServiceRequest, serviceMechanic);
        } else {
            Part part = partCatalogService.findPart(request.getPartSerialNumber());
            ServicePart servicePart = buildServicePart(request, carServiceRequest, part);
            serviceRequestProcessingDAO.process(carServiceRequest, serviceMechanic, servicePart);
        }

    }

    private ServicePart buildServicePart(
            CarServiceProcessingInputData request,
            CarServiceRequest carServiceRequest,
            Part part) {

        return ServicePart
                .builder()
                .quantity(request.getPartQuantity())
                .carServiceRequest(carServiceRequest)
                .part(part)
                .build();
    }

    private ServiceMechanic buildServiceMechanic(
            CarServiceProcessingInputData request,
            Mechanic mechanic,
            CarServiceRequest carServiceRequest,
            Service service) {

        return ServiceMechanic.builder()
                .hours(request.getHours())
                .comment(request.getComment())
                .carServiceRequest(carServiceRequest)
                .service(service)
                .mechanic(mechanic)
                .build();
    }
}
