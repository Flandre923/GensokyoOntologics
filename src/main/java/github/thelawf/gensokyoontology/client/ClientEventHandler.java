package github.thelawf.gensokyoontology.client;

import github.thelawf.gensokyoontology.client.model.DomainFieldModel;
import github.thelawf.gensokyoontology.client.renderer.*;
import github.thelawf.gensokyoontology.common.entity.*;
import github.thelawf.gensokyoontology.common.entity.projectile.*;
// import github.thelawf.gensokyoontology.common.entity.spellcard.IdonokaihoEntity;
import github.thelawf.gensokyoontology.common.entity.spellcard.*;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onClientSetUp(FMLClientSetupEvent event) {
        ItemRenderer itemRenderer = event.getMinecraftSupplier().get().getItemRenderer();

        // ========================== 弹幕的渲染器 ======================== //
        RenderingRegistry.registerEntityRenderingHandler(DanmakuShotEntity.DANMAKU,
                manager -> new SpriteRenderer<>(manager, itemRenderer));
        RenderingRegistry.registerEntityRenderingHandler(HeartShotEntity.HEART_SHOT,
                manager -> new SpriteRenderer<>(manager, itemRenderer, 2.0f, false));
        RenderingRegistry.registerEntityRenderingHandler(LargeShotEntity.LARGE_SHOT,
                manager -> new SpriteRenderer<>(manager, itemRenderer));

        RenderingRegistry.registerEntityRenderingHandler(StarShotEntity.STAR_SHOT_SMALL,
                manager -> new SpriteRenderer<>(manager, itemRenderer, 0.5f, false));
        RenderingRegistry.registerEntityRenderingHandler(StarShotEntity.STAR_SHOT_SMALL,
                manager -> new SpriteRenderer<>(manager, itemRenderer, 2.0f, false));

        RenderingRegistry.registerEntityRenderingHandler(FakeLunarEntity.FAKE_LUNAR,
                manager -> new SpriteRenderer<>(manager, itemRenderer, 5.0f, false));

        // ======================== 贴图类怪物的渲染器 ==================== //
        RenderingRegistry.registerEntityRenderingHandler(InyoJadeMonsterEntity.INYO_JADE_MONSTER,
                manager -> new SpriteRenderer<>(manager, itemRenderer, 3.0f, false));
        RenderingRegistry.registerEntityRenderingHandler(SpectreEntity.SPECTRE,
                manager -> new SpriteRenderer<>(manager, itemRenderer, 3.0f, false));

        // ======================== 符卡实体的渲染器 ======================= //
        RenderingRegistry.registerEntityRenderingHandler(WaveAndParticleEntity.WAVE_AND_PARTICLE,
                manager -> new SpriteRenderer<>(manager, itemRenderer, 3.0f, false));
        RenderingRegistry.registerEntityRenderingHandler(IdonokaihoEntity.IDONOKAIHO_ENTITY,
                manager -> new SpriteRenderer<>(manager, itemRenderer, 3.0f, false));
        RenderingRegistry.registerEntityRenderingHandler(CircleCrossEntity.CIRCLE_CROSS_ENTITY,
                manager -> new SpriteRenderer<>(manager, itemRenderer, 3.0f, false));
        RenderingRegistry.registerEntityRenderingHandler(HellEclipseEntity.HELL_ECLIPSE_ENTITY,
                manager -> new SpriteRenderer<>(manager, itemRenderer, 3.0f, false));
        RenderingRegistry.registerEntityRenderingHandler(MountainOfFaithEntity.MOUTAIN_OF_FAITH_ENTITY,
                manager -> new SpriteRenderer<>(manager, itemRenderer, 3.0f, false));

        // =========================== 人形生物的渲染器 ========================= //
        RenderingRegistry.registerEntityRenderingHandler(FairyEntity.FAIRY, FairyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(YukariEntity.YUKARI, YukariRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(HumanResidentEntity.HUMAN_RESIDENT,
                HumanResidentRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CitizenEntity.CITIZEN,
                CitizenRenderer::new);

        // ====================== 奇怪实体的渲染器 ===================== //
        RenderingRegistry.registerEntityRenderingHandler(NamespaceDomain.NAMESPACE_DOMAIN,
               manager -> new NamespaceDomainRenderer(manager, new DomainFieldModel()));
    }
}
