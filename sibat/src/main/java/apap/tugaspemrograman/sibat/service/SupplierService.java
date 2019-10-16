package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.model.SupplierModel;
import java.util.List;
import java.util.Optional;

public interface SupplierService {
    Optional<SupplierModel> getSupplerById(Long id);
    List<SupplierModel> findAllSupplier();
}
