package com.hhdd.task;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.google.gson.*;
import com.hhdd.api.APIList;
import com.hhdd.bean.Level_info;
import com.hhdd.bean.TaskStatus;
import com.hhdd.bean.User;
import com.hhdd.exception.BiliException;
import com.hhdd.utils.ResourceUtils;
import org.apache.http.Header;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 日常任务
 *
 * @Author HuangLusong
 * @Date 2020/11/16 14:15
 */
public class DailyTask {

    private static final Logger LOGGER = LogManager.getLogger(DailyTask.class);
    private static final String USER_AGENT = "Easy BiLiBiLi/1.0.0 (123456@qq.com)";
    private static final String ACCEPT_CONTENT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
    private static final String STATUS_CODE = "code";
    private String errorMsg = "";
    private Header[] headers;
    private HttpConfig httpConfig;
    private JsonParser jsonParser;
    private TaskStatus taskStatus;
    private ArrayList<String> BVIdList;
    private final String sessData;
    private final String userId;
    private final String bill_jct;
    private final String sckey;
    private User user;

    public DailyTask(String sessData, String userId, String bill_jct) {
        this(sessData, userId, bill_jct, "");
    }

    public DailyTask(String sessData, String userId, String bill_jct, String sckey) {
        BVIdList = new ArrayList<>();
        this.sessData = sessData;
        this.userId = userId;
        this.bill_jct = bill_jct;
        this.sckey = sckey;
        String cookieStr = "SESSDATA=" + this.sessData + ";DedeUserID=" + this.userId;
        headers = HttpHeader.custom().cookie(cookieStr).accept(ACCEPT_CONTENT).userAgent(USER_AGENT).build();
        httpConfig = HttpConfig.custom().headers(headers);
        jsonParser = new JsonParser();
    }


    /**
     * 检查cookie是否正确或者cookie是否过期
     */
    public void checkCookie() {
        LOGGER.debug("开始检查cookie的有效性");
        try {
            JsonObject jsonObject = jsonParser.parse(HttpClientUtil.get(httpConfig.url(APIList.LOGIN))).getAsJsonObject();
            if (jsonObject.get(STATUS_CODE).getAsInt() == 0) {
                LOGGER.info("cookie有效，可以正常登录");
            } else {
                LOGGER.warn("cookie无效，可能输入错误或已经过期");
                errorMsg = "cookie问题";
            }
        } catch (HttpProcessException e) {
            LOGGER.warn("网络出现问题");
            errorMsg = "网络问题";
            throw new BiliException(errorMsg, e);
            //e.printStackTrace();
        } catch (Exception e) {
            errorMsg = "api有问题，需要更新";
            throw new BiliException(errorMsg, e);
        }
        if (!"".equals(errorMsg)) {
            throw new BiliException(errorMsg);
        }

    }

    /**
     * 获取每日任务的完成情况
     */
    public void getDailyTaskStatus() {
        LOGGER.debug("开始获取每日任务执行情况");

        try {
            JsonObject jsonObject = jsonParser.parse(HttpClientUtil.get(httpConfig.url(APIList.reward))).getAsJsonObject();
            taskStatus = new Gson().fromJson(jsonObject.get("data"), TaskStatus.class);
            LOGGER.debug("每日任务执行情况:" + taskStatus);
        } catch (HttpProcessException e) {
            errorMsg = "网络问题";
            throw new BiliException(errorMsg, e);
        } catch (Exception e) {
            errorMsg = "api有问题，需要更新";
            throw new BiliException(errorMsg, e);
        }

    }

