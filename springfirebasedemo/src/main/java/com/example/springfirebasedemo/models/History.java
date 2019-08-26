package com.example.springfirebasedemo.models;

import lombok.Data;

@Data
public class History {
    private String bookCode;
    private String dateBorrow;
    private String dateReturn;
    private String customerBorrow;
    private String librarianAccept;
    private String librarianApprove;

    public History(){

    }

    public History(String bookCode, String dateBorrow, String dateReturn, String customerBorrow, String librarianAccept, String librarianApprove) {
        this.bookCode = bookCode;
        this.dateBorrow = dateBorrow;
        this.dateReturn = dateReturn;
        this.customerBorrow = customerBorrow;
        this.librarianAccept = librarianAccept;
        this.librarianApprove = librarianApprove;
    }

    //================================= Getter =================================//
    public String getBookCode() {
        return bookCode;
    }

    public String getDateBorrow() {
        return dateBorrow;
    }

    public String getDateReturn() {
        return dateReturn;
    }

    public String getCustomerBorrow() {
        return customerBorrow;
    }

    public String getLibrarianAccept() {
        return librarianAccept;
    }

    public String getLibrarianApprove() {
        return librarianApprove;
    }

    //================================= Setter =================================//

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public void setDateBorrow(String dateBorrow) {
        this.dateBorrow = dateBorrow;
    }

    public void setDateReturn(String dateReturn) {
        this.dateReturn = dateReturn;
    }

    public void setCustomerBorrow(String customerBorrow) {
        this.customerBorrow = customerBorrow;
    }

    public void setLibrarianAccept(String librarianAccept) {
        this.librarianAccept = librarianAccept;
    }

    public void setLibrarianApprove(String librarianApprove) {
        this.librarianApprove = librarianApprove;
    }
}
