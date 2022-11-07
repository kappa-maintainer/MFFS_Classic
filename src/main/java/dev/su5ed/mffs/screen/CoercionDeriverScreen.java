package dev.su5ed.mffs.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.su5ed.mffs.MFFSMod;
import dev.su5ed.mffs.blockentity.CoercionDeriverBlockEntity.EnergyMode;
import dev.su5ed.mffs.menu.CoercionDeriverMenu;
import dev.su5ed.mffs.network.Network;
import dev.su5ed.mffs.network.ToggleEnergyModePacket;
import dev.su5ed.mffs.network.ToggleModePacket;
import dev.su5ed.mffs.network.UpdateFrequencyPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CoercionDeriverScreen extends BaseScreen<CoercionDeriverMenu> {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(MFFSMod.MODID, "textures/gui/coercion_deriver.png");

    private NumericEditBox frequency;

    public CoercionDeriverScreen(CoercionDeriverMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, BACKGROUND);
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new ToggleButton(this.width / 2 - 82, this.height / 2 - 104, this.menu.blockEntity::isActive,
            () -> Network.INSTANCE.sendToServer(new ToggleModePacket(this.menu.blockEntity.getBlockPos(), !this.menu.blockEntity.isActive()))
        ));
        addRenderableWidget(new TextButton(this.width / 2 - 10, this.height / 2 - 28, 58, 20,
            () -> Component.literal(this.menu.blockEntity.isInversed() ? "Integrate" : "Derive"),
            button -> {
                EnergyMode mode = this.menu.blockEntity.getEnergyMode().next();
                this.menu.blockEntity.setEnergyMode(mode);
                Network.INSTANCE.sendToServer(new ToggleEnergyModePacket(this.menu.blockEntity.getBlockPos(), mode));
            })
        );

        this.frequency = new NumericEditBox(this.font, this.leftPos + 30, this.topPos + 43, 50, 12, Component.literal("Frequency:"));
        this.frequency.setCanLoseFocus(true);
        this.frequency.setBordered(true);
        this.frequency.setEditable(true);
        this.frequency.setMaxLength(6);
        this.frequency.setResponder(this::onFrequencyChanged);
        this.frequency.setValue(Integer.toString(this.menu.getFrequency()));
        addWidget(this.frequency);
        
        addRenderableWidget(new FortronChargeWidget(this.leftPos + 8, this.topPos + 115, 107, 11, Component.empty(),
            () -> this.menu.blockEntity.getFortronEnergy() / (double) this.menu.blockEntity.getFortronCapacity()));
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.frequency.tick();
    }
    
    @Override
    public void renderFg(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.frequency.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        super.renderLabels(poseStack, mouseX, mouseY);

        this.font.draw(poseStack, this.frequency.getMessage(), 8, 30, GuiColors.DARK_GREY);
        
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(-90));
        this.font.draw(poseStack, "Upgrade", -95, 140, GuiColors.DARK_GREY);
        poseStack.popPose();
        
        this.font.draw(poseStack, "Progress: " + (this.menu.blockEntity.isActive() ? "Running" : "Idle"), 8, 70, GuiColors.DARK_GREY);
        
        int energy = this.menu.blockEntity.getFortronEnergy();
        this.font.draw(poseStack, "Fortron: " + energy + " L", 8, 105, GuiColors.DARK_GREY); // TODO production rate
    }
    
    private void onFrequencyChanged(String str) {
        int frequency = str.isEmpty() ? 0 : Integer.parseInt(str);
        Network.INSTANCE.sendToServer(new UpdateFrequencyPacket(this.menu.blockEntity.getBlockPos(), frequency));
    }
}
