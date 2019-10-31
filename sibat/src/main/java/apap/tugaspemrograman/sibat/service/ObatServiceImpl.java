package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.repository.ObatDb;
import apap.tugaspemrograman.sibat.model.ObatModel;
import apap.tugaspemrograman.sibat.model.GudangModel;

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

        String strBentuk = "01";
        if (obat.getBentuk().equals("Kapsul")||obat.getBentuk().equals("kapsul")) {
            strBentuk = "02";
        } else if (obat.getBentuk().equals("Tablet")||obat.getBentuk().equals("tablet")) {
            strBentuk = "03";
        }

        Date date = obat.getTanggalTerbit();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        year=year+5;
        String strYear = String.valueOf(year);

        String randoms = generateRandomStr();

        int yearInput = Calendar.getInstance().get(Calendar.YEAR);
        String strYearInput = String.valueOf(yearInput);

        String kode = String.valueOf(obat.getJenis().getId())+strBentuk+strYearInput+strYear+randoms;

        if (validateKode(kode)) {
            return kode;
        } else {
            while (!validateKode(kode)) {
                randoms = generateRandomStr();
                kode = String.valueOf(obat.getJenis().getId())+strBentuk+strYearInput+strYear+randoms;
            }
            return kode;
        }
    }

    @Override
    public String generateRandomStr() {
        String capital = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(2);

        for (int i = 0; i < 2; i++) {
            int index = (int)(capital.length() * Math.random());
            sb.append(capital.charAt(index));
        }
        return String.valueOf(sb);
    }

    @Override
    public boolean validateKode(String kode) {
        List<ObatModel> listObat = getListObat();
        for (int i=0; i<listObat.size(); i++) {
            if (listObat.get(i).getKode().equals(kode)) {
                return false;
            }
        }
        return true;
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

    @Override
    public List<ObatModel> getExpiredObat(GudangModel gudang) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        cal.getTimeInMillis();
        cal.add(Calendar.YEAR, -5);

        List<ObatModel> listObat = gudang.getObatList();
        List<ObatModel> listResult = new ArrayList<>();

        for (int i = 0; i < listObat.size(); i++) {
            ObatModel thisObat = listObat.get(i);

            if (thisObat.getTanggalTerbit().before(cal.getTime())) {
                listResult.add(thisObat);
            }
        }
        return listResult;
    }

    @Override
    public void deleteObat(ObatModel obat) {
        obatDb.delete(obat);
    }

    @Override
    public void clearGudangList(ObatModel obat) {
        ObatModel obatData = obatDb.findById(obat.getIdObat()).get();
        obatData.getGudangList().clear();
    }
}
