package dev.su5ed.mffs.datagen;

import dev.su5ed.mffs.MFFSMod;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = MFFSMod.MODID, bus = Bus.MOD)
public final class ModDataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        
        generator.addProvider(event.includeClient(), new BlockStateGen(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ItemModelGen(generator, event.getExistingFileHelper()));
    }

    private ModDataGen() {}
}
