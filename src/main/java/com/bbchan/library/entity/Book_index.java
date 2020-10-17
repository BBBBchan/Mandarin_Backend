package com.bbchan.library.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Book_index {
    @Id
    private String bookid;
    private String bookname;
    private String author;
    private String iamge;
    private Integer borrow_num;
    private Integer reserve_num;
    private Integer total_num;
    private Double price;
    private Integer count;
    private String introduction;
    private String barcode;

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIamge() {
        return iamge;
    }

    public void setIamge(String iamge) {
        this.iamge = iamge;
    }

    public Integer getBorrow_num() {
        return borrow_num;
    }

    public void setBorrow_num(Integer borrow_num) {
        this.borrow_num = borrow_num;
    }

    public Integer getReserve_num() {
        return reserve_num;
    }

    public void setReserve_num(Integer reserve_num) {
        this.reserve_num = reserve_num;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
