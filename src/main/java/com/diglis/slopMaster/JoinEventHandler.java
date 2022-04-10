package com.diglis.slopMaster;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class JoinEventHandler extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        EmbedBuilder joinedUser = new EmbedBuilder();
        joinedUser.setTitle("✉ new clope");
        Guild g = event.getGuild();
        boolean isMainServer = g.getName().equals("Femboy Sorority");
        boolean isSecondServer = g.getName().equals("zeenith's zoo");
        joinedUser.setColor(Color.BLUE);
        if(isMainServer) {
            TextChannel channel = g.getTextChannelById("931616914227208203");
            joinedUser.setDescription(event.getMember().getAsMention());
            assert channel != null;
            channel.sendMessageEmbeds(joinedUser.build()).queue();

        } else if(isSecondServer) {
            TextChannel channel = g.getTextChannelById("939768536295952507");
            joinedUser.setDescription(event.getMember().getAsMention());
            assert channel != null;
            channel.sendMessageEmbeds(joinedUser.build()).queue();
        }

    }

}
