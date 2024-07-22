package dev.wolfieboy09.singularity.blockentity.screen;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.wolfieslib.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.singularity.blockentity.menu.VacuumChamberMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@NothingNullByDefault
public class VacuumChamberScreen extends AbstractContainerScreen<VacuumChamberMenu> {
    // TEMPORARY CODE - WILL IMPROVE ON
    private static final ResourceLocation TEXTURE = new ResourceLocation(SingularityReactor.MOD_ID, "textures/gui/vacuum_chamber_gui.png");

    public VacuumChamberScreen(VacuumChamberMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        int energyScaled = this.menu.getEnergyStoredScaled();

        // background
        pGuiGraphics.fill(
                this.leftPos + 115,
                this.topPos + 20,
                this.leftPos + 131,
                this.topPos + 60,
                0xFF555555);

        // foreground
        pGuiGraphics.fill(
                this.leftPos + 116,
                this.topPos + 21 + (38 - energyScaled),
                this.leftPos + 130,
                this.topPos + 59,
                0xFFCC2222
        );
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int energyStored = this.menu.getEnergy();
        int maxEnergy = this.menu.getMaxEnergy();
        // ⚡
        Component text = Component.literal("%s FE / %s FE".formatted(energyStored, maxEnergy));
        if(isHovering(115, 20, 16, 40, pMouseX, pMouseY)) {
            pGuiGraphics.renderTooltip(this.font, text, pMouseX, pMouseY);
        }
    }
}
