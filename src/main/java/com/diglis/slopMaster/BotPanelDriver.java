package com.diglis.slopMaster;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class BotPanelDriver {

    public JDA jda;

    public static boolean isOn = false;
    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
        startupForMain();
        File f = new File("uptime.txt");
        printTimeToFile(f, false, 0);
        @SuppressWarnings("unused")
        RuntimeMXBean uptime = ManagementFactory.getRuntimeMXBean();
        boolean isRunning = true;
        while(isRunning) {
            clearConsole();
            SlopTimer timer = new SlopTimer();
            long mil = System.currentTimeMillis();
            timer.run();
            Thread.sleep(1000 - mil % 1000);
        }
    }

    public JDA startup() throws IOException, LoginException {
        String token = Files.readAllLines(Paths.get("C:/Users/mmmmm/Desktop/botgifs/token.txt")).get(0);
        //Caches members, messages, and builds the bot for use
        return this.jda = JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_WEBHOOKS)
                .disableCache(CacheFlag.EMOTE)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_WEBHOOKS)
                .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.ONLINE_STATUS, CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.ROLE_TAGS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new Commands(), new ModCommands(), new JoinEventHandler(), new LeaveEventHandler())
                .setActivity(Activity.playing("slopping innocent people (i hate them)"))
                .setStatus(OnlineStatus.ONLINE).build();
    }


    public static JDA startupForMain() throws IOException, LoginException{
        String token = Files.readAllLines(Paths.get("C:/Users/mmmmm/Desktop/botgifs/token.txt")).get(0);
        //Caches members, messages, and builds the bot for use
        JDA jda = JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.DIRECT_MESSAGES)
                .disableCache(CacheFlag.EMOTE)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.DIRECT_MESSAGES)
                .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.ONLINE_STATUS, CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new Commands(), new ModCommands(), new JoinEventHandler(), new LeaveEventHandler())
                .setActivity(Activity.playing("slopping innocent people (i hate them)"))
                .setStatus(OnlineStatus.ONLINE).build();
        return jda;
    }

    public void shutdown() throws LoginException, IOException {
        startup().shutdownNow();
    }

    public static void clearConsole() { System.out.println(System.lineSeparator()); }

    public static void printTimeToFile(File f, boolean thread, long sec) {
        if(!thread) {
            try {
                try {
                    PrintWriter pw = new PrintWriter(new FileWriter(f, true));
                    if(java.time.LocalDate.now() == java.time.LocalDate.now()) { //Checks on the weird, theoretically possible case that the bot is started and doesnt start printing to the file as soon as the date changes
                        pw.println("Current Uptime For: "  + java.time.LocalTime.now());
                        pw.close();
                    } else {
                        pw.println("Current Uptime Start At: "  + java.time.LocalDate.now() + " " + java.time.LocalTime.now());
                        pw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch(Exception e) {
                System.out.println("");
            }
        } else if(thread) {
            try {
                try {
                    PrintWriter pw = new PrintWriter(new FileWriter(f, true));
                    if(sec >= 3600 && sec % 3600 == 0) {
                        pw.println("Current Uptime: " + sec / 3600 + " hour(s)");
                    } else if(sec % 60 == 0 && sec < 3600) {
                        pw.println("Current Uptime: "  + sec / 60 + " minute(s)");
                        pw.close();
                    } else if(sec % 60 != 0 && sec < 60){
                        pw.println("Current Uptime: " + sec + " second(s)");
                        pw.close();
                    }
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch(Exception e) {
                System.out.println("");
            }
        }
    }

}
