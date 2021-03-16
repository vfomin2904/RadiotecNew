package ru.radiotec.site.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.Journals;
import ru.radiotec.site.entity.Number;
import ru.radiotec.site.repository.JournalRepository;
import ru.radiotec.site.repository.NumberRepository;

import java.util.List;

@Service
public class NumberService {

    @Autowired
    private NumberRepository numberRepository;

    public List<Number> getAllNumbers() {
        List<Number> numbers = numberRepository.findAll();
        return numbers;
    }
    public Number getNumberById(int id) {
        return numberRepository.findById(id).get();
    }

    public void create(Number number) {
        numberRepository.save(number);
    }

    public void update( Number number){
//        Number original = getNumberById(number.getId());
//        original = number;
        numberRepository.save(number);
    }

    public void delete(Number number){
        numberRepository.delete(number);
    }

    public List<Number> getNumbersByYear(String year){
        return numberRepository.findByYearIsContaining(year);
    }
}
