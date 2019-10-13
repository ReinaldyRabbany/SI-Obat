package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.repository.GudangDb;
import apap.tugaspemrograman.sibat.model.GudangModel;
import apap.tugaspemrograman.sibat.model.ObatModel;
import apap.tugaspemrograman.sibat.service.ObatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GudangServiceImpl implements GudangService {
    @Autowired
    GudangDb gudangDb;

    @Autowired
    private ObatService obatService;

    @Override
    public void addObat(GudangModel gudang) {
        gudangDb.save(gudang);
    }

    @Override
    public List<GudangModel> getListGudang() {
        return gudangDb.findAll();
    }

    @Override
    public Optional<GudangModel> getGudangById(Long id) {
        return gudangDb.findById(id);
    }
}
