package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.BookSec;
import ru.radiotec.site.entity.Books;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {
    public List<Books> findBooksByBookNew(String bookNew);
    public List<Books> findBooksBySection(BookSec section);
}
