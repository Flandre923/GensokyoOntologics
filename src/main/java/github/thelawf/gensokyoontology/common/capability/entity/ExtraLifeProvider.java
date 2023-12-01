package github.thelawf.gensokyoontology.common.capability.entity;

import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
//它实现了ICapabilityProvider接口,可以为实体提供自定义capability
//同时实现了INBTSerializable接口用于序列化
public class ExtraLifeProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
//维护一个ExtraLifeCapability类型的字段
    private ExtraLifeCapability capability;
    private int extraLifeCount;

    public ExtraLifeProvider(ExtraLifeCapability capability, int extraLifeCount) {
        this.capability = capability;
        this.extraLifeCount = extraLifeCount;
    }
//getCapability方法中返回了自定义的EXTRA_LIFE capability
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == GSKOCapabilities.EXTRA_LIFE ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
    }
//getOrCreateCapability可生成ExtraLifeCapability实例
    private ExtraLifeCapability getOrCreateCapability() {
        if (this.capability == null) {
            this.capability = new ExtraLifeCapability();
        }
        return this.capability;
    }
//序列化与反序列化方法代理给内部的ExtraLifeCapability执行
    @Override
    public CompoundNBT serializeNBT() {
        return getOrCreateCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        getOrCreateCapability().deserializeNBT(nbt);
    }
}
