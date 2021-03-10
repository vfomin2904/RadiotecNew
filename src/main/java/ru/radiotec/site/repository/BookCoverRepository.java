package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.BookCover;

import java.util.List;

@Repository
public interface BookCoverRepository extends JpaRepository<BookCover, Integer> {
}
