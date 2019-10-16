package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.model.JenisModel;
import apap.tugaspemrograman.sibat.repository.JenisDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JenisServiceImpl implements JenisService {

    @Autowired
    JenisDb jenisDb;

    @Override
    public Optional<JenisModel> getJenisById(Long idJenis) {
        return jenisDb.findById(idJenis);
    }

    @Override
    public List<JenisModel> findAllJenis() {
        return jenisDb.findAll();
    }
}
