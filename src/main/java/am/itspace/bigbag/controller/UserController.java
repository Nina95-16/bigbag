package am.itspace.bigbag.controller;

import am.itspace.bigbag.exception.ResourceNotFoundException;
import am.itspace.bigbag.model.CurrentUser;
import am.itspace.bigbag.model.User;
import am.itspace.bigbag.model.UserType;
import am.itspace.bigbag.service.EmailService;
import am.itspace.bigbag.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    @Value("${file.upload.dir}")
    private String uploadDir;
    @Value("${mail.upload.file}")
    private String mailingFile;

    @GetMapping("/register")
    public String registerGet() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute @Valid User user, BindingResult result, ModelMap modelMap, MultipartFile image) throws ResourceNotFoundException, IOException, MessagingException {
        if (result.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                stringBuilder.append(fieldError.getDefaultMessage());
            }
            modelMap.addAttribute("errorMessage", stringBuilder.toString());
            return "register";
        }
        Optional<User> byEmail = userService.findByEmail(user.getEmail());
        if (byEmail.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user, image);
            log.debug("User with {} email was registered", user.getEmail());
            emailService.sendWithAttachment(user.getEmail(), "Welcome subject",
                    "Hello " + user.getName() + " " + user.getSurname() +
                            " you  have successfully registered in BigBag Store", mailingFile);
            return "redirect:/";
        }
        return "register";
    }

    @GetMapping("/signIn")
    public String sighIn() {
        return "/signIn";
    }

    @GetMapping("/successLogin")
    public String successLogin(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return "redirect:/signIn";
        }
        User user = currentUser.getUser();
        if (user.getUserType() == UserType.ADMIN) {
            return "redirect:/admin";
        }
        return "redirect:/user";
    }


    @GetMapping("/user")
    public String userPage(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        User user = currentUser.getUser();
        if (user.getId() != 0) {
            modelMap.addAttribute("user", user);
        }
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute @Valid User user, BindingResult result, @RequestParam("image") MultipartFile multipartFile, ModelMap modelMap) throws IOException {
        if (result.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                stringBuilder.append(fieldError.getDefaultMessage());
            }
            modelMap.addAttribute("errorMessage", stringBuilder.toString());
            return "user";
        }
        if (multipartFile != null) {
            String photoUrl = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDir + File.separator + photoUrl);
            multipartFile.transferTo(file);
            user.setPicUrl(photoUrl);
        }
        if (user.getId() != 0) {
            if (user.getPassword().isEmpty()) {
                return "redirect:/user";
            }
            user.setPassword(user.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user, multipartFile);
            return "user";
        }
        return "register";
    }

    @GetMapping("/user/image")
    public @ResponseBody
    byte[] getImage(@RequestParam("photoUrl") String photoUrl) throws IOException {
        InputStream in = new FileInputStream(uploadDir + File.separator + photoUrl);
        return IOUtils.toByteArray(in);
    }

    @GetMapping("/allUsers")
    public String allUsers(ModelMap modelMap) {
        List<User> allUsers = userService.allUsers();
        modelMap.addAttribute("users", allUsers);
        return "allUsers";
    }

    @GetMapping("/user/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/allUsers";
    }


}
