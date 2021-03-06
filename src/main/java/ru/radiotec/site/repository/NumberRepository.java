package ru.radiotec.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.radiotec.site.entity.Journals;
import ru.radiotec.site.entity.Number;

@Repository
public interface NumberRepository extends JpaRepository<Number, Integer> {

}
