package io.github.wafarm.calculator.command;

import com.mojang.brigadier.CommandDispatcher;
import io.github.wafarm.calculator.command.argument.ExpressionArgumentType;
import io.github.wafarm.calculator.interpreter.Interpreter;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;

public class CalculateCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
                ClientCommandManager.literal("calc").then(ClientCommandManager.argument("expression", ExpressionArgumentType.expression()).executes(context -> {
                    String expressionString = ExpressionArgumentType.getExpression("expression", context);
                    try {
                        String result = Interpreter.Companion.interpret(expressionString);
                        context.getSource().sendFeedback(Component.literal(result));
                    } catch (Error e) {
                        context.getSource().sendError(Component.literal(e.getMessage()));
                    }
                    return 0;
                }))
        );
    }
}
