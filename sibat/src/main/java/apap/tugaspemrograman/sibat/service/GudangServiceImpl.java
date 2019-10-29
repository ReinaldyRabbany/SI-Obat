package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.repository.GudangDb;
import apap.tugaspemrograman.sibat.model.GudangModel;
import apap.tugaspemrograman.sibat.model.ObatModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GudangServiceImpl implements GudangService {
    @Autowired
    GudangDb gudangDb;

    @Override
    public void addGudang(GudangModel gudang) {
        gudangDb.save(gudang);
    }

    @Override
    public List<GudangModel> getListGudang() {
        return gudangDb.findAll();
    }

    @Override
    public Optional<GudangModel> getGudangById(Long id) { return gudangDb.findById(id); }

    @Override
    public void hapusGudang(GudangModel gudang) { gudangDb.delete(gudang); }

    @Override
    public void addObat(ObatModel obat) {
        return;
    }

    @Override
    public void clearObatList(GudangModel gudang) {
        GudangModel gudangData = gudangDb.findById(gudang.getId()).get();
        gudangData.getObatList().clear();
    }
}
