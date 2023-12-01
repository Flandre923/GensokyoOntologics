package github.thelawf.gensokyoontology.common.block;

import github.thelawf.gensokyoontology.common.tileentity.DisposableSpawnerTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
/**
 * 总的来说,DisposableSpawnerBlock是一个可以通过刷怪蛋来设置刷出生物的刷怪笼。
 * 它与一个TileEntity绑定以控制刷怪类型。
 * 玩家可以通过使用刷怪蛋来定义它刷出的生物。12312312
 */
public class DisposableSpawnerBlock extends Block {
    public DisposableSpawnerBlock() {
        super(Properties.from(Blocks.SPAWNER));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(@NotNull BlockState state, @NotNull World worldIn, @NotNull BlockPos pos, PlayerEntity player, @NotNull Hand handIn, @NotNull BlockRayTraceResult hit) {
        if (player.getHeldItem(handIn).getItem() instanceof ForgeSpawnEggItem) {
            ForgeSpawnEggItem spawnEgg = (ForgeSpawnEggItem) player.getHeldItem(handIn).getItem();
            DisposableSpawnerTile spawnerTile = (DisposableSpawnerTile) worldIn.getTileEntity(pos);

            if (spawnerTile == null) return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
            spawnerTile.setEntityType(spawnEgg.getType(player.getHeldItem(handIn).getTag()));
            player.getHeldItem(handIn).shrink(1);
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new DisposableSpawnerTile();
    }
}
