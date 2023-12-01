package github.thelawf.gensokyoontology.common.entity.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

// 碰撞伤害实体
public abstract class CollideDamageEntity extends Entity {
    public CollideDamageEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }
}
