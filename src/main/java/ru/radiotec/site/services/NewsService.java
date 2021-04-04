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

    public void create(News news) {
        newsRepository.save(news);
    }
    public void update(News news) {
        newsRepository.save(news);
    }
    public void delete(News news) {
        newsRepository.delete(news);
    }

    public List<News> getNewsByType(int type) {
        return newsRepository.findNewsByType(type);
    }
}
