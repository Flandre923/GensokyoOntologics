package github.thelawf.gensokyoontology.client;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.client.render.FairyRenderer;
import github.thelawf.gensokyoontology.client.render.InWorldRenderer;
import github.thelawf.gensokyoontology.client.render.SpaceFissureRenderer;
import github.thelawf.gensokyoontology.common.potion.HypnosisEffect;
import github.thelawf.gensokyoontology.common.screen.container.RailAdjustGUI;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = GensokyoOntology.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static final Logger LOGGER = LogManager.getLogger();

    public static void init(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(InWorldRenderer::renderBezierRail);
        event.enqueueWork(SpaceFissureRenderer::register);
    }

    // @SubscribeEvent
    // public static void onTextureStitch(TextureStitchEvent.Pre event){
    //     if (!event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
    //         return;
    //     }
    //     event.addSprite(HypnosisEffect.HYPNOSIS_TEXTURE);
    //     event.addSprite(SpaceFissureRenderer.SPACE_FISSURE_TEX);
    // }

    public static void onRailAdjust(GuiScreenEvent event) {
        if (event.getGui() instanceof RailAdjustGUI) {
            LOGGER.info("get player open it!");
        }
    }

}
