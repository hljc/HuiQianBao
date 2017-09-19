package com.ceq.app.core.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/18.
 */
@Entity
public class Bean_Properties  implements Serializable {
    @Transient
    private static final long serialVersionUID = 1L;
    @Id
    private long pid;

    private boolean rememberPassword;

    @Generated(hash = 401943986)
    public Bean_Properties(long pid, boolean rememberPassword) {
        this.pid = pid;
        this.rememberPassword = rememberPassword;
    }

    @Generated(hash = 23341628)
    public Bean_Properties() {
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public boolean isRememberPassword() {
        return rememberPassword;
    }

    public void setRememberPassword(boolean rememberPassword) {
        this.rememberPassword = rememberPassword;
    }

    @Override
    public String toString() {
        return "Bean_Properties{" +
                "pid=" + pid +
                ", rememberPassword=" + rememberPassword +
                '}';
    }

    public boolean getRememberPassword() {
        return this.rememberPassword;
    }
}
