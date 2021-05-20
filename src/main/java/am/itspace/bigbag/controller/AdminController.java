package am.itspace.bigbag.controller;

import am.itspace.bigbag.model.CurrentUser;
import am.itspace.bigbag.model.User;
import am.itspace.bigbag.model.UserType;
import am.itspace.bigbag.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    @Value("${file.upload.dir}")
    private String uploadDir;

    @GetMapping("/admin")
    public String adminPanel() {
        return "admin";
    }

    @GetMapping("/adminProfile")
    public String adminProfile(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        User user = currentUser.getUser();
        if (user.getId() != 0) {
            modelMap.addAttribute("user", user);
        }
        return "adminProfile";
    }

    @PostMapping("/updateAdmin")
    public String updateAdmin(@ModelAttribute @Valid User user, BindingResult result, ModelMap modelMap, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                stringBuilder.append(fieldError.getDefaultMessage());
            }
            modelMap.addAttribute("errorMessage", stringBuilder.toString());
            return "adminProfile";}
        boolean imageExist = multipartFile.getOriginalFilename() != null && !multipartFile.getOriginalFilename().isEmpty();
        if (imageExist){
            String photoUrl = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDir + File.separator + photoUrl);
            multipartFile.transferTo(file);
            user.setPicUrl(photoUrl);
        }
        if (user.getId() != 0) {
            if (user.getPassword().isEmpty()) {
                return "redirect:/adminProfile";
            }
            if (user.getPicUrl()!=null){
                user.setPicUrl(user.getPicUrl());
            }
            user.setUserType(UserType.ADMIN);
            user.setPassword(user.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user,multipartFile);
            return "adminProfile";
        }
        return "register";
    }
}
