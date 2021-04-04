package ru.radiotec.site.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.radiotec.site.entity.*;
import ru.radiotec.site.entity.Number;
import ru.radiotec.site.services.*;
//import org.springframework.security.core.userdetails.User;


import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping(value = {"/ru/", "/en/"})
public class MainController {

    private RedisProperties.Jedis jedis;

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

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession httpSession;



    @GetMapping("/")
    public String getMainPage(Model model) {
        init_menu(model);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        TreeSet<Books> booksNew = new TreeSet<>(new Comparator<Books>() {
            @Override
            public int compare(Books o1, Books o2) {
                return o2.getSortNew()- o1.getSortNew();
            }
        });
        booksNew.addAll(bookService.getNewBooks());
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

    @GetMapping("/journals_info")
    public String getJournalsInfoPage(Model model) {
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        init_menu(model);
        Page page = pageService.getPageById(9);
        model.addAttribute("page", page);
        return "journals_info";
    }

    @GetMapping("/books_info")
    public String getBooksInfoPage(Model model) {
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        init_menu(model);
        Page page = pageService.getPageById(10);
        model.addAttribute("page", page);
        return "books_info";
    }


    @GetMapping("/cart")
    public String getCartPage(Model model) {
        init_menu(model);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        List<Cart> carts = (List<Cart>)httpSession.getAttribute("cart");

        if(carts != null) {
            List<Books> books = new ArrayList<>();
            List<Article> articles = new ArrayList<>();
            List<Number> numbers = new ArrayList<>();

            int totalPrice = 0;

            for (Cart cart : carts) {
                if (cart.getType().equals("book")) {
                    Books book = bookService.getBookById(cart.getProduct());
                    books.add(book);
                    totalPrice += book.getPrice();
                } else if (cart.getType().equals("article")) {
                    Article article = articleService.getArticleById(cart.getProduct());
                    articles.add(article);
                    totalPrice += article.getPrice();
                } else if (cart.getType().equals("number")) {
                    Number number = numberService.getNumberById(cart.getProduct());
                    numbers.add(number);
                    totalPrice += number.getPrice();
                }
            }

            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("books", books);
            model.addAttribute("articles", articles);
            model.addAttribute("numbers", numbers);
        }


        model.addAttribute("carts", carts);
        model.addAttribute("order", new Order());
        return "cart";
    }


    @GetMapping("/journal/{journalId}")
    public String getJournalPage(Model model, @PathVariable int journalId, @RequestParam(defaultValue = "", required = false) String page) {
        Journals currentJournal = journalsService.getJournalById(journalId);
        TreeMap<String, TreeSet<Number>> numberSorted = journalsService.getNumberSortedByYear(currentJournal, true);

        if (numberSorted.size() > 0) {
            String lastYear = numberSorted.firstKey();
            int currentNumber = numberSorted.get(lastYear).last().getId();
            model.addAttribute("currentNumber", currentNumber);
        }
        model.addAttribute("currentJournal", currentJournal);
        model.addAttribute("numbersSorted", numberSorted);
        model.addAttribute("page", page);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);

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

        List<Cart> carts = (List<Cart>)httpSession.getAttribute("cart");
        boolean cartActive = false;
        if(carts != null){
            for(Cart cart: carts){
                if (cart.getProduct() == numberId && cart.getType().equals("number")) {
                    cartActive = true;
                    break;
                }
            }}
        model.addAttribute("cartActive", cartActive);
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

        List<Cart> carts = (List<Cart>)httpSession.getAttribute("cart");
        boolean cartActive = false;
        if(carts != null){
            for(Cart cart: carts){
                if (cart.getProduct() == artId && cart.getType().equals("article")) {
                    cartActive = true;
                    break;
                }
            }}
        model.addAttribute("cartActive", cartActive);
        return "article";
    }

    @GetMapping("/books/{book_id}")
    public String getBookPage(Model model, @PathVariable int book_id) {
        Books currentBook = bookService.getBookById(book_id);
        model.addAttribute("currentBook", currentBook);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        ArrayList<BookSec> bookSec = new ArrayList<>(bookSecService.getAllBookSec());
        model.addAttribute("booksecs", bookSec);

        List<Cart> carts = (List<Cart>)httpSession.getAttribute("cart");
        boolean cartActive = false;
        if(carts != null){
        for(Cart cart: carts){
            if (cart.getProduct() == book_id && cart.getType().equals("book")) {
                cartActive = true;
                break;
            }
        }}
        model.addAttribute("cartActive", cartActive);
        model.addAttribute("backActive", false);

        return "book";
    }

