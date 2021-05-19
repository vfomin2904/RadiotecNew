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
        if(articleRepository.findById(id).isPresent()){
            return articleRepository.findById(id).get();
        } else{
            return null;
        }
    }

    public List<Article> getArticlesByKeywords(String keywords) {
        return articleRepository.findByKeywordsContainingOrKeywordsEngContaining(keywords,keywords);
    }

    public void create(Article article) {
        articleRepository.save(article);
    }

    public List<Article> getArticleBySection(int section_id) {
        return articleRepository.findBySectionId(section_id);
    }

    public List<Article> getArticleByType(int article_type) {
        return articleRepository.findByType(article_type);
    }

    public void update(Article article) {
        articleRepository.save(article);
    }
    public void delete(Article article) {
        articleRepository.delete(article);
    }
}
