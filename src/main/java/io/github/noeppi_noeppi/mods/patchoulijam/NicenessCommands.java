package io.github.noeppi_noeppi.mods.patchoulijam;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.championash5357.naughtyornice.api.capability.CapabilityInstances;
import io.github.championash5357.naughtyornice.api.capability.INiceness;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg;
import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;
import static net.minecraft.command.arguments.EntityArgument.players;

public class NicenessCommands {
    
    public static LiteralArgumentBuilder<CommandSource> getCommandPart() {
        return literal("niceness")
                .then(literal("get").then(argument("players", players()).executes(cs -> {
                    List<ServerPlayerEntity> players = cs.getArgument("players", EntitySelector.class).selectPlayers(cs.getSource());
                    cs.getSource().sendFeedback(new TranslationTextComponent("patchouli_jam.command.niceness.query", Integer.toString(players.size())), false);
                    players.forEach(player -> {
                        Optional<INiceness> niceness = player.getCapability(CapabilityInstances.NICENESS_CAPABILITY).resolve();
                        double n = 0;
                        if (niceness.isPresent()) { n = niceness.get().getNiceness(); }
                        BigDecimal bd = new BigDecimal(n);
                        bd = bd.setScale(2, RoundingMode.HALF_UP);
                        cs.getSource().sendFeedback(new TranslationTextComponent("patchouli_jam.command.niceness.player").append(player.getDisplayName()).append(new StringTextComponent(": " + bd.toPlainString())), false);
                    });
                    return 0;
                })))
                .then(literal("set").then(argument("players", players()).then(argument("amount", doubleArg(-100, 100)).executes(cs -> {
                    List<ServerPlayerEntity> players = cs.getArgument("players", EntitySelector.class).selectPlayers(cs.getSource());
                    double amount = MathHelper.clamp(cs.getArgument("amount", Double.class), -100, 100);
                    cs.getSource().sendFeedback(new TranslationTextComponent("patchouli_jam.command.niceness.set", Integer.toString(players.size())), false);
                    players.forEach(player -> {
                        Optional<INiceness> niceness = player.getCapability(CapabilityInstances.NICENESS_CAPABILITY).resolve();
                        niceness.ifPresent(n -> n.setNiceness(amount, true));
                    });
                    return 0;
                }))))
                .then(literal("add").then(argument("players", players()).then(argument("amount", doubleArg()).executes(cs -> {
                    List<ServerPlayerEntity> players = cs.getArgument("players", EntitySelector.class).selectPlayers(cs.getSource());
                    double amount = cs.getArgument("amount", Double.class);
                    cs.getSource().sendFeedback(new TranslationTextComponent("patchouli_jam.command.niceness.set", Integer.toString(players.size())), false);
                    players.forEach(player -> {
                        Optional<INiceness> niceness = player.getCapability(CapabilityInstances.NICENESS_CAPABILITY).resolve();
                        niceness.ifPresent(n -> {
                            double old = n.getNiceness();
                            n.setNiceness(MathHelper.clamp(old + amount, -100, 100), true);
                        });
                    });
                    return 0;
                }))));
    }
}
