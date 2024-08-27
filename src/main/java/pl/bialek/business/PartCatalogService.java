package pl.bialek.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bialek.business.dao.PartDAO;
import pl.bialek.domain.Part;

import java.util.Optional;
@Service
@AllArgsConstructor
public class PartCatalogService {
    private final PartDAO partDAO;

    public Part findPart(String partSerialNumber) {
        Optional<Part> part = partDAO.findBySerialNumber(partSerialNumber);
        if (part.isEmpty()) {
            throw new RuntimeException("Provided part with part serial number: [%s] doesn't exist".formatted(partSerialNumber));
        }
        return part.get();
    }
}
