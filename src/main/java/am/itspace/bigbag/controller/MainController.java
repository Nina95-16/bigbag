package am.itspace.bigbag.controller;

import am.itspace.bigbag.model.CurrentUser;
import am.itspace.bigbag.model.Product;
import am.itspace.bigbag.serviceImpl.ProductServiceImpl;
import am.itspace.bigbag.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final ProductServiceImpl productService;

    @GetMapping("/")
    public String main(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser,
                       @ModelAttribute Product product) {
        if (currentUser != null) {
            modelMap.addAttribute("user", currentUser.getUser());
        }
        return "index";
    }

    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "aboutUs";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, ModelMap modelMap){
       modelMap.addAttribute("products", productService.findAllByNameStartingWith(keyword));
       return "productsForUser";
    }
}


