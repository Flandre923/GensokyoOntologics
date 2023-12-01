package github.thelawf.gensokyoontology.common.entity.monster;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public abstract class YoukaiEntity extends RetreatableEntity {

    /**
     * 是否被退治
     */
    protected boolean isRetreated = false;
    /**
     * 好感度
     */
    protected int favorability = 0;

    @OnlyIn(Dist.CLIENT)
    private Animation animation = Animation.IDLE;
    public static final DataParameter<Boolean> DATA_RETREATED = EntityDataManager.createKey(
            YoukaiEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> DATA_FAVORABILITY = EntityDataManager.createKey(YoukaiEntity.class, DataSerializers.VARINT);

    protected YoukaiEntity(EntityType<? extends TameableEntity> type, World worldIn) {
        super(type, worldIn);
    }
//方法用于注册实体的数据参数，包括isRetreated和favorability。
    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_RETREATED, this.isRetreated);
        this.dataManager.register(DATA_FAVORABILITY, this.favorability);
    }
//方法在实体死亡时被调用，如果妖怪没有被退治，则重置其健康值，并将其所有者设置为导致死亡的实体（如果是玩家），然后将其设置为已退治状态。
    @Override
    public void onDeath(@NotNull DamageSource cause) {
        if (!this.isRetreated) {
            this.setHealth(this.getMaxHealth());
            this.setOwnerId(cause.getTrueSource() instanceof PlayerEntity && cause.getTrueSource() == null ?
                    cause.getTrueSource().getUniqueID() : null);
            if (this.getOwnerId() != null) this.setRetreated(true);
            return;
        }
        super.onDeath(cause);
    }
// 方法用于获取和设置妖怪的好感度。
    public int getFavorability() {
        return this.dataManager.get(DATA_FAVORABILITY);
    }

    public void setFavorability(int favorabilityIn) {
        this.dataManager.set(DATA_FAVORABILITY, favorabilityIn);
    }
// setRetreated()和isRetreated()方法用于设置和判断妖怪是否被退治。
    public void setRetreated(boolean isRetreated) {
        this.dataManager.set(DATA_RETREATED, isRetreated);
    }

    public boolean isRetreated() {
        return this.dataManager.get(DATA_RETREATED);
    }
    // setAnimation()和getAnimation()方法用于设置和获取妖怪的动画（仅在客户端使用）。
    @OnlyIn(Dist.CLIENT)
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    @OnlyIn(Dist.CLIENT)
    public Animation getAnimation() {
        return animation;
    }


}
