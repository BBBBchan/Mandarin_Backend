package com.bbchan.library.service;

import com.bbchan.library.entity.Book_category;
import com.bbchan.library.entity.Book_detail;
import com.bbchan.library.entity.Book_index;
import com.bbchan.library.entity.User;
import com.bbchan.library.repository.Book_categoryRepository;
import com.bbchan.library.repository.Book_detailRepository;
import com.bbchan.library.repository.Book_indexRepository;
import com.bbchan.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private Book_categoryRepository bookCategoryRepository;
    @Autowired
    private Book_detailRepository bookDetailRepository;
    @Autowired
    private Book_indexRepository bookIndexRepository;
    @Autowired
    private UserRepository userRepository;

    //查找book_index，如果没找到，就加入一个
    public Book_index find(Book_detail book_detail, String url, String introduction) {
        Optional<Book_index> bookList = bookIndexRepository.findById(book_detail.getBookid());
        if (bookList.isEmpty()) {
            Book_index book;
            book = new Book_index();
            book.setAuthor(book_detail.getAuthor());
            book.setBookid(book_detail.getBookid());
            book.setPrice(book_detail.getPrice());
            book.setBorrow_num(0);
            book.setTotal_num(0);
            book.setIamge(url);//注意
            book.setReserve_num(0);
            book.setCount(0);
            book.setBookname(book_detail.getBookname());
            book.setIntroduction(introduction);
            //book_detail.setBook_detail_id(book.getBookid()+book.getTotal_num());
            String barcode = BarcodeService.BookBarcode(book_detail.getBookid());
            book.setBarcode(barcode);
            return bookIndexRepository.save(book);
        } else {
            return bookList.get();
        }
    }


    public boolean addbook_detail(Book_detail book_detail, Book_index book) {
        book_detail.setStatus(0);
        book.setTotal_num(book.getTotal_num() + 1);
        book.setCount(book.getCount() + 1);
        book_detail.setBook_detail_id(book.getBookid() + "-" + book.getCount());
        bookIndexRepository.save(book);
        Book_detail book_detail1 = bookDetailRepository.save(book_detail);
        System.out.println(book_detail.toString());
        return book_detail1 != null;
    }

    public List<Book_index> searchBook(String key) {
        return bookIndexRepository.findAllByBooknameLike("%" + key + "%");
    }

    public boolean lendbook_index(String ISBN, String book_detail_id) {
        Book_index bookindex = bookIndexRepository.findByBookid(ISBN);
        if (bookindex != null) {
            bookindex.setBorrow_num(bookindex.getBorrow_num() + 1);
            bookindex.setReserve_num(bookindex.getReserve_num() - 1);
            if (lendbook_detail(book_detail_id)) {
                bookIndexRepository.save(bookindex);
                return true;
            } else return false;

        } else {
            System.out.println("这类本书不存在");
            return false;
        }
    }

    public String checkReserve_username(String bookid) {
        Optional<Book_detail> bookList = bookDetailRepository.findById(bookid);
        if (bookList.isPresent()) {
            Book_detail book_detail = bookList.get();
            return book_detail.getReserveusername();

        } else {
            System.out.println("这本书不存在");
            return null;
        }
    }

    public Date checkReserve_time(String bookid) {
        Optional<Book_detail> bookList = bookDetailRepository.findById(bookid);
        if (bookList.isPresent()) {
            Book_detail book_detail = bookList.get();
            return book_detail.getReserve_time();

        } else {
            System.out.println("这本书不存在");
            return null;
        }
    }


    public boolean lendbook_detail(String bookid) {
        Book_detail book_detail = bookDetailRepository.findByBookdetailid(bookid);
        if (book_detail != null) {
            if (book_detail.getStatus() != -1) return false;
            book_detail.setStatus(1);
            book_detail.setReserve_time(null);
            book_detail.setReserveusername(null);
            return bookDetailRepository.save(book_detail) != null;

        } else {
            System.out.println("这本书不存在");
            return false;
        }

    }

    public Book_index onlyfindbook_index(String ISBN) {
        return bookIndexRepository.findByBookid(ISBN);
    }

    public Book_detail findBook_detail(String book_detail_id) {
        return bookDetailRepository.findByBookdetailid(book_detail_id);
    }

    public boolean returnbook(Book_index book_index, Book_detail book_detail) {
        if (book_index.getBorrow_num() > 0) {
            book_index.setBorrow_num(book_index.getBorrow_num() - 1);
            if (bookIndexRepository.save(book_index) != null) {
                return returnbook_detail(book_detail);
            }
        }
        return false;

    }


    public boolean returnbook_detail(Book_detail book_detail) {
        if (book_detail.getStatus() == 1) {
            book_detail.setStatus(0);
            book_detail.setReserveusername(null);
            book_detail.setReserve_time(null);
            return bookDetailRepository.save(book_detail) != null;
        } else
            return false;
    }

    public boolean book_indexAvailable(Book_index book_index) {
        return book_index.getReserve_num() + book_index.getBorrow_num() < book_index.getTotal_num();
    }

    public boolean book_detailAvailable(Book_detail book_detail) {
        if (book_detail.getStatus() == 0) {
            System.out.println("书未被预约");

            return true;
        } else {
            System.out.println("书已被预约");
            return false;
        }
    }

    public boolean Reservebook(Book_index book_index, Book_detail book_detail, String username) {
        if (Reservebook_detail(book_detail, username)) {
            book_index.setReserve_num(book_index.getReserve_num() + 1);
            return bookIndexRepository.save(book_index) != null;
        }

        return false;
    }

    public boolean Reservebook_detail(Book_detail book_detail, String usrname) {
        Date date = new Date();
        date.setTime(date.getTime());
        book_detail.setReserve_time(date);
        book_detail.setReserveusername(usrname);
        book_detail.setStatus(-1);
        return bookDetailRepository.save(book_detail) != null;
    }

    public void flushAllBook() {
        List<Book_detail> book_detailList = bookDetailRepository.findAll();
        for (Book_detail book_detail : book_detailList) {
            if (book_detail.getStatus() == -1) {
                Date date = new Date();
                date.setTime(date.getTime());
                double time = ((double) (-book_detail.getReserve_time().getTime() + date.getTime()) / (1000 * 60 * 60));
                if (time > 2) {
                    User user = userRepository.findByUsername(book_detail.getReserveusername());
                    user.setReserve_num(user.getReserve_num() - 1);
                    Book_index book_index = bookIndexRepository.findByBookid(book_detail.getBookid());
                    book_index.setReserve_num(book_index.getReserve_num() - 1);
                    book_detail.setReserve_time(null);
                    book_detail.setReserveusername(null);
                    book_detail.setStatus(0);
                    bookDetailRepository.save(book_detail);
                    bookIndexRepository.save(book_index);
                    userRepository.save(user);
                }
            }
        }
    }

    public List<Book_detail> findAllDetailByBookId(String BookId) {
        return bookDetailRepository.findAllByBookid(BookId);
    }

    public Book_category findCategoryByBookId(String BookId) {
        return bookCategoryRepository.findByBookid(BookId);
    }

    public boolean modifybookindex(Book_index book_index) {
        if (book_index != null) {
            bookIndexRepository.save(book_index);
        }
        return book_index != null;
    }

    public boolean modifybookdetail(Book_detail book_detail) {
        if (book_detail != null) {
            bookDetailRepository.save(book_detail);

        }
        return book_detail != null;
    }

    public void saveBookDetail(Book_detail book_detail) {
        bookDetailRepository.save(book_detail);
    }

    public Book_category addCategoryByBookid(String Bookid, String category) {
        Book_category book_category = bookCategoryRepository.findByBookid(Bookid);
        Book_category book_category1;
        book_category1 = Objects.requireNonNullElseGet(book_category, Book_category::new);
        book_category1.setCategory(category);
        book_category1.setBookid(Bookid);
        return bookCategoryRepository.save(book_category1);
    }
}
