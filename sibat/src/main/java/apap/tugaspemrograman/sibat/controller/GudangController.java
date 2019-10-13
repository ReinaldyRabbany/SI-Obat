package apap.tugaspemrograman.sibat.controller;

import apap.tugaspemrograman.sibat.model.GudangModel;
import apap.tugaspemrograman.sibat.model.ObatModel;
import apap.tugaspemrograman.sibat.service.GudangService;
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
public class GudangController {

    @Autowired
    private GudangService gudangService;

    @RequestMapping("/gudang")
    public String gudangList(Model model) {
        List<GudangModel> gudangList = gudangService.getListGudang();

        model.addAttribute("list_gudang", gudangList);
        model.addAttribute("page_title", "Daftar Gudang");

        return "daftar-gudang";
    }

    @RequestMapping(value = "/gudang/view", method = RequestMethod.GET)
    public String viewDetailGudang(@RequestParam(value = "idGudang") Long idGudang, Model model) {
        GudangModel gudang = gudangService.getGudangById(idGudang).get();

        model.addAttribute("page_title", "Detail View Gudang");
        model.addAttribute("gudang", gudang);
        model.addAttribute("list_obat", gudang.getObatList());
        model.addAttribute("list_obat_size", gudang.getObatList().size());

        return "view-gudang";
    }

    @RequestMapping(value = "/gudang/tambah", method = RequestMethod.GET)
    public String tambahGudangFormPage(Model model) {
        GudangModel newGudang = new GudangModel();

        model.addAttribute("gudang", newGudang);
        model.addAttribute("page_title", "Tambah Gudang");

        return "form-tambah-gudang";
    }

    @RequestMapping(value = "/gudang/tambah", method = RequestMethod.POST)
    public String tambahGudangSubmit(@ModelAttribute GudangModel gudang, Model model) {

        gudangService.addGudang(gudang);

        model.addAttribute("namaGudang", gudang.getNama());
        model.addAttribute("idGudang", gudang.getId());
        model.addAttribute("page_title", "Tambah Gudang");

        return "tambah-gudang";
    }

    @RequestMapping(value = "/gudang/hapus/{idGudang}")
    public String hapusGudangSubmit(@PathVariable Long idGudang, Model model) {

        GudangModel gudang = gudangService.getGudangById(idGudang).get();

        model.addAttribute("namaGudang", gudang.getNama());
        model.addAttribute("idGudang", gudang.getId());
        model.addAttribute("page_title", "Hapus Gudang");

        if (gudang.getObatList().size() > 0) {
            return "tolak-hapus-gudang";
        }
        gudangService.hapusGudang(gudang);
        return "hapus-gudang";
    }
}
