package com.comersss.modeltwo.bean;

/**
 * 作者：create by comersss on 2019/5/24 17:15
 * 邮箱：904359289@qq.com
 */
public class MemberResult {

    /**
     * Data : {"Id":17,"Name":"庄","Sex":false,"PhoneNum":"13587832034","Birthday":"2019-05-15T00:00:00","RechargePrice":0.02,"ConsumePrice":0,"RealConsumePrice":0,"Balance":0.02,"Point":0,"AddressCode":"110101","Address":"大飒飒的","EntryMode":0,"MemberLevelId":10,"MemberLevelName":"砖石会员","LevelIconUrl":"/Image\\256x256\\vUaL0FA5gS.jpg","AliUnqueId":null,"WechatUnqueId":null,"StoreId":4,"StoreName":"庄仁福烧烤店","MerchantId":25,"MerchantName":"湖北省武汉市吴氏软件公司","face_token":null,"auth_code":null,"IsLocked":false,"IsDeleted":0,"CreatorId":0,"CreatorName":"casher","CreateTime":"2019-05-24T10:02:52.837","ModifierId":0,"ModifierName":"casher","ModifiyTime":"2019-05-24T10:09:58.26"}
     * Message : 操作成功
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
         * Id : 17
         * Name : 庄
         * Sex : false
         * PhoneNum : 13587832034
         * Birthday : 2019-05-15T00:00:00
         * RechargePrice : 0.02
         * ConsumePrice : 0.0
         * RealConsumePrice : 0.0
         * Balance : 0.02
         * Point : 0
         * AddressCode : 110101
         * Address : 大飒飒的
         * EntryMode : 0
         * MemberLevelId : 10
         * MemberLevelName : 砖石会员
         * LevelIconUrl : /Image\256x256\vUaL0FA5gS.jpg
         * AliUnqueId : null
         * WechatUnqueId : null
         * StoreId : 4
         * StoreName : 庄仁福烧烤店
         * MerchantId : 25
         * MerchantName : 湖北省武汉市吴氏软件公司
         * face_token : null
         * auth_code : null
         * IsLocked : false
         * IsDeleted : 0
         * CreatorId : 0
         * CreatorName : casher
         * CreateTime : 2019-05-24T10:02:52.837
         * ModifierId : 0
         * ModifierName : casher
         * ModifiyTime : 2019-05-24T10:09:58.26
         */

        private int Id;
        private String Name;
        private boolean Sex;
        private String PhoneNum;
        private String Birthday;
        private double RechargePrice;
        private double ConsumePrice;
        private double RealConsumePrice;
        private double Balance;
        private int Point;
        private String AddressCode;
        private String Address;
        private int EntryMode;
        private int MemberLevelId;
        private String MemberLevelName;
        private String LevelIconUrl;
        private Object AliUnqueId;
        private Object WechatUnqueId;
        private int StoreId;
        private String StoreName;
        private int MerchantId;
        private String MerchantName;
        private Object face_token;
        private Object auth_code;
        private boolean IsLocked;
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

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public boolean isSex() {
            return Sex;
        }

        public void setSex(boolean Sex) {
            this.Sex = Sex;
        }

        public String getPhoneNum() {
            return PhoneNum;
        }

        public void setPhoneNum(String PhoneNum) {
            this.PhoneNum = PhoneNum;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }

        public double getRechargePrice() {
            return RechargePrice;
        }

        public void setRechargePrice(double RechargePrice) {
            this.RechargePrice = RechargePrice;
        }

        public double getConsumePrice() {
            return ConsumePrice;
        }

        public void setConsumePrice(double ConsumePrice) {
            this.ConsumePrice = ConsumePrice;
        }

        public double getRealConsumePrice() {
            return RealConsumePrice;
        }

        public void setRealConsumePrice(double RealConsumePrice) {
            this.RealConsumePrice = RealConsumePrice;
        }

        public double getBalance() {
            return Balance;
        }

        public void setBalance(double Balance) {
            this.Balance = Balance;
        }

        public int getPoint() {
            return Point;
        }

        public void setPoint(int Point) {
            this.Point = Point;
        }

        public String getAddressCode() {
            return AddressCode;
        }

        public void setAddressCode(String AddressCode) {
            this.AddressCode = AddressCode;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public int getEntryMode() {
            return EntryMode;
        }

        public void setEntryMode(int EntryMode) {
            this.EntryMode = EntryMode;
        }

        public int getMemberLevelId() {
            return MemberLevelId;
        }

        public void setMemberLevelId(int MemberLevelId) {
            this.MemberLevelId = MemberLevelId;
        }

        public String getMemberLevelName() {
            return MemberLevelName;
        }

        public void setMemberLevelName(String MemberLevelName) {
            this.MemberLevelName = MemberLevelName;
        }

        public String getLevelIconUrl() {
            return LevelIconUrl;
        }

        public void setLevelIconUrl(String LevelIconUrl) {
            this.LevelIconUrl = LevelIconUrl;
        }

        public Object getAliUnqueId() {
            return AliUnqueId;
        }

        public void setAliUnqueId(Object AliUnqueId) {
            this.AliUnqueId = AliUnqueId;
        }

        public Object getWechatUnqueId() {
            return WechatUnqueId;
        }

        public void setWechatUnqueId(Object WechatUnqueId) {
            this.WechatUnqueId = WechatUnqueId;
        }

        public int getStoreId() {
            return StoreId;
        }

        public void setStoreId(int StoreId) {
            this.StoreId = StoreId;
        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String StoreName) {
            this.StoreName = StoreName;
        }

        public int getMerchantId() {
            return MerchantId;
        }

        public void setMerchantId(int MerchantId) {
            this.MerchantId = MerchantId;
        }

        public String getMerchantName() {
            return MerchantName;
        }

        public void setMerchantName(String MerchantName) {
            this.MerchantName = MerchantName;
        }

        public Object getFace_token() {
            return face_token;
        }

        public void setFace_token(Object face_token) {
            this.face_token = face_token;
        }

        public Object getAuth_code() {
            return auth_code;
        }

        public void setAuth_code(Object auth_code) {
            this.auth_code = auth_code;
        }

        public boolean isIsLocked() {
            return IsLocked;
        }

        public void setIsLocked(boolean IsLocked) {
            this.IsLocked = IsLocked;
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
