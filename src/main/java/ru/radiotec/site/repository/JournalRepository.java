package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.Journals;

import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository<Journals, Integer> {
    List<Journals> findJournalsByActive(int active);
}
