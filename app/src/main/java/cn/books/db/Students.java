package cn.books.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Students extends LitePalSupport {
    @Column
    private int id; //不可构造set方法 自增ID
    @Column
    private String stu_number;//学号
    @Column
    private String stu_name;//姓名
    @Column
    private String stu_class;//班级
    @Column
    private String stu_sex;//性别
    @Column
    private String stu_tel;//手机号码
    @Column
    private String add_admin_id;//添加人的ID
    @Column
    private String add_admin_name;//添加人的姓名

    public int getId() {
        return id;
    }

    public String getStu_number() {
        return stu_number;
    }

    public void setStu_number(String stu_number) {
        this.stu_number = stu_number;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getStu_class() {
        return stu_class;
    }

    public void setStu_class(String stu_class) {
        this.stu_class = stu_class;
    }

    public String getStu_sex() {
        return stu_sex;
    }

    public void setStu_sex(String stu_sex) {
        this.stu_sex = stu_sex;
    }

    public String getStu_tel() {
        return stu_tel;
    }

    public void setStu_tel(String stu_tel) {
        this.stu_tel = stu_tel;
    }

    public String getAdd_admin_id() {
        return add_admin_id;
    }

    public void setAdd_admin_id(String add_admin_id) {
        this.add_admin_id = add_admin_id;
    }

    public String getAdd_admin_name() {
        return add_admin_name;
    }

    public void setAdd_admin_name(String add_admin_name) {
        this.add_admin_name = add_admin_name;
    }
}
