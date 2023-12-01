package github.thelawf.gensokyoontology.common.entity.monster;

import github.thelawf.gensokyoontology.api.entity.ISpellCardUser;
import github.thelawf.gensokyoontology.api.dialog.DialogTreeNode;
import github.thelawf.gensokyoontology.common.entity.ConversationalEntity;
import github.thelawf.gensokyoontology.common.entity.ai.goal.SpellCardAttackGoal;
import github.thelawf.gensokyoontology.common.entity.ai.goal.LilyWhiteBossBattleGoal;
import github.thelawf.gensokyoontology.common.entity.spellcard.FullCherryBlossomEntity;
import github.thelawf.gensokyoontology.common.entity.spellcard.SpellCardEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class LilyWhiteEntity extends ConversationalEntity implements ISpellCardUser {

    public LilyWhiteEntity(EntityType<? extends TameableEntity> type, World worldIn) {
        super(type, worldIn);
// 设置对话对话树（dialog tree）为"lily_white"。
        this.setDialog(new DialogTreeNode("lily_white"));
    }
//registerGoals()方法用于注册实体的行为目标
    @Override
    protected void registerGoals() {
        List<SpellCardAttackGoal.Stage> stages = new ArrayList<>();
        stages.add(new SpellCardAttackGoal.Stage(SpellCardAttackGoal.Type.NON_SPELL, new FullCherryBlossomEntity(world, this), 500, true));
        // stages.put(SpellCardAttackGoal.Type.SPELL_CARD_BREAKABLE, Pair.of(50f, 2000));

        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new LilyWhiteBossBattleGoal(this, stages, 0.4f));
        this.goalSelector.addGoal(2, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }
// spellCardAttack()方法实现了ISpellCardUser接口的方法，用于执行符卡攻击操作。在此处的实现中，对符卡进行了一些逻辑处理，并使用了注释的代码段来生成并发射子弹实体。
    @Override
    public void spellCardAttack(SpellCardEntity spellCard, int ticksIn) {
        if (spellCard == null) {
            return;
        }

        spellCard.onTick(world, this, ticksIn);

        // List<Vector3d> roseLinePos = DanmakuUtil.getRoseLinePos(1.2, 3, 2, 0.05);
//
        // for (Vector3d vector3d : roseLinePos) {
        //     RiceShotEntity riceShot = new RiceShotEntity(this, world, DanmakuType.RICE_SHOT, DanmakuColor.PURPLE);
        //     Vector3d shootVec = new Vector3d(vector3d.x, vector3d.y, vector3d.z);
        //     shootVec = DanmakuUtil.rotateRandomAngle(shootVec, (float) Math.PI * 2, (float) Math.PI * 2);
        //     vector3d = vector3d.add(DanmakuUtil.getRandomPosWithin(3.5f, DanmakuUtil.Plane.XYZ));
        //     vector3d = vector3d.add(this.getPositionVec());
//
        //     DanmakuUtil.initDanmaku(riceShot, vector3d, new Vector2f((float) vector3d.x, (float) vector3d.y), true);
        //     riceShot.shoot(shootVec.x, shootVec.y, shootVec.z, 0.3f, 0f);
        //     world.addEntity(riceShot);
        // }
    }
// createChild()方法在此处没有具体实现。
    @Nullable
    @Override
    public AgeableEntity createChild(@NotNull ServerWorld world, @NotNull AgeableEntity mate) {
        return null;
    }

// createSpawnPacket()方法用于创建生成实体的数据包。
    @Override
    @NotNull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
// getAngerTime()和setAngerTime()方法用于获取和设置实体的愤怒时间。
    @Override
    public int getAngerTime() {
        return 0;
    }

    @Override
    public void setAngerTime(int time) {

    }
// getAngerTarget()和setAngerTarget()方法用于获取和设置实体的愤怒目标。
    @Nullable
    @Override
    public UUID getAngerTarget() {
        return null;
    }

    @Override
    public void setAngerTarget(@Nullable UUID target) {

    }
// func_230258_H__()方法在此处没有具体实现。
    @Override
    public void func_230258_H__() {

    }

// getNextDialog()方法实现了对话树的逻辑，根据选项索引返回下一个对话节点。
    @Override
    public DialogTreeNode getNextDialog(int optionIndex) {
        return optionIndex == 0 ? new DialogTreeNode("root").accessBranch(optionIndex) :
                new DialogTreeNode("root");
    }
}
