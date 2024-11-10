package org.sam.oresmod.client.gui.screen.ingame;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.RenameItemC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.sam.oresmod.screen.NetheriteScreenHandler; // Corrected to use NetheriteScreenHandler

@Environment(EnvType.CLIENT)
public class NetheriteAnvilScreen extends ForgingScreen<NetheriteScreenHandler> { // Use NetheriteScreenHandler here

    private static final Identifier TEXTURE = new Identifier("oresmod", "textures/gui/container/netherite_anvil.png"); // Correct texture path
    private static final Text TOO_EXPENSIVE_TEXT = Text.translatable("container.repair.expensive");
    private TextFieldWidget nameField;
    private final PlayerEntity player;

    public NetheriteAnvilScreen(NetheriteScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.player = inventory.player;
        this.titleX = 60;
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        this.nameField.tick();
    }

    @Override
    protected void setup() {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.nameField = new TextFieldWidget(this.textRenderer, i + 62, j + 24, 103, 12, Text.translatable("container.repair"));
        this.nameField.setFocusUnlocked(false);
        this.nameField.setEditableColor(-1);
        this.nameField.setUneditableColor(-1);
        this.nameField.setDrawsBackground(false);
        this.nameField.setMaxLength(50);
        this.nameField.setChangedListener(this::onRenamed);
        this.nameField.setText("");
        this.addSelectableChild(this.nameField);
        this.setInitialFocus(this.nameField);
        this.nameField.setEditable(false);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        String string = this.nameField.getText();
        this.init(client, width, height);
        this.nameField.setText(string);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.client.player.closeHandledScreen();
        }
        return !this.nameField.keyPressed(keyCode, scanCode, modifiers) && !this.nameField.isActive() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
    }

    private void onRenamed(String name) {
        Slot slot = ((NetheriteScreenHandler)this.handler).getSlot(0); // Corrected handler reference
        if (slot.hasStack()) {
            String string = name;
            if (!slot.getStack().hasCustomName() && string.equals(slot.getStack().getName().getString())) {
                string = "";
            }
            if (((NetheriteScreenHandler)this.handler).setNewItemName(string)) { // Corrected handler reference
                this.client.player.networkHandler.sendPacket(new RenameItemC2SPacket(string));
            }
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
        int i = ((NetheriteScreenHandler)this.handler).getLevelCost(); // Corrected handler reference

        // Remove the "Too Expensive!" check entirely
        Object text;
        int j = 8453920;

        // If there's no item in the output slot, skip showing the cost
        if (!((NetheriteScreenHandler)this.handler).getSlot(2).hasStack()) {
            text = null;
        } else {
            text = Text.translatable("container.repair.cost", i);
            if (!((NetheriteScreenHandler)this.handler).getSlot(2).canTakeItems(this.player)) {
                j = 16736352;
            }
        }

        if (text != null) {
            int k = this.backgroundWidth - 8 - this.textRenderer.getWidth((StringVisitable)text) - 2;
            context.fill(k - 2, 67, this.backgroundWidth - 8, 79, 1325400064);
            context.drawTextWithShadow(this.textRenderer, (Text)text, k, 69, j);
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        super.drawBackground(context, delta, mouseX, mouseY);
        context.drawTexture(TEXTURE, this.x + 59, this.y + 20, 0, this.backgroundHeight + (((NetheriteScreenHandler)this.handler).getSlot(0).hasStack() ? 0 : 16), 110, 16); // Corrected handler reference
    }

    @Override
    public void renderForeground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.nameField.render(context, mouseX, mouseY, delta);
    }

    @Override
    protected void drawInvalidRecipeArrow(DrawContext context, int x, int y) {
        if ((((NetheriteScreenHandler)this.handler).getSlot(0).hasStack() || ((NetheriteScreenHandler)this.handler).getSlot(1).hasStack()) && !((NetheriteScreenHandler)this.handler).getSlot(((NetheriteScreenHandler)this.handler).getResultSlotIndex()).hasStack()) { // Corrected handler reference
            context.drawTexture(TEXTURE, x + 99, y + 45, this.backgroundWidth, 0, 28, 21);
        }
    }

    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
        if (slotId == 0) {
            this.nameField.setText(stack.isEmpty() ? "" : stack.getName().getString());
            this.nameField.setEditable(!stack.isEmpty());
            this.setFocused(this.nameField);
        }
    }
}
