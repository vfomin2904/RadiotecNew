package ru.radiotec.site.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.ArticleType;
import ru.radiotec.site.repository.ArticleTypeRepository;

import java.util.List;

@Service
public class ArticleTypeService {

    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public List<ArticleType> getAllArticleType(){
        return articleTypeRepository.findAll();
    }
    
    public ArticleType getArticleTypeById(int id){
        return articleTypeRepository.findById(id).get();
    }

    public void create(ArticleType articleType) {
        articleTypeRepository.save(articleType);
    }

    public void update(ArticleType articleType) {
        articleTypeRepository.save(articleType);
    }

    public void delete(ArticleType articleType) {
        articleTypeRepository.delete(articleType);
    }
}
