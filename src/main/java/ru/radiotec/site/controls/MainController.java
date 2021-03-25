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
@RequestMapping(value = {"/ru/", "/en/"})
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
    public String getMainPage(Model model) {
        init_menu(model);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        ArrayList<Books> booksNew = new ArrayList<>(bookService.getNewBooks());
        ArrayList<News> urgentlyNews = new ArrayList<>(newsService.getNewsByType(1));
        ArrayList<News> shortNews = new ArrayList<>(newsService.getNewsByType(2));
        model.addAttribute("booksnew", booksNew);
        model.addAttribute("urgentlyNews", urgentlyNews);
        model.addAttribute("shortNews", shortNews);
        return "index";
    }

    @GetMapping("/search/")
    public String getSearchPage(Model model) {
        init_menu(model);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        return "search";
    }

    @GetMapping("/journal/{journalId}")
    public String getJournalPage(Model model, @PathVariable int journalId, @RequestParam(defaultValue = "", required = false) String page) {
        Journals currentJournal = journalsService.getJournalById(journalId);
        TreeMap<String, TreeSet<Number>> numberSorted = journalsService.getNumberSortedByYear(currentJournal, true);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        if (numberSorted.size() > 0) {
            String lastYear = numberSorted.firstKey();
            int currentNumber = numberSorted.get(lastYear).last().getId();
            model.addAttribute("currentNumber", currentNumber);
        }
        model.addAttribute("currentJournal", currentJournal);
        model.addAttribute("numbersSorted", numberSorted);
        model.addAttribute("page", page);
        return "journal";
    }

    @GetMapping("/journal/{journalId}/number/{numberId}")
    public String getNumberPage(Model model, @PathVariable int journalId, @PathVariable int numberId) {
        Journals currentJournal = journalsService.getJournalById(journalId);
        Number number = numberService.getNumberById(numberId);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        model.addAttribute("currentJournal", currentJournal);
        model.addAttribute(number);
        return "number";
    }

    @GetMapping("/journal/{journalId}/number/{numberId}/section/{secId}/article/{artId}")
    public String getArticlePage(Model model, @PathVariable int journalId, @PathVariable int numberId, @PathVariable int secId, @PathVariable int artId) {
        Article article = articleService.getArticleById(artId);
        Journals currentJournal = journalsService.getJournalById(journalId);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        model.addAttribute("currentJournal", currentJournal);
        model.addAttribute(article);
        return "article";
    }

    @GetMapping("/books/{book_id}")
    public String getBookPage(Model model, @PathVariable int book_id, @RequestParam(defaultValue = "", required = false) String page) {
        Books currentBook = bookService.getBookById(book_id);
        model.addAttribute("currentBook", currentBook);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        ArrayList<BookSec> bookSec = new ArrayList<>(bookSecService.getAllBookSec());
        model.addAttribute("booksecs", bookSec);
        model.addAttribute("page", page);
        if(page.equals("reader")){
            Page reader = pageService.getPageById(3);
            model.addAttribute("reader", reader);
        } else if(page.equals("partner")){
            Page partner = pageService.getPageById(4);
            model.addAttribute("partner", partner);
        } else if(page.equals("author")){
            Page author = pageService.getPageById(5);
            model.addAttribute("author", author);
        }
        return "book";
    }

    @GetMapping("/booksection/{id}")
    public String getBookSectionPage(Model model, @PathVariable int id, @RequestParam(required = false, defaultValue = "1") int page) {
        BookSec section = bookSecService.getBookSecById(id);
        int bookCount = section.getBooks().size();
        int bookCountOnPage = 9;
        int totalPages = (bookCount % bookCountOnPage == 0) ? bookCount / bookCountOnPage : bookCount / bookCountOnPage + 1;
        int start = bookCountOnPage * (page - 1);
        int end = (start + bookCountOnPage > bookCount) ? bookCount - 1 : start + bookCountOnPage - 1;
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        model.addAttribute("section", section);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        init_menu(model);
        return "bookssection";
    }

    @GetMapping("/news")
    public String getNewsPage(Model model) {
        List<News> news = newsService.getAllNews();
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        model.addAttribute("news", news);
        init_menu(model);
        return "news";
    }

    @GetMapping("/news/{id}")
    public String getNewsPage(Model model, @PathVariable int id) {
        News selectedNews = newsService.getNewsById(id);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        model.addAttribute("selectedNews", selectedNews);
        init_menu(model);
        return "news";
    }

    @GetMapping("/keywords")
    public String getArticleByKeywordsPage(Model model, @RequestParam(required = true) String keywords) {
        List<Article> articles = articleService.getArticlesByKeywords(keywords);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        model.addAttribute("articles", articles);
        model.addAttribute("keywords", keywords);
        init_menu(model);
        return "keywords";
    }

    @GetMapping("/subscription")
    public String getSubscriptionPage(Model model) {
        init_menu(model);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        return "subscription";
    }

    @GetMapping("/order")
    public String getOrderPage(Model model) {
        init_menu(model);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        return "order_books";
    }

    @GetMapping("/for_authors")
    public String getAuthorsPage(Model model) {
        init_menu(model);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        Page page = pageService.getPageById(1);
        model.addAttribute("page", page);
        return "custom_page";
    }

    @GetMapping("/about")
    public String getAboutPage(Model model) {
        init_menu(model);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        Page page = pageService.getPageById(2);
        model.addAttribute("page", page);
        return "custom_page";
    }

    @PostMapping("/upload_ckeditor")
    public String CKEditor_upload(Model model, @RequestParam String CKEditorFuncNum, @RequestParam("upload") MultipartFile file) {

        if (!file.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/CKEditor/" + file.getOriginalFilename();
            model.addAttribute("path", "/uploads/CKEditor/" + file.getOriginalFilename());
            if (file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")) {
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

        model.addAttribute("callback", CKEditorFuncNum);

        return "ckeditor_upload";
    }


    private void init_menu(Model model) {
        TreeSet<Journals> journals = new TreeSet<>(journalsService.getActiveJournals());
        ArrayList<BookSec> bookSec = new ArrayList<>(bookSecService.getAllBookSec());
        Map<String, Collection> attributes = new HashMap<>();
        attributes.put("journals", journals);
        attributes.put("booksecs", bookSec);
        model.addAllAttributes(attributes);
    }
}
