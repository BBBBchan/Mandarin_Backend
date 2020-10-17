package com.bbchan.library.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class General_info {
    @Id
    private Integer id;
    private Double fine_value;
    private Integer return_period;
    private Double security_deposit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getFine_value() {
        return fine_value;
    }

    public void setFine_value(Double fine_value) {
        this.fine_value = fine_value;
    }

    public Integer getReturn_period() {
        return return_period;
    }

    public void setReturn_period(Integer return_period) {
        this.return_period = return_period;
    }

    public Double getSecurity_deposit() {
        return security_deposit;
    }

    public void setSecurity_deposit(Double security_deposit) {
        this.security_deposit = security_deposit;
    }
}
