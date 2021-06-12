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
    private PageService pageService;

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
    private ArticleTypeService articleTypeService;

    @Autowired
    private BookTypeService bookTypeService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String getAdminMainPage(Model model) {

        return "admin_template";
    }

    @GetMapping("/journal_add")
    public String getAdminJournalAddPage(Model model) {
        model.addAttribute("action", "journal_add");
        model.addAttribute("journals", new Journals());
        return "admin_journal";
    }

    @GetMapping("/journal_delete")
    public String getAdminJournalDeletePage(@RequestParam(required = true) int id) {
        Journals journal = journalsService.getJournalById(id);
        journalsService.delete(journal);
        return "redirect:/admin/journal_change";
    }

    @PostMapping("/journal_add")
    public String getAdminJournalAddPage(@Valid Journals journal, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile cover) {

        if (!cover.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/journals/" + cover.getOriginalFilename();
            if (cover.getContentType().equals("image/png") || cover.getContentType().equals("image/jpeg")) {
                try {
                    byte[] bytes = cover.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(name));
                    stream.write(bytes);
                    stream.close();
                    System.out.println("Файл загружен");
                    journal.setCover(cover.getOriginalFilename());
                } catch (Exception e) {
                    bindingResult.rejectValue("cover", "", "Не удалось загрузить файл: " + e.getMessage());
                }
            } else {
                bindingResult.rejectValue("cover", "", "Файл должен быть в формате png или jpg");
            }
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "journal_add");
            return "admin_journal";
        }
        journalsService.create(journal);
        return "redirect:/admin/journal_change";
    }

    @PostMapping("/journal_change")
    public String getAdminJournalChangePage(@Valid Journals journal, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile cover) {
        if (!cover.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/journals/" + cover.getOriginalFilename();
            System.out.println(cover.getContentType());
            if (cover.getContentType().equals("image/png") || cover.getContentType().equals("image/jpeg")) {
                try {
                    byte[] bytes = cover.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(name));
                    stream.write(bytes);
                    stream.close();
                    System.out.println("Файл загружен");
                    journal.setCover(cover.getOriginalFilename());
                } catch (Exception e) {
                    System.out.println("Не удалось загрузить " + name + " => " + e.getMessage());

                    bindingResult.rejectValue("cover", "", "Не удалось загрузить файл: " + e.getMessage());
                }
            } else {
                bindingResult.rejectValue("cover", "", "Файл должен быть в формате png или jpg");
            }

        }

        if (bindingResult.hasErrors()) {
            return getAdminJournalChangePage(model, journal, 0);
        }
        journalsService.update(journal);
        return getAdminJournalChangePage(model,journal, 0);
    }

    @GetMapping("/journal_change")
    public String getAdminJournalChangePage(Model model, Journals journal, @RequestParam(defaultValue = "-1") int id) {

        if (id > 0 || journal.getId() > 0) {
            if (id > 0) {
                journal = journalsService.getJournalById(id);
            }
            model.addAttribute("journals", journal);
            model.addAttribute("action", "journal_change");
        } else {
            TreeSet<Journals> journals = new TreeSet<>(journalsService.getAllJournals());
            model.addAttribute("journals", journals);
            model.addAttribute("action", "journal_list");

            Map<Journals, TreeMap<String, TreeSet<Number>>> numberSortedList = new HashMap<>();
            for (Journals item : journals) {
                numberSortedList.put(item, journalsService.getNumberSortedByYear(item, false));
            }
            model.addAttribute("numberSortedList", numberSortedList);
        }
        return "admin_journal";
    }

    @GetMapping("/number_add")
    public String getAdminNumberAddPage(Model model, Number number, @RequestParam(required = false) Integer journal_id) {
        if (number == null) {
            number = new Number();
        }
        if (journal_id > 0) {
            Journals journal = journalsService.getJournalById(journal_id);
            number.setJournalId(journal.getId());
        } else {
            List<Journals> journals = journalsService.getAllJournals();
            model.addAttribute("journals", journals);
        }
        model.addAttribute("action", "number_add");
        model.addAttribute("number", number);
        return "admin_number";
    }

    @PostMapping("/number_add")
    public String getAdminJournalAddPage(@Valid Number number, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile file) {


        if(numberService.getNumberByYearAndNameAndJournalId(number.getYear(), number.getName(), number.getJournalId()) != null){
            bindingResult.rejectValue("name", "", "Такой номер уже существует");
            return getAdminNumberAddPage(model, number, 0);
        }

        if (!file.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/numbers/" + file.getOriginalFilename();
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(name));
                stream.write(bytes);
                stream.close();
                System.out.println("Файл загружен");
                number.setNumberFile(file.getOriginalFilename());
            } catch (Exception e) {
                System.out.println("Не удалось загрузить " + name + " => " + e.getMessage());
                bindingResult.rejectValue("numberFile", "", "Не удалось загрузить файл: " + e.getMessage());
            }

        }


        if (number.getJournalId() < 1) {
            bindingResult.rejectValue("journalId", "", "Не выбран журнал");
            return getAdminNumberAddPage(model, number, 0);
        }
        if (bindingResult.hasErrors()) {
            return getAdminNumberAddPage(model, number, 0);
        }

        numberService.create(number);
        return "redirect:/admin/journal_change";
    }

    @GetMapping("/number_change")
    public String getAdminNumberChangePage(Model model, Number number, @RequestParam(defaultValue = "-1") int id) {

        if (id > 0 || number.getId() > 0) {
            if (id > 0) {
                number = numberService.getNumberById(id);
            }
            model.addAttribute("number", number);
            model.addAttribute("action", "number_change");
        } else {
            List<Journals> journals = journalsService.getAllJournals();
            model.addAttribute("journals", journals);
            model.addAttribute("action", "number_list");

            Map<Journals, TreeMap<String, TreeSet<Number>>> numberSortedList = new HashMap<>();
            for (Journals journal : journals) {
                numberSortedList.put(journal, journalsService.getNumberSortedByYear(journal, false));
            }
            model.addAttribute("numberSortedList", numberSortedList);
        }
        return "admin_number";
    }

    @GetMapping("/number_delete")
    public String getAdminNumberDeletePage(@RequestParam(required = true) int id) {
        Number number = numberService.getNumberById(id);
        numberService.delete(number);
        return "redirect:/admin/number_change";
    }

    @PostMapping("/number_change")
    public String getAdminNumberChangePage(@Valid Number number, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/numbers/" + file.getOriginalFilename();
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(name));
                stream.write(bytes);
                stream.close();
                System.out.println("Файл загружен");
                number.setNumberFile(file.getOriginalFilename());
            } catch (Exception e) {
                System.out.println("Не удалось загрузить " + name + " => " + e.getMessage());
                bindingResult.rejectValue("numberFile", "", "Не удалось загрузить файл: " + e.getMessage());
            }

        }

        if (bindingResult.hasErrors()) {
            return getAdminNumberChangePage(model, number, 0);
        }

        numberService.update(number);
        return getAdminNumberChangePage(model, number, 0);
    }


    @GetMapping("section_list")
    public String getSectionList(Model model, @RequestParam(required = true) int id) {
        TreeSet<Section> sections = new TreeSet<>(sectionService.getSectionByNumber(id));
        model.addAttribute("section_list", sections);
        model.addAttribute("numberId", id);
        return "admin_section_list";
    }

    @GetMapping("/section_add")
    public String getAdminSectionAddPage(Model model, Section section, @RequestParam(required = false) int number_id) {
        if (section == null || section.getId() < 0) {
            section = new Section();
        }
        if (number_id > 0) {
            section.setNumberId(number_id);
        }
        List<Journals> journals = journalsService.getAllJournals();
        model.addAttribute("action", "section_add");
        model.addAttribute("section", section);
        model.addAttribute("journals", journals);
        return "admin_section";
    }

    @PostMapping("/section_add")
    public String getAdminSectionAddPage(@Valid Section section, BindingResult bindingResult, Model model) {

        if (section.getNumberId() <= 0) {
            bindingResult.rejectValue("numberId", "", "Номер не выбран");
            return getAdminSectionAddPage(model, section, 0);
        }

        if (bindingResult.hasErrors()) {
            Number number = numberService.getNumberById(section.getNumberId());
            model.addAttribute("number", number);
            return getAdminSectionAddPage(model, section, 0);
        }

        // Сортировка
        Number number = numberService.getNumberById(section.getNumberId());
        int max = 0;
        for (Section item : number.getSections()) {
            if (item.getSort() > max) {
                max = item.getSort();
            }
        }
        section.setSort(max + 1);

        sectionService.create(section);
        return "redirect:/admin/number_change?id="+section.getNumberId();
    }

    @GetMapping("/section_delete")
    public String getAdminSectionDeletePage(@RequestParam(required = true) int id) {
        Section section = sectionService.getSectionById(id);
        sectionService.delete(section);
        return "redirect:/admin/section_change";
    }

    @GetMapping("/section_change")
    public String getAdminSectionChangePage(Model model, Section section, @RequestParam(defaultValue = "-1") int id) {

        if (id > 0 || section.getId() > 0) {
            if (id > 0) {
                section = sectionService.getSectionById(id);
            }
            model.addAttribute("section", section);
            model.addAttribute("action", "section_change");
        } else {
            List<Journals> journals = journalsService.getAllJournals();
            model.addAttribute("journals", journals);
            model.addAttribute("action", "section_list");
        }
        return "admin_section";
    }

    @PostMapping("/section_change")
    public String getAdminSectionChangePage(@Valid Section section, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getAdminSectionChangePage(model, section, 0);
        }
        sectionService.update(section);
        return getAdminSectionChangePage(model, section, 0);
    }

    @GetMapping("article_list")
    public String getArticleList(Model model, @RequestParam(required = true) int id) {
        List<Article> articles = articleService.getArticleBySection(id);
        model.addAttribute("article_list", articles);
        model.addAttribute("sectionId", id);
        return "admin_article_list";
    }

    @GetMapping("/article_add")
    public String getAdminArticleAddPage(Model model, Article article, @RequestParam(required = false) int section_id) {
        if (article == null) {
            article = new Article();
        }
        if (section_id > 0) {
            article.setSectionId(section_id);
        }
//        List<Journals> journals = journalsService.getAllJournals();
        List<ArticleType> types = articleTypeService.getAllArticleType();
        model.addAttribute("action", "article_add");
        model.addAttribute("article", article);
//        model.addAttribute("journals",journals);
        model.addAttribute("types", types);
        return "admin_article";
    }

    @PostMapping("/article_add")
    public String getAdminArticleAddPage(@Valid Article article, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/articles/" + file.getOriginalFilename();
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(name));
                stream.write(bytes);
                stream.close();
                System.out.println("Файл загружен");
                article.setArticleFile(file.getOriginalFilename());
            } catch (Exception e) {
                System.out.println("Не удалось загрузить " + name + " => " + e.getMessage());
                bindingResult.rejectValue("articleFile", "", "Не удалось загрузить файл: " + e.getMessage());
            }

        }

        if (article.getSectionId() <= 0) {
            bindingResult.rejectValue("sectionId", "", "Не выбран раздел");
            return getAdminArticleAddPage(model, article, 0);
        }
        if (bindingResult.hasErrors()) {
            return getAdminArticleAddPage(model, article, 0);
        }
        articleService.create(article);
        return "redirect:/admin/section_change?id="+article.getSectionId();
    }

    @GetMapping("/article_change")
    public String getAdminArticleChangePage(Model model, Article article, @RequestParam(defaultValue = "-1") int id) {

        if (id > 0 || article.getId() > 0) {
            if (id > 0) {
                article = articleService.getArticleById(id);
            }
            model.addAttribute("article", article);
            model.addAttribute("action", "article_change");
            List<ArticleType> types = articleTypeService.getAllArticleType();
            model.addAttribute("types", types);
        } else {
            List<Journals> journals = journalsService.getAllJournals();
            model.addAttribute("journals", journals);
            model.addAttribute("action", "article_list");
        }
        return "admin_article";
    }

    @PostMapping("/article_change")
    public String getAdminArticleChangePage(@Valid Article article, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/articles/" + file.getOriginalFilename();
//            String name = file.getOriginalFilename();
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(name));
                stream.write(bytes);
                stream.close();
                System.out.println("Файл загружен");
                article.setArticleFile(file.getOriginalFilename());
            } catch (Exception e) {
                System.out.println("Не удалось загрузить " + name + " => " + e.getMessage());

                bindingResult.rejectValue("articleFile", "", "Не удалось загрузить файл: " + e.getMessage());
            }

        }

        if (bindingResult.hasErrors()) {
            return getAdminArticleChangePage(model, article, 0);
        }
        articleService.update(article);
        return getAdminArticleChangePage(model, article, 0);
    }

    @GetMapping("/article_delete")
    public String getAdminArticleDeletePage(@RequestParam(required = true) int id) {
        Article article = articleService.getArticleById(id);
        articleService.delete(article);
        return "redirect:/admin/article_change";
    }

    @GetMapping("/booksec_add")
    public String getAdminBookSecPage(Model model) {
        if (!model.containsAttribute("bookSec")) {
            model.addAttribute("bookSec", new BookSec());
        }
        model.addAttribute("action", "booksec_add");
        return "admin_booksec";
    }

    @PostMapping("/booksec_add")
    public String getAdminBookSecAddPage(@Valid BookSec bookSec, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            BookSec bookSecCopy = bookSec;
            model.addAttribute("bookSec", bookSecCopy);
            return getAdminBookSecPage(model);
        }
        bookSecService.create(bookSec);
        return "redirect:/admin/";
    }


    @GetMapping("/booksec_change")
    public String getAdminBookSecChangePage(Model model, @RequestParam(defaultValue = "-1") int id) {

        if (id > 0 || model.containsAttribute("bookSec")) {
            if (id > 0) {
                BookSec booksec = bookSecService.getBookSecById(id);
                model.addAttribute("bookSec", booksec);
            }
            model.addAttribute("action", "booksec_change");
        } else {
            List<BookSec> booksecs = bookSecService.getAllBookSec();
            model.addAttribute("booksecs", booksecs);
            model.addAttribute("action", "booksec_list");
        }

        return "admin_booksec";
    }

    @PostMapping("/booksec_change")
    public String getAdminBookSecChangePage(@Valid BookSec booksec, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            BookSec bookSecCopy = booksec;
            model.addAttribute("bookSec", bookSecCopy);
            return getAdminBookSecChangePage(model, 0);
        }

        bookSecService.update(booksec);
        return getAdminBookSecChangePage(model, 0);
    }


    @GetMapping("/booksec_delete")
    public String getAdminBookSecDeletePage(@RequestParam(required = true) int id) {
        BookSec booksec = bookSecService.getBookSecById(id);
        bookSecService.delete(booksec);
        return "redirect:/admin/booksec_change";
    }

    @GetMapping("/book_add")
    public String getAdminBookPage(Model model) {
        List<BookSize> booksizes = bookSizeService.getAllBookSizes();
        List<BookCover> bookcovers = bookCoverService.getAllBookCovers();
        List<Publisher> publishers = publisherService.getAllPublishers();
        List<BookSec> booksecs = bookSecService.getAllBookSec();
        List<BookType> bookTypes = bookTypeService.getAllBookType();
        model.addAttribute("bookcovers", bookcovers);
        model.addAttribute("booksizes", booksizes);
        model.addAttribute("publishers", publishers);
        model.addAttribute("booksecs", booksecs);
        model.addAttribute("bookTypes", bookTypes);

        if (!model.containsAttribute("books")) {
            model.addAttribute("books", new Books());
        }

        model.addAttribute("action", "book_add");
        return "admin_book";
    }

    @PostMapping("/book_add")
    public String getAdminBookAddPage(@Valid Books book, BindingResult bindingResult, Model model, @RequestParam("file1") MultipartFile file1, @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/books/files/" + file.getOriginalFilename();
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(name));
                stream.write(bytes);
                stream.close();
                System.out.println("Файл загружен");
                book.setBookFile(file.getOriginalFilename());
            } catch (Exception e) {
                System.out.println("Не удалось загрузить " + name + " => " + e.getMessage());
                bindingResult.rejectValue("bookFile", "", "Не удалось загрузить файл: " + e.getMessage());
            }

        }

        if (!file1.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/books/" + file1.getOriginalFilename();
            if (file1.getContentType().equals("image/png") || file1.getContentType().equals("image/jpeg")) {
                try {
                    byte[] bytes = file1.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(name));
                    stream.write(bytes);
                    stream.close();
                    System.out.println("Файл загружен");
                    book.setCoverImg(file1.getOriginalFilename());
                } catch (Exception e) {
                    bindingResult.rejectValue("coverImg", "", "Не удалось загрузить файл: " + e.getMessage());
                }
            } else {
                bindingResult.rejectValue("coverImg", "", "Файл должен быть в формате png или jpg");
            }
        }

        if (book.getSection() < 1) {
            bindingResult.rejectValue("section", "", "Раздел не выбран");
        }
        if (book.getBookcover() < 1) {
            bindingResult.rejectValue("bookcover", "", "Тип обложки не выбран");
        }
        if (book.getBooksize() < 1) {
            bindingResult.rejectValue("booksize", "", "Размер обложки не выбран");
        }
        if (book.getPublisher() < 1) {
            bindingResult.rejectValue("publisher", "", "Издательство не выбрано");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("books", book);
            return getAdminBookPage(model);
        }
        int sort = bookService.getBookWithMaxId();

        book.setSort(sort + 1);
        book.setSortNew(sort + 1);
        bookService.create(book);
        return "redirect:/admin/";
    }

    @GetMapping("/book_sort")
    public String getAdminBookSortPage(Model model, @RequestParam(defaultValue = "0") int id) {

        if (id == 0) {
            TreeSet<Books> newBooks = new TreeSet<>(new Comparator<Books>() {
                @Override
                public int compare(Books o1, Books o2) {
                    return o2.getSortNew() - o1.getSortNew();
                }
            });
            newBooks.addAll(bookService.getNewBooks());
            model.addAttribute("books", newBooks);
            model.addAttribute("action", "book_sort_new");
        } else {
            TreeSet<Books> books = new TreeSet<>(new Comparator<Books>() {
                @Override
                public int compare(Books o1, Books o2) {
                    return o1.getSort() - o2.getSort();
                }
            });
            BookSec booksec = bookSecService.getBookSecById(id);
            books.addAll(booksec.getBooks());
            model.addAttribute("books", books);
            model.addAttribute("booksec", booksec);
            model.addAttribute("action", "book_sort");
        }

        return "admin_book_list";
    }

    @GetMapping("/book_change")
    public String getAdminBookChangePage(Model model, @RequestParam(defaultValue = "-1") int id) {

        if (id > 0 || model.containsAttribute("books")) {
            if (!model.containsAttribute("books")) {
                Books book = bookService.getBookById(id);
                model.addAttribute("books", book);
            }
            List<BookSize> booksizes = bookSizeService.getAllBookSizes();
            List<BookCover> bookcovers = bookCoverService.getAllBookCovers();
            List<Publisher> publishers = publisherService.getAllPublishers();
            List<BookType> bookTypes = bookTypeService.getAllBookType();
            model.addAttribute("bookcovers", bookcovers);
            model.addAttribute("booksizes", booksizes);
            model.addAttribute("publishers", publishers);
            model.addAttribute("bookTypes", bookTypes);
            model.addAttribute("action", "book_change");
        } else {
            List<BookSec> booksecs = bookSecService.getAllBookSec();
            model.addAttribute("booksecs", booksecs);
            model.addAttribute("action", "book_list");
        }

        return "admin_book";
    }

    @PostMapping("/book_change")
    public String getAdminBookChangePage(@Valid Books book, BindingResult bindingResult, Model model, @RequestParam("file1") MultipartFile file1, @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/books/files/" + file.getOriginalFilename();
            System.out.println(file.getContentType());
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(name));
                stream.write(bytes);
                stream.close();
                System.out.println("Файл загружен");
                book.setBookFile(file.getOriginalFilename());
            } catch (Exception e) {
                System.out.println("Не удалось загрузить " + name + " => " + e.getMessage());

                bindingResult.rejectValue("bookFile", "", "Не удалось загрузить файл: " + e.getMessage());
            }

        }

        if (!file1.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/books/" + file1.getOriginalFilename();
            System.out.println(file1.getContentType());
            if (file1.getContentType().equals("image/png") || file1.getContentType().equals("image/jpeg")) {
                try {
                    byte[] bytes = file1.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(name));
                    stream.write(bytes);
                    stream.close();
                    System.out.println("Файл загружен");
                    book.setCoverImg(file1.getOriginalFilename());
                } catch (Exception e) {
                    System.out.println("Не удалось загрузить " + name + " => " + e.getMessage());

                    bindingResult.rejectValue("coverImg", "", "Не удалось загрузить файл: " + e.getMessage());
                }
            } else {
                bindingResult.rejectValue("coverImg", "", "Файл должен быть в формате png или jpg");
            }
        }
        if (book.getSection() < 1) {
            bindingResult.rejectValue("section", "", "Раздел не выбран");
        }
        if (book.getBookcover() < 1) {
            bindingResult.rejectValue("bookcover", "", "Тип обложки не выбран");
        }
        if (book.getBooksize() < 1) {
            bindingResult.rejectValue("booksize", "", "Размер обложки не выбран");
        }
        if (book.getPublisher() < 1) {
            bindingResult.rejectValue("publish", "", "Издательство не выбрано");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("books", book);
            return getAdminBookChangePage(model, 0);
        }
        bookService.update(book);
        return getAdminBookChangePage(model, 0);
    }

    @GetMapping("/book_delete")
    public String getAdminBookDeletePage(@RequestParam(required = true) int id) {
        Books book = bookService.getBookById(id);
        bookService.delete(book);
        return "redirect:/admin/book_change";
    }

    @GetMapping("/news_add")
    public String getAdminNewsPage(Model model, @RequestParam(defaultValue = "0") int type) {
        if (!model.containsAttribute("news")) {
            News news = new News();
            news.setType(type);
            model.addAttribute("news", news);

        }
        model.addAttribute("type", type);
        model.addAttribute("action", "news_add");
        return "admin_news";
    }

    @PostMapping("/news_add")
    public String getAdminNewsAddPage(@Valid News news, BindingResult bindingResult, Model model) {
        if (news.getType() == 0) {
            if (news.getName().equals("")) {
                bindingResult.rejectValue("name", "", "Название не должно быть пустым");
            }
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("news", news);
            return getAdminNewsPage(model, news.getType());
        }
        newsService.create(news);
        return "redirect:/admin/news_change?type=" + news.getType();
    }

    @GetMapping("/news_delete")
    public String getAdminNewsDeletePage(@RequestParam(required = true) int id) {
        News news = newsService.getNewsById(id);
        newsService.delete(news);
        return "redirect:/admin/news_change?type=" + news.getType();
    }

    @GetMapping("/news_change")
    public String getAdminNewsChangePage(Model model, @RequestParam(defaultValue = "-1") int id, @RequestParam(defaultValue = "0") int type) {

        if (id > 0 || model.containsAttribute("news")) {
            if (!model.containsAttribute("news")) {
                News news = newsService.getNewsById(id);
                news.setType(type);
                model.addAttribute("news", news);
            }
            model.addAttribute("action", "news_change");
        } else {
            List<News> news_list = newsService.getNewsByType(type);
            model.addAttribute("news_list", news_list);
            model.addAttribute("action", "news_list");
        }
        model.addAttribute("type", type);

        return "admin_news";
    }

    @PostMapping("/news_change")
    public String getAdminNewsChangePage(@Valid News news, BindingResult bindingResult, Model model) {
        if (news.getType() == 0) {
            if (news.getName().equals("")) {
                bindingResult.rejectValue("name", "", "Название не должно быть пустым");
            }
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("news", news);
            return getAdminNewsChangePage(model, 0, news.getType());
        }
        newsService.update(news);
        return "redirect:/admin/news_change?type=" + news.getType();
    }

    @GetMapping("/menu_change")
    public String getMenuChangePage(Model model, @RequestParam(required = false, defaultValue = "0") int id, @RequestParam(required = false, defaultValue = "") String type) {

        if (id > 0) {
            Page page = pageService.getPageById(id);
            model.addAttribute("page", page);
        }
        model.addAttribute("type", type);
        return "admin_menu";
    }

    @PostMapping("/menu_change")
    public String getMenuChangePostPage(Model model, Page page) {
        pageService.update(page);
        return "redirect:/admin/menu_change?id=" + page.getId();
    }

    @PostMapping("/change_price_list")
    public String changePriceList(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String name = "/home/tomcat/webapps/uploads/price.xls";
            if (file.getOriginalFilename().indexOf(".xls") > 0) {
                try {
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(name));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "redirect:/admin/menu_change?type=main";
    }


    @GetMapping("/field_change")
    public String getFieldChangePage(Model model, @RequestParam(required = true) int id) {
        switch (id) {
            case 1: {
                List<BookSize> items = bookSizeService.getAllBookSizes();
                model.addAttribute("items", items);
                model.addAttribute("type", "size");
                model.addAttribute("object", new BookSize());
                break;
            }
            case 2: {
                List<BookCover> items = bookCoverService.getAllBookCovers();
                model.addAttribute("items", items);
                model.addAttribute("type", "cover");
                model.addAttribute("object", new BookCover());
                break;
            }
            case 3: {
                List<BookType> items = bookTypeService.getAllBookType();
                model.addAttribute("items", items);
                model.addAttribute("type", "book_type");
                model.addAttribute("object", new BookType());
                break;
            }
            case 4: {
                List<Publisher> items = publisherService.getAllPublishers();
                model.addAttribute("items", items);
                model.addAttribute("type", "publisher");
                model.addAttribute("object", new Publisher());
                break;
            }
            case 5: {
                List<ArticleType> items = articleTypeService.getAllArticleType();
                model.addAttribute("items", items);
                model.addAttribute("type", "article_type");
                model.addAttribute("object", new ArticleType());
                break;
            }
        }
        model.addAttribute("action", "field_change");
        return "admin_field";
    }

    @PostMapping("/size_change")
    public String getSizeChangePostPage(Model model, BookSize bookSize) {
        bookSizeService.update(bookSize);
        return "redirect:/admin/field_change?id=1";
    }

    @GetMapping("/size_delete")
    public String getAdminSizeDeletePage(@RequestParam(required = true) int id) {
        BookSize bookSize = bookSizeService.getBookSizeById(id);
//        List<Books> books = bookService.getBookBySize(id);
//        for (Books book : books) {
//            book.setBooksize(0);
//            bookService.update(book);
//        }
        bookSizeService.delete(bookSize);
        return "redirect:/admin/field_change?id=1";
    }

    @PostMapping("/cover_change")
    public String getSizeChangePostPage(Model model, BookCover bookCover) {
        bookCoverService.update(bookCover);
        return "redirect:/admin/field_change?id=2";
    }

    @GetMapping("/cover_delete")
    public String getAdminCoverDeletePage(@RequestParam(required = true) int id) {
        BookCover bookCover = bookCoverService.getBookCoverById(id);
        bookCoverService.delete(bookCover);
        return "redirect:/admin/field_change?id=2";
    }

    @PostMapping("/book_type_change")
    public String getBookTypeChangePostPage(Model model, BookType bookType) {
        bookTypeService.update(bookType);
        return "redirect:/admin/field_change?id=3";
    }

    @GetMapping("/book_type_delete")
    public String getAdminBookTypeDeletePage(@RequestParam(required = true) int id) {
        BookType bookType = bookTypeService.getBookTypeById(id);
        bookTypeService.delete(bookType);
        return "redirect:/admin/field_change?id=3";
    }

    @PostMapping("/article_type_change")
    public String getArticleTypeChangePostPage(Model model, ArticleType articleType) {
        articleTypeService.update(articleType);
        return "redirect:/admin/field_change?id=5";
    }

    @GetMapping("/article_type_delete")
    public String getAdminArticleTypeDeletePage(@RequestParam(required = true) int id) {
        ArticleType articleType = articleTypeService.getArticleTypeById(id);
        List<Article> articles = articleService.getArticleByType(id);
        for (Article article : articles) {
            article.setType(0);
            articleService.update(article);
        }
        articleTypeService.delete(articleType);
        return "redirect:/admin/field_change?id=5";
    }


    @PostMapping("/publisher_change")
    public String getSizeChangePostPage(Model model, Publisher publisher) {
        publisherService.update(publisher);
        return "redirect:/admin/field_change?id=4";
    }

    @GetMapping("/publisher_delete")
    public String getAdminPublisherDeletePage(@RequestParam(required = true) int id) {
        Publisher publisher = publisherService.getPublisherById(id);
        publisherService.delete(publisher);
        return "redirect:/admin/field_change?id=4";
    }


    @PostMapping("/sort/")
    @ResponseBody
    public String sortEntity(@RequestParam(required = true) String type, @RequestParam(required = true) int id, @RequestParam(required = true) int num) {
        if (type.equals("section")) {
            Section section = sectionService.getSectionById(id);
            section.setSort(num);
            sectionService.update(section);
        } else if (type.equals("book_sort_new")) {
            Books book = bookService.getBookById(id);
            book.setSortNew(num);
            bookService.update(book);
        } else if (type.equals("book_sort")) {
            Books book = bookService.getBookById(id);
            book.setSort(num);
            bookService.update(book);
        }
        return "Ok";
    }

    @GetMapping("/other")
    public String getOtherPage(Model model, @RequestParam(required = true) String type) {
        model.addAttribute("type", type);
        return "admin_other";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, @RequestParam(required = false, defaultValue = "") String status) {
        List<Order> orders;
        if (status.equals("")) {
            orders = orderService.getOrdersByStatus("Оплачено");
        } else {
            orders = orderService.getOrdersByStatus(status);
        }
        Collections.sort(orders, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getId() - o1.getId();
            }
        });
        model.addAttribute("orders", orders);
        model.addAttribute("type", "all");
        Integer editorialFeePrice = Integer.parseInt(pageService.getPageById(12).getContent());
        model.addAttribute("editorialFeePrice", editorialFeePrice);

        return "admin_cart";
    }

    @GetMapping("/order_delete")
    public String deleteOrder(@RequestParam(required = true) int id) {
        Order order = orderService.getOrderById(id);
        orderService.delete(order);
        return "redirect:/admin/cart";
    }

    @ResponseBody
    @PostMapping("/order_status")
    public String changeOrderStatus(@RequestParam(required = true) int order, @RequestParam(required = true) String status) {
        Order item = orderService.getOrderById(order);
        item.setStatus(status);
        orderService.update(item);
        return "Ok";
    }

    @GetMapping("/subscribes")
    public String getAdminSubscribePage(Model model, @RequestParam(required = true) String type, @RequestParam(required = false, defaultValue = "") String status, @RequestParam(required = false, defaultValue = "0") int item) {
        List<Order> orders;
        if (status.equals("")) {
            orders = orderService.getOrdersByStatus("Оплачено");
        } else {
            orders = orderService.getOrdersByStatus(status);
        }
        Collections.sort(orders, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getId() - o1.getId();
            }
        });
        orders.removeIf((order) -> {
            for (Cart cart : order.getCarts()) {
                if(cart.getType().equals(type)){
                    return false;
                }
            }
            return true;
        });
        if(item > 0){
            if(type.equals("order_book")){
                orders.removeIf((order) -> {
                    for (Cart cart : order.getCarts()) {
                        if(cart.getType().equals("order_book")) {
                            Books book = bookService.getBookById(cart.getProduct());
                            if (book.getSection() == item) {
                                return false;
                            }
                        }
                    }
                    return true;
                });
            } else if(type.equals("journal_subscribe")) {
                orders.removeIf((order) -> {
                    for (Cart cart : order.getCarts()) {
                        if (cart.getProduct() == item) {
                            return false;
                        }
                    }
                    return true;
                });
            }
        }

        model.addAttribute("orders", orders);
        model.addAttribute("type", type);
        Integer editorialFeePrice = Integer.parseInt(pageService.getPageById(12).getContent());
        model.addAttribute("editorialFeePrice", editorialFeePrice);
        model.addAttribute("item", item);
        return "admin_cart";
    }


    @PostMapping("/handler")
    public String getAdminHandlerPage(Model model, @RequestParam(defaultValue = "-1") int journal_id,
                                      @RequestParam(defaultValue = "") String number_year, @RequestParam(defaultValue = "-1") int number_id,
                                      @RequestParam(defaultValue = "-1") int section_id, @RequestParam(defaultValue = "") String action,
                                      @RequestParam(defaultValue = "-1") int art_id, @RequestParam(defaultValue = "-1") int booksec_id,
                                      @RequestParam(defaultValue = "-1") int book_id) {
        if (journal_id > 0) {
            Journals journal = journalsService.getJournalById(journal_id);
            if (number_year.equals("")) {
                TreeSet<String> years = new TreeSet<>();
                journal.getNumbers().forEach((number) -> {
                    years.add(number.getYear());
                });
                model.addAttribute("years", years);
            } else {
                List<Number> numbersList = journalsService.getNumbersByYear(journal, number_year);
                TreeSet<Number> numbers = new TreeSet<>(numbersList);
                System.out.println(numbers);
                System.out.println("numbers");
                model.addAttribute("numbers", numbers);
            }
        } else if (number_id > 0) {
            Number selectedNumber = numberService.getNumberById(number_id);
            model.addAttribute("selectedNumber", selectedNumber);
        } else if (section_id > 0) {
            if (action.equals("section_change")) {
                Section section = sectionService.getSectionById(section_id);
                model.addAttribute("section", section);
                model.addAttribute("action", action);
                return "admin_section";
            } else if (action.equals("article_change")) {
                Section section = sectionService.getSectionById(section_id);
                model.addAttribute("selectedSection", section);
            }
        } else if (art_id > 0) {
            Article article = articleService.getArticleById(art_id);
            model.addAttribute("article", article);
            model.addAttribute("action", "article_change");
            return "admin_article";
        } else if (booksec_id > 0) {
            if (action.equals("booksec_change")) {
                BookSec bookSec = bookSecService.getBookSecById(booksec_id);
                model.addAttribute("bookSec", bookSec);
                model.addAttribute("action", "booksec_change");
                return "admin_booksec";
            } else if (action.equals("book_change")) {
                BookSec booksec = bookSecService.getBookSecById(booksec_id);
                model.addAttribute("booksec", booksec);
                model.addAttribute("action", action);
            }
        } else if (book_id > 0) {
            Books book = bookService.getBookById(book_id);
            List<BookSize> booksizes = bookSizeService.getAllBookSizes();
            List<BookCover> bookcovers = bookCoverService.getAllBookCovers();
            List<Publisher> publishers = publisherService.getAllPublishers();
            List<BookType> bookTypes = bookTypeService.getAllBookType();
            model.addAttribute("bookTypes", bookTypes);
            model.addAttribute("bookcovers", bookcovers);
            model.addAttribute("booksizes", booksizes);
            model.addAttribute("publishers", publishers);
            model.addAttribute("books", book);
            model.addAttribute("action", "book_change");
            return "admin_book";
        }
        return "admin_handler";
    }

}
