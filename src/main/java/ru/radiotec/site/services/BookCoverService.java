package ru.radiotec.site.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.BookCover;
import ru.radiotec.site.entity.Books;
import ru.radiotec.site.repository.BookCoverRepository;
import ru.radiotec.site.repository.BookRepository;

import java.util.List;

@Service
public class BookCoverService {

    @Autowired
    private BookCoverRepository bookCoverRepository;

    public List<BookCover> getAllBookCovers(){
        return bookCoverRepository.findAll();
    }

    public BookCover getBookCoverById(int id){
        return bookCoverRepository.findById(id).get();
    }

    public void create(BookCover book) {
        bookCoverRepository.save(book);
    }

    public void update(BookCover book) {
        bookCoverRepository.save(book);
    }

    public void delete(BookCover book) {
        bookCoverRepository.delete(book);
    }
}
