package apap.tugaspemrograman.sibat.repository;

import apap.tugaspemrograman.sibat.model.GudangModel;
import apap.tugaspemrograman.sibat.model.ObatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObatDb extends JpaRepository<ObatModel, Long> {
    Optional<ObatModel> findById(Long idObat);
    Optional<ObatModel> findByNomorRegistrasi(String noReg);
}
