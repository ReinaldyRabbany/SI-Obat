package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.model.JenisModel;
import java.util.List;
import java.util.Optional;

public interface JenisService {
    Optional<JenisModel> getJenisById(Long idJenis);
    List<JenisModel> findAllJenis();
}
