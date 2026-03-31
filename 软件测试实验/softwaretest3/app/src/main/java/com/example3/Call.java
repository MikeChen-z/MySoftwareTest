package com.example3;

import java.time.ZonedDateTime;

public class Call {
    private final ZonedDateTime startTime;
    private final ZonedDateTime endTime;

    // 获取初始时间和结束时间
    public Call(final ZonedDateTime startTime, final ZonedDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // 返回开始时间
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    // 返回结束时间
    public ZonedDateTime getEndTime() {
        return endTime;
    }

}
