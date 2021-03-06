package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.BookSec;

@Repository
public interface BookSecRepository extends JpaRepository<BookSec, Integer> {

}
