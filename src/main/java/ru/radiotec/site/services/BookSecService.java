package ru.radiotec.site.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.BookSec;
import ru.radiotec.site.repository.BookSecRepository;

import java.util.List;

@Service
public class BookSecService {

    @Autowired
    private BookSecRepository bookSecRepository;

    public List<BookSec> getAllBookSec(){
        return bookSecRepository.findAll();
    }
    public List<BookSec> getBooksBySectionLimit(BookSec section, int page, int bookCount){
        int booksCountOnPage = 10;

        return bookSecRepository.findAll();
    }
    public BookSec getBookSecById(int id){
        return bookSecRepository.findById(id).get();
    }

}
