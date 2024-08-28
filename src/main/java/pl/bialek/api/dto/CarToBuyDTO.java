package pl.bialek.api.dto;

import lombok.*;
import pl.bialek.domain.Invoice;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarToBuyDTO {
    String vin;
    String brand;
    String model;
    Integer year;
    String color;
    BigDecimal price;
}
