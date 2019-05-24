package com.comersss.modeltwo.bean;

/**
 * 作者：create by comersss on 2019/5/22 10:45
 * 邮箱：904359289@qq.com
 */
public class MemberBean {

    public MemberBean(String id, String name, int sex, String phoneNum, String birthday, String addressCode, String address, int entryMode, int memberLevelId, int isLocked) {
        Id = id;
        Name = name;
        Sex = sex;
        PhoneNum = phoneNum;
        Birthday = birthday;
        AddressCode = addressCode;
        Address = address;
        EntryMode = entryMode;
        MemberLevelId = memberLevelId;
        IsLocked = isLocked;
    }

    public MemberBean(String id) {
        this.Id = id;
    }

    private String Id;
    private String Name;
    private int Sex;//0女  1男
    private String PhoneNum;
    private String Birthday;
    private String AddressCode;
    private String Address;
    private int EntryMode;
    private int MemberLevelId;
    private int IsLocked;


    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getAddressCode() {
        return AddressCode;
    }

    public void setAddressCode(String addressCode) {
        AddressCode = addressCode;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getEntryMode() {
        return EntryMode;
    }

    public void setEntryMode(int entryMode) {
        EntryMode = entryMode;
    }

    public int getMemberLevelId() {
        return MemberLevelId;
    }

    public void setMemberLevelId(int memberLevelId) {
        MemberLevelId = memberLevelId;
    }

    public int getIsLocked() {
        return IsLocked;
    }

    public void setIsLocked(int isLocked) {
        IsLocked = isLocked;
    }
}
