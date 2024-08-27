package pl.bialek.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class CarServiceRequestInputData  {
    Customer customer;
    CarToService carToService;
    String customerComment;
}
