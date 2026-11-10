package itamio.voidrescue;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Void Rescue - never die in the void.
 */
@Mod(modid = VoidRescueMod.MOD_ID, name = VoidRescueMod.MOD_NAME, version = VoidRescueMod.VERSION)
public class VoidRescueMod {

    public static final String MOD_ID = "voidrescue";
    public static final String MOD_NAME = "Void Rescue";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server == null) {
            return;
        }
        for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
            if (player.isCreativeMode() || player.isSpectator()) {
                continue;
            }
            if (player.posY < 0.0D) {
                BlockPos bed = player.getBedLocation();
                if (bed != null) {
                    player.setPositionAndUpdate(bed.getX() + 0.5D, bed.getY() + 1.0D, bed.getZ() + 0.5D);
                } else {
                    BlockPos spawn = player.world.getSpawnPoint();
                    player.setPositionAndUpdate(spawn.getX() + 0.5D, spawn.getY() + 1.0D, spawn.getZ() + 0.5D);
                }
            }
        }
    }
}
