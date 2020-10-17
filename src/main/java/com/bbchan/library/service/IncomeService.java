package com.bbchan.library.service;

import com.bbchan.library.entity.General_info;
import com.bbchan.library.entity.History;
import com.bbchan.library.entity.Library_income;
import com.bbchan.library.entity.User;
import com.bbchan.library.repository.Library_incomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private Library_incomeRepository libraryIncomeRepository;
    @Autowired
    private General_infoService generalInfoService;
    @Autowired
    private HistoryService historyService;

    public boolean addIncomeDeposit(User user) {
        Library_income library_income = new Library_income();
        library_income.setSource(0);
        library_income.setUsername(user.getUsername());
        General_info generalInfo = generalInfoService.getGeneralInfo();
        library_income.setIncome(generalInfo.getSecurity_deposit());
        Date date = new Date();
        date.setTime(date.getTime());
        library_income.setIncome_time(date);
        return libraryIncomeRepository.save(library_income) != null;
    }

    public boolean addincomeBook(History history) {
        General_info generalInfo = generalInfoService.getGeneralInfo();
        if (generalInfo == null)
            return false;
        if (history == null)
            return false;
        if (!historyService.historyavail(history))
            return false;
        Library_income library_income = new Library_income();
        library_income.setBook_detail_id(history.getBookdetailid());
        library_income.setIncome(history.getFine());
        library_income.setIncome_time(history.getReturn_time());
        library_income.setUsername(history.getReaderusername());
        library_income.setSource(1);
//        library_income.setOrder_id("2");//记得删除
        return libraryIncomeRepository.save(library_income) != null;
    }

    public List<Library_income> checkincome(Date start_time, Date end_time) {
        if (end_time.getTime() - start_time.getTime() < 0) {
            return null;
        }
        List<Library_income> library_incomes = libraryIncomeRepository.findAll();
        ArrayList<Library_income> list = new ArrayList<>();
        for (Library_income library_income : library_incomes) {
            if (library_income.getIncome() <= 0)
                continue;
            Date realdate = library_income.getIncome_time();
            double time1 = (double) (realdate.getTime() - start_time.getTime());
            double time2 = (double) (end_time.getTime() - realdate.getTime());
            System.out.println(time1 + "here" + time2);
            if (time1 >= 0 && time2 >= 0) {
                list.add(library_income);
            }
        }
        return list;
    }

    public double getAllIncome(List<Library_income> incomeList) {
        double all_income = 0;
        for (Library_income library_income : incomeList) {
            all_income += library_income.getIncome();
        }
        return all_income;
    }
}

