package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.repository.ObatDb;
import apap.tugaspemrograman.sibat.model.ObatModel;
import apap.tugaspemrograman.sibat.model.GudangModel;
import apap.tugaspemrograman.sibat.service.GudangService;
import apap.tugaspemrograman.sibat.model.JenisModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ObatServiceImpl implements ObatService {
    @Autowired
    private ObatDb obatDb;

    @Override
    public void addObat(ObatModel obat) { obatDb.save(obat); }

    @Override
    public List<ObatModel> getListObat() { return obatDb.findAll(); }

    @Override
    public String generateKode(ObatModel obat) {
        System.out.println(obat.getBentuk());

        String strBentuk = "01";
        if (obat.getBentuk().equals("Kapsul")||obat.getBentuk().equals("kapsul")) {
            strBentuk = "02";
        } else {
            strBentuk = "03";
        }

        Date date = obat.getTanggalTerbit();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        year=year+5;
        String strYear = String.valueOf(year);

        String capital = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(2);

        for (int i = 0; i < 2; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int)(capital.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(capital.charAt(index));
        }
        System.out.println(obat.getJenis());
        String kode = String.valueOf(obat.getJenis().getId())+strBentuk+"2019"+strYear+sb.toString();
        return kode;
    }

    @Override
    public String convertIdJenisToString(Long idJenis) {
        if (idJenis == 1) {
            return "Generik";
        }
        return "Paten";
    }

    @Override
    public ObatModel ubahObat(ObatModel obat) {
        ObatModel targetObat = obatDb.findById(obat.getIdObat()).get();
        try {
            targetObat.setNama(obat.getNama());
            targetObat.setTanggalTerbit(obat.getTanggalTerbit());
            targetObat.setHarga(obat.getHarga());
            targetObat.setBentuk(obat.getBentuk());
            obatDb.save(targetObat);
            return targetObat;
        } catch (NullPointerException nullException) {
            return null;
        }
    }

    @Override
    public Optional<ObatModel> getObatByNoRegistrasiObat(String noReg) {
        return obatDb.findByNomorRegistrasi(noReg);
    }

    @Override
    public Optional<ObatModel> getObatByIdObat(Long idObat) {
        return obatDb.findById(idObat);
    }

//    public List<ObatModel> getExpiredObat(GudangModel gedung) {
//        List<ObatModel> listObat = gedung.getObatList();
//
//
//        for (ObatModel obat : listObat) {
//            if (obat.getTanggalTerbit() > )
//        }
//    }
}
