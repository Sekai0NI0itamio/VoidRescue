package itamio.voidrescue;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/**
 * Void Rescue - never die in the void.
 */
@Mod(VoidRescueNeoForgeMod.MOD_ID)
public class VoidRescueNeoForgeMod {

    public static final String MOD_ID = "voidrescue";

    public VoidRescueNeoForgeMod(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        MinecraftServer server = event.getServer();
        if (server == null) {
            return;
        }
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (player.isCreative() || player.isSpectator()) {
                continue;
            }
            ServerLevel level = (ServerLevel) player.level();
            if (player.getY() < level.getMinBuildHeight()) {
                rescue(server, player, level);
            }
        }
    }

    private void rescue(MinecraftServer server, ServerPlayer player, ServerLevel level) {
        BlockPos respawnPos = player.getRespawnPosition();
        ResourceKey<Level> respawnDim = player.getRespawnDimension();
        if (respawnPos != null && respawnDim != null) {
            ServerLevel target = server.getLevel(respawnDim);
            if (target != null) {
                player.teleportTo(target, respawnPos.getX() + 0.5D, respawnPos.getY() + 1.0D,
                        respawnPos.getZ() + 0.5D, player.getRespawnAngle(), player.getXRot());
                return;
            }
        }
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) {
            overworld = level;
        }
        BlockPos spawn = overworld.getSharedSpawnPos();
        player.teleportTo(spawn.getX() + 0.5D, spawn.getY() + 1.0D, spawn.getZ() + 0.5D,
                player.getYRot(), player.getXRot());
    }
}
