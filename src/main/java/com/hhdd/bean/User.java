package com.hhdd.bean;

/**
 * 封装用户信息
 * @Author HuangLusong
 * @Date 2020/11/16 14:43
 */
public class User {

    private boolean isLogin;
    private int email_verified;
    private String face;
    private Level_info level_info;
    private long mid;
    private int mobile_verified;
    private double money;
    private int moral;
    private Official official;
    private OfficialVerify officialVerify;
    private Pendant pendant;
    private int scores;
    private String uname;
    private long vipDueDate;
    private int vipStatus;
    private int vipType;
    private int vip_pay_type;
    private int vip_theme_type;
    private Vip_label vip_label;
    private int vip_avatar_subscript;
    private String vip_nickname_color;
    private Wallet wallet;
    private boolean has_shop;
    private String shop_url;
    private int allowance_count;
    private int answer_status;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public int getEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(int email_verified) {
        this.email_verified = email_verified;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Level_info getLevel_info() {
        return level_info;
    }

    public void setLevel_info(Level_info level_info) {
        this.level_info = level_info;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public int getMobile_verified() {
        return mobile_verified;
    }

    public void setMobile_verified(int mobile_verified) {
        this.mobile_verified = mobile_verified;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getMoral() {
        return moral;
    }

    public void setMoral(int moral) {
        this.moral = moral;
    }

    public Official getOfficial() {
        return official;
    }

    public void setOfficial(Official official) {
        this.official = official;
    }

    public OfficialVerify getOfficialVerify() {
        return officialVerify;
    }

    public void setOfficialVerify(OfficialVerify officialVerify) {
        this.officialVerify = officialVerify;
    }

    public Pendant getPendant() {
        return pendant;
    }

    public void setPendant(Pendant pendant) {
        this.pendant = pendant;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public long getVipDueDate() {
        return vipDueDate;
    }

    public void setVipDueDate(long vipDueDate) {
        this.vipDueDate = vipDueDate;
    }

    public int getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(int vipStatus) {
        this.vipStatus = vipStatus;
    }

    public int getVipType() {
        return vipType;
    }

    public void setVipType(int vipType) {
        this.vipType = vipType;
    }

    public int getVip_pay_type() {
        return vip_pay_type;
    }

    public void setVip_pay_type(int vip_pay_type) {
        this.vip_pay_type = vip_pay_type;
    }

    public int getVip_theme_type() {
        return vip_theme_type;
    }

    public void setVip_theme_type(int vip_theme_type) {
        this.vip_theme_type = vip_theme_type;
    }

    public Vip_label getVip_label() {
        return vip_label;
    }

    public void setVip_label(Vip_label vip_label) {
        this.vip_label = vip_label;
    }

    public int getVip_avatar_subscript() {
        return vip_avatar_subscript;
    }

    public void setVip_avatar_subscript(int vip_avatar_subscript) {
        this.vip_avatar_subscript = vip_avatar_subscript;
    }

    public String getVip_nickname_color() {
        return vip_nickname_color;
    }

    public void setVip_nickname_color(String vip_nickname_color) {
        this.vip_nickname_color = vip_nickname_color;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public boolean isHas_shop() {
        return has_shop;
    }

    public void setHas_shop(boolean has_shop) {
        this.has_shop = has_shop;
    }

    public String getShop_url() {
        return shop_url;
    }

    public void setShop_url(String shop_url) {
        this.shop_url = shop_url;
    }

    public int getAllowance_count() {
        return allowance_count;
    }

    public void setAllowance_count(int allowance_count) {
        this.allowance_count = allowance_count;
    }

    public int getAnswer_status() {
        return answer_status;
    }

    public void setAnswer_status(int answer_status) {
        this.answer_status = answer_status;
    }

    @Override
    public String toString() {
        return "User{" +
                "isLogin=" + isLogin +
                ", email_verified=" + email_verified +
                ", face='" + face + '\'' +
                ", level_info=" + level_info +
                ", mid=" + mid +
                ", mobile_verified=" + mobile_verified +
                ", money=" + money +
                ", moral=" + moral +
                ", official=" + official +
                ", officialVerify=" + officialVerify +
                ", pendant=" + pendant +
                ", scores=" + scores +
                ", uname='" + uname + '\'' +
                ", vipDueDate=" + vipDueDate +
                ", vipStatus=" + vipStatus +
                ", vipType=" + vipType +
                ", vip_pay_type=" + vip_pay_type +
                ", vip_theme_type=" + vip_theme_type +
                ", vip_label=" + vip_label +
                ", vip_avatar_subscript=" + vip_avatar_subscript +
                ", vip_nickname_color='" + vip_nickname_color + '\'' +
                ", wallet=" + wallet +
                ", has_shop=" + has_shop +
                ", shop_url='" + shop_url + '\'' +
                ", allowance_count=" + allowance_count +
                ", answer_status=" + answer_status +
                '}';
    }
}
