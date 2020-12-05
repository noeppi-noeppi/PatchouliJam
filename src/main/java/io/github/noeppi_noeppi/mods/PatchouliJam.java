package io.github.noeppi_noeppi.mods;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import vazkii.patchouli.client.book.ClientBookRegistry;

@Mod("patchouli_jam")
public class PatchouliJam {

    public static final String MODID = "patchouli_jam";

    public PatchouliJam() {
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
            return null;
        });
    }

    @OnlyIn(Dist.CLIENT)
    private void clientSetup(FMLClientSetupEvent event) {
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(MODID, "special_crafting"), PageSpecialCrafting.class);
        if (ModList.get().isLoaded("next_christmas")) {
            //noinspection TrivialFunctionalExpressionUsage
            ((Runnable) () -> {
                ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(MODID, "oven"), PageOven.class);
                ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(MODID, "grain_mill"), PageGrainMill.class);
            }).run();
        }
    }
}

