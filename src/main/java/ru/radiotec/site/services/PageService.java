package ru.radiotec.site.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.Page;
import ru.radiotec.site.repository.PageRepository;


@Service
public class PageService {

    @Autowired
    private PageRepository pageRepository;


    public Page getPageById(int id){
        return pageRepository.findById(id).get();
    }


    public void update(Page page) {
        pageRepository.save(page);
    }
}
