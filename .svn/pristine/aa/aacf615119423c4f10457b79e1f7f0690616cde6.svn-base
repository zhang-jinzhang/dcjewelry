package com.ceyi.project.dcjewelry.timer;

import java.util.Date;

/**
 * Created by lingh on 2017/5/6.
 */
public class TaskItem {
    private String name;
    private int interval;
    private Date previousTime;
    private boolean running;
    private Task task;

    public TaskItem(String name, int seconds, Task task) {
        this.name = name;
        this.interval = seconds * 1000;
        this.previousTime = new Date();
        this.running = false;
        this.task = task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Date getPreviousTime() {
        return previousTime;
    }

    public void setPreviousTime(Date previousTime) {
        this.previousTime = previousTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
