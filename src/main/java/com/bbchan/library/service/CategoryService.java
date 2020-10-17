package com.bbchan.library.service;

import com.bbchan.library.entity.Book_category;
import com.bbchan.library.entity.Book_index;
import com.bbchan.library.repository.Book_categoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private Book_categoryRepository bookCategoryRepository;
    @Autowired
    private BookService bookService;

    public boolean addcategory(String category, String ISBN) {
        Book_category book_category = new Book_category();
        book_category.setCategory(category);
        book_category.setBookid(ISBN);
        return bookCategoryRepository.save(book_category) != null;
    }

    public String showcategory(String ISBN) {
        Book_category book_category = bookCategoryRepository.findByBookid(ISBN);
        if (book_category == null) return null;
        else return book_category.getCategory();
    }

    public boolean updatecategory(String category, String ISBN) {
        Book_category book_category = bookCategoryRepository.findByBookid(ISBN);
        if (book_category == null) {
            return addcategory(category, ISBN);
        } else {
            book_category.setCategory(category);
            return bookCategoryRepository.save(book_category) != null;
        }
    }

    public List<Book_category> searchBook(String key) {
        return bookCategoryRepository.findAllByCategoryLike("%" + key + "%");
    }

    public List<Book_index> searchcategory(String category) {
        List<Book_category> categoryList = searchBook(category);
        List<Book_index> book_indexList = new ArrayList<>();
        Book_index book_index = null;
        for (Book_category book_category : categoryList) {
//            book_category.getBookid();
            book_index = bookService.onlyfindbook_index(book_category.getBookid());
            if (book_index != null) {
                book_indexList.add(book_index);
            }
        }
        return book_indexList;
    }


}
