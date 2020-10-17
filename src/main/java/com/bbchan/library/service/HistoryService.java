package com.bbchan.library.service;

import com.bbchan.library.entity.Delete_history;
import com.bbchan.library.entity.History;
import com.bbchan.library.repository.Delete_historyRepository;
import com.bbchan.library.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private General_infoService general_infoService;
    @Autowired
    private Delete_historyRepository delete_historyRepository;
    @Autowired
    private IncomeService incomeService;
    private Object RuntimeException;
    //还书前要根据username和book_detail_id找到之前的history记录，在更新他
    //借书时，要先新建一个history记录

    public History newHistory(String Usrname, String book_detail_id, String librarian_username) {
        History history = new History();
        history.setReader_username(Usrname);
        history.setBookdetailid(book_detail_id);
        history.setLibrarian_username(librarian_username);
        return history;
    }

    public History findByUsernameAndBookDetailIdAndIsreturn(String name, String book_detail_id) {
        return historyRepository.findByReaderusernameAndBookdetailidAndIsreturn(name, book_detail_id, false);
    }

    public List<History> findAllHistory() {
        return historyRepository.findAll();
    }

    public List<History> findByUsername(String username) {
        return historyRepository.findAllByReaderusername(username);
    }

    public List<History> findByBoookDetailId(String book_detail_id) {
        return historyRepository.findAllByBookdetailid(book_detail_id);
    }

    public List<History> findByUsernameAndBookDetailId(String name, String book_detail_id) {
        return historyRepository.findAllByReaderusernameAndBookdetailid(name, book_detail_id);
    }

    public boolean addlendHistory(History history) {
        if (history == null)
            return false;
        Date date = new Date();
        date.setTime(date.getTime());
        history.setLend_time(date);
        history.setIsreturn(false);
        history.setIs_overtime(false);
        return saveHistory(history);
    }

    //overtime
    public boolean flushHistory(History history, boolean is_return) {
        if (history == null) return false;
        Date date = new Date();
        date.setTime(date.getTime());
        Date lend_time = history.getLend_time();
        double time = (double) (date.getTime() - lend_time.getTime()) / (1000 * 60 * 60 * 24);
        Integer return_period = general_infoService.getGeneralInfo().getReturn_period();
        Double fine_value = general_infoService.getGeneralInfo().getFine_value();
        assert return_period != null;

        if (is_return)
            history.setReturn_time(date);
        history.setIsreturn(true);
        if (time <= return_period) history.setIs_overtime(false);
        else {
            history.setIs_overtime(true);
            assert fine_value != null;
            Double outtime = Math.ceil(time - return_period);
            Double money = outtime * fine_value;
            history.setFine(money);
            //incomeService.addincome(history);
        }
        return saveHistory(history);
    }

    public void flushAllHistory() {
        List<History> allHistory = findAllHistory();
        for (History history : allHistory) {
            flushHistory(history, false);
        }
    }

    public boolean saveHistory(History history) {
        if (history.getReader_username() == null || history.getBookdetailid() == null || history.getLend_time() == null) {
            return false;
        }
        if (history.getFine() == null) history.setFine(0.0);
        System.out.println(history.toString());
        return historyRepository.save(history) != null;
    }

    public History findHistory(String name, String book_detail_id) {
        return historyRepository.findByReaderusernameAndBookdetailidAndIsreturn(name, book_detail_id, false);
    }

    public boolean savedelhistory(Delete_history delete_history) {
        if (delete_history != null) {
            delete_historyRepository.save(delete_history);
        }
        return delete_history != null;
    }

    public boolean historyavail(History history) {
        if (history.getReader_username() == null || history.getBookdetailid() == null || history.getLend_time() == null
                || history.getFine() == null || history.getFine() < 0) {
            return false;
        } else return true;
    }

}
