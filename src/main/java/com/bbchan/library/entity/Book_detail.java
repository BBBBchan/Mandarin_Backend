package com.bbchan.library.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Book_detail {
    @Id
    @Column(name = "book_detail_id")
    private String bookdetailid;
    private String bookid;
    private String bookname;
    private String author;
    private Integer status;
    private String location;
    private Double price;
    @Column(name = "reserve_username")
    private String reserveusername;
    private Date reserve_time;


    public String getBook_detail_id() {
        return bookdetailid;
    }

    public void setBook_detail_id(String book_detail_id) {
        this.bookdetailid = book_detail_id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public String getReserveusername() {
        return reserveusername;
    }

    public void setReserveusername(String reserve_username) {
        this.reserveusername = reserve_username;
    }

    public Date getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(Date reserve_time) {
        this.reserve_time = reserve_time;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    @Override
    public String toString() {
        return "Book_detail{" +
                "bookdetailid='" + bookdetailid + '\'' +
                ", bookid='" + bookid + '\'' +
                ", bookname='" + bookname + '\'' +
                ", author='" + author + '\'' +
                ", status=" + status +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", reserve_username='" + reserveusername + '\'' +
                ", reserve_time=" + reserve_time +
                '}';
    }
}