    /**
     * 从关注的up主中随机选取一位观看视频
     */
    public void watchVideoFromFollowUps() {
        try {
            getBVIdListFromFollowUps();
        } catch (Exception e) {
            LOGGER.warn("获取bvid列表失败，执行观看视频任务失败");
            e.printStackTrace();
            return;
        }
        LOGGER.debug("开始观看视频");
        // 如果今天的观看任务已经完成，则直接返回
        if (taskStatus.isWatch()) {
            LOGGER.info("今天已经看过视频，所以跳过观看任务");
            return;
        }
        HashMap<String, Object> requestBody = new HashMap<>();
        //1 随机获取一个bvid
        String bvid = BVIdList.get(new Random().nextInt(BVIdList.size()));
        requestBody.put("bvid", bvid);
        String res = null;
        try {
            res = HttpClientUtil.post(httpConfig.url(APIList.videoHeartbeat).map(requestBody));
            JsonObject jsonObject = jsonParser.parse(res).getAsJsonObject();
            if (jsonObject.get(STATUS_CODE).getAsInt() != 0) {
                LOGGER.warn("观看视频api过期，执行观看视频任务失败");
            } else {
                LOGGER.info("完成观看视频任务");
            }
        } catch (HttpProcessException e) {
            LOGGER.warn("网络错误，执行观看视频任务失败");
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.warn("观看视频api过期，执行观看视频任务失败");
            e.printStackTrace();
        }
    }

    /**
     * 检查视频是否投币过
     *
     * @param bvid
     * @return
     */
    private boolean checkCoinStatus(String bvid) {
        String params = "?bvid=" + bvid;
        try {
            String res = HttpClientUtil.get(httpConfig.url(APIList.isCoin + params));
            JsonObject jsonObject = jsonParser.parse(res).getAsJsonObject();
            int flag = jsonObject.getAsJsonObject("data").get("multiply").getAsInt();
            if (flag != 0) {
                LOGGER.debug("这个视频已经投过币了");
                return true;
            } else {
                LOGGER.debug("这个视频没有投币过");
                return false;
            }
        } catch (Exception e) {
            throw new BiliException("网络错误或api错误", e);
        }
    }

    /**
     * 获取硬币余额
     *
     * @return
     */
    private int getCoinBalance() {
        try {
            String res = HttpClientUtil.get(httpConfig.url(APIList.getCoinBalance));
            JsonObject data = jsonParser.parse(res).getAsJsonObject().getAsJsonObject("data");
            int money = data.get("money").getAsInt();
            return money;
        } catch (Exception e) {
            LOGGER.warn("网络错误或者api过时错误，无法检查硬币的余额");
            throw new BiliException("网络错误或者api过时错误", e);
        }
    }


    /**
     * 投币并且点赞
     * 从动态的视频中顺序选取一个进行投币
     */
    public void addCoin() {
        if (BVIdList != null) {
            try {
                int coinExp = getCoinExp();
                if (coinExp >= 50) {
                    LOGGER.warn("投币获得的经验为" + coinExp + "， 已经达到上限，所以跳过投币任务");
                    return;
                }
                for (String bvid : BVIdList) {
                    // 检查是否给这个视频投币过
                    if (!checkCoinStatus(bvid)) {
                        //检查硬币余额是否足够
                        int coinBalance = getCoinBalance();
                        if (coinBalance > 0) {
                            //进行投币操作 todo 无法解决csrf校验问题
                            // resolved in 2020-11-17 cause of 使用postman不靠谱啊，还有不要用跨域标识
                            HashMap<String, Object> requestBody = new HashMap<>();
                            requestBody.put("multiply", "1");
                            // requestBody.put("cross_domain", "true");
                            requestBody.put("select_like", "1");
                            requestBody.put("csrf", bill_jct);
                            requestBody.put("bvid", bvid);
                            String res = HttpClientUtil.post(httpConfig.url(APIList.CoinAdd).map(requestBody));
                            //  String res = HttpClientUtil.post(httpConfig.url(APIList.CoinAdd));
                            JsonObject jsonObject = jsonParser.parse(res).getAsJsonObject();
                            if (jsonObject.get("code").getAsInt() == 0) {

                                LOGGER.info("投币成功，投币任务完成");
                            } else {
                                LOGGER.warn("投币失败，投币任务失败");
                            }

                        } else {
                            LOGGER.warn("硬币不够用了，无法进行投币操作，投币任务失败");
                        }
                        return;
                    } else {
                        LOGGER.debug("这个视频已经投币过了，跳过，选择下一个视频");
                    }
                }
                LOGGER.warn("动态获取到的所有视频都投币过，投币任务失败");
            } catch (Exception e) {
                LOGGER.warn("发生网络错误或者api过时了，投币任务失败");
            }
        }
    }

