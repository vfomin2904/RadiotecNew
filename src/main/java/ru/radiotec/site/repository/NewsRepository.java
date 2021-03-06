package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

}
