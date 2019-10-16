package apap.tugaspemrograman.sibat.repository;

import apap.tugaspemrograman.sibat.model.SupplierModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierDb extends JpaRepository<SupplierModel, Long> {
    Optional<SupplierModel> findById(Long idSupplier);
}
