package itamio.voidrescue;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

/**
 * Void Rescue - never die in the void.
 *
 * <p>Every server tick, any survival player below the bottom of the world is
 * sent back to their respawn point (bed / respawn anchor) or, when they have
 * none, to the world spawn. Creative and spectator players are ignored.</p>
 */
public class VoidRescueMod implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register(VoidRescueMod::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
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

    private static void rescue(MinecraftServer server, ServerPlayer player, ServerLevel level) {
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
