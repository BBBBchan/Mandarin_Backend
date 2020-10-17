package com.bbchan.library.service;

import com.bbchan.library.entity.General_info;
import com.bbchan.library.repository.General_infoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class General_infoService {
    @Autowired
    General_infoRepository general_infoRepository;

    public General_info getGeneralInfo() {
        List<General_info> general_infoList = general_infoRepository.findAll();
        return general_infoList.get(0);
    }

    public boolean setGeneralInfo(Double fine_value, Integer return_period, Double security_deposit) {
        General_info old = getGeneralInfo();
        if (fine_value != null)
            old.setFine_value(fine_value);
        if (return_period != null)
            old.setReturn_period(return_period);
        if (security_deposit != null)
            old.setSecurity_deposit(security_deposit);
        return general_infoRepository.save(old) != null;
    }
}
