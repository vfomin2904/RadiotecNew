package ru.radiotec.site.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.Publisher;
import ru.radiotec.site.repository.PublisherRepository;

import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public List<Publisher> getAllPublishers(){
        return publisherRepository.findAll();
    }

    public Publisher getPublisherById(int id){
        return publisherRepository.findById(id).get();
    }

    public void create(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public void update(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public void delete(Publisher publisher) {
        publisherRepository.delete(publisher);
    }
}
