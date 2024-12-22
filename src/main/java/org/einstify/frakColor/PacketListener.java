package org.einstify.frakColor;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class PacketListener implements com.github.retrooper.packetevents.event.PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPlayer() instanceof Player && event.getPacketType() == PacketType.Play.Server.PLAYER_INFO) {
            //String receiverFrak = playerManager.getPlayerData(event.getPlayer).getFaction();
            try {
                WrapperPlayServerPlayerInfo playerInfo = new WrapperPlayServerPlayerInfo(event);
                List<WrapperPlayServerPlayerInfo.PlayerData> dataList = new ArrayList<>();
                if (playerInfo.getPlayerDataList() != null && !playerInfo.getPlayerDataList().isEmpty()) {
                    for (WrapperPlayServerPlayerInfo.PlayerData data : playerInfo.getPlayerDataList()) {
                        if (data.getDisplayName() != null) {
                            //if(playerManager.getPlayerData(Bukkit.getPlayer(String.valueOf(data.getDisplayName()))).getFaction().equals(receiverFrak)) {
                                data.setDisplayName(Component.text(data.getDisplayName().toString()).color(NamedTextColor.BLUE));
                                dataList.add(data);
                            //}
                        }
                    }
                }
                playerInfo.setPlayerDataList(dataList);
                event.getUser().sendPacket(playerInfo);
            } catch (ArrayIndexOutOfBoundsException e) {
                StringWriter stack = new StringWriter();
                e.printStackTrace(new PrintWriter(stack));
                Bukkit.getLogger().info("Fehler beim Nametag laden: " + e.getMessage() + " " + stack.toString());
            }
        }
    }
}
