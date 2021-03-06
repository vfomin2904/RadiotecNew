package ru.radiotec.site.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.radiotec.site.entity.*;
import ru.radiotec.site.entity.Number;
import ru.radiotec.site.services.*;

import java.util.*;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private JournalsService journalsService;

    @Autowired
    private BookSecService bookSecService;

    @Autowired
    private BookService bookService;

    @Autowired
    private NumberService numberService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public String getMainPage(Model model){
        init_menu(model);
        ArrayList<Books> booksNew = new ArrayList<>(bookService.getNewBooks());
        model.addAttribute("booksnew", booksNew);
        return "index";
    }
    @GetMapping("/journal/{id}")
    public String getJournalPage(Model model, @PathVariable int id){
        Journals currentJournal = journalsService.getJournalById(id);
        init_menu(model);

        TreeMap<String, TreeSet<Number>> numberSorted = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o2) - Integer.parseInt(o1);
            }
        });

        currentJournal.getNumbers().forEach((Number number) ->{

            TreeSet<Number> currentValue = numberSorted.getOrDefault(number.getYear(), new TreeSet<Number>());

            currentValue.add(number);
                 numberSorted.put(number.getYear(), currentValue);
                }
        );

        model.addAttribute("currentJournal", currentJournal);
        model.addAttribute("numbersSorted", numberSorted);
        return "journal";
    }

    @GetMapping("/number/{numb_id}")
    private String getNumberPage(Model model, @PathVariable int numb_id){
        Number number = numberService.getNumberById(numb_id);
        model.addAttribute(number);
        init_menu(model);
        return "number";
    }

    @GetMapping("/article/{art_id}")
    private String getArticlePage(Model model, @PathVariable int art_id){
        Article article = articleService.getArticleById(art_id);
        model.addAttribute(article);
        init_menu(model);
        return "article";
    }

    @GetMapping("/books/{book_id}")
    private String getBookPage(Model model, @PathVariable int book_id){
        Books book = bookService.getBookById(book_id);
        model.addAttribute("book", book);
        init_menu(model);
        return "book";
    }

    @GetMapping("/booksection/{id}")
    private String getBookSectionPage(Model model, @PathVariable int id, @RequestParam(required = false, defaultValue = "1") int page){
        BookSec section = bookSecService.getBookSecById(id);
        int bookCount = section.getBooks().size();
        int bookCountOnPage = 9;
        int totalPages = (bookCount%bookCountOnPage==0)?bookCount/bookCountOnPage:bookCount/bookCountOnPage+1;
        int start = bookCountOnPage*(page-1);
        int end = (start+bookCountOnPage>bookCount)?bookCount-1:start+bookCountOnPage-1;
        model.addAttribute("section", section);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        init_menu(model);
        return "bookssection";
    }

    @GetMapping("/news")
    private String getNewsPage(Model model){
        List<News> news = newsService.getAllNews();
        model.addAttribute("news", news);
        init_menu(model);
        return "news";
    }

    @GetMapping("/news/{id}")
    private String getNewsPage(Model model, @PathVariable int id){
        News selectedNews = newsService.getNewsById(id);
        model.addAttribute("selectedNews", selectedNews);
        init_menu(model);
        return "news";
    }

    @GetMapping("/keywords")
    private String getArticleByKeywordsPage(Model model, @RequestParam (required = true) String keywords){
        List<Article> articles = articleService.getArticlesByKeywords(keywords);
        model.addAttribute("articles", articles);
        model.addAttribute("keywords", keywords);
        init_menu(model);
        return "keywords";
    }

    private void init_menu(Model model){
        ArrayList<Journals> journals = new ArrayList<>(journalsService.getAllJournals());
        ArrayList<BookSec> bookSec = new ArrayList<>(bookSecService.getAllBookSec());
        Map<String, ArrayList> attributes = new HashMap<>();
        attributes.put("journals", journals);
        attributes.put("booksecs", bookSec);
        model.addAllAttributes(attributes);
    }
}
