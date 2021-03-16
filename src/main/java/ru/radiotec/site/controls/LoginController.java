package ru.radiotec.site.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.radiotec.site.entity.BookSec;
import ru.radiotec.site.entity.Journals;
import ru.radiotec.site.entity.User;
import ru.radiotec.site.services.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value={"/","/ru/","/en/"})
public class LoginController {

    @Autowired
    private JournalsService journalsService;

    @Autowired
    private BookSecService bookSecService;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    private String getLoginPage(Model model){
        init_menu(model);
        return "login";
    }

    @GetMapping("/register")
    private String getRegisterPage(Model model){
        init_menu(model);
        model.addAttribute("userRepo", new UserRepo());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid UserRepo userRepo, BindingResult bindingResult, Model model){
        init_menu(model);
        if(bindingResult.hasErrors()){
            return "register";
        }
        if(!userRepo.getPassword().equals(userRepo.getRepeatPassword())){
            bindingResult.rejectValue("password","","Введенные пароли не совпадают");
            return "register";
        }
        userService.create(userRepo);
        return "redirect:/login";
    }

    private void init_menu(Model model){
        ArrayList<Journals> journals = new ArrayList<>(journalsService.getAllJournals());
        ArrayList<BookSec> bookSec = new ArrayList<>(bookSecService.getAllBookSec());
        Map<String, ArrayList> attributes = new HashMap<>();
        attributes.put("journals", journals);
        attributes.put("booksecs", bookSec);
        model.addAllAttributes(attributes);
    }
}
