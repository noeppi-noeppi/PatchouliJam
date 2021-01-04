package io.github.noeppi_noeppi.mods.patchoulijam;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.ModList;

import static net.minecraft.command.Commands.literal;

public class JamCommands {

    public static void registerCommands(RegisterCommandsEvent event) {
        LiteralArgumentBuilder<CommandSource> jamCommand = literal("jam")
                .requires((player) -> player.hasPermissionLevel(2));
                
        if (ModList.get().isLoaded("fancy_snowy_weather")) {
            jamCommand.then(
                    WeatherCommands.getCommandPart()
            );
        }
        
        if (ModList.get().isLoaded("naughtyornice")) {
            jamCommand.then(
                    NicenessCommands.getCommandPart()
            );
        }
        
        event.getDispatcher().register(jamCommand);
    }
}
