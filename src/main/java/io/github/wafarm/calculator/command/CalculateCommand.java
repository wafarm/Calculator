package io.github.wafarm.calculator.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import io.github.wafarm.calculator.command.argument.ExpressionArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class CalculateCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
                ClientCommandManager.literal("calc").then(ClientCommandManager.argument("expression", ExpressionArgumentType.expression()).executes(context -> {
                    String expressionString = ExpressionArgumentType.getExpression("expression", context);
                    context.getSource().sendFeedback(Text.literal("expression: " + expressionString));
                    return Command.SINGLE_SUCCESS;
                }))
        );
    }
}
