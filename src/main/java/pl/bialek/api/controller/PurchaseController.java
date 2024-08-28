package pl.bialek.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.bialek.api.dto.CarPurchaseRequestDTO;
import pl.bialek.api.dto.CarToBuyDTO;
import pl.bialek.api.dto.mapper.CarDtoMapper;
import pl.bialek.api.dto.mapper.CarPurchaseRequestDtoMapper;
import pl.bialek.business.CarPurchaseService;
import pl.bialek.domain.Salesman;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class PurchaseController {

    public static final String PURCHASE = "/purchase";


    private final CarPurchaseService carPurchaseService;
    private final CarPurchaseRequestDtoMapper carPurchaseRequestDtoMapper;
    private final CarDtoMapper carDtoMapper;


    @GetMapping(value = PURCHASE)
    public ModelAndView carPurchasePage(){
        Map<String,? > model = initCarPurchaseData();
        return new ModelAndView("car_purchase", model);
    }

    private Map<String, ?> initCarPurchaseData() {
        List<CarToBuyDTO> carList = carPurchaseService.availableCars()
                .stream()
                .map(carDtoMapper::mapToDTO)
                .toList();

        List<String> carVins = carList.stream().map(CarToBuyDTO::getVin).toList();

        Stream<String> salesmenPesels = carPurchaseService.availableSalesmen().stream().map(Salesman::getPesel);

        return Map.of(
                "availableCarsDTO", carList,
                "availableCarVinsDTO", carVins,
                "availableSalesmenPeselsDTO", salesmenPesels,
                "CarPurchaseRequestDTO", CarPurchaseRequestDTO.buildDefaultCustomer()
        );

    }
}
