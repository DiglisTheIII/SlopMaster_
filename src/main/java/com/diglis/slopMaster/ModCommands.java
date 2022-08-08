package com.diglis.slopMaster;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ModCommands extends ListenerAdapter {

    public File f = new File("C:/Users/Mason Wells/Desktop/Misc/bot shit/log.txt");

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");
        String prefix = "s$";
        boolean isAdmin = event.getMember().hasPermission(Permission.ADMINISTRATOR);
        CommandShortcuts scts = new CommandShortcuts();

        if(args[0].equalsIgnoreCase(prefix + "warn")) {
            if(isAdmin) {
                List<Member> ments = event.getMessage().getMentionedMembers();
                ArrayList<String> warning = new ArrayList<String>();
                if(args.length > 2) {
                    for(int i = 2; i < args.length; i++) {
                        warning.add(args[i]);
                    }
                    String msg = warning.stream().map(Object::toString).collect(Collectors.joining(" "));
                    //ments.get(0).getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(msg)).queue();
                    scts.sendDM(event, ments.get(0).getUser(), msg);
                } else {
                    ments.get(0).getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage("watch it")).queue();
                }
            } else {
                scts.sendMessage(event, "You can't do that idiot", true);
            }
        }

        if(args[0].equalsIgnoreCase("trolenames") && event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            Member self = event.getMember();
            Random randNum = new Random();
            Set<Integer>set = new LinkedHashSet<Integer>();
            while (set.size() < event.getGuild().getMemberCount()) {
                set.add(randNum.nextInt(event.getGuild().getMemberCount()) + 1);
            }
            ArrayList<Integer> arr = new ArrayList<Integer>();
            for(Iterator<Integer> it = set.iterator(); it.hasNext();) {
                arr.add(it.next());
            }
            int i = 0;
            for(Member member : event.getGuild().getMembers()) {
                if(self.canInteract(member) && i < arr.size()) {
                    event.getGuild().modifyNickname(member, "cock lover 3000").queue();
                    i++;
                }
            }
        } else if(args[0].equalsIgnoreCase("trolenames") && event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            scts.sendMessage(event, "You cannot do that idiot", true);
        }

        if(args[0].equalsIgnoreCase(prefix + "ban")) {
            List<Member> mentions = event.getMessage().getMentionedMembers(); //gets mentioned members from the command
            if(event.getMember().isOwner() || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                event.getGuild().ban(mentions.get(0), Integer.parseInt(args[2])).queue(); //setting time argument as an int to for ban timing
                scts.sendMessage(event, mentions.get(0).getAsMention() + " has been banned", false);
            } else if(!event.getMember().isOwner() || !event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                scts.sendMessage(event, "You cannot do that", true);
            }
        }


        if(args[0].equalsIgnoreCase(prefix + "nuke")) {
            if(event.getMember().isOwner() || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                scts.deleteMessage(event);
                scts.sendMessage(event, "This channel will be cleared in 10 seconds", false);
                event.getTextChannel().createCopy().queue();
                event.getChannel().delete().queueAfter(10, TimeUnit.SECONDS);

            } else if(!event.getMember().isOwner() || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                scts.sendMessage(event, "You are not an admin, get fucked", true);
            }
            try {
                PrintWriter pw = new PrintWriter(new FileWriter(f, true));
                pw.println(event.getChannel().getName() + " has been cleared");
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(args[0].equalsIgnoreCase(prefix + "kick") && event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            if(args.length < 3) {
                event.getMessage().getMentionedMembers().get(0).kick("").queue();
            } else if(args.length == 3) {
                event.getMessage().getMentionedMembers().get(0).kick(args[2]).queue();
            }
            event.getChannel().sendMessage(event.getMessage().getMentionedMembers().get(0).getAsMention() + " has done bingus").queue();
        }

        if(args[0].equalsIgnoreCase(prefix + "silence")) {
            List<Member> mentionedMember = event.getMessage().getMentionedMembers();
            if(event.getMember().isOwner() || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                event.getGuild().addRoleToMember(mentionedMember.get(0), event.getGuild().getRoleById("932112631546916884")).queue();
                scts.sendMessage(event, mentionedMember.get(0).getAsMention() + " has been silenced", false);
            } else if(event.getMember().isOwner() || !event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                scts.sendMessage(event, "you cant do that", false);
            }
        }

        if(args[0].equalsIgnoreCase(prefix + "unsilence")) {
            Member member = event.getMessage().getMentionedMembers().get(0);
            User author = event.getMessage().getAuthor();
            if(!event.getGuild().getMember(author).hasPermission(Permission.ADMINISTRATOR)) {
                scts.sendMessage(event, "You are not an administrator idiot!", false);
                isAdmin = false;
            }
            if(isAdmin) {
                event.getGuild().removeRoleFromMember(member, event.getGuild().getRoleById("932112631546916884")).queue();
                scts.sendMessage(event, member.getAsMention() + " Has been unmuted :(", false);
            }
        }

    }

}
