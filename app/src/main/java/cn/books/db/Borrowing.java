package cn.books.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 图书实体
 */
public class Borrowing extends LitePalSupport implements Serializable {
    @Column
    private int id; //不可构造set方法 自增ID
    @Column
    private int book_id; //图书ID
    @Column
    private String book_name; //图书名称
    @Column
    private String book_image_path; //图书图片的路径
    @Column
    private int studen_id; //学生ID
    @Column
    private String studen_name; //学生名称
    @Column
    private String stu_borr_time; //借阅开始时间 学生填写
    @Column
    private String stu_return_time; //借阅归还时间
    @Column
    private String book_return_time; //图书真正的归还时间
    @Column
    private int status; //借阅状态 1:借阅中 2:已归还 3.已逾期
    @Column
    private int add_admin_id;//管理员添加人的ID
    @Column
    private String add_admin_name;//管理员添加人的姓名
    @Column
    private String note; //备注

    public String getBook_image_path() {
        return book_image_path;
    }

    public void setBook_image_path(String book_image_path) {
        this.book_image_path = book_image_path;
    }

    public int getId() {
        return id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public int getStuden_id() {
        return studen_id;
    }

    public void setStuden_id(int studen_id) {
        this.studen_id = studen_id;
    }

    public String getStuden_name() {
        return studen_name;
    }

    public void setStuden_name(String studen_name) {
        this.studen_name = studen_name;
    }

    public String getStu_borr_time() {
        return stu_borr_time;
    }

    public void setStu_borr_time(String stu_borr_time) {
        this.stu_borr_time = stu_borr_time;
    }

    public String getStu_return_time() {
        return stu_return_time;
    }

    public void setStu_return_time(String stu_return_time) {
        this.stu_return_time = stu_return_time;
    }

    public String getBook_return_time() {
        return book_return_time;
    }

    public void setBook_return_time(String book_return_time) {
        this.book_return_time = book_return_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
