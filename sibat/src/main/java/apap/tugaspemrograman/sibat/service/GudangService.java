package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.model.GudangModel;
import java.util.List;
import java.util.Optional;

public interface GudangService {
    void addObat(GudangModel gudang);
    List<GudangModel> getListGudang();
    Optional<GudangModel> getGudangById(Long id);

}
