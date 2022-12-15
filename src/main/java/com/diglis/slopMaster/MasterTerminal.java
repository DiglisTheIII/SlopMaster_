package com.diglis.slopMaster;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class MasterTerminal {

    public JTextField tf;
    public JTextField ch;
    public MasterTerminal() throws LoginException, IOException, InterruptedException {
        createTextFrame();
    }

    public JDA startup() throws IOException, LoginException {
        String token = Files.readAllLines(Paths.get("C:/Users/Mason Wells/Desktop/Misc/bot shit/token.txt")).get(0);
        //Caches members, messages, and builds the bot for use
        JDA jda;
        return jda = JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_WEBHOOKS)
                .disableCache(CacheFlag.EMOTE)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_WEBHOOKS)
                .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.ONLINE_STATUS, CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.ROLE_TAGS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new Commands(), new ModCommands(), new JoinEventHandler(), new LeaveEventHandler())
                .setActivity(Activity.playing("slopping innocent people (i hate them)"))
                .setStatus(OnlineStatus.ONLINE).build();
    }
    public void createTextFrame() throws IOException, LoginException, InterruptedException {
        JFrame frame = new JFrame("Master Terminal");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        mb.add(m1);
        JMenuItem m11 = new JMenuItem("Exit");
        JMenuItem m12 = new JMenuItem("Delete");
        m1.add(m11);
        m11.addActionListener(ActionEvent -> {
           frame.setExtendedState(JFrame.ICONIFIED);
        });


        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JPanel panels = new JPanel();
        JLabel label = new JLabel("Enter Text");
        JLabel sentMessage = new JLabel();
        tf = new JTextField("(GuildID)<ChannelID>message"); // accepts upto 10 characters
        ch = new JTextField();

        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        //panel.add();
        panel.add(send);
        panel.add(reset);
        panels.add(ch);
        File f = new File("C:/Users/Mason Wells/Desktop/SlopMaster_/message.txt");
        JDA jda = startup().awaitReady();
        JTextArea ta = new JTextArea();

        reset.addActionListener(ActionListener -> {
            tf.setText(" ");
        });

        send.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                messageDeliverEvent(tf, f, jda);
            }

        });
        // Text Area at the Center


        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);
    }

    public void deleteExec() {
        File file = new File("C:/Users/Mason Wells/Desktop/SlopMaster_.jar");
        file.delete();
    }

    public void messageDeliverEvent(JTextField tf, File f, JDA jda) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(tf.getText());
            writer.close();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String st = new Scanner(f).useDelimiter("\\Z").next();
            System.out.println(ch.getText());

            try {
                jda.getGuildById(st.substring(1, st.indexOf(")"))).getTextChannelById(st.substring(st.indexOf("<") + 1, st.indexOf(">"))).sendMessage(st.substring(st.indexOf(">") + 1)).queue();
            }catch(StringIndexOutOfBoundsException ex) {
                System.out.println(ex);
                tf.setText(" ");
            }catch(NumberFormatException xe) {
                tf.setText("Invalid Arguments/Snowflake ID");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}



