package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
}
