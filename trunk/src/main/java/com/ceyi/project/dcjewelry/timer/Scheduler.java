package com.ceyi.project.dcjewelry.timer;

import com.ceyi.project.dcjewelry.article.ArticleService;
import com.ceyi.project.dcjewelry.message.MessageService;
import com.ceyi.project.dcjewelry.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by lingh on 2017/5/6.
 */
@Component
public class Scheduler implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
    private List<TaskItem> taskList = new ArrayList<>();
    @Inject
    private UserService userService;
    @Inject
    private MessageService messageService;
    @Inject
    private ArticleService articleService;

    @Override
    public void afterPropertiesSet() throws Exception {
        taskList.add(new TaskItem("下载头像", 30, () -> userService.downloadHeadimg()));
        taskList.add(new TaskItem("私信提醒", 30, () -> messageService.notifyByWx()));
        taskList.add(new TaskItem("资讯提醒", 600, () -> articleService.notifyNewArticle()));
        start();
    }

    public void start() {
        new Timer("scheduler").schedule(new TimerTask() {
            @Override
            public void run() {
                Date now = new Date();
                for (TaskItem task : taskList) {
                    if (now.getTime() - task.getPreviousTime().getTime() > task.getInterval() && !task.isRunning()) {
                        synchronized (task) {
                            if (now.getTime() - task.getPreviousTime().getTime() > task.getInterval() && !task.isRunning()) {
                                new Thread(() -> {
                                    task.setPreviousTime(now);
                                    task.setRunning(true);
                                    logger.debug("开始任务: {}", task.getName());
                                    try {
                                        task.getTask().execute();
                                    } catch (Throwable t) {
                                        logger.debug("任务出错, task:{} t:{}", task.getName(), t);
                                    } finally {
                                        task.setRunning(false);
                                        logger.debug("完成任务: {}", task.getName());
                                    }
                                }).start();
                            }
                        }
                    }
                }
            }
        }, 10000, 1000);
    }
}