    /**
     * 从关注的up主中（动态）获取最新的bvidlist
     */
    private void getBVIdListFromFollowUps() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("uid", userId);
        paramsMap.put("type_list", "8");
        paramsMap.put("platform", "web");
        String params = "uid=" + userId + "&type_list=8"
                + "&platform=web";
        try {
            String url = APIList.queryDynamicNew + "?" + params;
            JsonObject jsonObject = jsonParser.parse(HttpClientUtil.get(httpConfig.url(url))).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonObject("data").getAsJsonArray("cards");
            for (JsonElement jsonElement : jsonArray) {
                String bvid = jsonElement.getAsJsonObject().getAsJsonObject("desc").get("bvid").getAsString();
                BVIdList.add(bvid);
            }
        } catch (HttpProcessException e) {
            errorMsg = "网络错误";
            throw new BiliException(errorMsg, e);
        } catch (Exception e) {
            errorMsg = "api过时了";
            throw new BiliException(errorMsg, e);
        }
    }

    /**
     * 获取获得的经验
     *
     * @return
     */
    public int getExpNum() throws HttpProcessException {
        //刷新dailyTask状态
        getDailyTaskStatus();
        int coinExp = getCoinExp();
        int exp = 0;
        if (taskStatus.isWatch()) {
            exp += 5;
        }
        if (taskStatus.isLogin()) {
            exp += 5;
        }
        if (taskStatus.isShare()) {
            exp += 5;
        }
        exp += coinExp;
        return exp;
    }

    private int getCoinExp() throws HttpProcessException {
        //获取今日投币获得的经验数
        String res = HttpClientUtil.get(httpConfig.url(APIList.needCoinNew));
        JsonObject jsonObject = jsonParser.parse(res).getAsJsonObject();
        return jsonObject.get("data").getAsInt();
    }

    public void getUserInfo() {
        try {
            JsonObject jsonObject = jsonParser.parse(HttpClientUtil.get(httpConfig.url(APIList.LOGIN))).getAsJsonObject();
            user = new Gson().fromJson(jsonObject.get("data"), User.class);
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录等级信息，包括今日获得的经验和升级所需的经验
     */
    public void recordLevelInfo() {
        try {
            int todayExp = getExpNum();
            getUserInfo();
            LOGGER.info("今天获得的经验为" + todayExp);
        } catch (HttpProcessException e) {
            LOGGER.warn("网络错误，获取今日经验失败");
            e.printStackTrace();
        }
        Level_info level_info = user.getLevel_info();
        LOGGER.info("当前等级为" + level_info.getCurrent_level());
        LOGGER.info("当前经验为" + level_info.getCurrent_exp());
        LOGGER.info("距离升级到下一级还需要的经验为" + level_info.getNext_exp());
    }

    /**
     * 通过server酱的方式来推送消息
     */
    public void pushMsg() {
        LOGGER.debug("开始使用server酱推送消息");
        if (this.sckey.equals("")) {
            LOGGER.info("没有绑定sckey，所以不进行消息推送");
            return;
        } else {
            try {
                String desp = ResourceUtils.loadLog2String();
                String text = "easy bilibili 任务简报";
                HashMap<String, Object> requestBody = new HashMap<>();
                requestBody.put("text", text);
                requestBody.put("desp", desp);
                String url = APIList.ServerPush + this.sckey + ".send";
                String res = HttpClientUtil.post(httpConfig.url(url).map(requestBody));
                JsonObject jsonObject = jsonParser.parse(res).getAsJsonObject();
                if (jsonObject.get("errno").getAsInt() != 0) {
                    LOGGER.warn("请求server酱出错，可能是sckey不正确，推送消息失败");
                } else {
                    LOGGER.info("推送消息成功");
                }
            } catch (IOException e) {
                LOGGER.warn("读取日志文件发生出错误，推送消息失败");
                e.printStackTrace();
            } catch (HttpProcessException e) {
                LOGGER.warn("网络错误，推送消息失败");
                e.printStackTrace();
            }

        }
    }

    //除了cookie检验、获取任务状态时出错了，会继续抛出异常让程序终止，其他的异常则让程序继续去做下一个任务
    public void execute() {
        try {
            checkCookie();
            getDailyTaskStatus();
            watchVideoFromFollowUps();
            addCoin();
            recordLevelInfo();
            pushMsg();
        } catch (BiliException e) {
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.error("未知错误");
            e.printStackTrace();
        }
    }

}
