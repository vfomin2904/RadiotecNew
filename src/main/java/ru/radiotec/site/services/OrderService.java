package ru.radiotec.site.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.Cart;
import ru.radiotec.site.entity.Order;
import ru.radiotec.site.repository.CartRepository;
import ru.radiotec.site.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }
    public List<Order> getOrdersByStatus(String status) {
        List<Order> orders = orderRepository.findByStatus(status);
        return orders;
    }
    public Order getOrderById(int id) {
        return orderRepository.findById(id).get();
    }

    public void create(Order order) {
        orderRepository.save(order);
    }

    public void update(Order order){
        orderRepository.save(order);
    }

    public void delete(Order order){
        orderRepository.delete(order);
    }
}
