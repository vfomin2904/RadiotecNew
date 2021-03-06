package ru.radiotec.site.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.Journals;
import ru.radiotec.site.repository.JournalRepository;

import java.util.List;

@Service
public class JournalsService {

    @Autowired
    private JournalRepository journalRepository;

    public List<Journals> getAllJournals() {
        List<Journals> journals = journalRepository.findAll();
        return journals;
    }
    public Journals getJournalById(int id) {
        return journalRepository.findById(id).get();
    }
}
