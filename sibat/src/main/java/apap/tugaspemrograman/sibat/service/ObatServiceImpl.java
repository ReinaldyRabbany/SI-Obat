package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.repository.ObatDb;

import apap.tugaspemrograman.sibat.model.ObatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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

        String kode = String.valueOf(obat.getJenis())+strBentuk+"2019"+strYear+sb.toString();
        return kode;
    }
}
