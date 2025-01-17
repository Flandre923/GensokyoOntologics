package github.thelawf.gensokyoontology.common.capability;

import github.thelawf.gensokyoontology.common.capability.entity.ExtraLifeCapability;
import github.thelawf.gensokyoontology.common.capability.entity.ExtraLifeProvider;
import github.thelawf.gensokyoontology.common.capability.world.BloodyMistCapability;
import github.thelawf.gensokyoontology.common.capability.world.ImperishableNightCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

public class GSKOCapabilities {

    @CapabilityInject(BloodyMistCapability.class)
    public static Capability<BloodyMistCapability> BLOODY_MIST;
    @CapabilityInject(ImperishableNightCapability.class)
    public static Capability<ImperishableNightCapability> IMPERISHABLE_NIGHT;
    @CapabilityInject(ExtraLifeCapability.class)
    public static Capability<ExtraLifeCapability> EXTRA_LIFE;

    public static void registerCapabilities() {
        register(BloodyMistCapability.class);
        register(ImperishableNightCapability.class);
        register(ExtraLifeProvider.class);

    }

    private static <T extends INBTSerializable<CompoundNBT>> void register(Class<T> capClass) {
        CapabilityManager.INSTANCE.register(capClass, new Capability.IStorage<T>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<T> capability, T instance, Direction side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) {
                instance.deserializeNBT((CompoundNBT) nbt);
            }
        }, () -> null);
    }
}
