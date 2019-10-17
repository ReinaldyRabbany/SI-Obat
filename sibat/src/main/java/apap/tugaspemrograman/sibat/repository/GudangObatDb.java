package apap.tugaspemrograman.sibat.repository;

import apap.tugaspemrograman.sibat.model.GudangObatModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GudangObatDb extends JpaRepository<GudangObatModel, Long> {
    //Optional<GudangObatModel> findById(Long idGudangObat);
}
