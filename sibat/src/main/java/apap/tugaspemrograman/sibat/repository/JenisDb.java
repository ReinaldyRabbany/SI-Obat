package apap.tugaspemrograman.sibat.repository;

import apap.tugaspemrograman.sibat.model.JenisModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface JenisDb extends JpaRepository<JenisModel, Long> {
    Optional<JenisModel> findById(Long idJenis);
}
