package ru.radiotec.site.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.Books;
import ru.radiotec.site.repository.BookRepository;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Books> getAllBooks(){
        return bookRepository.findAll();
    }

    public List<Books> getNewBooks(){
        return bookRepository.findBooksByBookNew("1");
    }

    public Books getBookById(int id){
        return bookRepository.findById(id).get();
    }
    public int getBookWithMaxId(){
        return bookRepository.findBooksWithMaxId();
    }

    public void create(Books book) {
        bookRepository.save(book);
    }

    public void update(Books book) {
        bookRepository.save(book);
    }

    public void delete(Books book) {
        bookRepository.delete(book);
    }
}
