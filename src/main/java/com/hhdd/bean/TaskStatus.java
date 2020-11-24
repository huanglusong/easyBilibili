package com.hhdd.bean;

/**
 * 封装每日任务的完成情况
 *
 * @Author HuangLusong
 * @Date 2020/11/16 18:56
 */
public class TaskStatus {
    //是否登录
    private boolean login;
    //是否观看
    private boolean watch;
    // 投币数
    private int coins;
    // 是否分享
    private boolean share;

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean isWatch() {
        return watch;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    @Override
    public String toString() {
        return "TaskStatus{" +
                "login=" + login +
                ", watch=" + watch +
                ", coins=" + coins +
                ", share=" + share +
                '}';
    }
}
