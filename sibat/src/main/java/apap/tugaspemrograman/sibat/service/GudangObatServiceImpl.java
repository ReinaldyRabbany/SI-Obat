package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.repository.GudangObatDb;
import apap.tugaspemrograman.sibat.model.GudangObatModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GudangObatServiceImpl implements GudangObatService {
    @Autowired
    GudangObatDb gudangObatDb;

    @Override
    public void addGudangObat(GudangObatModel gudangObat) {
        gudangObatDb.save(gudangObat);
    }

    @Override
    public Optional<GudangObatModel> getGudangObatById(Long idGudangObat) {
        return gudangObatDb.findById(idGudangObat);
    }
}
