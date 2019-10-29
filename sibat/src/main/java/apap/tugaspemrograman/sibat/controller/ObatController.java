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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/obat/bonus")
    public String bonus(Model model) {
        List<ObatModel> obatList = obatService.getListObat();
        List<Integer> listJmlSupplier = new ArrayList<>();

        for(int i=0; i<obatList.size();i++) {
            listJmlSupplier.add(obatList.get(i).getSupplierList().size());
        }

        model.addAttribute("list_obat", obatList);
        model.addAttribute("page_title", "Bonus");
        model.addAttribute("listJmlSupplier", listJmlSupplier);

        return "bonus";
    }

    @RequestMapping(value = "/obat/tambah", method = RequestMethod.GET)
    public String tambahObatFormPage(Model model) {
        ObatModel newObat = new ObatModel();
        SupplierModel supplier = new SupplierModel();
        newObat.getSupplierList().add(supplier);

        List<SupplierModel> supplierList = supplierService.findAllSupplier();

        model.addAttribute("obat", newObat);
        model.addAttribute("page_title", "Tambah Obat");
        model.addAttribute("listAllSupplier", supplierList);
        model.addAttribute("supplier", supplier);
        model.addAttribute("listAllJenis", jenisService.findAllJenis());

        return "form-tambah-obat";
    }

    @RequestMapping(value = "/obat/tambah", method = RequestMethod.POST, params = {"save"})
    public String tambahObatSubmit(@ModelAttribute ObatModel obat, HttpServletRequest request, ModelMap model) {

        String kodeObat = obatService.generateKode(obat);
        obat.setKode(kodeObat);

        obatService.addObat(obat);

        model.clear();

        model.addAttribute("obat", obat);
        model.addAttribute("page_title", "Tambah Obat");

        return "tambah-obat";
    }

    @RequestMapping(value = "/obat/tambah", method = RequestMethod.POST, params = {"tambahSupplier"})
    public String tambahObatSubmitSuppliers(@ModelAttribute ObatModel obat, Model model) {
        SupplierModel supplierModel = new SupplierModel();
        obat.getSupplierList().add(supplierModel);

        List<SupplierModel> supplierList = supplierService.findAllSupplier();

        model.addAttribute("listAllSupplier", supplierList);
        model.addAttribute("supplier", supplierModel);
        model.addAttribute("listAllJenis", jenisService.findAllJenis());
        model.addAttribute("obat", obat);
        model.addAttribute("page_title", "Tambah Obat");

        return "form-tambah-obat";
    }

    @RequestMapping(value = "/obat/view", method = RequestMethod.GET)
    public String viewDetailObat(@RequestParam(value = "noReg") String nomorRegistrasi, Model model) {
        ObatModel obat = obatService.getObatByNoRegistrasiObat(nomorRegistrasi).get();
        String jenis = obatService.convertIdJenisToString(obat.getJenis().getId());
        List<GudangModel> gudangList = obat.getGudangList();
        List<SupplierModel> supplierList = obat.getSupplierList();
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
        String supplier = "";
        for (int i = 0; i < supplierList.size(); i++) {
            if (supplierList.size()==0){
                break;
            }
            if (i>=1||(i==supplierList.size()-1&&supplierList.size()>1)) {
                supplier += ", ";
            }
            supplier = supplier + supplierList.get(i).getNama();
        }

        model.addAttribute("page_title", "Detail Obat");
        model.addAttribute("jenis", jenis);
        model.addAttribute("obat", obat);
        model.addAttribute("gudang", gudang);
        model.addAttribute("supplier", supplier);

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

    @RequestMapping(value = "/obat/filter/", method = RequestMethod.GET)
    public String filterObatView(Model model) {

        List<SupplierModel> listSupplier = supplierService.findAllSupplier();
        List<JenisModel> listJenis = jenisService.findAllJenis();
        List<GudangModel> listGudang = gudangService.getListGudang();

        model.addAttribute("listAllSupplier", listSupplier);
        model.addAttribute("listAllJenis", listJenis);
        model.addAttribute("listAllGudang", listGudang);
        model.addAttribute("page_title", "Filter");

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
            model.addAttribute("supplier_nama", supplier.getNama());

            GudangModel gudang = gudangService.getGudangById(idGudang).get();
            List<ObatModel> listObatGudang = gudang.getObatList();
            model.addAttribute("gudang_nama", gudang.getNama());

            JenisModel jenis = jenisService.getJenisById(idJenis).get();
            List<ObatModel> listObatJenis = jenis.getListObat();
            model.addAttribute("jenis_nama", jenis.getNama());

            listObatSupplier.retainAll(listObatGudang);
            listObatSupplier.retainAll(listObatJenis);
            listResult = listObatSupplier;
        }
        else if (idSupplier==null&&idGudang!=null&&idJenis!=null) {
            GudangModel gudang = gudangService.getGudangById(idGudang).get();
            List<ObatModel> listObatGudang = gudang.getObatList();
            model.addAttribute("gudang_nama", gudang.getNama());

            JenisModel jenis = jenisService.getJenisById(idJenis).get();
            List<ObatModel> listObatJenis = jenis.getListObat();
            model.addAttribute("jenis_nama", jenis.getNama());

            model.addAttribute("supplier_nama", "");

            listObatGudang.retainAll(listObatJenis);
            listResult = listObatGudang;
        }
        else if (idGudang == null&&idJenis!=null&&idSupplier!=null) {
            SupplierModel supplier = supplierService.getSupplerById(idSupplier).get();
            List<ObatModel> listObatSupplier = supplier.getObatList();
            JenisModel jenis = jenisService.getJenisById(idJenis).get();
            List<ObatModel> listObatJenis = jenis.getListObat();
            model.addAttribute("supplier_nama", supplier.getNama());
            model.addAttribute("jenis_nama", jenis.getNama());
            model.addAttribute("gudang_nama", "");
            listObatSupplier.retainAll(listObatJenis);
            listResult = listObatSupplier;
        }
        else if (idJenis == null&&idGudang!=null&&idSupplier!=null) {
            SupplierModel supplier = supplierService.getSupplerById(idSupplier).get();
            List<ObatModel> listObatSupplier = supplier.getObatList();
            GudangModel gudang = gudangService.getGudangById(idGudang).get();
            List<ObatModel> listObatGudang = gudang.getObatList();
            model.addAttribute("supplier_nama", supplier.getNama());
            model.addAttribute("gudang_nama", gudang.getNama());
            model.addAttribute("jenis_nama", "");
            listObatSupplier.retainAll(listObatGudang);
            listResult = listObatSupplier;
        }
        else if (idSupplier != null) {
            SupplierModel supplier = supplierService.getSupplerById(idSupplier).get();
            List<ObatModel> listObatSupplier = supplier.getObatList();
            model.addAttribute("supplier_nama", supplier.getNama());
            model.addAttribute("gudang_nama", "");
            model.addAttribute("jenis_nama", "");
            listResult = listObatSupplier;
        }
        else if (idGudang != null) {
            GudangModel gudang = gudangService.getGudangById(idGudang).get();
            List<ObatModel> listObatGudang = gudang.getObatList();
            model.addAttribute("gudang_nama", gudang.getNama());
            model.addAttribute("supplier_nama", "");
            model.addAttribute("jenis_nama", "");
            listResult = listObatGudang;
        }
        else if (idJenis != null) {
            JenisModel jenis = jenisService.getJenisById(idJenis).get();
            List<ObatModel> listObatJenis = jenis.getListObat();
            model.addAttribute("jenis_nama", jenis.getNama());
            model.addAttribute("gudang_nama", "");
            model.addAttribute("supplier_nama", "");
            listResult = listObatJenis;
        }
        else if (idSupplier==null&&idGudang==null&&idJenis==null) {
            listResult = obatService.getListObat();
        }

        model.addAttribute("list_result", listResult);

        List<SupplierModel> listSupplier = supplierService.findAllSupplier();
        List<JenisModel> listJenis = jenisService.findAllJenis();
        List<GudangModel> listGudang = gudangService.getListGudang();

        model.addAttribute("listAllSupplier", listSupplier);
        model.addAttribute("listAllJenis", listJenis);
        model.addAttribute("listAllGudang", listGudang);
        model.addAttribute("page_title", "Filter");

        return "filter-obat-view-result";
    }

    @RequestMapping(value = "/obat/delete", method = RequestMethod.POST)
    public String deleteMenu(@RequestParam(value = "idObat") Long id, Model model) {
        ObatModel obat = obatService.getObatByIdObat(id).get();

        model.addAttribute("obat", obat);
        model.addAttribute("page_title", "Delete Obat");
        obatService.deleteObat(obat);

        return "hapus-obat";
    }
}
