package com.example.commodityclassification;

import java.util.List;

public class CommodityClassificationEntity {

    private int pId;
    private int cId;
    private int type;
    private String name;
    private String iconUrl;
    private List<CommodityClassificationEntity> subclassificationList;//


    public CommodityClassificationEntity(int pId, int cId, String name, String iconUrl) {
        this.pId = pId;
        this.cId = cId;
        this.name = name;
        this.iconUrl = iconUrl;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public List<CommodityClassificationEntity> getSubclassificationList() {
        return subclassificationList;
    }

    public void setSubclassificationList(List<CommodityClassificationEntity> subclassificationList) {
        subclassificationList = subclassificationList;
    }

    @Override
    public String toString() {
        return "CommodityClassificationEntity{" +
                "pId=" + pId +
                ", cId=" + cId +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", subclassificationList=" + subclassificationList +
                '}';
    }
}
