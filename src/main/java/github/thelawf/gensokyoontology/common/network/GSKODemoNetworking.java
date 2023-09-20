package github.thelawf.gensokyoontology.common.network;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.network.packet.CountdownStartPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class GSKODemoNetworking {
    public static SimpleChannel KICK_PLAYER;
    public static final String VERSION_KICK_PLAYER = "1.0";
    private static int ID = 0;

    public static int nextId() {
        return ID++;
    }

    public static void registerMessage() {
        KICK_PLAYER = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(GensokyoOntology.MODID, "kick_player"),
                () -> VERSION_KICK_PLAYER,
                (version) -> version.equals(VERSION_KICK_PLAYER),
                (version) -> version.equals(VERSION_KICK_PLAYER));

        KICK_PLAYER.messageBuilder(CountdownStartPacket.class, nextId())
                .encoder(CountdownStartPacket::toBytes)
                .decoder(CountdownStartPacket::new)
                .consumer(CountdownStartPacket::handle)
                .add();

    }
}
