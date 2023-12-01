package github.thelawf.gensokyoontology.common.entity.projectile;


import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.SpellData;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
//小星弹
public class SmallStarShotEntity extends AbstractDanmakuEntity {

    public SmallStarShotEntity(EntityType<? extends ThrowableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public SmallStarShotEntity(LivingEntity throwerIn, World world, SpellData spellData) {
        super(EntityRegistry.STAR_SHOT_SMALL_ENTITY.get(), throwerIn, world, spellData);
    }

    public SmallStarShotEntity(LivingEntity throwerIn, World worldIn, DanmakuType danmakuTypeIn, DanmakuColor danmakuColorIn) {
        super(EntityRegistry.STAR_SHOT_SMALL_ENTITY.get(), throwerIn, worldIn, danmakuTypeIn, danmakuColorIn);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @NotNull
    public ItemStack getItem() {
        IItemProvider item = null;
        switch (getDanmakuColor()) {
            case RED:
            case PINK:
            case NONE:
                item = ItemRegistry.SMALL_STAR_SHOT_RED.get();
                break;
            case YELLOW:
                item = ItemRegistry.SMALL_STAR_SHOT_YELLOW.get();
                break;
            case GREEN:
                item = ItemRegistry.SMALL_STAR_SHOT_GREEN.get();
                break;
            case AQUA:
                item = ItemRegistry.SMALL_STAR_SHOT_AQUA.get();
                break;
            case BLUE:
                item = ItemRegistry.SMALL_STAR_SHOT_BLUE.get();
                break;
            case PURPLE:
                item = ItemRegistry.SMALL_STAR_SHOT_PURPLE.get();
                break;
        }

        if (item == null) {
            return ItemStack.EMPTY;
        } else {
            return new ItemStack(item);
        }
    }
}
