package xluke44.petward;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(
        modid = "petward",
        name = "PetWard",
        version = "1.0.1",
        acceptedMinecraftVersions = "[1.12.2]",
        acceptableRemoteVersions = "*"
)
public class PetWard {

    public PetWard() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntityLiving().world.isRemote) {
            return;
        }

        DamageSource source = event.getSource();
        if (source.isExplosion()) {
            return;
        }

        Entity trueSource = source.getTrueSource();
        if (!(trueSource instanceof EntityPlayer)) {
            return;
        }

        if (isProtectedTamedAnimal(event.getEntityLiving())) {
            event.setCanceled(true);
        }
    }

    private boolean isProtectedTamedAnimal(net.minecraft.entity.EntityLivingBase entity) {
        if (entity instanceof EntityTameable) {
            return ((EntityTameable) entity).isTamed();
        }

        return entity instanceof AbstractHorse && ((AbstractHorse) entity).isTame();
    }
}
