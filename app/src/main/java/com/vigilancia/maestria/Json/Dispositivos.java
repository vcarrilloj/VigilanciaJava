package com.vigilancia.maestria.Json;

public class Dispositivos {

    private int activeTime;
    private String bindSpaceId;
    private String category;
    private int createTime;
    private String customName;
    private String icon;
    private String id;
    private String ip;
    private boolean isOnline;
    private String lat;
    private String localKey;
    private String lon;
    private String model;
    private String name;
    private String productId;
    private String productName;
    private boolean sub;
    private String timeZone;
    private int updateTime;
    private String uuid;

    public Dispositivos(int activeTime, String bindSpaceId, String category, int createTime, String customName, String icon, String id, String ip, boolean isOnline, String lat, String localKey, String lon, String model, String name, String productId, String productName, boolean sub, String timeZone, int updateTime, String uuid) {
        this.activeTime = activeTime;
        this.bindSpaceId = bindSpaceId;
        this.category = category;
        this.createTime = createTime;
        this.customName = customName;
        this.icon = icon;
        this.id = id;
        this.ip = ip;
        this.isOnline = isOnline;
        this.lat = lat;
        this.localKey = localKey;
        this.lon = lon;
        this.model = model;
        this.name = name;
        this.productId = productId;
        this.productName = productName;
        this.sub = sub;
        this.timeZone = timeZone;
        this.updateTime = updateTime;
        this.uuid = uuid;
    }

    public int getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(int activeTime) {
        this.activeTime = activeTime;
    }

    public String getBindSpaceId() {
        return bindSpaceId;
    }

    public void setBindSpaceId(String bindSpaceId) {
        this.bindSpaceId = bindSpaceId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLocalKey() {
        return localKey;
    }

    public void setLocalKey(String localKey) {
        this.localKey = localKey;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isSub() {
        return sub;
    }

    public void setSub(boolean sub) {
        this.sub = sub;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String EstadoActual() {
        if(isOnline){
            return "Online";
        }else {
            return "Offline";
        }
    }
}
