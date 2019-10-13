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
    Optional<ObatModel> getObatByIdObat(Long idObat);
    String convertIdJenisToString(Long idJenis);
    ObatModel ubahObat(ObatModel obat);
}
