package github.thelawf.gensokyoontology.common.entity.ai.goal;

import github.thelawf.gensokyoontology.common.entity.monster.FlandreScarletEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

//控制芙兰（FlandreScarletEntity）实体进行符卡攻击。
public class FlandreSpellAttackGoal extends SpellCardAttackGoal {

    private final FlandreScarletEntity flandre;
    private Path path;
    private final float speed;
    private int ticksExisted;

    /**
     * @param stages  该形参是一个映射表类型，其索引值是符卡战斗的总共次数，其key表示符卡战斗的类型。Pair的第一个Float代表为了击破该符卡需要对BOSS造成的总伤害，第二个Integer表示该符卡提供给玩家的击破时间，单位为tick。
     * @param flandre 芙兰
     */
    public FlandreSpellAttackGoal(FlandreScarletEntity flandre, List<Stage> stages, float speedIn) {
        super(stages);
        this.flandre = flandre;
        this.speed = speedIn;
    }

//控制芙兰（FlandreScarletEntity）实体进行符卡攻击。
//在攻击目标存活且可见的情况下，使芙兰朝目标移动并执行符卡攻击。
//在目标不可见的情况下，停止移动。
//在目标死亡或不存在的情况下，停止执行攻击。
    @Override
    public void tick() {
        ticksExisted++;

        LivingEntity target = this.flandre.getAttackTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        this.flandre.getLookController().setLookPositionWithEntity(target, 30.0F, 30.0F);
        double distance = this.flandre.getDistanceSq(target);
        if (this.flandre.getEntitySenses().canSee(target)) {
            this.flandre.getNavigator().tryMoveToEntityLiving(target, this.speed);
            this.flandre.setNoGravity(true);

            if (this.stages.get(0).spellCard == null) {
                throw new NullPointerException("符卡未提供");
            }
            this.flandre.spellCardAttack(this.stages.get(0).spellCard, ticksExisted);

            // this.flandre.spellCardAttack(this.flandre.getHealth() > this.flandre.getMaxHealth() ?
            // this.stages.get(0).spellCard : null, ticksExisted);
        } else if (!this.flandre.getEntitySenses().canSee(target)) {
            this.flandre.getNavigator().clearPath();
        }
    }
//shouldExecute()方法确定是否应该执行任务
    @Override
    public boolean shouldExecute() {
        LivingEntity target = this.flandre.getAttackTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        this.path = this.flandre.getNavigator().pathfind(target, 0);
        return path != null;
    }
//在重置任务时，如果目标是玩家且无法被攻击（处于旁观模式或创造模式），将攻击目标设为null并停止移动。
    @Override
    public void resetTask() {
        LivingEntity target = this.flandre.getAttackTarget();
        boolean isPlayerAndCanNotBeAttacked = target instanceof PlayerEntity
                && (target.isSpectator() || ((PlayerEntity) target).isCreative());
        if (isPlayerAndCanNotBeAttacked) {
            this.flandre.setAttackTarget(null);
        }
        this.flandre.getNavigator().clearPath();
    }
//在开始执行任务时，设置芙兰的导航路径并指定速度。
    @Override
    public void startExecuting() {
        this.flandre.getNavigator().setPath(this.path, this.speed);
    }
}
