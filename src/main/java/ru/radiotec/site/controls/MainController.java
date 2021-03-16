package ru.radiotec.site.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.radiotec.site.entity.*;
import ru.radiotec.site.entity.Number;
import ru.radiotec.site.services.*;

import javax.sound.midi.Soundbank;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.*;

@Controller
@RequestMapping(value={"/", "/ru/", "/en/"})
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

    @Autowired
    private PageService pageService;

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

        TreeMap<String, TreeSet<Number>> numberSorted = journalsService.getNumberSortedByYear(currentJournal);
        model.addAttribute("currentJournal", currentJournal);
        model.addAttribute("numbersSorted", numberSorted);
        return "journal";
    }

    @GetMapping("/number/{numb_id}")
    public String getNumberPage(Model model, @PathVariable int numb_id){
        Number number = numberService.getNumberById(numb_id);
        model.addAttribute(number);
        init_menu(model);
        return "number";
    }

    @GetMapping("/article/{art_id}")
    public String getArticlePage(Model model, @PathVariable int art_id){
        Article article = articleService.getArticleById(art_id);
        model.addAttribute(article);
        init_menu(model);
        return "article";
    }

    @GetMapping("/books/{book_id}")
    public String getBookPage(Model model, @PathVariable int book_id){
        Books book = bookService.getBookById(book_id);
        model.addAttribute("book", book);
        init_menu(model);
        return "book";
    }

    @GetMapping("/booksection/{id}")
    public String getBookSectionPage(Model model, @PathVariable int id, @RequestParam(required = false, defaultValue = "1") int page){
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
    public String getNewsPage(Model model){
        List<News> news = newsService.getAllNews();
        model.addAttribute("news", news);
        init_menu(model);
        return "news";
    }

    @GetMapping("/news/{id}")
    public String getNewsPage(Model model, @PathVariable int id){
        News selectedNews = newsService.getNewsById(id);
        model.addAttribute("selectedNews", selectedNews);
        init_menu(model);
        return "news";
    }

    @GetMapping("/keywords")
    public String getArticleByKeywordsPage(Model model, @RequestParam (required = true) String keywords){
        List<Article> articles = articleService.getArticlesByKeywords(keywords);
        model.addAttribute("articles", articles);
        model.addAttribute("keywords", keywords);
        init_menu(model);
        return "keywords";
    }

    @GetMapping("/subscription")
    public String getSubscriptionPage(Model model){
        init_menu(model);
        return "subscription";
    }
    @GetMapping("/order")
    public String getOrderPage(Model model){
        init_menu(model);
        return "order_books";
    }

    @GetMapping("/for_authors")
    public String getAuthorsPage(Model model){
        init_menu(model);
        Page page = pageService.getPageById(1);
        model.addAttribute("page", page);
        return "custom_page";
    }

    @GetMapping("/about")
    public String getAboutPage(Model model){
        init_menu(model);
        Page page = pageService.getPageById(2);
        model.addAttribute("page", page);
        return "custom_page";
    }

    @PostMapping("/upload_ckeditor")
    public String CKEditor_upload(Model model, @RequestParam String CKEditorFuncNum, @RequestParam("upload") MultipartFile file){

        if (!file.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/CKEditor/"+file.getOriginalFilename();
            model.addAttribute("path","/uploads/CKEditor/"+file.getOriginalFilename());
            if(file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")){
                try {
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(name));
                    stream.write(bytes);
                    stream.close();
                    System.out.println("Файл загружен");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        model.addAttribute("callback",CKEditorFuncNum);

        return "ckeditor_upload";
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
