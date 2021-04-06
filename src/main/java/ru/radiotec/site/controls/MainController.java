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


import javax.servlet.http.HttpServletRequest;
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
        model.addAttribute("title", "Поиск");
        model.addAttribute("titleEng", "Search");
        return "search";
    }

    @GetMapping("/journals_info")
    public String getJournalsInfoPage(Model model) {
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        init_menu(model);
        Page page = pageService.getPageById(9);
        model.addAttribute("page", page);
        model.addAttribute("title", "Журналы");
        model.addAttribute("titleEng", "Journals");
        return "journals_info";
    }

    @GetMapping("/books_info")
    public String getBooksInfoPage(Model model) {
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        init_menu(model);
        Page page = pageService.getPageById(10);
        model.addAttribute("page", page);
        model.addAttribute("title", "Книги");
        model.addAttribute("titleEng", "Books");
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
            List<Journals> subscribes = new ArrayList<>();
            List<Books> orders = new ArrayList<>();
            Map<Integer, Integer> orderCount = new HashMap<>();
            Map<Integer, Integer> subscribeCount = new HashMap<>();

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
                } else if (cart.getType().equals("journal_subscribe")) {
                    Journals subscribe = journalsService.getJournalById(cart.getProduct());
                    subscribes.add(subscribe);
                    totalPrice += subscribe.getPriceSubscribe()*cart.getCount();
                    subscribeCount.put(subscribe.getId(),cart.getCount());
                } else if (cart.getType().equals("order_book")) {
                    Books order = bookService.getBookById(cart.getProduct());
                    orders.add(order);
                    totalPrice += order.getPriceOrder()*cart.getCount();
                    orderCount.put(order.getId(),cart.getCount());
                }
            }

            model.addAttribute("title", "Корзина");
            model.addAttribute("titleEng", "Cart");
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("books", books);
            model.addAttribute("articles", articles);
            model.addAttribute("numbers", numbers);
            model.addAttribute("orders", orders);
            model.addAttribute("subscribes", subscribes);
            model.addAttribute("orderCount", orderCount);
            model.addAttribute("subscribeCount", subscribeCount);
        }


        model.addAttribute("carts", carts);
        model.addAttribute("order", new Order());
        return "cart";
    }


    @GetMapping("/journal/{journalId}")
    public String getJournalPage(Model model, @PathVariable String journalId, @RequestParam(defaultValue = "", required = false) String page) {
        Journals currentJournal;
        try{
            int id = Integer.parseInt(journalId);
            currentJournal = journalsService.getJournalById(id);
        }catch(NumberFormatException e){
            currentJournal = journalsService.getJournalByLink(journalId);
        }


        TreeMap<String, TreeSet<Number>> numberSorted = journalsService.getNumberSortedByYear(currentJournal, true);

        if (numberSorted.size() > 0) {
            String lastYear = numberSorted.firstKey();
            Number currentNumber = numberSorted.get(lastYear).last();
            model.addAttribute("currentNumber", currentNumber);
        }
        model.addAttribute("currentJournal", currentJournal);
        model.addAttribute("numbersSorted", numberSorted);
        model.addAttribute("page", page);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);

        model.addAttribute("title", currentJournal.getName());
        model.addAttribute("titleEng", currentJournal.getNameEng());

        List<Cart> carts = (List<Cart>)httpSession.getAttribute("cart");
        boolean cartActive = false;
        if(carts != null){
            for(Cart cart: carts){
                if (cart.getProduct() == currentJournal.getId() && cart.getType().equals("journal_subscribe")) {
                    cartActive = true;
                    break;
                }
            }}
        model.addAttribute("cartActive", cartActive);

        return "journal";
    }

    @GetMapping("/journal/{journalId}/number/{numberId}")
    public String getNumberPage(Model model, @PathVariable String journalId, @PathVariable String numberId) {
        Journals currentJournal;
        try{
            int id = Integer.parseInt(journalId);
            currentJournal = journalsService.getJournalById(id);
        }catch(NumberFormatException e){
            currentJournal = journalsService.getJournalByLink(journalId);
        }

        Number number = null;
        try{
            int id = Integer.parseInt(numberId);
            number = numberService.getNumberById(id);
        }catch(NumberFormatException e){
            String[] search = numberId.split("-",2);
            if(search.length > 1) {
                number = numberService.getNumberByYearAndNameAndJournalId(search[0], search[1], currentJournal.getId());
            }
        }


        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        model.addAttribute("currentJournal", currentJournal);
        model.addAttribute(number);

        List<Cart> carts = (List<Cart>)httpSession.getAttribute("cart");
        boolean cartActive = false;
        if(carts != null){
            for(Cart cart: carts){
                if (cart.getProduct() == number.getId() && cart.getType().equals("number")) {
                    cartActive = true;
                    break;
                }
            }}
        model.addAttribute("cartActive", cartActive);
        model.addAttribute("title", currentJournal.getName());
        model.addAttribute("titleEng", currentJournal.getNameEng());
        return "number";
    }

    @GetMapping("/journal/{journalId}/number/{numberId}/article/{artId}")
    public String getArticlePage(Model model, @PathVariable String journalId, @PathVariable String numberId, @PathVariable int artId) {
        Journals currentJournal;
        try{
            int id = Integer.parseInt(journalId);
            currentJournal = journalsService.getJournalById(id);
        }catch(NumberFormatException e){
            currentJournal = journalsService.getJournalByLink(journalId);
        }

        Article article = articleService.getArticleById(artId);
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
        model.addAttribute("title", article.getName());
        model.addAttribute("titleEng", article.getNameEng());
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
        boolean orderActive = false;
        if(carts != null){
        for(Cart cart: carts){
            if (cart.getProduct() == book_id && cart.getType().equals("book")) {
                cartActive = true;
            }
            if (cart.getProduct() == book_id && cart.getType().equals("order_book")) {
                orderActive = true;
            }
        }}
        model.addAttribute("cartActive", cartActive);
        model.addAttribute("orderActive", orderActive);
        model.addAttribute("backActive", false);
        model.addAttribute("title", currentBook.getName());
        model.addAttribute("titleEng", currentBook.getNameEng());

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
        model.addAttribute("title", currentBookSection.getName());
        model.addAttribute("titleEng", currentBookSection.getNameEng());


        return "bookssection";
    }

    @GetMapping("/news")
    public String getNewsPage(Model model) {
        List<News> news = newsService.getAllNews();
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        model.addAttribute("news", news);
        init_menu(model);
        model.addAttribute("title", "Новости");
        model.addAttribute("titleEng", "News");
        return "news";
    }

    @GetMapping("/news/{id}")
    public String getNewsPage(Model model, @PathVariable int id) {
        News selectedNews = newsService.getNewsById(id);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        model.addAttribute("selectedNews", selectedNews);
        init_menu(model);
        model.addAttribute("title", "Новости");
        model.addAttribute("titleEng", "News");
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
        model.addAttribute("title", "Подписка");
        model.addAttribute("titleEng", "Subscribe");
        return "subscription";
    }

    @GetMapping("/order")
    public String getOrderPage(Model model) {
        init_menu(model);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        model.addAttribute("title", "Заказ книг");
        model.addAttribute("titleEng", "Ordering books");
        return "order_books";
    }

    @GetMapping("/for_authors")
    public String getAuthorsPage(Model model) {
        init_menu(model);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        Page page = pageService.getPageById(1);
        model.addAttribute("page", page);
        model.addAttribute("title", "Для авторов");
        model.addAttribute("titleEng", "For authors");
        return "custom_page";
    }

    @GetMapping("/about")
    public String getAboutPage(Model model) {
        init_menu(model);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        Page page = pageService.getPageById(2);
        model.addAttribute("page", page);
        model.addAttribute("title", "О журнале");
        model.addAttribute("titleEng", "About");
        return "custom_page";
    }

    @GetMapping("/subscribe/")
    public String getSubscribePage(Model model, @RequestParam(required = false, defaultValue = "0") int journal){
        Page subscribePage = pageService.getPageById(8);
        model.addAttribute("subscribePage", subscribePage);
        Page headerRight = pageService.getPageById(6);
        model.addAttribute("headerRight", headerRight);
        model.addAttribute("title", "Подписка");
        model.addAttribute("titleEng", "Subscribe");
        return "subscribe";
    }

    @GetMapping("/reader/")
    public String getReaderPage(Model model, @RequestParam(required = false, defaultValue = "0") int section, @RequestParam(required = false, defaultValue = "0") int book, HttpServletRequest request) {

        String referer = request.getHeader("Referer");
        System.out.println(referer);
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
        model.addAttribute("title", "Читателям");
        model.addAttribute("titleEng", "To readers");
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
        model.addAttribute("title", "Партнерам");
        model.addAttribute("titleEng", "Partners");
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
        model.addAttribute("title", "Авторам");
        model.addAttribute("titleEng", "Authors");
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
        Page page = pageService.getPageById(7);
        model.addAttribute("page", page);
        model.addAttribute("title", "Заказ книг");
        model.addAttribute("titleEng", "Ordering books");
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
