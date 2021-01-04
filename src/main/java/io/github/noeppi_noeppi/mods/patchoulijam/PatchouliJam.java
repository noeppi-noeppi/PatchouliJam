package io.github.noeppi_noeppi.mods.patchoulijam;

import io.github.noeppi_noeppi.mods.patchoulijam.config.ClientConfig;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.TrappedChestTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import vazkii.patchouli.client.book.ClientBookRegistry;
import vazkii.patchouli.common.item.PatchouliItems;


@Mod("patchouli_jam")
public class PatchouliJam {

    public static final String MODID = "patchouli_jam";
    public static final ItemGroup TAB = new ItemGroup(MODID) {
        
        @Override
        public ItemStack createIcon() {
            ItemStack stack = new ItemStack(PatchouliItems.book);
            CompoundNBT nbt = stack.getOrCreateTag();
            nbt.putString("patchouli:book", "patchouli_jam:christmas_guide");
            stack.setTag(nbt);
            return stack;
        }
    };

    public PatchouliJam() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG);
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
            return null;
        });
        MinecraftForge.EVENT_BUS.addListener(JamCommands::registerCommands);
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
        if (ClientConfig.alwaysChristmas.get()) {
            TileEntityRenderer<?> chestRenderer = TileEntityRendererDispatcher.instance.getRenderer(new ChestTileEntity());
            if (chestRenderer instanceof ChestTileEntityRenderer<?>) {
                ((ChestTileEntityRenderer<?>) chestRenderer).isChristmas = true;
            }
            chestRenderer = TileEntityRendererDispatcher.instance.getRenderer(new TrappedChestTileEntity());
            if (chestRenderer instanceof ChestTileEntityRenderer<?>) {
                ((ChestTileEntityRenderer<?>) chestRenderer).isChristmas = true;
            }
            chestRenderer = TileEntityRendererDispatcher.instance.getRenderer(new EnderChestTileEntity());
            if (chestRenderer instanceof ChestTileEntityRenderer<?>) {
                ((ChestTileEntityRenderer<?>) chestRenderer).isChristmas = true;
            }
        }
    }
}

