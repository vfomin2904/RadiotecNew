package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.BookSec;
import ru.radiotec.site.entity.Books;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {

    @Query("select b from Books b where b.bookNew = 1 order by b.sortNew desc, b.id desc")
    List<Books> findBooksByBookNew(String bookNew);
    List<Books> findBooksBySection(BookSec section);

    @Query("select MAX(id) from Books")
    int findBooksWithMaxId();

    List<Books> findBooksByBooksize(int booksize);
}
