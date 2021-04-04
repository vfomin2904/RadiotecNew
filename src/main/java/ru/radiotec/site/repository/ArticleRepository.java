package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.Article;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findByKeywordsContainingOrKeywordsEngContaining(String keywords, String keywordsEng);
    List<Article> findBySectionId(int id);

    List<Article> findByType(int article_type);
}
