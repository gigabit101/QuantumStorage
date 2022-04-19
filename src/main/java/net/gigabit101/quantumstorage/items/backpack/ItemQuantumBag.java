package net.gigabit101.quantumstorage.items.backpack;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.api.IColorable;
import net.gigabit101.quantumstorage.client.CreativeTabQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerBag;
import net.gigabit101.quantumstorage.items.prefab.ItemBase;
import com.google.common.collect.Maps;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ItemQuantumBag extends ItemBase implements IColorable, MenuProvider
{
    private static final Map<DyeColor, ItemQuantumBag> COLOR_DYE_ITEM_MAP = Maps.newEnumMap(DyeColor.class);
    private final DyeColor dyeColor;
    protected final int colour;

    public ItemQuantumBag(DyeColor dyeColor)
    {
        super(new Item.Properties().stacksTo(1).tab(CreativeTabQuantumStorage.INSTANCE));
        this.dyeColor = dyeColor;
        setRegistryName(QuantumStorage.MOD_ID, "quantum_bag_" + dyeColor.getName());
        float[] vals = dyeColor.getTextureDiffuseColors();
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++)
            rgb[i] = (int) (255 * vals[i]);
        int value = ((255 & 0xFF) << 24) | ((rgb[0] & 0xFF) << 16) | ((rgb[1] & 0xFF) << 8) | ((rgb[2] & 0xFF) << 0);
        colour = value;
    }

    @Override
    public int getColor(ItemStack stack, int tint)
    {
        return colour;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand)
    {
        if (!world.isClientSide && player.isCrouching())
        {
            ItemStack bagStack = player.getItemInHand(hand);
            ItemQuantumBag bag = (ItemQuantumBag) bagStack.getItem();

            toggleActive(bagStack);

            player.sendMessage(new net.minecraft.network.chat.TextComponent("AutoPickup is " + format(bag.isActive(bagStack))), Util.NIL_UUID);
            return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
        }
        if (!world.isClientSide) NetworkHooks.openGui((ServerPlayer) player, this);
        return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
    }

    public void toggleActive(ItemStack stack)
    {
        if(!stack.hasTag())
        {
            stack.setTag(new CompoundTag());
            stack.getTag().putBoolean("active", false);
        }

        if(stack.hasTag())
        {
            boolean current = stack.getTag().getBoolean("active");
            stack.getTag().putBoolean("active", !current);
        }
    }

    public boolean isActive(ItemStack stack)
    {
        if(!stack.hasTag()) return false;
        return stack.getTag().getBoolean("active");
    }

    public static ItemStackHandler getHandlerForContainer(ItemStack stack)
    {
        if (stack.isEmpty()) return null;
        ItemStackHandler handler = new ItemStackHandler(104);
        if (stack.hasTag()) handler.deserializeNBT(stack.getTag().getCompound("inv"));
        return handler;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<net.minecraft.network.chat.Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(stack.hasTag())
        {
            tooltip.add(new TextComponent("Auto-Pickup: " + format(stack.getTag().getBoolean("active"))));
        }
    }


    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
    {
        return new ContainerBag(id, inv);
    }


    public String format(boolean b)
    {
        if(b) return ChatFormatting.GREEN + "Enabled";
        return ChatFormatting.RED + "Disabled";
    }

    @Override
    public Component getDisplayName()
    {
        return new TextComponent("Quantum backpack");
    }
}
