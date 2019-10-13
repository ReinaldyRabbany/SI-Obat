package apap.tugaspemrograman.sibat.repository;

import apap.tugaspemrograman.sibat.model.GudangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GudangDb extends JpaRepository<GudangModel, Long> {
    Optional<GudangModel> findById(Long idGudang);
}
