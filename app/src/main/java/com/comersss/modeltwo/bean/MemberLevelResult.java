package com.comersss.modeltwo.bean;

import java.util.List;

public class MemberLevelResult {


    /**
     * Data : [{"Id":9,"LevelName":"铂金会员","OrderIndex":1,"ImageUrl":"/Image\\256x256\\oNM1PFCpr0.jpg","MerchantId":25,"IsDeleted":0,"CreatorId":3,"CreatorName":"testtech","CreateTime":"2019-05-09T10:10:23.5","ModifierId":3,"ModifierName":"testtech","ModifiyTime":"2019-05-10T09:50:49.04"},{"Id":10,"LevelName":"砖石会员","OrderIndex":2,"ImageUrl":"/Image\\256x256\\vUaL0FA5gS.jpg","MerchantId":25,"IsDeleted":0,"CreatorId":3,"CreatorName":"testtech","CreateTime":"2019-05-09T10:10:35.687","ModifierId":3,"ModifierName":"testtech","ModifiyTime":"2019-05-10T09:50:42.28"},{"Id":11,"LevelName":"普通会员","OrderIndex":0,"ImageUrl":"/Image\\256x256\\4ciCGYEgyn.jpg","MerchantId":25,"IsDeleted":0,"CreatorId":3,"CreatorName":"testtech","CreateTime":"2019-05-09T10:14:49.937","ModifierId":3,"ModifierName":"testtech","ModifiyTime":"2019-05-10T09:50:53.42"}]
     * Message : 操作成功
     * Success : true
     * Code : 0
     */

    private String Message;
    private boolean Success;
    private int Code;
    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * Id : 9
         * LevelName : 铂金会员
         * OrderIndex : 1
         * ImageUrl : /Image\256x256\oNM1PFCpr0.jpg
         * MerchantId : 25
         * IsDeleted : 0
         * CreatorId : 3
         * CreatorName : testtech
         * CreateTime : 2019-05-09T10:10:23.5
         * ModifierId : 3
         * ModifierName : testtech
         * ModifiyTime : 2019-05-10T09:50:49.04
         */

        private int Id;
        private String LevelName;
        private int OrderIndex;
        private String ImageUrl;
        private int MerchantId;
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

        public String getLevelName() {
            return LevelName;
        }

        public void setLevelName(String LevelName) {
            this.LevelName = LevelName;
        }

        public int getOrderIndex() {
            return OrderIndex;
        }

        public void setOrderIndex(int OrderIndex) {
            this.OrderIndex = OrderIndex;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String ImageUrl) {
            this.ImageUrl = ImageUrl;
        }

        public int getMerchantId() {
            return MerchantId;
        }

        public void setMerchantId(int MerchantId) {
            this.MerchantId = MerchantId;
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
