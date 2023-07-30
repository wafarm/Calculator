package io.github.wafarm.calculator.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;

public class ExpressionArgumentType implements ArgumentType<String> {
    public static ExpressionArgumentType expression() {
        return new ExpressionArgumentType();
    }

    public static <S> String getExpression(String name, CommandContext<S> context) {
        return context.getArgument(name, String.class);
    }

    @Override
    public String parse(StringReader reader) {
        int argBeginning = reader.getCursor();
        while (reader.canRead()) {
            reader.skip();
        }
        return reader.getString().substring(argBeginning);
    }
}
