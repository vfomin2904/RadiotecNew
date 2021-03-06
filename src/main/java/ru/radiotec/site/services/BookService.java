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
        return bookRepository.findBooksByBookNew("on");
    }

    public Books getBookById(int id){
        return bookRepository.findById(id).get();
    }
}
