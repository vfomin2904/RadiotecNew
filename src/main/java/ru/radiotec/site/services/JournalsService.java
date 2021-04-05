package ru.radiotec.site.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.Journals;
import ru.radiotec.site.entity.Number;
import ru.radiotec.site.repository.JournalRepository;

import java.util.*;

@Service
public class JournalsService {

    @Autowired
    private JournalRepository journalRepository;

    public List<Journals> getAllJournals() {
        List<Journals> journals = journalRepository.findAll();
        return journals;
    }

    public List<Journals> getActiveJournals() {
        List<Journals> journals = journalRepository.findJournalsByActive(1);
        return journals;
    }

    public Journals getJournalById(int id) {
        return journalRepository.findById(id).get();
    }

    public void create(Journals journal){
        journalRepository.save(journal);
    }

    public void delete(Journals journal){
        journalRepository.delete(journal);
    }

    public void update(Journals journal){
        Journals original = getJournalById(journal.getId());
        original = journal;
        journalRepository.save(original);
    }

    public TreeMap<String, TreeSet<Number>> getNumberSortedByYear(Journals journal, boolean onlyActive){
        TreeMap<String, TreeSet<Number>> numberSorted = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o2) - Integer.parseInt(o1);
            }
        });

        journal.getNumbers().forEach((Number number) ->{

                    if(number.getActive() == 1 || !onlyActive){
                        TreeSet<Number> currentValue = numberSorted.getOrDefault(number.getYear(), new TreeSet<Number>());

                        currentValue.add(number);
                        numberSorted.put(number.getYear(), currentValue);
                    }
                }
        );
        return numberSorted;
    }

    public List<Number> getNumbersByYear(Journals journal, String number_year) {
        List<Number> numbers = new ArrayList<>();
        journal.getNumbers().forEach((number) -> {
            if(number.getYear().equals(number_year)){
                numbers.add(number);
            }
        });
        return numbers;
    }

    public Journals getJournalByLink(String link) {
        return journalRepository.findByLink(link);
    }
}
