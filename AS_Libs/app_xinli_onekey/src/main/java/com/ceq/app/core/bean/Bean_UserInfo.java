package com.ceq.app.core.bean;

import com.ceq.app_core.bean.Bean_BaseUserInfo;
import com.ceq.dao.Bean_PropertiesDao;
import com.ceq.dao.Bean_UserInfoDao;
import com.ceq.dao.DaoSession;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/15.
 */
@Entity
public class Bean_UserInfo implements Serializable, Bean_BaseUserInfo {
    @Transient
    private static final long serialVersionUID = 1L;
    @Id
    String id;
    long pid;
    @ToOne(joinProperty = "pid")
    Bean_Properties bean_properties;
    long modifyDate;
    String token;

    String phone;
    String password;
    String paypass;
    String nickName;
    String fullname;
    String origcode;
    String signcode;
    String address;
    String contactname;
    String zipcode;
    String userHeadUrl;
    String province;
    String city;
    String email;
    String sex;
    String profession;
    String birthday;
    String openid;
    String unionid;
    String userToken;
    String validStatus;
    String createTime;
    String inviteCode;
    String brandId;
    String brandname;
    String grade;
    String preUserId;
    String preUserPhone;
    String realnameStatus;
    String verifyStatus;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 117490119)
    private transient Bean_UserInfoDao myDao;

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    @Generated(hash = 974610931)
    public Bean_UserInfo(String id, long pid, long modifyDate, String token, String phone,
                         String password, String paypass, String nickName, String fullname, String origcode,
                         String signcode, String address, String contactname, String zipcode, String userHeadUrl,
                         String province, String city, String email, String sex, String profession, String birthday,
                         String openid, String unionid, String userToken, String validStatus, String createTime,
                         String inviteCode, String brandId, String brandname, String grade, String preUserId,
                         String preUserPhone, String realnameStatus) {
        this.id = id;
        this.pid = pid;
        this.modifyDate = modifyDate;
        this.token = token;
        this.phone = phone;
        this.password = password;
        this.paypass = paypass;
        this.nickName = nickName;
        this.fullname = fullname;
        this.origcode = origcode;
        this.signcode = signcode;
        this.address = address;
        this.contactname = contactname;
        this.zipcode = zipcode;
        this.userHeadUrl = userHeadUrl;
        this.province = province;
        this.city = city;
        this.email = email;
        this.sex = sex;
        this.profession = profession;
        this.birthday = birthday;
        this.openid = openid;
        this.unionid = unionid;
        this.userToken = userToken;
        this.validStatus = validStatus;
        this.createTime = createTime;
        this.inviteCode = inviteCode;
        this.brandId = brandId;
        this.brandname = brandname;
        this.grade = grade;
        this.preUserId = preUserId;
        this.preUserPhone = preUserPhone;
        this.realnameStatus = realnameStatus;
    }

    @Generated(hash = 1309046910)
    public Bean_UserInfo() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPid() {
        return this.pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getModifyDate() {
        return this.modifyDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaypass() {
        return this.paypass;
    }

    public void setPaypass(String paypass) {
        this.paypass = paypass;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getOrigcode() {
        return this.origcode;
    }

    public void setOrigcode(String origcode) {
        this.origcode = origcode;
    }

    public String getSigncode() {
        return this.signcode;
    }

    public void setSigncode(String signcode) {
        this.signcode = signcode;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactname() {
        return this.contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getUserHeadUrl() {
        return this.userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProfession() {
        return this.profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getUserToken() {
        return this.userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getValidStatus() {
        return this.validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInviteCode() {
        return this.inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getBrandId() {
        return this.brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandname() {
        return this.brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPreUserId() {
        return this.preUserId;
    }

    public void setPreUserId(String preUserId) {
        this.preUserId = preUserId;
    }

    public String getPreUserPhone() {
        return this.preUserPhone;
    }

    public void setPreUserPhone(String preUserPhone) {
        this.preUserPhone = preUserPhone;
    }

    public String getRealnameStatus() {
        return this.realnameStatus;
    }

    public void setRealnameStatus(String realnameStatus) {
        this.realnameStatus = realnameStatus;
    }

    @Generated(hash = 1463971720)
    private transient Long bean_properties__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1971218163)
    public Bean_Properties getBean_properties() {
        long __key = this.pid;
        if (bean_properties__resolvedKey == null || !bean_properties__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            Bean_PropertiesDao targetDao = daoSession.getBean_PropertiesDao();
            Bean_Properties bean_propertiesNew = targetDao.load(__key);
            synchronized (this) {
                bean_properties = bean_propertiesNew;
                bean_properties__resolvedKey = __key;
            }
        }
        return bean_properties;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 776246319)
    public void setBean_properties(@NotNull Bean_Properties bean_properties) {
        if (bean_properties == null) {
            throw new DaoException(
                    "To-one property 'pid' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.bean_properties = bean_properties;
            pid = bean_properties.getPid();
            bean_properties__resolvedKey = pid;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 908991206)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBean_UserInfoDao() : null;
    }

    @Override
    public String toString() {
        return "Bean_UserInfo{" +
                "id='" + id + '\'' +
                ", pid=" + pid +
                ", bean_properties=" + bean_properties +
                ", modifyDate=" + modifyDate +
                ", token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", paypass='" + paypass + '\'' +
                ", nickName='" + nickName + '\'' +
                ", fullname='" + fullname + '\'' +
                ", origcode='" + origcode + '\'' +
                ", signcode='" + signcode + '\'' +
                ", address='" + address + '\'' +
                ", contactname='" + contactname + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", userHeadUrl='" + userHeadUrl + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", profession='" + profession + '\'' +
                ", birthday='" + birthday + '\'' +
                ", openid='" + openid + '\'' +
                ", unionid='" + unionid + '\'' +
                ", userToken='" + userToken + '\'' +
                ", validStatus='" + validStatus + '\'' +
                ", createTime='" + createTime + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", brandId='" + brandId + '\'' +
                ", brandname='" + brandname + '\'' +
                ", grade='" + grade + '\'' +
                ", preUserId='" + preUserId + '\'' +
                ", preUserPhone='" + preUserPhone + '\'' +
                ", realnameStatus='" + realnameStatus + '\'' +
                ", daoSession=" + daoSession +
                ", myDao=" + myDao +
                ", bean_properties__resolvedKey=" + bean_properties__resolvedKey +
                '}';
    }
}