    @GetMapping("/booksection/{id}")
    public String getBookSectionPage(Model model, @PathVariable int id, @RequestParam(required = false, defaultValue = "1") int page) {
        BookSec currentBookSection = bookSecService.getBookSecById(id);
        int bookCount = currentBookSection.getBooks().size();
        int bookCountOnPage = 9;
        int totalPages = (bookCount % bookCountOnPage == 0) ? bookCount / bookCountOnPage : bookCount / bookCountOnPage + 1;
        int start = bookCountOnPage * (page - 1);
        int end = (start + bookCountOnPage > bookCount) ? bookCount - 1 : start + bookCountOnPage - 1;
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentBookSection", currentBookSection);
        init_menu(model);
        model.addAttribute("backActive", false);


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

    @GetMapping("/subscribe/")
    public String getSubscribePage(Model model, @RequestParam(required = false, defaultValue = "0") int journal){
        Page subscribePage = pageService.getPageById(8);
        model.addAttribute("subscribePage", subscribePage);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        Subscribe subscribe = new Subscribe();
        if(journal != 0) {
            subscribe.setProduct(journal);
            Journals currentJournal = journalsService.getJournalById(journal);
            model.addAttribute("currentJournal", currentJournal);
        }
        subscribe.setType("journal");

        List<Journals> journals = journalsService.getActiveJournals();
        model.addAttribute("journals", journals);


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!auth.getName().equals("anonymousUser")){
            User currentUser = userService.getUserByLogin(auth.getName());
            subscribe.setUserId(currentUser.getId().intValue());
        }
        model.addAttribute("subscribe", subscribe);
        return "subscribe";
    }

    @GetMapping("/reader/")
    public String getReaderPage(Model model, @RequestParam(required = false, defaultValue = "0") int section, @RequestParam(required = false, defaultValue = "0") int book) {

        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        if(book > 0){
            Books currentBook = bookService.getBookById(book);
            model.addAttribute("currentBook", currentBook);
        } else if(section > 0){
            BookSec currentBookSection = bookSecService.getBookSecById(section);
            model.addAttribute("currentBookSection", currentBookSection);
        }
        ArrayList<BookSec> bookSec = new ArrayList<>(bookSecService.getAllBookSec());
        model.addAttribute("booksecs", bookSec);

        Page reader = pageService.getPageById(3);
        model.addAttribute("reader", reader);
        return "reader";
    }

    @GetMapping("/partner/")
    public String getPartnerPage(Model model, @RequestParam(required = false, defaultValue = "0") int section, @RequestParam(required = false, defaultValue = "0") int book) {
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        if(book > 0){
            Books currentBook = bookService.getBookById(book);
            model.addAttribute("currentBook", currentBook);
        } else if(section > 0){
            BookSec currentBookSection = bookSecService.getBookSecById(section);
            model.addAttribute("currentBookSection", currentBookSection);
        }
        ArrayList<BookSec> bookSec = new ArrayList<>(bookSecService.getAllBookSec());
        model.addAttribute("booksecs", bookSec);

        Page partner = pageService.getPageById(4);
        model.addAttribute("partner", partner);
        return "partner";
    }

    @GetMapping("/author/")
    public String getAuthorPage(Model model, @RequestParam(required = false, defaultValue = "0") int section, @RequestParam(required = false, defaultValue = "0") int book) {
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        if(book > 0){
            Books currentBook = bookService.getBookById(book);
            model.addAttribute("currentBook", currentBook);
        } else if(section > 0){
            BookSec currentBookSection = bookSecService.getBookSecById(section);
            model.addAttribute("currentBookSection", currentBookSection);
        }
        ArrayList<BookSec> bookSec = new ArrayList<>(bookSecService.getAllBookSec());
        model.addAttribute("booksecs", bookSec);

        Page author = pageService.getPageById(5);
        model.addAttribute("author", author);
        return "author";
    }

    @GetMapping("/order_book/")
    public String getOrderBookPage(Model model, @RequestParam(required = false, defaultValue = "0") int section, @RequestParam(required = false, defaultValue = "0") int book) {
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        if(book > 0){
            Books currentBook = bookService.getBookById(book);
            model.addAttribute("currentBook", currentBook);
        } else if(section > 0){
            BookSec currentBookSection = bookSecService.getBookSecById(section);
            model.addAttribute("currentBookSection", currentBookSection);
        }
        ArrayList<BookSec> bookSec = new ArrayList<>(bookSecService.getAllBookSec());
        model.addAttribute("booksecs", bookSec);


        Subscribe subscribe = new Subscribe();
        subscribe.setType("book_section");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!auth.getName().equals("anonymousUser")){
            User currentUser = userService.getUserByLogin(auth.getName());
            subscribe.setUserId(currentUser.getId().intValue());
        }
        model.addAttribute("subscribe", subscribe);

        Page page = pageService.getPageById(7);
        model.addAttribute("page", page);
        return "order_book";
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
