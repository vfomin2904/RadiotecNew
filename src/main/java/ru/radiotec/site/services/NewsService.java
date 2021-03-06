package ru.radiotec.site.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.News;
import ru.radiotec.site.repository.NewsRepository;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> getAllNews() {
        List<News> news = newsRepository.findAll();
        return news;
    }
    public News getNewsById(int id) {
        return newsRepository.findById(id).get();
    }
}
