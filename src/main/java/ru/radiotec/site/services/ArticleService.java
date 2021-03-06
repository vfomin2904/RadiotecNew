package ru.radiotec.site.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.Article;
import ru.radiotec.site.entity.Number;
import ru.radiotec.site.repository.ArticleRepository;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles;
    }
    public Article getArticleById(int id) {
        return articleRepository.findById(id).get();
    }

    public List<Article> getArticlesByKeywords(String keywords) {
        return articleRepository.findByKeywordsContaining(keywords);
    }
}
