package net.gigabit101.quantumstorage.items.backpack;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.api.IColorable;
import net.gigabit101.quantumstorage.client.CreativeTabQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerBag;
import net.gigabit101.quantumstorage.items.prefab.ItemBase;
import com.google.common.collect.Maps;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ItemQuantumBag extends ItemBase implements IColorable, INamedContainerProvider
{
    private static final Map<DyeColor, ItemQuantumBag> COLOR_DYE_ITEM_MAP = Maps.newEnumMap(DyeColor.class);
    private final DyeColor dyeColor;
    protected final int colour;

    public ItemQuantumBag(DyeColor dyeColor)
    {
        super(new Item.Properties().maxStackSize(1).group(CreativeTabQuantumStorage.INSTANCE));
        this.dyeColor = dyeColor;
        setRegistryName(QuantumStorage.MOD_ID, "quantum_bag_" + dyeColor.getTranslationKey());
        float[] vals = dyeColor.getColorComponentValues();
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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        if (!world.isRemote && player.isSneaking())
        {
            ItemStack bagStack = player.getHeldItem(hand);
            ItemQuantumBag bag = (ItemQuantumBag) bagStack.getItem();

            toggleActive(bagStack);

            player.sendStatusMessage(new StringTextComponent("AutoPickup is " + format(bag.isActive(bagStack))), true);
            return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
        }
        if (!world.isRemote) NetworkHooks.openGui((ServerPlayerEntity) player, this);
        return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
    }

    public void toggleActive(ItemStack stack)
    {
        if(!stack.hasTag())
        {
            stack.setTag(new CompoundNBT());
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
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(stack.hasTag())
        {
            tooltip.add(new StringTextComponent("AutoPickup is " + format(stack.getTag().getBoolean("active"))));
        }
    }

    @Override
    public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
    {
        return new ContainerBag(id, inv);
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent(this.getTranslationKey());
    }

    public String format(boolean b)
    {
        if(b) return TextFormatting.GREEN + "Enabled";
        return TextFormatting.RED + "Disabled";
    }
}
