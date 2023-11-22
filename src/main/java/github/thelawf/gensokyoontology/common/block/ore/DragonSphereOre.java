package github.thelawf.gensokyoontology.common.block.ore;

import net.minecraft.block.*;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DragonSphereOre extends OreBlock {
    public DragonSphereOre() {
        super(Properties.from(Blocks.DIAMOND_ORE).setRequiresTool());
    }
}
