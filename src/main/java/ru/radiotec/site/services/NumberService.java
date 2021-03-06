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
}
