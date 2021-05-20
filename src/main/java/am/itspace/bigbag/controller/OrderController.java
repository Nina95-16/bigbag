package am.itspace.bigbag.controller;

import am.itspace.bigbag.exception.ResourceNotFoundException;
import am.itspace.bigbag.model.CurrentUser;
import am.itspace.bigbag.model.Order;
import am.itspace.bigbag.model.Product;
import am.itspace.bigbag.model.User;
import am.itspace.bigbag.serviceImpl.OrderServiceImpl;
import am.itspace.bigbag.serviceImpl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderServiceImpl orderService;
    private final ProductServiceImpl productService;

    @GetMapping("/product/view")
    public String addOrder(@RequestParam("id") int id, ModelMap modelMap) throws ResourceNotFoundException {
        Product byId = productService.getById(id);
        modelMap.addAttribute("product", byId);
        return "singleProduct";
    }

    @GetMapping("/order/add")
    public String addOrder(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap, @RequestParam("id") int id) throws ResourceNotFoundException {
        try {
            Product byId = productService.getById(id);
            modelMap.addAttribute("product", byId);
            User user = currentUser.getUser();
            if (user != null) {
                modelMap.addAttribute("user", user);
            }
        } catch (NullPointerException e) {
            return "redirect:/signIn";
        }
        return "singleOrder";
    }

    @PostMapping("/order/add")
    public String saveOrder(Order order){
        orderService.saveOrder(order);
        return "redirect:/allOrders";
    }
    @GetMapping("/allOrders")
    public String allOrders(ModelMap modelMap){
        List<Order> orders = orderService.allOrders();
        modelMap.addAttribute("orders",orders);
        return "allOrders";
    }

}
