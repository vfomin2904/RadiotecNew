package ru.radiotec.site.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.Number;
import ru.radiotec.site.entity.Section;
import ru.radiotec.site.repository.NumberRepository;
import ru.radiotec.site.repository.SectionRepository;

import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    public List<Section> getAllSections() {
        List<Section> section = sectionRepository.findAll();
        return section;
    }
    public Section getSectionById(int id) {
        return sectionRepository.findById(id).get();
    }

    public void create(Section section) {
        sectionRepository.save(section);
    }

    public void update( Section section){
        sectionRepository.save(section);
    }

    public void delete(Section section){
        sectionRepository.delete(section);
    }

    public List<Section> getSectionByNumber(int number) {
        return sectionRepository.findByNumberId(number);
    }
}
