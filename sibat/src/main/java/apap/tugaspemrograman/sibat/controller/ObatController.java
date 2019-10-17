package apap.tugaspemrograman.sibat.controller;

import apap.tugaspemrograman.sibat.model.GudangModel;
import apap.tugaspemrograman.sibat.model.JenisModel;
import apap.tugaspemrograman.sibat.model.ObatModel;
import apap.tugaspemrograman.sibat.model.SupplierModel;
import apap.tugaspemrograman.sibat.service.GudangService;
import apap.tugaspemrograman.sibat.service.JenisService;
import apap.tugaspemrograman.sibat.service.ObatService;
import apap.tugaspemrograman.sibat.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;

@Controller
public class ObatController {

    //@Qualifier("ObatServiceImpl")
    @Autowired
    private ObatService obatService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private JenisService jenisService;

    @Autowired
    private GudangService gudangService;

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
        SupplierModel supplier = new SupplierModel();
        List<SupplierModel> supplierList = supplierService.findAllSupplier();

        model.addAttribute("namaObat", newObat);
        model.addAttribute("page_title", "Tambah Obat");
        model.addAttribute("listAllSupplier", supplierList);
        model.addAttribute("supplier", supplier);

        return "form-tambah-obat";
    }

    @RequestMapping(value = "/obat/tambah", method = RequestMethod.POST)
    public String tambahObatSubmit(@ModelAttribute ObatModel obat, @ModelAttribute SupplierModel supplier, Model model) {
        String kodeObat = obatService.generateKode(obat);
        obat.setKode(kodeObat);

        SupplierModel supplierModel = supplierService.getSupplerById(supplier.getId()).get();
        obat.getSupplierList().add(supplierModel);

        obatService.addObat(obat);

        model.addAttribute("namaObat", obat.getNama());
        model.addAttribute("kodeObat", obat.getKode());
        model.addAttribute("page_title", "Tambah Obat");

        return "tambah-obat";
    }

    @RequestMapping(value = "/obat/view", method = RequestMethod.GET)
    public String viewDetailObat(@RequestParam(value = "noReg") String nomorRegistrasi, Model model) {
        ObatModel obat = obatService.getObatByNoRegistrasiObat(nomorRegistrasi).get();
        String jenis = obatService.convertIdJenisToString(obat.getJenis().getId());
        List<GudangModel> gudangList = obat.getGudangList();
        String gudang = "";
        for (int i = 0; i < gudangList.size(); i++) {
            if (gudangList.size()==0){
                break;
            }
            if (i>=1||(i==gudangList.size()-1&&gudangList.size()>1)) {
                gudang += ", ";
            }
            gudang = gudang + gudangList.get(i).getNama();
        }

        model.addAttribute("page_title", "Detail View Obat");
        model.addAttribute("jenis", jenis);
        model.addAttribute("obat", obat);
        model.addAttribute("gudang", gudang);

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

    @RequestMapping(value = "/obat/filters")
    public String filterObatView(Model model) {

        List<SupplierModel> listSupplier = supplierService.findAllSupplier();
        List<JenisModel> listJenis = jenisService.findAllJenis();
        List<GudangModel> listGudang = gudangService.getListGudang();

        model.addAttribute("listAllSupplier", listSupplier);
        model.addAttribute("listAllJenis", listJenis);
        model.addAttribute("listAllGudang", listGudang);

        return "filter-obat-view";
    }

    @RequestMapping(value = "/obat/filter")
    public String filterObat(@RequestParam(value = "idGudang", required=false, defaultValue="") Long idGudang,
                             @RequestParam(value = "idSupplier", required=false, defaultValue="") Long idSupplier,
                             @RequestParam(value = "idJenis", required=false, defaultValue="") Long idJenis,
                             Model model) {

        List<ObatModel> listResult = new ArrayList<>();

        if (idSupplier!=null&&idGudang!=null&&idJenis!=null) {
            SupplierModel supplier = supplierService.getSupplerById(idSupplier).get();
            List<ObatModel> listObatSupplier = supplier.getObatList();
            model.addAttribute("supplier", supplier.getNama());

            GudangModel gudang = gudangService.getGudangById(idGudang).get();
            List<ObatModel> listObatGudang = gudang.getObatList();
            model.addAttribute("nama_gudang", gudang.getNama());

            JenisModel jenis = jenisService.getJenisById(idJenis).get();
            List<ObatModel> listObatJenis = jenis.getListObat();
            model.addAttribute("jenis", jenis.getNama());

            listObatSupplier.retainAll(listObatGudang);
            listObatSupplier.retainAll(listObatJenis);
            listResult = listObatSupplier;
        }
        if (idSupplier==null&&idGudang!=null&&idJenis!=null) {
            GudangModel gudang = gudangService.getGudangById(idGudang).get();
            List<ObatModel> listObatGudang = gudang.getObatList();
            model.addAttribute("gudang", gudang.getNama());

            JenisModel jenis = jenisService.getJenisById(idJenis).get();
            List<ObatModel> listObatJenis = jenis.getListObat();
            model.addAttribute("jenis", jenis.getNama());

            model.addAttribute("supplier", "");

            listObatGudang.retainAll(listObatJenis);
            listResult = listObatGudang;
        }
        if (idGudang == null&&idJenis!=null&&idSupplier!=null) {
            SupplierModel supplier = supplierService.getSupplerById(idSupplier).get();
            List<ObatModel> listObatSupplier = supplier.getObatList();
            model.addAttribute("supplier", supplier.getNama());

            JenisModel jenis = jenisService.getJenisById(idJenis).get();
            List<ObatModel> listObatJenis = jenis.getListObat();
            model.addAttribute("jenis", jenis.getNama());

            model.addAttribute("gudang", "");

            listObatSupplier.retainAll(listObatJenis);
            listResult = listObatSupplier;
        }
        if (idJenis == null&&idGudang!=null&&idSupplier!=null) {
            SupplierModel supplier = supplierService.getSupplerById(idSupplier).get();
            List<ObatModel> listObatSupplier = supplier.getObatList();
            model.addAttribute("supplier", supplier.getNama());

            GudangModel gudang = gudangService.getGudangById(idGudang).get();
            List<ObatModel> listObatGudang = gudang.getObatList();
            model.addAttribute("gudang", gudang.getNama());

            model.addAttribute("jenis", "");

            listObatSupplier.retainAll(listObatGudang);
            listResult = listObatSupplier;
        }
        if (idSupplier != null) {
            SupplierModel supplier = supplierService.getSupplerById(idSupplier).get();
            List<ObatModel> listObatSupplier = supplier.getObatList();
            model.addAttribute("supplier", supplier.getNama());

            model.addAttribute("gudang", "");
            model.addAttribute("jenis", "");

            listResult = listObatSupplier;
        }
        if (idGudang != null) {
            GudangModel gudang = gudangService.getGudangById(idGudang).get();
            List<ObatModel> listObatGudang = gudang.getObatList();
            model.addAttribute("gudang", gudang.getNama());

            model.addAttribute("supplier", "");
            model.addAttribute("jenis", "");

            listResult = listObatGudang;
        }
        if (idJenis != null) {
            JenisModel jenis = jenisService.getJenisById(idJenis).get();
            List<ObatModel> listObatJenis = jenis.getListObat();
            model.addAttribute("jenis", jenis.getNama());

            model.addAttribute("gudang", "");

            model.addAttribute("supplier", "");

            listResult = listObatJenis;
        }

        model.addAttribute("list_result", listResult);

        List<SupplierModel> listSupplier = supplierService.findAllSupplier();
        List<JenisModel> listJenis = jenisService.findAllJenis();
        List<GudangModel> listGudang = gudangService.getListGudang();

        model.addAttribute("listAllSupplier", listSupplier);
        model.addAttribute("listAllJenis", listJenis);
        model.addAttribute("listAllGudang", listGudang);

        return "filter-obat-view-result";
    }

    @RequestMapping(value = "/obat/delete")
    public String deleteMenu(@RequestParam(value = "idObat") Long id, Model model) {
        ObatModel obat = obatService.getObatByIdObat(id).get();

        model.addAttribute("obat", obat);
        model.addAttribute("page_title", "Delete Obat");
        obatService.deleteObat(obat);

        return "hapus-obat";
    }
}
