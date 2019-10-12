package apap.tugaspemrograman.sibat.controller;

import apap.tugaspemrograman.sibat.model.ObatModel;
import apap.tugaspemrograman.sibat.service.ObatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
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
    public String addObatFormPage(Model model) {
        ObatModel newObat = new ObatModel();

        model.addAttribute("namaObat", newObat);
        model.addAttribute("page_title", "Tambah Obat");

        return "form-tambah-obat";
    }

    @RequestMapping(value = "/obat/tambah", method = RequestMethod.POST)
    public String addRestoranSubmit(@ModelAttribute ObatModel obat, Model model) {
        String kodeObat = obatService.generateKode(obat);
        System.out.println(kodeObat);
        obat.setKode(kodeObat);

        obatService.addObat(obat);

        model.addAttribute("namaObat", obat.getNama());
        model.addAttribute("kodeObat", obat.getKode());
        model.addAttribute("page_title", "Add Restoran");

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
}
