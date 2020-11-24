package com.hhdd;

import com.hhdd.task.DailyTask;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @Author HuangLusong
 * @Date 2020/11/18 10:42
 */
public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        if (args.length < 3) {
            LOGGER.error("请完整输入必填项");
        } else if (args.length == 3) {
            DailyTask dailyTask = new DailyTask(args[0], args[1], args[2]);
            dailyTask.execute();
        } else {
            DailyTask dailyTask = new DailyTask(args[0], args[1], args[2], args[3]);
            dailyTask.execute();
        }
    }

}
