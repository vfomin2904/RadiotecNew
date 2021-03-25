package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.BookType;

@Repository
public interface BookTypeRepository extends JpaRepository<BookType, Integer> {
}
