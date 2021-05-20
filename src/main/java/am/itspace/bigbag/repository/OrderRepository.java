package am.itspace.bigbag.repository;

import am.itspace.bigbag.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByUserId(int id);
}
