package ru.radiotec.site.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.BookSize;
import ru.radiotec.site.repository.BookSizeRepository;

import java.util.List;

@Service
public class BookSizeService {

    @Autowired
    private BookSizeRepository BookSizeRepository;

    public List<BookSize> getAllBookSizes(){
        return BookSizeRepository.findAll();
    }
    
    public BookSize getBookSizeById(int id){
        return BookSizeRepository.findById(id).get();
    }

    public void create(BookSize book) {
        BookSizeRepository.save(book);
    }

    public void update(BookSize book) {
        BookSizeRepository.save(book);
    }

    public void delete(BookSize book) {
        BookSizeRepository.delete(book);
    }
}
