package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.model.ObatModel;
import java.util.List;
import java.util.Optional;

// Service for Obat
public interface ObatService {
    void addObat(ObatModel obat);
    List<ObatModel> getListObat();
    String generateKode(ObatModel obat);
    Optional<ObatModel> getObatByNoRegistrasiObat(String noReg);
    String convertIdJenisToString(Long idJenis);
}
