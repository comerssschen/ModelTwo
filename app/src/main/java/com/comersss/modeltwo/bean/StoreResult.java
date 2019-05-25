package com.comersss.modeltwo.bean;

public class StoreResult {

    /**
     * Data : {"Id":4,"LoginId":6,"LoginName":null,"LoginPassword":null,"StoreName":"庄仁福烧烤店","LogoUrl":"/Image\\256x256\\2HSssOWhny.png","Contacts":"庄仁福1","PhoneNum":"13587832035","AdressCode":"330110","Address":"海创科技中心","OwnerMerchantId":25,"Remark":"全场买一送二","IsLocked":false,"lockState":0,"IsDeleted":0,"CreatorId":3,"CreatorName":"testtech","CreateTime":"2019-05-14T19:13:16.273","ModifierId":1,"ModifierName":"admin","ModifiyTime":"2019-05-25T14:54:08.647"}
     * Message :
     * Success : true
     * Code : 0
     */

    private DataBean Data;
    private String Message;
    private boolean Success;
    private int Code;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public static class DataBean {
        /**
         * Id : 4
         * LoginId : 6
         * LoginName : null
         * LoginPassword : null
         * StoreName : 庄仁福烧烤店
         * LogoUrl : /Image\256x256\2HSssOWhny.png
         * Contacts : 庄仁福1
         * PhoneNum : 13587832035
         * AdressCode : 330110
         * Address : 海创科技中心
         * OwnerMerchantId : 25
         * Remark : 全场买一送二
         * IsLocked : false
         * lockState : 0
         * IsDeleted : 0
         * CreatorId : 3
         * CreatorName : testtech
         * CreateTime : 2019-05-14T19:13:16.273
         * ModifierId : 1
         * ModifierName : admin
         * ModifiyTime : 2019-05-25T14:54:08.647
         */

        private int Id;
        private int LoginId;
        private Object LoginName;
        private Object LoginPassword;
        private String StoreName;
        private String LogoUrl;
        private String Contacts;
        private String PhoneNum;
        private String AdressCode;
        private String Address;
        private int OwnerMerchantId;
        private String Remark;
        private boolean IsLocked;
        private int lockState;
        private int IsDeleted;
        private int CreatorId;
        private String CreatorName;
        private String CreateTime;
        private int ModifierId;
        private String ModifierName;
        private String ModifiyTime;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getLoginId() {
            return LoginId;
        }

        public void setLoginId(int LoginId) {
            this.LoginId = LoginId;
        }

        public Object getLoginName() {
            return LoginName;
        }

        public void setLoginName(Object LoginName) {
            this.LoginName = LoginName;
        }

        public Object getLoginPassword() {
            return LoginPassword;
        }

        public void setLoginPassword(Object LoginPassword) {
            this.LoginPassword = LoginPassword;
        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String StoreName) {
            this.StoreName = StoreName;
        }

        public String getLogoUrl() {
            return LogoUrl;
        }

        public void setLogoUrl(String LogoUrl) {
            this.LogoUrl = LogoUrl;
        }

        public String getContacts() {
            return Contacts;
        }

        public void setContacts(String Contacts) {
            this.Contacts = Contacts;
        }

        public String getPhoneNum() {
            return PhoneNum;
        }

        public void setPhoneNum(String PhoneNum) {
            this.PhoneNum = PhoneNum;
        }

        public String getAdressCode() {
            return AdressCode;
        }

        public void setAdressCode(String AdressCode) {
            this.AdressCode = AdressCode;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public int getOwnerMerchantId() {
            return OwnerMerchantId;
        }

        public void setOwnerMerchantId(int OwnerMerchantId) {
            this.OwnerMerchantId = OwnerMerchantId;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public boolean isIsLocked() {
            return IsLocked;
        }

        public void setIsLocked(boolean IsLocked) {
            this.IsLocked = IsLocked;
        }

        public int getLockState() {
            return lockState;
        }

        public void setLockState(int lockState) {
            this.lockState = lockState;
        }

        public int getIsDeleted() {
            return IsDeleted;
        }

        public void setIsDeleted(int IsDeleted) {
            this.IsDeleted = IsDeleted;
        }

        public int getCreatorId() {
            return CreatorId;
        }

        public void setCreatorId(int CreatorId) {
            this.CreatorId = CreatorId;
        }

        public String getCreatorName() {
            return CreatorName;
        }

        public void setCreatorName(String CreatorName) {
            this.CreatorName = CreatorName;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getModifierId() {
            return ModifierId;
        }

        public void setModifierId(int ModifierId) {
            this.ModifierId = ModifierId;
        }

        public String getModifierName() {
            return ModifierName;
        }

        public void setModifierName(String ModifierName) {
            this.ModifierName = ModifierName;
        }

        public String getModifiyTime() {
            return ModifiyTime;
        }

        public void setModifiyTime(String ModifiyTime) {
            this.ModifiyTime = ModifiyTime;
        }
    }
}
