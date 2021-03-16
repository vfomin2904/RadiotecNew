package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}
