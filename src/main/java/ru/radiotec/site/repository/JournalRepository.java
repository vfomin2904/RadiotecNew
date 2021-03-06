package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.Journals;

@Repository
public interface JournalRepository extends JpaRepository<Journals, Integer> {
//    private Journals find(String menu_name;
}
