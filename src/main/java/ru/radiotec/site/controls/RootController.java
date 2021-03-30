package ru.radiotec.site.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.radiotec.site.entity.Number;
import ru.radiotec.site.entity.*;
import ru.radiotec.site.services.*;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.*;

@Controller
@RequestMapping(value={ "/"})
public class RootController {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/")
    public String getMainPage(){
        return "redirect:/ru/";
    }

    @PostMapping("/upload_ckeditor")
    public String CKEditor_upload(Model model, @RequestParam String CKEditorFuncNum, @RequestParam("upload") MultipartFile file) {

        if (!file.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/CKEditor/" + file.getOriginalFilename();
            model.addAttribute("path", "/uploads/CKEditor/" + file.getOriginalFilename());
//            if (file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(name));
                stream.write(bytes);
                stream.close();
                System.out.println("Файл загружен");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
//            }
        }

        model.addAttribute("callback", CKEditorFuncNum);

        return "ckeditor_upload";
    }

    @ResponseBody
    @PostMapping("/subscribe")
    public String getSubscribePage(Model model, Subscribe subscribe){
        subscribeService.create(subscribe);
        return "Ok";
    }
    @ResponseBody
    @PostMapping("/cart_add")
    public String addProduct(@RequestParam(required = true) int product, @RequestParam(required = true) String type, @RequestParam(required = true) int count){
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setType(type);
        List<Cart> currentCart = (List<Cart>)httpSession.getAttribute("cart");

        if(currentCart != null){
            currentCart.add(cart);
            httpSession.setAttribute("cart", currentCart);
        }else{
            List<Cart> carts = new ArrayList<>();
            carts.add(cart);
            httpSession.setAttribute("cart", carts);
        }

        return "Ok";
    }

    @ResponseBody
    @PostMapping("/cart_delete")
    public String addProduct(@RequestParam(required = true) int product, @RequestParam(required = true) String type){

        List<Cart> currentCart = (List<Cart>)httpSession.getAttribute("cart");

        if(currentCart != null){
            currentCart.removeIf(cart -> cart.getType().equals(type) && cart.getProduct() == product);
            httpSession.setAttribute("cart", currentCart);
        }

        return "Ok";
    }

    @PostMapping("/order_cart")
    public String getOrderCartPage(Order order){
        List carts = new ArrayList((List<Cart>)httpSession.getAttribute("cart"));

        order.setCarts(carts);
        order.setStatus("Не оплачено");
        orderService.create(order);

        carts.forEach((item) -> {
            Cart cart = (Cart)item;
            cart.setOrderId(order.getId());
            cartService.create(cart);
        });

        httpSession.removeAttribute("cart");

        return "redirect:/";
    }
}
