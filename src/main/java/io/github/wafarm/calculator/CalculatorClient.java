package io.github.wafarm.calculator;

import io.github.wafarm.calculator.command.CalculateCommand;
import io.github.wafarm.calculator.command.argument.ExpressionArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalculatorClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("calculator");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Loading Calculator Client...");
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            CalculateCommand.register(dispatcher);
        });
        ArgumentTypeRegistry.registerArgumentType(new Identifier("calculator", "expression"), ExpressionArgumentType.class, ConstantArgumentSerializer.of(ExpressionArgumentType::expression));
    }
}
