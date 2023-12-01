package github.thelawf.gensokyoontology.common.entity.ai.goal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
//它扩展了Goal类,因此是一个Entity的AI目标(Goal)
// 跳水到指定目标
public class DivingToTargetGoal extends Goal {
 //可以指定一个LivingEntity实体、目标Entity以及速度
    private final LivingEntity entity;
    private final Entity target;
    private final int speed;

    public DivingToTargetGoal(LivingEntity entity, Entity target, int speed) {
        this.entity = entity;
        this.target = target;
        this.speed = speed;
    }
//tick()方法会每刻被调用以更新实体状态
    @Override
    public void tick() {
        super.tick();
    }
//houldExecute() 判断是否开始执行这个AI目标
    @Override
    public boolean shouldExecute() {
        return false;
    }
//shouldContinueExecuting() 判断是否继续执行这个AI目标
    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting();
    }
}
