package cn.books.activity.user;

import org.litepal.annotation.Column;

import java.io.Serializable;

/**
 * Created by 蒋 on 2018/7/7.
 * 用户列表数据实体类
 */

public class UserBean implements Serializable {
    private int stuid;//用户ID
    private String stu_number;//学号
    private String stu_name;//姓名
    private String stu_class;//班级
    private String stu_sex;//班级
    private boolean checked = false;//是否选择了此用户

    public String getStu_sex() {
        return stu_sex;
    }

    public void setStu_sex(String stu_sex) {
        this.stu_sex = stu_sex;
    }

    public int getStuid() {
        return stuid;
    }

    public void setStuid(int stuid) {
        this.stuid = stuid;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
