package pl.bialek.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.bialek.api.dto.CarToBuyDTO;
import pl.bialek.api.dto.MechanicDTO;
import pl.bialek.api.dto.SalesmanDTO;
import pl.bialek.api.dto.mapper.CarMapper;
import pl.bialek.api.dto.mapper.MechanicsMapper;
import pl.bialek.api.dto.mapper.SalesmanMapper;
import pl.bialek.business.CarPurchaseService;
import pl.bialek.business.CarServiceRequestService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesmanController {
    public static final String SALESMAN = "/salesman";

    private final CarPurchaseService carPurchaseService;
    private final CarServiceRequestService carServiceRequestService;

    private final CarMapper carMapper;
    private final MechanicsMapper mechanicsMapper;
    private final SalesmanMapper salesmanMapper;

    @GetMapping(value = SALESMAN)
    public String homePage(Model model) {
        List<CarToBuyDTO> availableCarDTO = carPurchaseService.availableCars().stream()
                .map(carMapper::mapToDTO)
                .toList();

        List<SalesmanDTO> availableSalesmen = carPurchaseService.availableSalesmen().stream()
                .map(salesmanMapper::mapToDTO)
                .toList();

        List<MechanicDTO> availableMechanics = carServiceRequestService.availableMechanics()
                .stream().map(mechanicsMapper::mapToDTO).toList();


        model.addAttribute("availableCarDTO", availableCarDTO);
        model.addAttribute("availableSalesmenDTO", availableSalesmen);
        model.addAttribute("availableMechanicDTO", availableMechanics);
        return "salesman_portal";
    }
}
