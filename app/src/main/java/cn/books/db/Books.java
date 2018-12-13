package cn.books.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 图书实体
 */
public class Books extends LitePalSupport implements Serializable {
    @Column
    private int id; //不可构造set方法 自增ID
    @Column
    private String book_image_path;//图片路劲
    @Column
    private String book_name;//书名
    @Column
    private String book_author;//作者
    @Column
    private String book_type;//类别
    @Column
    private String book_introduce;//简介
    @Column
    private int add_admin_id;//添加人的ID
    @Column
    private String add_admin_name;//添加人的姓名

    public int getId() {
        return id;
    }

    public String getBook_image_path() {
        return book_image_path;
    }

    public void setBook_image_path(String book_image_path) {
        this.book_image_path = book_image_path;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_type() {
        return book_type;
    }

    public void setBook_type(String book_type) {
        this.book_type = book_type;
    }

    public String getBook_introduce() {
        return book_introduce;
    }

    public void setBook_introduce(String book_introduce) {
        this.book_introduce = book_introduce;
    }

    public int getAdd_admin_id() {
        return add_admin_id;
    }

    public void setAdd_admin_id(int add_admin_id) {
        this.add_admin_id = add_admin_id;
    }

    public String getAdd_admin_name() {
        return add_admin_name;
    }

    public void setAdd_admin_name(String add_admin_name) {
        this.add_admin_name = add_admin_name;
    }
}
