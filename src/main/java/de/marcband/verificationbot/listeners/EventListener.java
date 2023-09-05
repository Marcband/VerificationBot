package de.marcband.verificationbot.listeners;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EventListener extends ListenerAdapter {

    private final ArrayList<String> verifiedUsers;
    private final String roleIDverify;
    private final String roleID3212;

    public EventListener(String roleIDverify, String roleID3212) {
        this.roleIDverify = roleIDverify;
        this.roleID3212 = roleID3212;
        verifiedUsers = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/discordIDs.csv"))) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                verifiedUsers.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        String id = event.getUser().getId();
        System.out.println(event.getUser().getName());
        System.out.println(event.getGuild().getRoleById(roleIDverify).getName());

        if(verifiedUsers.contains(id)) {
            User user = event.getUser();
            Role roleVerify = event.getGuild().getRoleById(roleIDverify);
            Role role3212 = event.getGuild().getRoleById(roleID3212);
            event.getGuild().addRoleToMember(user, roleVerify).queue();
            event.getGuild().addRoleToMember(user, role3212).queue();
            System.out.println("Added Roles to: " + user.getName());
        }

    }
}
