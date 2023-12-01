package github.thelawf.gensokyoontology.common.entity.ai.goal;

import github.thelawf.gensokyoontology.common.entity.monster.FairyEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.vector.Vector3d;

/**
 * Copy from <a href="https://github.com/TartaricAcid/TouhouLittleMaid/blob/1.16.5/src/main/java/com/github/tartaricacid/touhoulittlemaid/entity/ai/goal/FairyAttackGoal.java#L12">车万女仆中有关妖精AI的GitHub仓库界面</a>
 * <br>
 */
public class FairyAttackGoal extends Goal {
    private static final int MAX_WITH_IN_RANGE_TIME = 20;
    private final FairyEntity fairy;
    private final double minDistance;
    private final double speedIn;
    private Path path;
    private int withInRangeTime;
// 可以指定精灵实体、最小攻击距离、接近速度
    public FairyAttackGoal(FairyEntity entityFairy, double minDistance, double speedIn) {
        this.fairy = entityFairy;
        this.minDistance = minDistance;
        this.speedIn = speedIn;
    }

// shouldExecute方法会判断是否开始执行攻击
    @Override
    public boolean shouldExecute() {
        LivingEntity target = this.fairy.getAttackTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        this.path = this.fairy.getNavigator().pathfind(target, 0);
        return path != null;
    }
//startExecuting方法会导航到目标entity
    @Override
    public void startExecuting() {
        this.fairy.getNavigator().setPath(this.path, this.speedIn);
    }
//tick方法为主要执行逻辑,判断距离是否到达攻击并释放弹幕
    @Override
    public void tick() {
        LivingEntity target = this.fairy.getAttackTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        this.fairy.getLookController().setLookPositionWithEntity(target, 30.0F, 30.0F);
        double distance = this.fairy.getDistanceSq(target);
        if (this.fairy.getEntitySenses().canSee(target) && distance >= minDistance) {
            this.fairy.getNavigator().tryMoveToEntityLiving(target, this.speedIn);
            withInRangeTime = 0;
        } else if (distance < minDistance) {
            this.fairy.getNavigator().clearPath();
            withInRangeTime++;
            Vector3d motion = fairy.getMotion();
            fairy.setMotion(motion.x, 0, motion.z);
            fairy.setNoGravity(true);
            if (withInRangeTime > MAX_WITH_IN_RANGE_TIME) {
                fairy.performDanmakuAttack(target);
                withInRangeTime = 0;
            }
        } else {
            withInRangeTime = 0;
        }
    }

//shouldContinueExecuting判断是否继续攻击目标
    @Override
    public boolean shouldContinueExecuting() {
        LivingEntity target = this.fairy.getAttackTarget();
        if (target == null || !target.isAlive()) {
            return false;
        } else {
            boolean isPlayerAndCanNotBeAttacked = target instanceof PlayerEntity
                    && (target.isSpectator() || ((PlayerEntity) target).isCreative());
            return !isPlayerAndCanNotBeAttacked;
        }
    }
//resetTask会在攻击结束后重置AI状态
    @Override
    public void resetTask() {
        LivingEntity target = this.fairy.getAttackTarget();
        boolean isPlayerAndCanNotBeAttacked = target instanceof PlayerEntity
                && (target.isSpectator() || ((PlayerEntity) target).isCreative());
        if (isPlayerAndCanNotBeAttacked) {
            this.fairy.setAttackTarget(null);
        }
        this.fairy.getNavigator().clearPath();
        withInRangeTime = 0;
    }
}
