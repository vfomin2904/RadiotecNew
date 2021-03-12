package ru.radiotec.site.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.radiotec.site.entity.Number;
import ru.radiotec.site.entity.*;
import ru.radiotec.site.services.*;
import ru.radiotec.site.settings.Settings;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private JournalsService journalsService;

    @Autowired
    private BookSecService bookSecService;

    @Autowired
    private BookService bookService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private NumberService numberService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BookSizeService bookSizeService;

    @Autowired
    private BookCoverService bookCoverService;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public String getAdminMainPage(Model model){

        return "admin_template";
    }

    @GetMapping("/journal_add")
    public String getAdminJournalAddPage(Model model){
        model.addAttribute("action", "journal_add");
        model.addAttribute("journals", new Journals());
        return "admin_template";
    }

    @GetMapping("/journal_delete")
    public String getAdminJournalDeletePage(@RequestParam(required = true) int id){
        Journals journal = journalsService.getJournalById(id);
        journalsService.delete(journal);
        return "redirect:/admin/journal_change";
    }

    @PostMapping("/journal_add")
    public String getAdminJournalAddPage(@Valid Journals journal, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile cover){

        if (!cover.isEmpty()) {
            String name = Settings.uploadPath+"journals\\"+cover.getOriginalFilename();
            System.out.println(cover.getContentType());
            if(cover.getContentType().equals("image/png") || cover.getContentType().equals("image/jpeg")){
                try {
                    byte[] bytes = cover.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(name));
                    stream.write(bytes);
                    stream.close();
                    System.out.println("Файл загружен");
                    journal.setCover(name);
                } catch (Exception e) {
                    bindingResult.rejectValue("cover","","Не удалось загрузить файл");
                }
            } else{
                bindingResult.rejectValue("cover","","Файл должен быть в формате png или jpg");
            }
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("action", "journal_add");
            return "admin_template";
        }
        journalsService.create(journal);
        return "redirect:/admin/";
    }

    @PostMapping("/journal_change")
    public String getAdminJournalChangePage(@Valid Journals journal, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile cover){
        if (!cover.isEmpty()) {
            String name = Settings.uploadPath+"/journals/"+cover.getOriginalFilename();
            System.out.println(cover.getContentType());
            if(cover.getContentType().equals("image/png") || cover.getContentType().equals("image/jpeg")){
                try {
                    byte[] bytes = cover.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(name));
                    stream.write(bytes);
                    stream.close();
                    System.out.println("Файл загружен");
                    journal.setCover(cover.getOriginalFilename());
                } catch (Exception e) {
                    System.out.println("Вам не удалось загрузить " + name + " => " + e.getMessage());

                    bindingResult.rejectValue("cover","","Не удалось загрузить файл");
                }
            } else{
                bindingResult.rejectValue("cover","","Файл должен быть в формате png или jpg");
            }

        }
        if(bindingResult.hasErrors()){
            System.out.println("error");
            return getAdminJournalChangePage(model, journal,0);
        }
        journalsService.update(journal);
        return "redirect:/admin/";
    }

    @GetMapping("/journal_change")
    public String getAdminJournalChangePage(Model model, Journals journal, @RequestParam(defaultValue = "-1") int id){

        if(id > 0 || journal.getId() > 0){
            if(id > 0){
                journal = journalsService.getJournalById(id);
            }
            model.addAttribute("journals", journal);
            model.addAttribute("action", "journal_change");
        } else{
            List<Journals> journals = journalsService.getAllJournals();
            model.addAttribute("journals", journals);
            model.addAttribute("action", "journal_list");

            Map<Journals, TreeMap<String, TreeSet<Number>>> numberSortedList = new HashMap<>();
            for(Journals item: journals){
                numberSortedList.put(item, journalsService.getNumberSortedByYear(item));
            }
            model.addAttribute("numberSortedList", numberSortedList);
        }
        return "admin_template";
    }

    @GetMapping("/number_add")
    public String getAdminNumberAddPage(Model model, Number number){
        if(number == null){
            number = new Number();
        }
        List<Journals> journals = journalsService.getAllJournals();
        model.addAttribute("action", "number_add");
        model.addAttribute("number", number);
        model.addAttribute("journals",journals);
        return "admin_template";
    }

    @PostMapping("/number_add")
    public String getAdminJournalAddPage(@Valid Number number, BindingResult bindingResult, Model model){

        if(number.getJournalId()<1){
            bindingResult.rejectValue("journalId","","Не выбран журнал");
            return getAdminNumberAddPage(model, number);
        }
        if(bindingResult.hasErrors()){
            return getAdminNumberAddPage(model, number);
        }

        numberService.create(number);
        return "redirect:/admin/";
    }

    @GetMapping("/number_change")
    public String getAdminNumberChangePage(Model model, Number number, @RequestParam(defaultValue = "-1") int id){

        if(id > 0 || number.getId() > 0){
            if(id>0){
                number = numberService.getNumberById(id);
            }
            model.addAttribute("number", number);
            model.addAttribute("action", "number_change");
        } else{
            List<Journals> journals = journalsService.getAllJournals();
            model.addAttribute("journals", journals);
            model.addAttribute("action", "number_list");

            Map<Journals, TreeMap<String, TreeSet<Number>>> numberSortedList = new HashMap<>();
            for(Journals journal: journals){
                numberSortedList.put(journal, journalsService.getNumberSortedByYear(journal));
            }
            model.addAttribute("numberSortedList", numberSortedList);
        }
        return "admin_template";
    }

    @GetMapping("/number_delete")
    public String getAdminNumberDeletePage(@RequestParam(required = true) int id){
        Number number = numberService.getNumberById(id);
        numberService.delete(number);
        return "redirect:/admin/number_change";
    }

    @PostMapping("/number_change")
    public String getAdminNumberChangePage(@Valid Number number, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return getAdminNumberChangePage(model, number, 0);
        }

        numberService.update(number);
        return "redirect:/admin/";
    }


    @GetMapping("/section_add")
    public String getAdminSectionAddPage(Model model, Section section){
        if(section == null || section.getId() < 0){
            section = new Section();
        }
        List<Journals> journals = journalsService.getAllJournals();
        model.addAttribute("action", "section_add");
        model.addAttribute("section", section);
        model.addAttribute("journals",journals);
        return "admin_template";
    }

    @PostMapping("/section_add")
    public String getAdminSectionAddPage(@Valid Section section, BindingResult bindingResult, Model model){

        if(section.getNumberId() <= 0){
            bindingResult.rejectValue("numberId","","Номер не выбран");
            return getAdminSectionAddPage(model, section);
        }

        if(bindingResult.hasErrors()){
            Number number = numberService.getNumberById(section.getNumberId());
            model.addAttribute("number", number);
            return getAdminSectionAddPage(model, section);
        }

        sectionService.create(section);
        return "redirect:/admin/";
    }

    @GetMapping("/section_delete")
    public String getAdminSectionDeletePage(@RequestParam(required = true) int id){
        Section section = sectionService.getSectionById(id);
        sectionService.delete(section);
        return "redirect:/admin/section_change";
    }

    @GetMapping("/section_change")
    public String getAdminSectionChangePage(Model model, Section section,  @RequestParam(defaultValue = "-1") int id){

        if(id > 0 || section.getId()>0){
            if(id > 0){
                section = sectionService.getSectionById(id);
            }
            model.addAttribute("section", section);
            model.addAttribute("action", "section_change");
        } else{
            List<Journals> journals = journalsService.getAllJournals();
            model.addAttribute("journals", journals);
            model.addAttribute("action", "section_list");
        }
        return "admin_template";
    }

    @PostMapping("/section_change")
    public String getAdminSectionChangePage(@Valid Section section, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return getAdminSectionChangePage(model, section, 0);
        }
        sectionService.update(section);
        return "redirect:/admin/section_change";
    }

    @GetMapping("/article_add")
    public String getAdminArticleAddPage(Model model, Article article){
        if(article == null){
            article = new Article();
        }
        List<Journals> journals = journalsService.getAllJournals();
        model.addAttribute("action", "article_add");
        model.addAttribute("article", article);
        model.addAttribute("journals",journals);
        return "admin_template";
    }

    @PostMapping("/article_add")
    public String getAdminArticleAddPage(@Valid Article article, BindingResult bindingResult, Model model){
        if(article.getSectionId() <=0){
            bindingResult.rejectValue("sectionId", "", "Не выбран раздел");
            return getAdminArticleAddPage(model, article);
        }
        if(bindingResult.hasErrors()){
            return getAdminArticleAddPage(model, article);
        }
        articleService.create(article);
        return "redirect:/admin/";
    }

    @GetMapping("/article_change")
    public String getAdminArticleChangePage(Model model, Article article, @RequestParam(defaultValue = "-1") int id){

        if(id > 0 || article.getId() > 0){
            if(id > 0){
                article = articleService.getArticleById(id);
            }
            model.addAttribute("article", article);
            model.addAttribute("action", "article_change");
        } else{
            List<Journals> journals = journalsService.getAllJournals();
            model.addAttribute("journals", journals);
            model.addAttribute("action", "article_list");
        }
        return "admin_template";
    }

    @PostMapping("/article_change")
    public String getAdminArticleChangePage(@Valid Article article, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return getAdminArticleChangePage(model, article, 0);
        }

        articleService.update(article);
        return "redirect:/admin/article_change";
    }

    @GetMapping("/article_delete")
    public String getAdminArticleDeletePage(@RequestParam(required = true) int id){
        Article article = articleService.getArticleById(id);
        articleService.delete(article);
        return "redirect:/admin/article_change";
    }

    @GetMapping("/booksec_add")
    public String getAdminBookSecPage(Model model){
        if(!model.containsAttribute("bookSec")){
            model.addAttribute("bookSec", new BookSec());
        }
        model.addAttribute("action", "booksec_add");
        return "admin_template";
    }

    @PostMapping("/booksec_add")
    public String getAdminBookSecAddPage(@Valid BookSec bookSec, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            BookSec bookSecCopy = bookSec;
            model.addAttribute("bookSec", bookSecCopy);
            return getAdminBookSecPage(model);
        }
        bookSecService.create(bookSec);
        return "redirect:/admin/";
    }


    @GetMapping("/booksec_change")
    public String getAdminBookSecChangePage(Model model, @RequestParam(defaultValue = "-1") int id){

        if(id > 0 || model.containsAttribute("bookSec")){
            if(id > 0){
                BookSec booksec = bookSecService.getBookSecById(id);
                model.addAttribute("bookSec", booksec);
            }
            model.addAttribute("action", "booksec_change");
        } else{
            List<BookSec> booksecs = bookSecService.getAllBookSec();
            model.addAttribute("booksecs",booksecs);
            model.addAttribute("action", "booksec_list");
        }

        return "admin_template";
    }

    @PostMapping("/booksec_change")
    public String getAdminBookSecChangePage(@Valid BookSec booksec, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            BookSec bookSecCopy = booksec;
            model.addAttribute("bookSec", bookSecCopy);
            return getAdminBookSecChangePage(model,0);
        }

        bookSecService.update(booksec);
        return "redirect:/admin/booksec_change";
    }


    @GetMapping("/booksec_delete")
    public String getAdminBookSecDeletePage(@RequestParam(required = true) int id){
        BookSec booksec = bookSecService.getBookSecById(id);
        bookSecService.delete(booksec);
        return "redirect:/admin/booksec_change";
    }

    @GetMapping("/book_add")
    public String getAdminBookPage(Model model){
        List<BookSize> booksizes = bookSizeService.getAllBookSizes();
        List<BookCover> bookcovers = bookCoverService.getAllBookCovers();
        List<Publisher> publishers = publisherService.getAllPublishers();
        List<BookSec> booksecs = bookSecService.getAllBookSec();
        model.addAttribute("bookcovers", bookcovers);
        model.addAttribute("booksizes", booksizes);
        model.addAttribute("publishers", publishers);
        model.addAttribute("booksecs", booksecs);

        if(!model.containsAttribute("books")){
            model.addAttribute("books", new Books());
        }

        model.addAttribute("action", "book_add");
        return "admin_template";
    }

    @PostMapping("/book_add")
    public String getAdminBookAddPage(@Valid Books book, BindingResult bindingResult, Model model){

        if(book.getSection() < 1){
            bindingResult.rejectValue("section", "", "Раздел не выбран");
        }
        if(book.getBookcover() < 1){
            bindingResult.rejectValue("bookcover", "", "Тип обложки не выбран");
        }
        if(book.getBooksize() < 1){
            bindingResult.rejectValue("booksize", "", "Размер обложки не выбран");
        }
        if(book.getPublisher() < 1){
            bindingResult.rejectValue("publisher", "", "Издательство не выбрано");
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("books", book);
            return getAdminBookPage(model);
        }
        bookService.create(book);
        return "redirect:/admin/";
    }

    @GetMapping("/book_change")
    public String getAdminBookChangePage(Model model, @RequestParam(defaultValue = "-1") int id){

        if(id > 0 || model.containsAttribute("books")){
            if(!model.containsAttribute("books")){
                Books book = bookService.getBookById(id);
                model.addAttribute("books", book);
            }
            List<BookSize> booksizes = bookSizeService.getAllBookSizes();
            List<BookCover> bookcovers = bookCoverService.getAllBookCovers();
            List<Publisher> publishers = publisherService.getAllPublishers();
            model.addAttribute("bookcovers", bookcovers);
            model.addAttribute("booksizes", booksizes);
            model.addAttribute("publishers", publishers);
            model.addAttribute("action", "book_change");
        } else{
            List<BookSec> booksecs = bookSecService.getAllBookSec();
            model.addAttribute("booksecs",booksecs);
            model.addAttribute("action", "book_list");
        }

        return "admin_template";
    }

    @PostMapping("/book_change")
    public String getAdminBookChangePage(@Valid Books book, BindingResult bindingResult, Model model){
        if(book.getSection() < 1){
            bindingResult.rejectValue("section", "", "Раздел не выбран");
        }
        if(book.getBookcover() < 1){
            bindingResult.rejectValue("bookcover", "", "Тип обложки не выбран");
        }
        if(book.getBooksize() < 1){
            bindingResult.rejectValue("booksize", "", "Размер обложки не выбран");
        }
        if(book.getPublisher() < 1){
            bindingResult.rejectValue("publish", "", "Издательство не выбрано");
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("books", book);
            return getAdminBookChangePage(model, 0);
        }
        bookService.update(book);
        return "redirect:/admin/book_change";
    }

    @GetMapping("/book_delete")
    public String getAdminBookDeletePage(@RequestParam(required = true) int id){
        Books book = bookService.getBookById(id);
        bookService.delete(book);
        return "redirect:/admin/book_change";
    }

    @GetMapping("/news_add")
    public String getAdminNewsPage(Model model){
        if(!model.containsAttribute("news")){
            model.addAttribute("news", new News());
        }
        model.addAttribute("action", "news_add");
        return "admin_template";
    }
    @PostMapping("/news_add")
    public String getAdminNewsAddPage(@Valid News news, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("news", news);
            return getAdminNewsPage(model);
        }
        newsService.create(news);
        return "redirect:/admin/";
    }

    @GetMapping("/news_delete")
    public String getAdminNewsDeletePage(@RequestParam(required = true) int id){
        News news = newsService.getNewsById(id);
        newsService.delete(news);
        return "redirect:/admin/news_change";
    }

    @GetMapping("/news_change")
    public String getAdminNewsChangePage(Model model, @RequestParam(defaultValue = "-1") int id){

        if(id > 0 || model.containsAttribute("news")){
            if(!model.containsAttribute("news")){
                News news = newsService.getNewsById(id);
                model.addAttribute("news", news);
            }
            model.addAttribute("action", "news_change");
        } else{
            List<News> news_list = newsService.getAllNews();
            model.addAttribute("news_list",news_list);
            model.addAttribute("action", "news_list");
        }

        return "admin_template";
    }

    @PostMapping("/news_change")
    public String getAdminNewsChangePage(@Valid News news, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("news", news);
            return getAdminNewsChangePage(model, 0);
        }
        newsService.update(news);
        return "redirect:/admin/news_change";
    }


    @PostMapping("/handler")
    public String getAdminHandlerPage(Model model, @RequestParam (defaultValue = "-1") int journal_id,
                                      @RequestParam (defaultValue = "") String number_year, @RequestParam (defaultValue = "-1") int number_id,
                                      @RequestParam (defaultValue = "-1") int section_id, @RequestParam (defaultValue = "") String action,
                                      @RequestParam (defaultValue = "-1") int art_id, @RequestParam (defaultValue = "-1") int booksec_id,
                                      @RequestParam (defaultValue = "-1") int book_id){
       if(journal_id > 0){
           Journals journal = journalsService.getJournalById(journal_id);
           if(number_year.equals("")){
               TreeSet<String> years = new TreeSet<>();
               journal.getNumbers().forEach((number) -> {
                   years.add(number.getYear());
               });
               model.addAttribute("years",years);
           }
           else{
               List<Number> numbersList = journalsService.getNumbersByYear(journal, number_year);
               TreeSet<Number> numbers = new TreeSet<>(numbersList);
               System.out.println(numbers);
               System.out.println("numbers");
               model.addAttribute("numbers",numbers);
           }
       }
       else if(number_id > 0){
           Number selectedNumber = numberService.getNumberById(number_id);
           model.addAttribute("selectedNumber",selectedNumber);
       }
        else if(section_id > 0){
            if(action.equals("section_change")){
                Section section = sectionService.getSectionById(section_id);
                model.addAttribute("section", section);
                model.addAttribute("action", action);
                return "admin_template";
            }
            else if(action.equals("article_change")){
                Section section = sectionService.getSectionById(section_id);
                model.addAttribute("selectedSection", section);
            }
        }
        else if(art_id>0){
            Article article = articleService.getArticleById(art_id);
            model.addAttribute("article", article);
            model.addAttribute("action", "article_change");
            return "admin_template";
        }
        else if(booksec_id>0){
            if(action.equals("booksec_change")){
                BookSec bookSec = bookSecService.getBookSecById(booksec_id);
                model.addAttribute("bookSec", bookSec);
                model.addAttribute("action", "booksec_change");
                return "admin_template";
            } else if(action.equals("book_change")){
                BookSec booksec = bookSecService.getBookSecById(booksec_id);
                model.addAttribute("booksec", booksec);
                model.addAttribute("action", action);
            }
        } else if(book_id>0){
           Books book = bookService.getBookById(book_id);
           List<BookSize> booksizes = bookSizeService.getAllBookSizes();
           List<BookCover> bookcovers = bookCoverService.getAllBookCovers();
           List<Publisher> publishers = publisherService.getAllPublishers();
           model.addAttribute("bookcovers", bookcovers);
           model.addAttribute("booksizes", booksizes);
           model.addAttribute("publishers", publishers);
           model.addAttribute("books", book);
           model.addAttribute("action", "book_change");
           return "admin_template";
        }
        return "admin_handler";
    }

}
