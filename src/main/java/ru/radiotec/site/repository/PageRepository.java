package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.Page;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {

}
