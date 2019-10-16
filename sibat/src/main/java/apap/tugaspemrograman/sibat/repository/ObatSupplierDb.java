package apap.tugaspemrograman.sibat.repository;

import apap.tugaspemrograman.sibat.model.ObatSupplierModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObatSupplierDb extends JpaRepository<ObatSupplierModel, Long> {
    Optional<ObatSupplierModel> findById(Long idObatSupplier);
}
