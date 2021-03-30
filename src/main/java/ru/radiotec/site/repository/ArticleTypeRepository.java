package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.ArticleType;

@Repository
public interface ArticleTypeRepository extends JpaRepository<ArticleType, Integer> {
}
