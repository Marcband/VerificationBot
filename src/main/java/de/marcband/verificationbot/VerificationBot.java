package de.marcband.verificationbot;

import de.marcband.verificationbot.listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class VerificationBot {

    private final ShardManager shardManager;
    private final Dotenv config;

    public VerificationBot() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("Verifying 3212 Members"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MODERATION);
        shardManager = builder.build();

        //Register Managers
        shardManager.addEventListener(new EventListener(config.get("ROLEIDVERIFY"), config.get("ROLEID3212")));
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public Dotenv getConfig() {
        return config;
    }

    public static void main(String[] args) {
        try {
            VerificationBot bot = new VerificationBot();
        } catch (LoginException e) {
            System.out.println("ERROR: Bot Token is invalid!");
        }

    }
}
