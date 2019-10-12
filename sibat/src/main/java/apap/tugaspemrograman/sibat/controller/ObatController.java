package apap.tugaspemrograman.sibat.controller;

import apap.tugaspemrograman.sibat.model.ObatModel;
import apap.tugaspemrograman.sibat.service.ObatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
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

}
