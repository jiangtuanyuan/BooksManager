package cn.books.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Administrator extends LitePalSupport {
    @Column
    private int id; //不可构造set方法 自增ID
    @Column
    private String Name;//姓名
    @Column
    private String Tel;//电话
    @Column
    private String Pwd;//密码

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }
}
