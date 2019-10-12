package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.model.ObatModel;
import java.util.List;

// Service for Obat
public interface ObatService {
    void addObat(ObatModel obat);
    List<ObatModel> getListObat();
}
