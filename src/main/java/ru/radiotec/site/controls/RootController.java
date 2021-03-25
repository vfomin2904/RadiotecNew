package ru.radiotec.site.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.radiotec.site.entity.Number;
import ru.radiotec.site.entity.*;
import ru.radiotec.site.services.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.*;

@Controller
@RequestMapping(value={ "/"})
public class RootController {


    @GetMapping("/")
    public String getMainPage(){
        return "redirect:/ru/";
    }
}
