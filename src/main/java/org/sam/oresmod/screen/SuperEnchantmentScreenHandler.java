package org.sam.oresmod.screen;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.random.Random;
import org.sam.oresmod.block.ModBlocks;
import org.sam.oresmod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SuperEnchantmentScreenHandler extends ScreenHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SuperEnchantmentScreenHandler.class);

    private final Inventory inventory;
    private final ScreenHandlerContext context;
    private final Random random;
    private final Property seed;
    public final int[] enchantmentPower;
    public final int[] enchantmentId;
    public final int[] enchantmentLevel;

    public SuperEnchantmentScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public SuperEnchantmentScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.SUPER_ENCH_HANDLER, syncId);
        this.inventory = new SimpleInventory(2) {
            public void markDirty() {
                super.markDirty();
                SuperEnchantmentScreenHandler.this.onContentChanged(this);
            }
        };
        this.random = Random.create();
        this.seed = Property.create();
        this.enchantmentPower = new int[3];
        this.enchantmentId = new int[]{-1, -1, -1};
        this.enchantmentLevel = new int[]{-1, -1, -1};
        this.context = context;
        this.addSlot(new Slot(this.inventory, 0, 15, 47) {
            public boolean canInsert(ItemStack stack) {
                return true;
            }

            public int getMaxItemCount() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.inventory, 1, 35, 47) {
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ModItems.ULTRIS);
            }
        });

        int i;
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        this.addProperty(Property.create(this.enchantmentPower, 0));
        this.addProperty(Property.create(this.enchantmentPower, 1));
        this.addProperty(Property.create(this.enchantmentPower, 2));
        this.addProperty(this.seed).set(playerInventory.player.getEnchantmentTableSeed());
        this.addProperty(Property.create(this.enchantmentId, 0));
        this.addProperty(Property.create(this.enchantmentId, 1));
        this.addProperty(Property.create(this.enchantmentId, 2));
        this.addProperty(Property.create(this.enchantmentLevel, 0));
        this.addProperty(Property.create(this.enchantmentLevel, 1));
        this.addProperty(Property.create(this.enchantmentLevel, 2));
    }

    public void onContentChanged(Inventory inventory) {
        if (inventory == this.inventory) {
            ItemStack itemStack = inventory.getStack(0);
            if (!itemStack.isEmpty() && itemStack.isEnchantable()) {
                this.context.run((world, pos) -> {
                    // Set fixed experience levels required for each enchantment option
                    this.enchantmentPower[0] = 50; // First enchantment requires 50 XP
                    this.enchantmentPower[1] = 75; // Second enchantment requires 75 XP
                    this.enchantmentPower[2] = 100; // Third enchantment requires 100 XP
                    for (int j = 0; j < 3; ++j) {
                        this.enchantmentId[j] = -1;
                        this.enchantmentLevel[j] = -1;
                    }
                    for (int j = 0; j < 3; ++j) {
                        if (this.enchantmentPower[j] > 0) {
                            List<EnchantmentLevelEntry> list = this.generateEnchantments(itemStack, j, this.enchantmentPower[j]);
                            if (list != null && !list.isEmpty()) {
                                EnchantmentLevelEntry enchantmentLevelEntry = list.get(this.random.nextInt(list.size()));
                                this.enchantmentId[j] = Registries.ENCHANTMENT.getRawId(enchantmentLevelEntry.enchantment);
                                this.enchantmentLevel[j] = enchantmentLevelEntry.level;
                            } else {
                                // Set random fallback enchantments with appropriate levels
                                List<Enchantment> enchantments = new ArrayList<>();
                                // Filter enchantments that are appropriate for the itemStack
                                Registries.ENCHANTMENT.iterator().forEachRemaining(enchantment -> {
                                    if (enchantment.isAcceptableItem(itemStack)) {
                                        enchantments.add(enchantment);
                                    }
                                });

                                Enchantment fallbackEnchantment;
                                int fallbackLevel = 1;

                                // Retry mechanism if no valid enchantments are found for the item
                                if (!enchantments.isEmpty()) {
                                    fallbackEnchantment = enchantments.get(this.random.nextInt(enchantments.size()));
                                } else {
                                    // If no valid enchantments, fall back to any enchantment
                                    List<Enchantment> allEnchantments = new ArrayList<>();
                                    Registries.ENCHANTMENT.iterator().forEachRemaining(allEnchantments::add);
                                    fallbackEnchantment = allEnchantments.get(this.random.nextInt(allEnchantments.size()));
                                }

                                // Determine fallback enchantment level based on enchantmentPower
                                if (this.enchantmentPower[j] == 50 || this.enchantmentPower[j] == 75) {
                                    fallbackLevel = fallbackEnchantment.getMaxLevel() + 1;
                                } else if (this.enchantmentPower[j] == 100) {
                                    fallbackLevel = fallbackEnchantment.getMaxLevel() + 2;
                                }

                                // Assign the selected fallback enchantment and its level
                                this.enchantmentId[j] = Registries.ENCHANTMENT.getRawId(fallbackEnchantment);
                                this.enchantmentLevel[j] = fallbackLevel;
                            }
                        }
                    }
                    this.sendContentUpdates();
                });
            } else {
                for (int i = 0; i < 3; ++i) {
                    this.enchantmentPower[i] = 0;
                    this.enchantmentId[i] = -1;
                    this.enchantmentLevel[i] = -1;
                }
            }
        }
    }


    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id >= 0 && id < this.enchantmentPower.length) {
            ItemStack itemStack = this.inventory.getStack(0);  // The item being enchanted
            ItemStack itemStack2 = this.inventory.getStack(1);  // Lapis lazuli or other material
            int materialCount = id + 1;  // Material count requirement
            int xpCost = 50 + (id * 25);  // XP cost requirement

            LOGGER.info("Button clicked, ID: {}", id);

            // Check if the player has enough materials and experience
            if ((itemStack2.isEmpty() || itemStack2.getCount() < materialCount) && !player.getAbilities().creativeMode) {
                LOGGER.info("Failed due to insufficient items or not in creative mode.");
                return false;
            } else if (this.enchantmentPower[id] <= 0 || itemStack.isEmpty() || (player.experienceLevel < xpCost || player.experienceLevel < this.enchantmentPower[id]) && !player.getAbilities().creativeMode) {
                LOGGER.info("Failed due to insufficient experience levels or empty item stack.");
                return false;
            } else {
                // Apply enchantments
                this.context.run((world, pos) -> {
                    final ItemStack finalItemStack3 = itemStack.copy();  // Final or effectively final copy of the item

                    List<EnchantmentLevelEntry> list = this.generateEnchantments(finalItemStack3, id, this.enchantmentPower[id]);
                    LOGGER.info("Generated enchantments: {}", list);

                    // Apply fallback logic if no enchantments were generated
                    if (list.isEmpty()) {
                        List<Enchantment> enchantments = new ArrayList<>();
                        Registries.ENCHANTMENT.iterator().forEachRemaining(enchantments::add);

                        // Filter enchantments by what can be applied to the finalItemStack3
                        enchantments.removeIf(enchantment -> !enchantment.isAcceptableItem(finalItemStack3));

                        // Select a valid fallback enchantment
                        Enchantment fallbackEnchantment = enchantments.get(this.random.nextInt(enchantments.size()));
                        int fallbackLevel = fallbackEnchantment.getMaxLevel() + (this.enchantmentPower[id] == 100 ? 2 : 1);

                        // Add the fallback enchantment to the list
                        list.add(new EnchantmentLevelEntry(fallbackEnchantment, fallbackLevel));
                        LOGGER.info("Fallback enchantment added: {}", list);
                    }

                    // Apply enchantment costs (XP, Lapis, etc.)
                    player.applyEnchantmentCosts(finalItemStack3, xpCost);

                    // Check if item is a book and needs to convert to an enchanted book
                    boolean isBook = finalItemStack3.isOf(Items.BOOK);
                    ItemStack itemStack3 = finalItemStack3;
                    if (isBook) {
                        itemStack3 = new ItemStack(Items.ENCHANTED_BOOK);
                        NbtCompound nbtCompound = itemStack.getNbt();
                        if (nbtCompound != null) {
                            itemStack3.setNbt(nbtCompound.copy());
                        }
                        this.inventory.setStack(0, itemStack3);
                    }

                    // Apply all enchantments from the list to the item
                    for (EnchantmentLevelEntry entry : list) {
                        if (isBook) {
                            EnchantedBookItem.addEnchantment(itemStack3, entry);
                        } else {
                            itemStack3.addEnchantment(entry.enchantment, entry.level);
                            LOGGER.info("Applied enchantment: {} Level: {}", entry.enchantment, entry.level);
                        }
                    }

                    // Update inventory to reflect the enchanted item
                    this.inventory.setStack(0, itemStack3);
                    this.sendContentUpdates();

                    // Deduct material costs (if not in creative mode)
                    if (!player.getAbilities().creativeMode) {
                        itemStack2.decrement(materialCount);  // Reduce the amount of Lapis or material used
                        if (itemStack2.isEmpty()) {
                            this.inventory.setStack(1, ItemStack.EMPTY);
                        }
                    }

                    // Trigger stats, advancements, and sound effects
                    player.incrementStat(Stats.ENCHANT_ITEM);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ENCHANTED_ITEM.trigger((ServerPlayerEntity) player, itemStack3, xpCost);
                    }

                    // Mark inventory as dirty to refresh enchantments and re-seed for new options
                    this.inventory.markDirty();
                    this.seed.set(player.getEnchantmentTableSeed());
                    this.onContentChanged(this.inventory);

                    // Play the enchantment sound
                    world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
                });

                return true;
            }
        } else {
            Util.error(player.getName() + " pressed invalid button id: " + id);
            return false;
        }
    }

    private List<EnchantmentLevelEntry> generateEnchantments(ItemStack stack, int slot, int level) {
        this.random.setSeed((long)(this.seed.get() + slot));
        List<EnchantmentLevelEntry> list = new ArrayList<>();
        List<EnchantmentLevelEntry> enchantments = EnchantmentHelper.generateEnchantments(this.random, stack, level, false);

        if (level >= 100) {
            // Ensure 1 enchantment with max level + 2
            if (enchantments.size() > 0) {
                EnchantmentLevelEntry entry1 = enchantments.get(0);
                list.add(new EnchantmentLevelEntry(entry1.enchantment, entry1.level + 2));
            }
            // Ensure 2nd enchantment with max level
            if (enchantments.size() > 1) {
                EnchantmentLevelEntry entry2 = enchantments.get(1);
                list.add(entry2);
            }
            // Ensure 3rd enchantment with max level
            if (enchantments.size() > 2) {
                EnchantmentLevelEntry entry3 = enchantments.get(2);
                list.add(entry3);
            }
        } else if (level >= 75) {
            // Ensure 2 enchantments, one with max level + 1 and the other with max level
            if (enchantments.size() > 0) {
                EnchantmentLevelEntry entry1 = enchantments.get(0);
                list.add(new EnchantmentLevelEntry(entry1.enchantment, entry1.level + 1));
            }
            if (enchantments.size() > 1) {
                EnchantmentLevelEntry entry2 = enchantments.get(1);
                list.add(entry2);
            }
            // Ensure 3rd enchantment with max level (if needed)
            if (enchantments.size() > 2) {
                EnchantmentLevelEntry entry3 = enchantments.get(2);
                list.add(entry3);
            }
        } else if (level >= 50) {
            // Ensure 1 enchantment with max level + 1
            if (enchantments.size() > 0) {
                EnchantmentLevelEntry entry = enchantments.get(0);
                list.add(new EnchantmentLevelEntry(entry.enchantment, entry.level + 1));
            }
        } else {
            // Default behavior
            list = EnchantmentHelper.generateEnchantments(this.random, stack, level, false);
        }

        if (stack.isOf(Items.BOOK) && list.size() > 1) {
            list.remove(this.random.nextInt(list.size()));
        }

        return list;
    }


    public int getLapisCount() {
        ItemStack itemStack = this.inventory.getStack(1);
        return itemStack.isEmpty() ? 0 : itemStack.getCount();
    }

    public int getSeed() {
        return this.seed.get();
    }

    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> {
            this.dropInventory(player, this.inventory);
        });
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModBlocks.SUPER_ENCH_TABLE);
    }

    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = (Slot)this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot == 0) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot == 1) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemStack2.isOf(ModItems.ULTRIS)) {
                if (!this.insertItem(itemStack2, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (((Slot)this.slots.get(0)).hasStack() || !((Slot)this.slots.get(0)).canInsert(itemStack2)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemStack3 = itemStack2.copyWithCount(1);
                itemStack2.decrement(1);
                ((Slot)this.slots.get(0)).setStack(itemStack3);
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }
}
