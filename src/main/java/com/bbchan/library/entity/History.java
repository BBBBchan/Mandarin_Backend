package com.bbchan.library.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "reader_username")
    private String readerusername;
    @Column(name = "book_detail_id")
    private String bookdetailid;
    private Date lend_time;
    private Date return_time;
    @Column(name = "is_return")
    private Boolean isreturn;
    @Column(name = "is_overtime")
    private Boolean isovertime;

    public String getReaderusername() {
        return readerusername;
    }

    public void setReaderusername(String readerusername) {
        this.readerusername = readerusername;
    }

    public String getBookdetailid() {
        return bookdetailid;
    }

    public void setBookdetailid(String bookdetailid) {
        this.bookdetailid = bookdetailid;
    }

    public Boolean getIsreturn() {
        return isreturn;
    }

    public void setIsreturn(Boolean isreturn) {
        this.isreturn = isreturn;
    }

    public Boolean getIsovertime() {
        return isovertime;
    }

    public void setIsovertime(Boolean isovertime) {
        this.isovertime = isovertime;
    }

    public String getLibrarianusername() {
        return librarianusername;
    }

    public void setLibrarianusername(String librarianusername) {
        this.librarianusername = librarianusername;
    }

    private Double fine;
    @Column(name = "librarian_username")
    private String librarianusername;

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", readerusername='" + readerusername + '\'' +
                ", bookdetailid='" + bookdetailid + '\'' +
                ", lend_time=" + lend_time +
                ", return_time=" + return_time +
                ", isreturn=" + isreturn +
                ", isovertime=" + isovertime +
                ", fine=" + fine +
                ", librarianusername='" + librarianusername + '\'' +
                '}';
    }

    public String getLibrarian_username() {
        return librarianusername;
    }

    public void setLibrarian_username(String librarian_username) {
        this.librarianusername = librarian_username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReader_username() {
        return readerusername;
    }

    public void setReader_username(String reader_username) {
        this.readerusername = reader_username;
    }

    public String getBook_detail_id() {
        return bookdetailid;
    }

    public void setBook_detail_id(String book_detail_id) {
        this.bookdetailid = book_detail_id;
    }

    public Date getLend_time() {
        return lend_time;
    }

    public void setLend_time(Date lend_time) {
        this.lend_time = lend_time;
    }

    public Date getReturn_time() {
        return return_time;
    }

    public void setReturn_time(Date return_time) {
        this.return_time = return_time;
    }

    public Boolean getIs_return() {
        return isreturn;
    }

    public void setIs_return(Boolean is_return) {
        this.isreturn = is_return;
    }

    public Boolean getIs_overtime() {
        return isovertime;
    }

    public void setIs_overtime(Boolean is_overtime) {
        this.isovertime = is_overtime;
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }
}
