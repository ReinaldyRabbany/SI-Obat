package apap.tugaspemrograman.sibat.controller;

import apap.tugaspemrograman.sibat.model.GudangModel;
import apap.tugaspemrograman.sibat.model.ObatModel;
import apap.tugaspemrograman.sibat.service.GudangService;
import apap.tugaspemrograman.sibat.service.ObatService;
import apap.tugaspemrograman.sibat.repository.ObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GudangController {

    @Autowired
    private GudangService gudangService;

    @Autowired
    private ObatService obatService;

    @Autowired
    private ObatDb obatDb;

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
        ObatModel obat = new ObatModel();
        List<ObatModel> listObat = new ArrayList<>();

        for (int i = 0; i < obatService.getListObat().size(); i++) {
            obat = obatService.getListObat().get(i);
            listObat.add(obat);
        }

        ObatModel assignedObat = new ObatModel();
        //GudangObatModel gudangObat = new GudangObatModel();

        model.addAttribute("page_title", "Detail View Gudang");
        model.addAttribute("gudang", gudang);
        model.addAttribute("list_obat", gudang.getObatList());
        model.addAttribute("list_obat_size", gudang.getObatList().size());
        model.addAttribute("listAllObat", listObat);
        model.addAttribute("obat", assignedObat);
        //model.addAttribute("gudangObat", gudangObat);

        return "view-gudang";
    }

    @RequestMapping(value = "/gudang/tambah-obat", method = RequestMethod.POST)
    public String tambahObatDiGudang(@ModelAttribute ObatModel obat, @ModelAttribute GudangModel gudang, Model model) {
        ObatModel obatData = obatService.getObatByIdObat(obat.getIdObat()).get();
        GudangModel gudangData = gudangService.getGudangById(gudang.getId()).get();

        List<GudangModel> saveGudangList = obatData.getGudangList();

        saveGudangList.add(gudangData);
        obatData.setGudangList(saveGudangList);
        obatDb.save(obatData);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'pukul' HH:mm:ss z");
        Date theDate = new Date(System.currentTimeMillis());

        model.addAttribute("page_title", "Tambah Obat ke Gudang");
        model.addAttribute("obat", obatData);
        model.addAttribute("gudang", gudangData);
        model.addAttribute("date", formatter.format(theDate));

        return "tambah-obat-gudang";
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

    @RequestMapping(value = "/gudang/cari")
    public String viewExpiredObatinGudang(Model model) {
        List<GudangModel> gudangList = gudangService.getListGudang();
        GudangModel gudang = new GudangModel();

        model.addAttribute("list_gudang", gudangList);
        model.addAttribute("page_title", "Cari");
        model.addAttribute("gudang", gudang);

        return "form-expired-obat";
    }

    @RequestMapping(value = "/gudang/expired-obat")
    public String viewExpiredObatinGudangLihat(@RequestParam(value = "idGudang", required=false, defaultValue="") Long idGudang, Model model) {
        if(idGudang==null) {
            List<GudangModel> gudangList = gudangService.getListGudang();
            GudangModel gudang = new GudangModel();

            model.addAttribute("list_gudang", gudangList);
            model.addAttribute("page_title", "Cari");
            model.addAttribute("gudang", gudang);

            return "form-expired-obat";
        }
        
        GudangModel gudang = gudangService.getGudangById(idGudang).get();
        List<GudangModel> gudangList = gudangService.getListGudang();

        List<ObatModel> listResult = obatService.getExpiredObat(gudang);

        model.addAttribute("list_result", listResult);
        model.addAttribute("gudang", gudang);
        model.addAttribute("list_gudang", gudangList);
        model.addAttribute("page_title", "Cari");

        if(listResult.size()==0) {
            return "no-expired-obat";
        }

        return "form-expired-obat-result";
    }
}
