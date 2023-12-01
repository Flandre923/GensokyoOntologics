package github.thelawf.gensokyoontology.common.entity.monster;

import github.thelawf.gensokyoontology.api.dialog.DialogTreeNode;
import github.thelawf.gensokyoontology.api.entity.ISpellCardUser;
import github.thelawf.gensokyoontology.common.entity.ai.goal.FlandreSpellAttackGoal;
import github.thelawf.gensokyoontology.common.entity.ai.goal.SpellCardAttackGoal;
import github.thelawf.gensokyoontology.common.entity.spellcard.FullCherryBlossomEntity;
import github.thelawf.gensokyoontology.common.entity.spellcard.SpellCardEntity;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlandreScarletEntity extends YoukaiEntity implements ISpellCardUser {


    public FlandreScarletEntity(EntityType<? extends TameableEntity> type, World worldIn) {
        super(type, worldIn);
        this.favorability = -10;
        // this.setHeldItem(Hand.MAIN_HAND, new ItemStack(ItemRegistry.CLOCK_HAND_ITEM.get()));
    }
// 方法在此处没有具体实现。
    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }
//registerGoals()方法用于注册实体的行为目标。
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4f));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 0.8f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, CreatureEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, TsumiBukuroEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(5, new ResetAngerGoal<>(this, true));
    }
// getEntityInteractionResult()方法处理与实体交互的结果。
    @Override
    @NotNull
    public ActionResultType getEntityInteractionResult(@NotNull PlayerEntity playerIn, @NotNull Hand hand) {
        return super.getEntityInteractionResult(playerIn, hand);
    }
// livingTick()方法在每个游戏刻中执行逻辑，包括设置实体的动画状态。
    @Override
    public void livingTick() {
        super.livingTick();
        if (this.getIdleTime() == 0 && this.getAnimation() == Animation.IDLE) {
            this.setAnimation(Animation.WALKING);
        } else {
            this.setAnimation(Animation.IDLE);
        }
    }
//tick()方法在每个游戏刻中执行逻辑。
    @Override
    public void tick() {
        super.tick();
    }
//createSpawnPacket()方法用于创建生成实体的数据包。
    @Override
    @NotNull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
//onDeath()方法在实体死亡时执行的操作。
    @Override
    public void onDeath(@NotNull DamageSource cause) {
        super.onDeath(cause);
    }
//getAngerTime()和setAngerTime()方法用于获取和设置实体的愤怒时间。
    @Override
    public int getAngerTime() {
        return super.getAngerTime();
    }

    @Override
    public void setAngerTime(int time) {
        super.setAngerTime(time);
    }
//getAngerTarget()和setAngerTarget()方法用于获取和设置实体的愤怒目标。
    @Nullable
    @Override
    public UUID getAngerTarget() {
        return super.getAngerTarget();
    }

    @Override
    public void setAngerTarget(@Nullable UUID target) {
        super.setAngerTarget(target);
    }

    @Override
    public void func_230258_H__() {
        super.func_230258_H__();
    }
//spellCardAttack()方法实现了ISpellCardUser接口的方法，用于执行符卡攻击操作。
    @Override
    public void spellCardAttack(SpellCardEntity spellCard, int ticksIn) {
        if (spellCard == null) return;
        spellCard.onTick(this.world, this, ticksIn);
    }
//Doppelganger类是FlandreScarletEntity的一个子类，代表Flandre Scarlet的分身实体。
    public static class Doppelganger extends FlandreScarletEntity {
        public static final EntityType<Doppelganger> FLANDRE_DOPPELGANGER = EntityType.Builder.create(
                        Doppelganger::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true)
                .size(0.6f, 1.55f).trackingRange(10).build("flandre_doppelganger");

        public Doppelganger(EntityType<? extends TameableEntity> type, World worldIn) {
            super(type, worldIn);
        }
//registerGoals()方法在Doppelganger类中重写，注册了特定的行为目标，包括施放符卡攻击和跟随主人等行为。
        @Override
        protected void registerGoals() {
            List<SpellCardAttackGoal.Stage> stages = new ArrayList<>();
            stages.add(new SpellCardAttackGoal.Stage(SpellCardAttackGoal.Type.SPELL_CARD_BREAKABLE, new FullCherryBlossomEntity(world, this), 500, true));

            this.goalSelector.addGoal(1, new SwimGoal(this));
            this.goalSelector.addGoal(2, new SitGoal(this));
            this.goalSelector.addGoal(3, new FlandreSpellAttackGoal(this, stages, 0.4F));
            this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
            this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4F));
            this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 0.8F));
            this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        }
    }

}
