package github.thelawf.gensokyoontology.common;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import github.thelawf.gensokyoontology.common.command.GSKOCommand;
import github.thelawf.gensokyoontology.common.command.GUICommand;
import github.thelawf.gensokyoontology.common.network.CountDownNetworking;
import github.thelawf.gensokyoontology.common.network.GSKOIncidentNetworking;
import github.thelawf.gensokyoontology.common.world.dimension.biome.GSKOBiomesProvider;
import github.thelawf.gensokyoontology.common.world.feature.GSKOFeatures;
import github.thelawf.gensokyoontology.core.PlacerRegistry;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.StructureRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = GensokyoOntology.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonSetUp {

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Registry.register(Registry.CHUNK_GENERATOR_CODEC,
            //         new ResourceLocation(GensokyoOntology.MODID, "chunkgen"),
            //         GSKOChunkGenerator.CHUNK_GEN_CODEC);

            StructureRegistry.setupStructures();
            PlacerRegistry.registerPlacers();
            GSKOFeatures.registerStructure();
            GSKOFeatures.registerOre();
            GSKOFeatures.registerFeature();

            GSKOIncidentNetworking.registerMessage();
            GSKOCapabilities.registerCapabilities();
            CountDownNetworking.registerMessage();

            Registry.register(Registry.BIOME_PROVIDER_CODEC,
                    new ResourceLocation(GensokyoOntology.MODID, "gensokyo"),
                    GSKOBiomesProvider.GSKO_BIOME_CODEC);

            EntitySpawnPlacementRegistry.register(EntityRegistry.FAIRY_ENTITY.get(),
                    EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    MonsterEntity::canMonsterSpawn);

            EntitySpawnPlacementRegistry.register(EntityRegistry.LILY_WHITE_ENTITY.get(),
                    EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    TameableEntity::canAnimalSpawn);

        });
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        GSKOCommand.register(event.getDispatcher());
        GUICommand.register(event.getDispatcher());
    }
}
