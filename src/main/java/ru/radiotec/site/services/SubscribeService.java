package ru.radiotec.site.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.Subscribe;
import ru.radiotec.site.repository.SubscribeRepository;

import java.util.List;

@Service
public class SubscribeService {

    @Autowired
    private SubscribeRepository subscribeRepository;

    public List<Subscribe> getAllSubscribes() {
        List<Subscribe> subscribes = subscribeRepository.findAll();
        return subscribes;
    }
    public Subscribe getSubscribeById(int id) {
        return subscribeRepository.findById(id).get();
    }
    public List<Subscribe> getSubscribeByUser(int user) {
        return subscribeRepository.findByUser(user);
    }

    public void create(Subscribe subscribe) {
        subscribeRepository.save(subscribe);
    }

    public void update(Subscribe subscribe){
        subscribeRepository.save(subscribe);
    }

    public void delete(Subscribe subscribe){
        subscribeRepository.delete(subscribe);
    }

    public List<Subscribe> getSubscribeByType(String type) {
        return subscribeRepository.findByType(type);
    }
}
