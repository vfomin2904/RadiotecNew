package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.BookSize;

@Repository
public interface BookSizeRepository extends JpaRepository<BookSize, Integer> {
}
