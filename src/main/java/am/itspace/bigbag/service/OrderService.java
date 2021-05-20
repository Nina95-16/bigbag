package am.itspace.bigbag.service;

import am.itspace.bigbag.exception.ResourceNotFoundException;
import am.itspace.bigbag.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    void saveOrder(Order order);

    List<Order> allOrders();

    Order getById(int id) throws ResourceNotFoundException;

    void delete(int id);

    List<Order> findByUserId(int id) throws ResourceNotFoundException;
}
