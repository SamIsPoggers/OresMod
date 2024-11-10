package org.sam.oresmod.client.gui.screen.ingame;

import com.google.common.collect.Lists;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.EnchantingPhrases;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import net.minecraft.enchantment.Enchantment;
import org.sam.oresmod.OresMod;
import org.sam.oresmod.screen.SuperEnchantmentScreenHandler;

public class SuperEnchantmentScreen extends HandledScreen<SuperEnchantmentScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(OresMod.MOD_ID, "textures/gui/container/super_enchanting_table.png");
    private static final Identifier BOOK_TEXTURE = new Identifier("minecraft", "textures/entity/enchanting_table_book.png");
    private final Random random = Random.create();
    private BookModel bookModel;
    public int ticks;
    public float nextPageAngle;
    public float pageAngle;
    public float approximatePageAngle;
    public float pageRotationSpeed;
    public float nextPageTurningSpeed;
    public float pageTurningSpeed;
    private ItemStack stack;

    public SuperEnchantmentScreen(SuperEnchantmentScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.stack = ItemStack.EMPTY;
    }

    protected void init() {
        super.init();
        this.bookModel = new BookModel(this.client.getEntityModelLoader().getModelPart(EntityModelLayers.BOOK));
    }

    public void handledScreenTick() {
        super.handledScreenTick();
        this.doTick();
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;

        for (int k = 0; k < 3; ++k) {
            double d = mouseX - (double)(i + 60);
            double e = mouseY - (double)(j + 14 + 19 * k);
            if (d >= 0.0 && e >= 0.0 && d < 108.0 && e < 19.0 && ((SuperEnchantmentScreenHandler)this.handler).onButtonClick(this.client.player, k)) {
                this.client.interactionManager.clickButton(((SuperEnchantmentScreenHandler)this.handler).syncId, k);
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        this.drawBook(context, i, j, delta);
        EnchantingPhrases.getInstance().setSeed((long)((SuperEnchantmentScreenHandler)this.handler).getSeed());
        int k = ((SuperEnchantmentScreenHandler)this.handler).getLapisCount();

        for (int l = 0; l < 3; ++l) {
            int m = i + 60;
            int n = m + 20;
            int o = ((SuperEnchantmentScreenHandler)this.handler).enchantmentPower[l];
            if (o == 0) {
                context.drawTexture(TEXTURE, m, j + 14 + 19 * l, 0, 185, 108, 19);
            } else {
                String string = "" + o;
                int p = 86 - this.textRenderer.getWidth(string);
                StringVisitable stringVisitable = EnchantingPhrases.getInstance().generatePhrase(this.textRenderer, p);
                int q = 6839882;
                if ((k < l + 1 || this.client.player.experienceLevel < o) && !this.client.player.getAbilities().creativeMode) {
                    context.drawTexture(TEXTURE, m, j + 14 + 19 * l, 0, 185, 108, 19);
                    context.drawTexture(TEXTURE, m + 1, j + 15 + 19 * l, 16 * l, 239, 16, 16);
                    context.drawTextWrapped(this.textRenderer, stringVisitable, n, j + 16 + 19 * l, p, (q & 16711422) >> 1);
                    q = 4226832;
                } else {
                    int r = mouseX - (i + 60);
                    int s = mouseY - (j + 14 + 19 * l);
                    if (r >= 0 && s >= 0 && r < 108 && s < 19) {
                        context.drawTexture(TEXTURE, m, j + 14 + 19 * l, 0, 204, 108, 19);
                        q = 16777088;
                    } else {
                        context.drawTexture(TEXTURE, m, j + 14 + 19 * l, 0, 166, 108, 19);
                    }

                    context.drawTexture(TEXTURE, m + 1, j + 15 + 19 * l, 16 * l, 223, 16, 16);
                    context.drawTextWrapped(this.textRenderer, stringVisitable, n, j + 16 + 19 * l, p, q);
                    q = 8453920;
                }

                context.drawTextWithShadow(this.textRenderer, string, n + 86 - this.textRenderer.getWidth(string), j + 16 + 19 * l + 7, q);
            }
        }
    }

    private void drawBook(DrawContext context, int x, int y, float delta) {
        float f = MathHelper.lerp(delta, this.pageTurningSpeed, this.nextPageTurningSpeed);
        float g = MathHelper.lerp(delta, this.pageAngle, this.nextPageAngle);
        DiffuseLighting.method_34742();
        context.getMatrices().push();
        context.getMatrices().translate((float)x + 33.0F, (float)y + 31.0F, 100.0F);
        float h = 40.0F;
        context.getMatrices().scale(-40.0F, 40.0F, 40.0F);
        context.getMatrices().multiply(RotationAxis.POSITIVE_X.rotationDegrees(25.0F));
        context.getMatrices().translate((1.0F - f) * 0.2F, (1.0F - f) * 0.1F, (1.0F - f) * 0.25F);
        float i = -(1.0F - f) * 90.0F - 90.0F;
        context.getMatrices().multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i));
        context.getMatrices().multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));
        float j = MathHelper.clamp(MathHelper.fractionalPart(g + 0.25F) * 1.6F - 0.3F, 0.0F, 1.0F);
        float k = MathHelper.clamp(MathHelper.fractionalPart(g + 0.75F) * 1.6F - 0.3F, 0.0F, 1.0F);
        this.bookModel.setPageAngles(0.0F, j, k, f);
        VertexConsumer vertexConsumer = context.getVertexConsumers().getBuffer(this.bookModel.getLayer(BOOK_TEXTURE));
        this.bookModel.render(context.getMatrices(), vertexConsumer, 15728880, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        context.draw();
        context.getMatrices().pop();
        DiffuseLighting.enableGuiDepthLighting();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        delta = this.client.getTickDelta();
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
        boolean isCreativeMode = this.client.player.getAbilities().creativeMode;
        int lapisCount = ((SuperEnchantmentScreenHandler)this.handler).getLapisCount();

        for (int j = 0; j < 3; ++j) {
            int enchantmentPower = ((SuperEnchantmentScreenHandler)this.handler).enchantmentPower[j];
            Enchantment enchantment = Enchantment.byRawId(((SuperEnchantmentScreenHandler)this.handler).enchantmentId[j]);
            int enchantmentLevel = ((SuperEnchantmentScreenHandler)this.handler).enchantmentLevel[j];

            // Calculate specific requirements for each slot
            int infrexCount = j + 1;                     // 1, 2, 3 infrex for slots 0, 1, 2
            int xpLevelRequirement = 50 + (j * 25);      // 50, 75, 100 levels for slots 0, 1, 2

            if (this.isPointWithinBounds(60, 14 + 19 * j, 108, 17, mouseX, mouseY) && enchantmentPower > 0 && enchantmentLevel >= 0 && enchantment != null) {
                List<Text> tooltipList = Lists.newArrayList();
                tooltipList.add(Text.translatable("container.superenchant.clue", enchantment.getName(enchantmentLevel)).formatted(Formatting.WHITE));

                if (!isCreativeMode) {
                    tooltipList.add(ScreenTexts.EMPTY);

                    // Add infrex ingot requirement
                    MutableText infrexText = Text.translatable("container.superenchant.infrex.required", infrexCount)
                            .formatted(lapisCount >= infrexCount ? Formatting.GRAY : Formatting.RED);
                    tooltipList.add(infrexText);

                    // Add XP level requirement
                    MutableText xpLevelText = Text.translatable("container.superenchant.xp.required", xpLevelRequirement)
                            .formatted(this.client.player.experienceLevel >= xpLevelRequirement ? Formatting.GRAY : Formatting.RED);
                    tooltipList.add(xpLevelText);
                }

                context.drawTooltip(this.textRenderer, tooltipList, mouseX, mouseY);
                break;
            }
        }
    }

    public void doTick() {
        ItemStack itemStack = ((SuperEnchantmentScreenHandler)this.handler).getSlot(0).getStack();
        if (!ItemStack.areEqual(itemStack, this.stack)) {
            this.stack = itemStack;

            do {
                this.approximatePageAngle += (float)(this.random.nextInt(4) - this.random.nextInt(4));
            } while(this.nextPageAngle <= this.approximatePageAngle + 1.0F && this.nextPageAngle >= this.approximatePageAngle - 1.0F);
        }

        ++this.ticks;
        this.pageAngle = this.nextPageAngle;
        this.pageTurningSpeed = this.nextPageTurningSpeed;
        boolean bl = false;

        for (int i = 0; i < 3; ++i) {
            if (((SuperEnchantmentScreenHandler)this.handler).enchantmentPower[i] != 0) {
                bl = true;
            }
        }

        if (bl) {
            this.nextPageTurningSpeed += 0.2F;
        } else {
            this.nextPageTurningSpeed -= 0.2F;
        }

        this.nextPageTurningSpeed = MathHelper.clamp(this.nextPageTurningSpeed, 0.0F, 1.0F);
        float f = (this.approximatePageAngle - this.nextPageAngle) * 0.4F;
        float g = 0.2F;
        f = MathHelper.clamp(f, -0.2F, 0.2F);
        this.pageRotationSpeed += (f - this.pageRotationSpeed) * 0.9F;
        this.nextPageAngle += this.pageRotationSpeed;
    }
}
