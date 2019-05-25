package com.comersss.modeltwo.bean;

public class CashResult {

    /**
     * Data : {"Id":10,"UserName":"收银员","LoginName":"casher","LoginPassword":"5D93CEB70E2BF5DAA84EC3D0CD2C731A","RefundPassword":"E10ADC3949BA59ABBE56E057F20F883E","StoreId":4,"MerchantId":25,"IsCasher":true,"RoleId":0,"RoleName":null,"IsLocked":false,"lockState":0,"IsDeleted":0,"CreatorId":0,"CreatorName":null,"CreateTime":"2019-05-25T16:15:57.081695+08:00","ModifierId":0,"ModifierName":null,"ModifiyTime":"2019-05-25T16:15:57.081695+08:00"}
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
         * Id : 10
         * UserName : 收银员
         * LoginName : casher
         * LoginPassword : 5D93CEB70E2BF5DAA84EC3D0CD2C731A
         * RefundPassword : E10ADC3949BA59ABBE56E057F20F883E
         * StoreId : 4
         * MerchantId : 25
         * IsCasher : true
         * RoleId : 0
         * RoleName : null
         * IsLocked : false
         * lockState : 0
         * IsDeleted : 0
         * CreatorId : 0
         * CreatorName : null
         * CreateTime : 2019-05-25T16:15:57.081695+08:00
         * ModifierId : 0
         * ModifierName : null
         * ModifiyTime : 2019-05-25T16:15:57.081695+08:00
         */

        private int Id;
        private String UserName;
        private String LoginName;
        private String LoginPassword;
        private String RefundPassword;
        private int StoreId;
        private int MerchantId;
        private boolean IsCasher;
        private int RoleId;
        private Object RoleName;
        private boolean IsLocked;
        private int lockState;
        private int IsDeleted;
        private int CreatorId;
        private Object CreatorName;
        private String CreateTime;
        private int ModifierId;
        private Object ModifierName;
        private String ModifiyTime;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getLoginName() {
            return LoginName;
        }

        public void setLoginName(String LoginName) {
            this.LoginName = LoginName;
        }

        public String getLoginPassword() {
            return LoginPassword;
        }

        public void setLoginPassword(String LoginPassword) {
            this.LoginPassword = LoginPassword;
        }

        public String getRefundPassword() {
            return RefundPassword;
        }

        public void setRefundPassword(String RefundPassword) {
            this.RefundPassword = RefundPassword;
        }

        public int getStoreId() {
            return StoreId;
        }

        public void setStoreId(int StoreId) {
            this.StoreId = StoreId;
        }

        public int getMerchantId() {
            return MerchantId;
        }

        public void setMerchantId(int MerchantId) {
            this.MerchantId = MerchantId;
        }

        public boolean isIsCasher() {
            return IsCasher;
        }

        public void setIsCasher(boolean IsCasher) {
            this.IsCasher = IsCasher;
        }

        public int getRoleId() {
            return RoleId;
        }

        public void setRoleId(int RoleId) {
            this.RoleId = RoleId;
        }

        public Object getRoleName() {
            return RoleName;
        }

        public void setRoleName(Object RoleName) {
            this.RoleName = RoleName;
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

        public Object getCreatorName() {
            return CreatorName;
        }

        public void setCreatorName(Object CreatorName) {
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

        public Object getModifierName() {
            return ModifierName;
        }

        public void setModifierName(Object ModifierName) {
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
