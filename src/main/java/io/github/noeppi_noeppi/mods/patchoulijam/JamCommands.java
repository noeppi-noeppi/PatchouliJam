package io.github.noeppi_noeppi.mods.patchoulijam;

import com.tfc.fancysnowyweather.FancySnowyWeather;
import com.tfc.fancysnowyweather.WeatherPacket;
import com.tfc.fancysnowyweather.WeatherSaveData;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import static net.minecraft.command.Commands.literal;

public class JamCommands {

    public static final int WEIGHT_LIGHT = 0;
    public static final int WEIGHT_HEAVY = 1;

    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(literal("jam").requires((player) -> player.hasPermissionLevel(2))
                .then(literal("weather")
                        .then(literal("clear").executes(cmd -> {
                            ServerWorld world = cmd.getSource().getWorld();
                            world.func_241113_a_(6000, 0, false, false);
                            WeatherSaveData weatherSaveData = WeatherSaveData.get(world);
                            weatherSaveData.IS_ACTIVE = false;
                            weatherSaveData.DURATION = 6000;
                            weatherSaveData.markDirty();
                            syncFancyWeather(world, weatherSaveData);
                            cmd.getSource().sendFeedback(new TranslationTextComponent("patchouli_jam.command.weather.set", "clear"), true);
                            return 0;
                        }))
                        .then(literal("rain").executes(cmd -> {
                            ServerWorld world = cmd.getSource().getWorld();
                            world.func_241113_a_(0, 6000, true, false);
                            WeatherSaveData weatherSaveData = WeatherSaveData.get(world);
                            weatherSaveData.IS_ACTIVE = false;
                            weatherSaveData.DURATION = 6000;
                            weatherSaveData.markDirty();
                            syncFancyWeather(world, weatherSaveData);
                            cmd.getSource().sendFeedback(new TranslationTextComponent("patchouli_jam.command.weather.set", "clear"), true);
                            return 0;
                        }))
                        .then(literal("thunder").executes(cmd -> {
                            ServerWorld world = cmd.getSource().getWorld();
                            world.func_241113_a_(0, 6000, true, true);
                            WeatherSaveData weatherSaveData = WeatherSaveData.get(world);
                            weatherSaveData.IS_ACTIVE = false;
                            weatherSaveData.DURATION = 6000;
                            weatherSaveData.markDirty();
                            syncFancyWeather(world, weatherSaveData);
                            cmd.getSource().sendFeedback(new TranslationTextComponent("patchouli_jam.command.weather.set", "clear"), true);
                            return 0;
                        }))
                        .then(literal("light_snow").executes(cmd -> {
                            ServerWorld world = cmd.getSource().getWorld();
                            world.func_241113_a_(6000, 0, false, false);
                            WeatherSaveData weatherSaveData = WeatherSaveData.get(world);
                            weatherSaveData.IS_ACTIVE = true;
                            weatherSaveData.DURATION = 6000;
                            weatherSaveData.WEIGHT = WEIGHT_LIGHT;
                            weatherSaveData.markDirty();
                            syncFancyWeather(world, weatherSaveData);
                            cmd.getSource().sendFeedback(new TranslationTextComponent("patchouli_jam.command.weather.set", "light snow"), true);
                            return 0;
                        }))
                        .then(literal("heavy_snow").executes(cmd -> {
                            ServerWorld world = cmd.getSource().getWorld();
                            world.func_241113_a_(6000, 0, false, false);
                            WeatherSaveData weatherSaveData = WeatherSaveData.get(world);
                            weatherSaveData.IS_ACTIVE = true;
                            weatherSaveData.DURATION = 6000;
                            weatherSaveData.WEIGHT = WEIGHT_HEAVY;
                            weatherSaveData.markDirty();
                            syncFancyWeather(world, weatherSaveData);
                            cmd.getSource().sendFeedback(new TranslationTextComponent("patchouli_jam.command.weather.set", "heavy snow"), true);
                            return 0;
                        }))
                ));
    }

    private static void syncFancyWeather(ServerWorld world, WeatherSaveData weatherSaveData) {
        world.getPlayers().forEach((player) -> FancySnowyWeather.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new WeatherPacket(weatherSaveData.IS_ACTIVE, weatherSaveData.WEIGHT)));
    }
}
