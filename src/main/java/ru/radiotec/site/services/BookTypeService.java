package ru.radiotec.site.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.BookType;
import ru.radiotec.site.repository.BookTypeRepository;

import java.util.List;

@Service
public class BookTypeService {

    @Autowired
    private BookTypeRepository bookTypeRepository;

    public List<BookType> getAllBookType(){
        return bookTypeRepository.findAll();
    }
    
    public BookType getBookTypeById(int id){
        return bookTypeRepository.findById(id).get();
    }

    public void create(BookType book) {
        bookTypeRepository.save(book);
    }

    public void update(BookType book) {
        bookTypeRepository.save(book);
    }

    public void delete(BookType book) {
        bookTypeRepository.delete(book);
    }
}
