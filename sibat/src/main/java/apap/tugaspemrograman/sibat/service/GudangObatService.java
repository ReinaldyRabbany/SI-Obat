package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.model.GudangObatModel;
import java.util.Optional;

public interface GudangObatService {
    void addGudangObat(GudangObatModel gudangObat);
    Optional<GudangObatModel> getGudangObatById(Long idGudangObat);
}
