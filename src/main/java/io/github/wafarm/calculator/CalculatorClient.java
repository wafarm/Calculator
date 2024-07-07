package io.github.wafarm.calculator;

import io.github.wafarm.calculator.command.CalculateCommand;
import io.github.wafarm.calculator.command.argument.ExpressionArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.resources.ResourceLocation;

public class CalculatorClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //noinspection CodeBlock2Expr
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            CalculateCommand.register(dispatcher);
        });
        ArgumentTypeRegistry.registerArgumentType(ResourceLocation.fromNamespaceAndPath("calculator", "expression"), ExpressionArgumentType.class, SingletonArgumentInfo.contextFree(ExpressionArgumentType::expression));
    }
}
