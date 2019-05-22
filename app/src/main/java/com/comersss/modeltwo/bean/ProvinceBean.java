package com.comersss.modeltwo.bean;


/**
 * 作者：create by comersss on 2019/5/22 15:16
 * 邮箱：904359289@qq.com
 */
public class ProvinceBean {
    private String id;
    private String name;

    public ProvinceBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public class city {
        private String id;
        private String name;

        public city(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
