package github.thelawf.gensokyoontology.common.entity.projectile;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.world.World;
//终极火花
public class MasterSparkEntity extends ThrowableEntity {
    public final int LIFE_SPAN = 200;
    public static final EntityType<MasterSparkEntity> MASTER_SPARK = EntityType.Builder.<MasterSparkEntity>create(
                    MasterSparkEntity::new, EntityClassification.MISC).size(2F, 25F).trackingRange(4)
            .updateInterval(2).build("master_spark_entity");


    protected MasterSparkEntity(EntityType<? extends ThrowableEntity> type, LivingEntity livingEntityIn, World worldIn) {
        super(type, livingEntityIn, worldIn);
    }

    public MasterSparkEntity(EntityType<? extends ThrowableEntity> type, World world) {
        super(MASTER_SPARK, world);

    }
// tick()方法在每个游戏刻钟中被调用，用于更新实体的状态。在这个类中，它调用了父类的tick()方法，并检查实体的存在时间是否超过了预设的寿命。如果超过了寿命，则将实体移除。
    @Override
    public void tick() {
        super.tick();
        if (ticksExisted >= this.LIFE_SPAN) {
            this.remove();
        }

    }

    @Override
    protected void registerData() {

    }
}
