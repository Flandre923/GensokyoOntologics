package github.thelawf.gensokyoontology.common.capability.entity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
// 它实现了INBTSerializable接口,即可以序列化与反序列化为NBT
public class ExtraLifeCapability implements INBTSerializable<CompoundNBT> {
    //维护了一个额外生命值数量extraLifeCount的整型字段
    private int extraLifeCount = 0;
//提供getter和setter方法访问extraLifeCount字段
    public int getExtraLifeCount() {
        return extraLifeCount;
    }

    public void setExtraLifeCount(int extraLifeCount) {
        this.extraLifeCount = extraLifeCount;
    }

    public void addExtraLife(int extraLifeCount) {
        this.extraLifeCount += extraLifeCount;
    }
//serializeNBT方法将extraLifeCount保存到一个CompoundNBT中进行序列化
    @Override
    @NotNull
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("extra_life", this.extraLifeCount);
        return nbt;
    }
//deserializeNBT方法从CompoundNBT中反序列化extraLifeCount字段
    @Override
    public void deserializeNBT(@NotNull CompoundNBT nbt) {
        if (nbt.contains("extra_life")) {
            this.extraLifeCount = nbt.getInt("extra_life");
        }
    }
}
