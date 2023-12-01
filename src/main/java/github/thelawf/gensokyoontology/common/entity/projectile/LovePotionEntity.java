package github.thelawf.gensokyoontology.common.entity.projectile;

import github.thelawf.gensokyoontology.core.init.PotionRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
//它继承自PotionEntity类，并实现了IRendersAsItem接口。
public class LovePotionEntity extends PotionEntity implements IRendersAsItem {
    //LOVE_POTION_TYPE是该实体的实体类型，用于创建和注册实体。
    public static final EntityType<LovePotionEntity> LOVE_POTION_TYPE = EntityType.Builder.<LovePotionEntity>create(
                    LovePotionEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).trackingRange(4)
            .updateInterval(10).build("love_potion_entity");

    public LovePotionEntity(EntityType<? extends PotionEntity> type, World worldIn) {
        super(type, worldIn);
    }

    private static final DataParameter<Optional<UUID>> LOVE_MASTER = EntityDataManager.createKey(
            LovePotionEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
// registerData()方法用于注册实体的数据参数。在这个类中，使用dataManager注册了一个名为LOVE_MASTER的数据参数，表示施放爱情药水的玩家的UUID。
    @Override
    protected void registerData() {
        this.dataManager.register(LOVE_MASTER, Optional.of(Objects.requireNonNull(this.getShooter()).getUniqueID()));
    }
// getItem()方法在客户端被调用，用于获取爱情药水对应的物品堆栈（ItemStack）。在这个类中，该方法返回默认的药水物品堆栈。
    @Override
    public ItemStack getItem() {
        return Items.POTION.getDefaultInstance();
    }
//onImpact()方法在实体与碰撞目标发生碰撞时被调用。在这个类中，根据药水的类型和效果，对碰撞目标应用相应的效果。
    @Override
    public void onImpact(@NotNull RayTraceResult result) {
        ItemStack stack = this.getItem();
        Potion potion = PotionUtils.getPotionFromItem(stack);
        List<EffectInstance> list = PotionUtils.getEffectsFromStack(stack);
        boolean flag = potion == PotionRegistry.LOVE_POTION.get();
        if (flag) {

        }
    }

    /*
    原版应用药水效果的方法叫做 func_213888_a
    applyEffect()方法用于将药水效果应用于附近的生物实体。该方法遍历附近的实体，并根据距离和效果的属性，对实体应用相应的效果。
     */
    public void applyEffect(List<EffectInstance> listIn, @Nullable Entity entityIn) {
        AxisAlignedBB axisalignedbb = this.getBoundingBox().grow(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, axisalignedbb);
        if (!list.isEmpty()) {
            for (LivingEntity livingentity : list) {
                if (livingentity.canBeHitWithPotion()) {
                    double d0 = this.getDistanceSq(livingentity);
                    if (d0 < 16.0D) {
                        double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
                        if (livingentity == listIn) {
                            d1 = 1.0D;
                        }

                        for (EffectInstance effectinstance : listIn) {
                            Effect effect = effectinstance.getPotion();
                            if (effect.isInstant()) {
                                effect.affectEntity(this, this.getShooter(), livingentity, effectinstance.getAmplifier(), d1);
                            } else {
                                int i = (int) (d1 * (double) effectinstance.getDuration() + 0.5D);
                                if (i > 20) {
                                    livingentity.addPotionEffect(new EffectInstance(effect, i, effectinstance.getAmplifier(), effectinstance.isAmbient(), effectinstance.doesShowParticles()));
                                }
                            }
                        }
                    }
                }
            }
        }

    }
// getShooter()方法返回施放爱情药水的实体，即投掷者。
    @Nullable
    @Override
    public Entity getShooter() {
        return super.getShooter();
    }
}
