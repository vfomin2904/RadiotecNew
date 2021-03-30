package ru.radiotec.site.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.Cart;
import ru.radiotec.site.entity.Number;
import ru.radiotec.site.repository.CartRepository;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts;
    }
    public Cart getCartById(int id) {
        return cartRepository.findById(id).get();
    }

    public void create(Cart cart) {
        cartRepository.save(cart);
    }

    public void update(Cart cart){
        cartRepository.save(cart);
    }

    public void delete(Cart cart){
        cartRepository.delete(cart);
    }
}
