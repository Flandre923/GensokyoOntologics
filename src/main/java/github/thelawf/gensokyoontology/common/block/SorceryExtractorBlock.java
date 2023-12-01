package github.thelawf.gensokyoontology.common.block;

import github.thelawf.gensokyoontology.client.gui.container.SorceryExtractorContainer;
import github.thelawf.gensokyoontology.common.tileentity.SorceryExtractorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
//它是一个可以放置的方块,扩展了Block类
public class SorceryExtractorBlock extends Block {
    public SorceryExtractorBlock() {
        super(Properties.from(Blocks.ENCHANTING_TABLE));
    }

    public static final VoxelShape shape;
//定义了一个立体的VoxelShape作为方块的碰撞体积
    static {
        VoxelShape terrace = Block.makeCuboidShape(0, 0, 0, 16, 7, 16);
        VoxelShape crystal = Block.makeCuboidShape(7, 7, 9, 7, 16, 9);
        shape = VoxelShapes.or(terrace, crystal);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
//与一个SorceryExtractorTileEntity方块实体绑定
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SorceryExtractorTileEntity();
    }
//在玩家点击时打开一个名为SorceryExtractorContainer的自定义GUI容器
    @SuppressWarnings("deprecation")
    @Override
    @NotNull
    public ActionResultType onBlockActivated(@NotNull BlockState state, World worldIn, @NotNull BlockPos pos, @NotNull PlayerEntity player, @NotNull Hand handIn, @NotNull BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof SorceryExtractorTileEntity) {
                INamedContainerProvider provider = SorceryExtractorTileEntity.createContainer(worldIn, pos);
                NetworkHooks.openGui((ServerPlayerEntity) player, provider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Missing Container Provider");
            }
        }
        return ActionResultType.SUCCESS;
    }
//提供了一个方法用于创建该方块的容器提供者
    public INamedContainerProvider createContainer(World worldIn, BlockPos pos) {
        return new SimpleNamedContainerProvider(SorceryExtractorContainer::new,
                SorceryExtractorTileEntity.CONTAINER_NAME);
    }
}
