package apap.tugaspemrograman.sibat.controller;

import apap.tugaspemrograman.sibat.model.ObatModel;
import apap.tugaspemrograman.sibat.service.ObatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.List;

@Controller
public class ObatController {

    //@Qualifier("ObatServiceImpl")
    @Autowired
    private ObatService obatService;

    @RequestMapping("/")
    public String home(Model model) {
        List<ObatModel> obatList = obatService.getListObat();

        model.addAttribute("list_obat", obatList);
        model.addAttribute("page_title", "Daftar Obat");

        return "home";
    }

    @RequestMapping(value = "/obat/tambah", method = RequestMethod.GET)
    public String tambahObatFormPage(Model model) {
        ObatModel newObat = new ObatModel();

        model.addAttribute("namaObat", newObat);
        model.addAttribute("page_title", "Tambah Obat");

        return "form-tambah-obat";
    }

    @RequestMapping(value = "/obat/tambah", method = RequestMethod.POST)
    public String tambahObatSubmit(@ModelAttribute ObatModel obat, Model model) {
        String kodeObat = obatService.generateKode(obat);
        obat.setKode(kodeObat);

        obatService.addObat(obat);

        model.addAttribute("namaObat", obat.getNama());
        model.addAttribute("kodeObat", obat.getKode());
        model.addAttribute("page_title", "Tambah Obat");

        return "tambah-obat";
    }

    @RequestMapping(value = "/obat/view", method = RequestMethod.GET)
    public String viewDetailObat(@RequestParam(value = "noReg") String nomorRegistrasi, Model model) {
        ObatModel obat = obatService.getObatByNoRegistrasiObat(nomorRegistrasi).get();
        String jenis = obatService.convertIdJenisToString(obat.getIdJenis());

        model.addAttribute("page_title", "Detail View Obat");
        model.addAttribute("jenis", jenis);
        model.addAttribute("obat", obat);

        return "view-obat";
    }

    @RequestMapping(value = "/obat/ubah", method = RequestMethod.GET)
    public String ubahObatFormPage(@RequestParam(value = "id") Long idObat, Model model) {

        try {
            ObatModel obat = obatService.getObatByIdObat(idObat).get();
        } catch (NoSuchElementException e) {
            return "home";
        }

        ObatModel existingObat = obatService.getObatByIdObat(idObat).get();

        model.addAttribute("obat", existingObat);
        model.addAttribute("page_title", "Ubah Obat");

        return "form-ubah-obat";
    }

    @RequestMapping(value = "/obat/ubah", method = RequestMethod.POST)
    public String ubahObatSubmit(@RequestParam(value = "id") Long idObat, @ModelAttribute ObatModel obat, Model model) {
//        try {
//            ObatModel obat2 = obatService.getObatByIdObat(obat.getIdObat()).get();
//        } catch (NoSuchElementException e) {
//            return "home";
//        }

        ObatModel newObat = obatService.ubahObat(obat);

        String kodeObat = obatService.generateKode(newObat);
        newObat.setKode(kodeObat);
        obat.setKode(kodeObat);

        System.out.println(kodeObat);

        model.addAttribute("namaObat", obat.getNama());
        model.addAttribute("kodeObat", obat.getKode());
        model.addAttribute("page_title", "Ubah Obat");

        return "ubah-obat";
    }
}
