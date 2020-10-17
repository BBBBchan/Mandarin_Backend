package com.bbchan.library.service;

import com.bbchan.library.entity.User;
import com.bbchan.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderService {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserRepository userRepository;

    //    public User GetUser(String name)
//    {
//        Optional<User> userList = userRepository.findById(name);
//        return userList.orElse(null);
//    }
    public boolean lendbook(User user, String ISBN, String book_detail_id) {

        if (bookService.lendbook_index(ISBN, book_detail_id)) {
            if (user.getBorrow_num() == null) user.setBorrow_num(1);
            else user.setBorrow_num(user.getBorrow_num() + 1);
            if (user.getReserve_num() == null || user.getReserve_num() == 0) return false;
            else user.setReserve_num(user.getReserve_num() - 1);
            System.out.println(user.toString());
            userRepository.save(user);
            return true;
        } else {
            return false;
        }

    }

    public boolean returnbook(User user) {
        if (user.getBorrow_num() == null || user.getBorrow_num() <= 0) return false;
        else user.setBorrow_num(user.getBorrow_num() - 1);
        return userRepository.save(user) != null;
    }

    public boolean reservebook(User user) {
        if (user.getReserve_num() == null) user.setReserve_num(0);
        if (user.getReserve_num() >= 3)
            return false;
        user.setReserve_num(user.getReserve_num() + 1);
        return userRepository.save(user) != null;

    }
}
