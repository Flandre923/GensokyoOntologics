package github.thelawf.gensokyoontology.common.entity.monster;

import github.thelawf.gensokyoontology.common.entity.ai.goal.FairyAttackGoal;
import github.thelawf.gensokyoontology.common.entity.projectile.SmallShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Random;
//精灵实体
//继承自MonsterEntity类，并实现了IFlyingAnimal接口。
@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class FairyEntity extends MonsterEntity implements IFlyingAnimal {
//最大生存刻限制
    private static final int MAX_LIVING_TICK = 3000;

    public FairyEntity(EntityType<? extends MonsterEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.getAttributeManager().createInstanceIfAbsent(Attributes.MAX_HEALTH);
    }
//注册实体的行为目标。
    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new FairyAttackGoal(this, 30, 0.3f));
        this.goalSelector.addGoal(2, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }
// /registerData()方法在此处没有具体实现。
    @Override
    protected void registerData() {
        super.registerData();
    }
// createNavigator()方法用于创建实体的导航器。
    @Override
    @NotNull
    protected PathNavigator createNavigator(@NotNull World worldIn) {
        FlyingPathNavigator navigator = new FlyingPathNavigator(this, worldIn);
        navigator.setCanOpenDoors(false);
        navigator.setCanEnterDoors(true);
        navigator.setCanSwim(true);
        return navigator;
    }
// onKillEntity()方法在杀死实体时执行的操作。
    @Override
    public void onKillEntity(@NotNull ServerWorld world, @NotNull LivingEntity killedEntity) {
        super.onKillEntity(world, killedEntity);
    }
// createSpawnPacket()方法用于创建生成实体的数据包。
    @Override
    @NotNull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
// tick()方法在每个游戏刻中执行逻辑，包括检查实体的生存刻限制并将其移除。
    @Override
    public void tick() {
        super.tick();
        if (this.ticksExisted >= MAX_LIVING_TICK) {
            this.remove();
        }
    }
// canSpawn()方法确定实体是否可以生成。
    @Override
    public boolean canSpawn(@NotNull IWorld worldIn, @NotNull SpawnReason spawnReasonIn) {
        return super.canSpawn(worldIn, spawnReasonIn);
    }
//checkDespawn()方法检查实体是否应该消失。
    @Override
    public void checkDespawn() {
        super.checkDespawn();
    }
//setNoGravity()方法设置实体是否有重力
    @Override
    public void setNoGravity(boolean noGravity) {
        super.setNoGravity(true);
    }
//用于实现实体的随机飞行行为。
    /**
     * Copy below from Minecraft vanilla {@link GhastEntity}
     */
    static class RandomFlyGoal extends Goal {
        private final FairyEntity parentEntity;

        public RandomFlyGoal(FairyEntity fairy) {
            this.parentEntity = fairy;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }
// 确定随机飞行行为是否应该执行。
        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            MovementController movementcontroller = this.parentEntity.getMoveHelper();
            if (!movementcontroller.isUpdating()) {
                return true;
            } else {
                double d0 = movementcontroller.getX() - this.parentEntity.getPosX();
                double d1 = movementcontroller.getY() - this.parentEntity.getPosY();
                double d2 = movementcontroller.getZ() - this.parentEntity.getPosZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }
//确定随机飞行行为是否应该继续执行。
        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return false;
        }
// 开始随机飞行行为时执行的操作。
        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            Random random = this.parentEntity.getRNG();
            double d0 = this.parentEntity.getPosX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.parentEntity.getPosY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.parentEntity.getPosZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 0.45D);
        }
    }
// 执行弹幕攻击，创建多个SmallShotEntity实体并发射。
    public void performDanmakuAttack(LivingEntity target) {

        Vector3d vector3d = new Vector3d(Vector3f.ZP).scale(2);
        Random random = this.getRNG();

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                SmallShotEntity smallShot = new SmallShotEntity(this, this.world, DanmakuType.SMALL_SHOT, DanmakuColor.RED);

                vector3d = vector3d.rotatePitch((float) (Math.PI / 12 * i));
                vector3d = vector3d.rotateYaw((float) (Math.PI / 12 * j));

                DanmakuUtil.shootDanmaku(world, this, smallShot, vector3d, 0.4f, 0f);
            }
        }
    }

}
