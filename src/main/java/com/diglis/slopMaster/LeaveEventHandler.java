package com.diglis.slopMaster;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class LeaveEventHandler extends ListenerAdapter {

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        String leave = event.getMember().getAsMention();
        EmbedBuilder bld = new EmbedBuilder();
        bld.setTitle("Idiot!");
        bld.setDescription(leave);
        bld.setImage("https://cdn.discordapp.com/attachments/948538571999035392/958459101111787550/Screenshot_374.png");
        Guild g = event.getGuild();
        boolean isMyServer = g.getName().equals("Femboy Sorority");
        boolean isZeenServer = g.getName().equals("zeenith's zoo");
        if(isMyServer) {
            TextChannel tc = g.getTextChannelById("931616914227208203");
            assert tc != null;
            tc.sendMessageEmbeds(bld.build()).queue();

        } else if(isZeenServer) {
            TextChannel tc = g.getTextChannelById("939768536295952507");
            assert tc != null;
            tc.sendMessageEmbeds(bld.build()).queue();
        }

    }

}
