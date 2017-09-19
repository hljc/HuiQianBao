package com.ceq.app_core.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/11 0011.
 */

public class Bean_SystemContacts implements Serializable{
    String name,hasPhone,contactId,phoneNumber;

    @Override
    public String toString() {
        return "Bean_SystemContacts{" +
                "name='" + name + '\'' +
                ", hasPhone='" + hasPhone + '\'' +
                ", contactId='" + contactId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHasPhone() {
        return hasPhone;
    }

    public void setHasPhone(String hasPhone) {
        this.hasPhone = hasPhone;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
