package com.diglis.slopMaster;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class SlopTimer {

    File f = new File("bottimer.txt");
    public void run() {
        RuntimeMXBean uptime = ManagementFactory.getRuntimeMXBean();
        boolean isMinute = false;
        if((uptime.getUptime() / 1000) % 60 == 0 && isMinute) {
            System.out.println((uptime.getUptime() / 1000) / 60 + " minute(s)");
            isMinute = true;
        } else if((uptime.getUptime() / 1000) % 60 != 0 && !isMinute) {
            System.out.println(uptime.getUptime() / 1000 + " second(s)");
        }
    }

}
