package am.itspace.bigbag.controller;

import am.itspace.bigbag.model.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class HeaderController {
//    @ModelAttribute("user")
//    public String getUser(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap){
//        if (currentUser != null) {
//            modelMap.addAttribute("user", currentUser.getUser());
//        }
//        return "redirect:/";
//    }
}
