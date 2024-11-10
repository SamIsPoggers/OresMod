package org.sam.oresmod.screen;

import net.minecraft.SharedConstants;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.sam.oresmod.block.custom.NetheriteAnvilBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

public class NetheriteScreenHandler extends ForgingScreenHandler {
    public static final int INPUT_1_ID = 0;
    public static final int INPUT_2_ID = 1;
    public static final int OUTPUT_ID = 2;
    private static final Logger LOGGER = LoggerFactory.getLogger(NetheriteScreenHandler.class);
    private static final boolean field_30752 = false;
    public static final int MAX_NAME_LENGTH = 50;
    private int repairItemUsage;
    @Nullable
    private String newItemName;
    private final Property levelCost;

    // Coordinates for slots
    private static final int INPUT_1_X = 27;
    private static final int INPUT_2_X = 76;
    private static final int OUTPUT_X = 134;
    private static final int SLOT_Y = 47;

    public NetheriteScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, ScreenHandlerContext.EMPTY);
    }

    public NetheriteScreenHandler(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.NETHERITE_SCREEN_HANDLER, syncId, inventory, context);
        this.levelCost = Property.create();
        this.addProperty(this.levelCost);
    }

    protected ForgingSlotsManager getForgingSlotsManager() {
        return ForgingSlotsManager.create().input(0, 27, 47, (stack) -> {
            return true;
        }).input(1, 76, 47, (stack) -> {
            return true;
        }).output(2, 134, 47).build();
    }

    @Override
    protected boolean canUse(BlockState state) {
        return state.getBlock() instanceof NetheriteAnvilBlock;
        // Ensures it's used on an anvil block
    }

    @Override
    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        // Allow the player to take the output if they have enough experience levels and the output exists
        LOGGER.info("levelCost: {}, present: {}", this.levelCost.get(), present);
        return (player.getAbilities().creativeMode || player.experienceLevel >= this.levelCost.get()) && this.levelCost.get() > 0 && present;

    }

    @Override
    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            player.addExperienceLevels(-this.levelCost.get());
        }

        // Clear the input slot after taking the output
        this.input.setStack(0, ItemStack.EMPTY);

        // If there's repair item usage, decrement the second input stack
        if (this.repairItemUsage > 0) {
            ItemStack itemStack = this.input.getStack(1);
            if (!itemStack.isEmpty() && itemStack.getCount() > this.repairItemUsage) {
                itemStack.decrement(this.repairItemUsage);
                this.input.setStack(1, itemStack);
            } else {
                this.input.setStack(1, ItemStack.EMPTY);
            }
        } else {
            this.input.setStack(1, ItemStack.EMPTY);
        }

        this.levelCost.set(0); // Reset the level cost

        // Sync the world event (no block removal)
        this.context.run((world, pos) -> {
            BlockState blockState = world.getBlockState(pos);
            world.syncWorldEvent(1030, pos, 0); // Sync the world event to refresh the anvil state
        });
    }

    public void updateResult() {
        ItemStack itemStack = this.input.getStack(0);
        this.levelCost.set(1);
        int i = 0;
        int j = 0;
        int k = 0;
        if (itemStack.isEmpty()) {
            this.output.setStack(0, ItemStack.EMPTY);
            this.levelCost.set(0);
        } else {
            ItemStack itemStack2 = itemStack.copy();
            ItemStack itemStack3 = this.input.getStack(1);
            Map<Enchantment, Integer> map = EnchantmentHelper.get(itemStack2);
            j += itemStack.getRepairCost() + (itemStack3.isEmpty() ? 0 : itemStack3.getRepairCost());
            this.repairItemUsage = 0;
            if (!itemStack3.isEmpty()) {
                boolean bl = itemStack3.isOf(Items.ENCHANTED_BOOK) && !EnchantedBookItem.getEnchantmentNbt(itemStack3).isEmpty();
                int l;
                int m;
                int n;
                if (itemStack2.isDamageable() && itemStack2.getItem().canRepair(itemStack, itemStack3)) {
                    l = Math.min(itemStack2.getDamage(), itemStack2.getMaxDamage() / 4);
                    if (l <= 0) {
                        this.output.setStack(0, ItemStack.EMPTY);
                        this.levelCost.set(0);
                        return;
                    }

                    for(m = 0; l > 0 && m < itemStack3.getCount(); ++m) {
                        n = itemStack2.getDamage() - l;
                        itemStack2.setDamage(n);
                        ++i;
                        l = Math.min(itemStack2.getDamage(), itemStack2.getMaxDamage() / 4);
                    }

                    this.repairItemUsage = m;
                } else {
                    if (!bl && (!itemStack2.isOf(itemStack3.getItem()) || !itemStack2.isDamageable())) {
                        this.output.setStack(0, ItemStack.EMPTY);
                        this.levelCost.set(0);
                        return;
                    }

                    if (itemStack2.isDamageable() && !bl) {
                        l = itemStack.getMaxDamage() - itemStack.getDamage();
                        m = itemStack3.getMaxDamage() - itemStack3.getDamage();
                        n = m + itemStack2.getMaxDamage() * 12 / 100;
                        int o = l + n;
                        int p = itemStack2.getMaxDamage() - o;
                        if (p < 0) {
                            p = 0;
                        }

                        if (p < itemStack2.getDamage()) {
                            itemStack2.setDamage(p);
                            i += 2;
                        }
                    }

                    Map<Enchantment, Integer> map2 = EnchantmentHelper.get(itemStack3);
                    boolean bl2 = false;
                    boolean bl3 = false;
                    Iterator var23 = map2.keySet().iterator();

                    label159:
                    while(true) {
                        Enchantment enchantment;
                        do {
                            if (!var23.hasNext()) {
                                if (bl3 && !bl2) {
                                    this.output.setStack(0, ItemStack.EMPTY);
                                    this.levelCost.set(0);
                                    return;
                                }
                                break label159;
                            }

                            enchantment = (Enchantment)var23.next();
                        } while(enchantment == null);

                        int q = (Integer)map.getOrDefault(enchantment, 0);
                        int r = (Integer)map2.get(enchantment);
                        r = q == r ? r + 1 : Math.max(r, q);
                        boolean bl4 = enchantment.isAcceptableItem(itemStack);
                        if (this.player.getAbilities().creativeMode || itemStack.isOf(Items.ENCHANTED_BOOK)) {
                            bl4 = true;
                        }

                        Iterator var17 = map.keySet().iterator();

                        while(var17.hasNext()) {
                            Enchantment enchantment2 = (Enchantment)var17.next();
                            if (enchantment2 != enchantment && !enchantment.canCombine(enchantment2)) {
                                bl4 = false;
                                ++i;
                            }
                        }


                        if (!bl4) {
                            bl3 = true;
                        } else {
                            bl2 = true;
                            int level1 = map.getOrDefault(enchantment, 0); // Enchantment level from item 1
                            int level2 = map2.getOrDefault(enchantment, 0); // Enchantment level from item 2

                            if (r > enchantment.getMaxLevel() + 1) {
                                // Check if either level1 or level2 exceeds the max allowed level
                                if (level1 > enchantment.getMaxLevel() + 1 || level2 > enchantment.getMaxLevel() + 1) {
                                    // Set r to the higher of the two levels
                                    r = Math.max(level1, level2);
                                } else {
                                    // Otherwise, set r to max level + 1
                                    r = enchantment.getMaxLevel() + 1;
                                }
                            }

                            map.put(enchantment, r);
                            int s = 0;
                            switch (enchantment.getRarity()) {
                                case COMMON -> s = 1;
                                case UNCOMMON -> s = 2;
                                case RARE -> s = 4;
                                case VERY_RARE -> s = 8;
                            }

                            if (bl) {
                                s = Math.max(1, s / 2);
                            }

                            i += s * r;
                            if (itemStack.getCount() > 1) {
                                i = 40;
                            }
                        }
                    }
                }
            }

            // Handle renaming
            if (this.newItemName != null && !Util.isBlank(this.newItemName)) {
                LOGGER.info("Renaming: {}, New name: {}", !this.newItemName.equals(itemStack.getName().getString()), this.newItemName);
                if (!this.newItemName.equals(itemStack.getName().getString())) {
                    k = 1;
                    i += k;  // Increment the cost for renaming
                    itemStack2.setCustomName(Text.literal(this.newItemName));
                }
            } else if (itemStack.hasCustomName()) {
                k = 1;
                i += k;
                itemStack2.removeCustomName();  // Remove the custom name if empty
            }

            // Update the cost with the renaming cost added
            this.levelCost.set(j + i);

            if (i <= 0) {
                itemStack2 = ItemStack.EMPTY;
            }

            //if (this.levelCost.get() >= 40 && !this.player.getAbilities().creativeMode) {
            //     itemStack2 = ItemStack.EMPTY;
            //}

            if (!itemStack2.isEmpty()) {
                int t = itemStack2.getRepairCost();
                if (!itemStack3.isEmpty() && t < itemStack3.getRepairCost()) {
                    t = itemStack3.getRepairCost();
                }

                if (k != i || k == 0) {
                    t = getNextCost(t);
                }

                itemStack2.setRepairCost(t);
                EnchantmentHelper.set(map, itemStack2);
            }

            this.output.setStack(0, itemStack2);
            this.sendContentUpdates();
        }
    }

    public static int getNextCost(int cost) {
        return cost * 2 + 1;
    }

    public boolean setNewItemName(String newItemName) {
        String string = sanitize(newItemName);
        if (string != null && !string.equals(this.newItemName)) {
            this.newItemName = string;
            if (this.getSlot(2).hasStack()) {
                ItemStack itemStack = this.getSlot(2).getStack();
                if (Util.isBlank(string)) {
                    itemStack.removeCustomName();
                } else {
                    itemStack.setCustomName(Text.literal(string));
                }
            }

            this.updateResult();
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    private static String sanitize(String name) {
        String string = SharedConstants.stripInvalidChars(name);
        return string.length() <= 50 ? string : null;
    }

    public int getLevelCost() {
        return this.levelCost.get();
    }
}
