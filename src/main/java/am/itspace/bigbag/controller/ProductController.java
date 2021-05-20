package am.itspace.bigbag.controller;

import am.itspace.bigbag.exception.ResourceNotFoundException;
import am.itspace.bigbag.model.*;
import am.itspace.bigbag.serviceImpl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductServiceImpl productService;
    @Value("${file.upload.dir}")
    private String uploadDir;

    @GetMapping("/product/save")
    public String saveProduct(@ModelAttribute Product product, ModelMap modelMap) throws ResourceNotFoundException {
        int id = product.getId();
        if (id != 0) {
            Product byId = productService.getById(id);
            modelMap.addAttribute("product", byId);
        } else {
            modelMap.addAttribute("product", new Product());
        }
        return "addProduct";
    }

    @PostMapping("/product/save")
    public String addProduct(@ModelAttribute @Valid Product product, BindingResult result, @RequestParam("image") MultipartFile multipartFile, ModelMap modelMap) throws IOException, ResourceNotFoundException {
        if (result.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                stringBuilder.append(fieldError.getDefaultMessage());
            }
            modelMap.addAttribute("errorMessage", stringBuilder.toString());
            return "addProduct";
        }
        String photoUrl = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File file = new File(uploadDir + File.separator + photoUrl);
        multipartFile.transferTo(file);
        product.setPicUrl(photoUrl);
        productService.saveProduct(product);

        return "redirect:/allProducts";
    }

    @GetMapping("/product/image")
    public @ResponseBody
    byte[] getImage(@RequestParam("photoUrl") String photoUrl) throws IOException {
        InputStream in = new FileInputStream(uploadDir + File.separator + photoUrl);
        return IOUtils.toByteArray(in);
    }

    @GetMapping("/allProducts")
    public String allProducts(ModelMap modelMap) {
        List<Product> products = productService.allProducts();
        modelMap.addAttribute("products", products);
        return "allProducts";
    }

    @GetMapping("/productsForUser")
    public String productsForUser(ModelMap modelMap) {
        List<Product> products = productService.allProducts();
        modelMap.addAttribute("products", products);
        return "productsForUser";
    }

    @GetMapping("/product/delete")
    public String deleteBook(@RequestParam("id") int id) {
        productService.delete(id);
        return "redirect:/allProducts";
    }

    @PostMapping("/product/sorted")
    public String productByType(ModelMap modelMap, @RequestParam("type") ProductType type, @RequestParam("category") Category category) {
        List<Product> byCategoryAndType = productService.findByCategoryAndType(category, type);
        modelMap.addAttribute("products", byCategoryAndType);
        return "productsForUser";
    }
}
