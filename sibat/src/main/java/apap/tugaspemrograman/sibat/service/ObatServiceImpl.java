package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.repository.ObatDb;

import apap.tugaspemrograman.sibat.model.ObatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObatServiceImpl implements ObatService {
    @Autowired
    private ObatDb obatDb;

    @Override
    public void addObat(ObatModel obat) { obatDb.save(obat); }

    @Override
    public List<ObatModel> getListObat() { return obatDb.findAll(); }
}
