package am.itspace.bigbag.serviceImpl;

import am.itspace.bigbag.exception.ResourceNotFoundException;
import am.itspace.bigbag.model.Order;
import am.itspace.bigbag.repository.OrderRepository;
import am.itspace.bigbag.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> allOrders() {
        List<Order> all = orderRepository.findAll();
        return all;
    }

    @Override
    public Order getById(int id) throws ResourceNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order with" + id + " does not exist"));
     return order;
    }

    @Override
    public void delete(int id) {
           orderRepository.deleteById(id);
    }

    @Override
    public List<Order> findByUserId(int id) {
        List<Order> allByUserId = orderRepository.findAllByUserId(id);
        return allByUserId;
    }
}
