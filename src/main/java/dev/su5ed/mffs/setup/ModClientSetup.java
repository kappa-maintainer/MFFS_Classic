package dev.su5ed.mffs.setup;

import dev.su5ed.mffs.render.CoercionDeriverBlockRenderer;
import dev.su5ed.mffs.render.ProjectorBlockRenderer;
import dev.su5ed.mffs.render.model.CoercionDeriverTopModel;
import dev.su5ed.mffs.render.model.ProjectorRotorModel;
import dev.su5ed.mffs.render.particle.BeamParticleProvider;
import dev.su5ed.mffs.screen.CoercionDeriverScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public final class ModClientSetup {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(ModContainers.COERCION_DERIVER_MENU.get(), CoercionDeriverScreen::new));
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModObjects.PROJECTOR_BLOCK_ENTITY.get(), ProjectorBlockRenderer::new);
        event.registerBlockEntityRenderer(ModObjects.COERCION_DERIVER_BLOCK_ENTITY.get(), CoercionDeriverBlockRenderer::new);
    }
    
    @SubscribeEvent
    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        event.register(ModObjects.BEAM_PARTICLE.get(), new BeamParticleProvider());
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ProjectorRotorModel.LAYER_LOCATION, ProjectorRotorModel::createBodyLayer);
        event.registerLayerDefinition(CoercionDeriverTopModel.LAYER_LOCATION, CoercionDeriverTopModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        event.register(ProjectorBlockRenderer.FORCE_CUBE_MODEL);
    }

    private ModClientSetup() {}
}
