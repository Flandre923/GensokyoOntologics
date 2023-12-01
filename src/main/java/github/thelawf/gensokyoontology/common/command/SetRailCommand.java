package github.thelawf.gensokyoontology.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.command.impl.FillCommand;
import net.minecraft.command.impl.SetBlockCommand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class SetRailCommand {
//register方法用于在命令调度器中注册这个命令
//命令接受两个BlockPos类型的参数pos_a和pos_b
//当命令执行时,会调用setRail方法,传入命令源和两个坐标点
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("setrail").requires((literal) -> {
            return literal.hasPermissionLevel(2);
        }).then(Commands.argument("pos_a", BlockPosArgument.blockPos()).then(
                Commands.argument("pos_b", BlockPosArgument.blockPos()).executes((context -> {
                    return setRail(context.getSource(), BlockPosArgument.getBlockPos(context, "pos_a"),
                            BlockPosArgument.getBlockPos(context, "pos_b"));
                })))));
    }
    /*
    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {

        return 0;
    }
     */
//setRail方法中会执行设置铁轨的相关逻辑
    public static int setRail(CommandSource source, BlockPos posA, BlockPos posB) {
        return 0;
    }
}
