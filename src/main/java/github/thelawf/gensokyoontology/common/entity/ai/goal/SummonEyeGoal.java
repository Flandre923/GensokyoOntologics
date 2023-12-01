package github.thelawf.gensokyoontology.common.entity.ai.goal;

import github.thelawf.gensokyoontology.common.entity.misc.DestructiveEyeEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SummonEyeGoal extends Goal {
    protected final MobEntity entity;
    protected final PlayerEntity player;
    private static final int MAX_DISTANCE = 20;
    private Path path;
//构造函数接受一个MobEntity实体和一个PlayerEntity实体作为参数。
    public SummonEyeGoal(MobEntity entity, PlayerEntity player) {
        this.entity = entity;
        this.player = player;
    }
//tick()方法在每个游戏刻中执行逻辑，根据特定条件召唤破坏之眼实体并将其添加到世界中。
    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.entity.getAttackTarget();
        if (target == null || !target.isAlive()) return;

        this.entity.getLookController().setLookPositionWithEntity(target, 30.0F, 30.0F);
        double distance = this.entity.getDistanceSq(target);
        if (this.entity.getEntitySenses().canSee(target) && distance < MAX_DISTANCE) {
            this.entity.setNoGravity(true);

            DestructiveEyeEntity eye = new DestructiveEyeEntity(entity.world);
            eye.setLocationAndAngles(target.getPosX(), target.getPosY(), target.getPosZ(), 0F, 0F);
            generateEye(entity.world, target);
            entity.world.addEntity(eye);
        }
    }
//generateEye()方法用于在目标周围生成一定数量的破坏之眼实体。
    private void generateEye(World world, LivingEntity target) {
        for (int i = 0; i < 6; i++) {
            DestructiveEyeEntity eye = new DestructiveEyeEntity(entity.world);
            Vector3d vector3d = DanmakuUtil.getRandomPosWithin(MAX_DISTANCE, DanmakuUtil.Plane.XYZ).add(target.getPositionVec());
            eye.setLocationAndAngles(vector3d.x, vector3d.y, vector3d.z, 0F, 0F);
            world.addEntity(eye);
        }
    }
//shouldExecute()方法确定是否应该执行目标行为，判断条件为实体是否有攻击目标且目标存活。
    @Override
    public boolean shouldExecute() {
        LivingEntity target = this.entity.getAttackTarget();
        return target != null && target.isAlive();
    }
//shouldContinueExecuting()方法确定是否应该继续执行目标行为，判断条件为攻击目标是否存在且存活，并且如果目标是玩家且无法被攻击（旁观者模式或创造模式），则停止执行目标行为
    @Override
    public boolean shouldContinueExecuting() {
        LivingEntity target = this.entity.getAttackTarget();
        if (target == null || !target.isAlive()) {
            return false;
        } else {
            boolean isPlayerAndCanNotBeAttacked = target instanceof PlayerEntity
                    && (target.isSpectator() || ((PlayerEntity) target).isCreative());
            return !isPlayerAndCanNotBeAttacked;
        }
    }
//getPlayer()方法返回与该目标行为关联的玩家实体。
    public PlayerEntity getPlayer() {
        return player;
    }
}
