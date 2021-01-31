package net.gigabit101.quantumstorage.items;

import net.gigabit101.quantumstorage.client.CreativeTabQuantumStorage;
import net.gigabit101.quantumstorage.tiles.TileController;
import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemHammer extends Item
{
    public ItemHammer()
    {
        super(new Item.Properties().rarity(Rarity.EPIC).maxStackSize(1).group(CreativeTabQuantumStorage.INSTANCE));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if(!stack.hasTag())
        {
            stack.setTag(new CompoundNBT());
            stack.getTag().putString("mode", "link");
        }
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity)
    {
        if(entity.isSneaking())
        {
            switchMode(stack);
            return true;
        }
        return false;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        ItemStack hammerStack = context.getItem();

        if(!context.getWorld().isRemote)
        {
            if(getMode(hammerStack).equalsIgnoreCase("link"))
            {
                if (isBlockValid(context.getWorld(), context.getPos())) {
                    BlockPos blockPos = context.getPos();

                    if (!hammerStack.hasTag()) {
                        hammerStack.setTag(new CompoundNBT());
                    }
                    hammerStack.getTag().putInt("X", blockPos.getX());
                    hammerStack.getTag().putInt("Y", blockPos.getY());
                    hammerStack.getTag().putInt("Z", blockPos.getZ());
                    context.getPlayer().sendStatusMessage(new StringTextComponent("X :" + blockPos.getX() + "Y : " + blockPos.getY() + "Z : " + blockPos.getZ()), true);
                    return ActionResultType.PASS;
                } else if (context.getWorld().getTileEntity(context.getPos()) != null && context.getWorld().getTileEntity(context.getPos()) instanceof TileController) {
                    if (!hammerStack.hasTag()) return super.onItemUse(context);

                    TileController tileController = (TileController) context.getWorld().getTileEntity(context.getPos());
                    BlockPos hammerPos = new BlockPos(hammerStack.getTag().getInt("X"), hammerStack.getTag().getInt("Y"), hammerStack.getTag().getInt("Z"));
                    boolean connected = tileController.connectTileToController(hammerPos);
                    context.getPlayer().sendStatusMessage(new StringTextComponent("Connected: " + connected), true);
                    clearConnections(hammerStack);
                    return ActionResultType.PASS;
                }
            }
            else if(getMode(hammerStack).equalsIgnoreCase("lock"))
            {
                if(context.getWorld().getTileEntity(context.getPos()) instanceof TileQsu)
                {
                    TileQsu tileQsu = (TileQsu) context.getWorld().getTileEntity(context.getPos());
                    if(tileQsu != null)
                    {
                        if (!tileQsu.isLocked)
                        {
                            tileQsu.setLocked(tileQsu);
                            return ActionResultType.PASS;
                        }
                        tileQsu.setUnlocked(tileQsu);
                        return ActionResultType.PASS;
                    }
                }
            }
        }
        return super.onItemUse(context);
    }

    public void clearConnections(ItemStack stack)
    {
        if(stack.hasTag())
        {
            stack.removeChildTag("X");
            stack.removeChildTag("Y");
            stack.removeChildTag("Z");
        }
    }

    public String getMode(ItemStack stack)
    {
        if(stack.getItem() instanceof ItemHammer)
        {
            if(stack.getTag().get("mode") != null)
            {
                return stack.getTag().getString("mode");
            }
        }
        return null;
    }

    public void switchMode(ItemStack stack)
    {
        if(stack.getItem() instanceof ItemHammer)
        {
            String currentMode = getMode(stack);
            switch (currentMode)
            {
                case "lock":
                    stack.getTag().putString("mode", "link");
                    break;
                case "link":
                    stack.getTag().putString("mode", "lock");
                    break;
            }
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return stack.hasTag() && stack.getTag().get("X") != null;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if(stack.hasTag())
        {
            if(stack.getTag().get("mode") != null)
            {
                tooltip.add(new StringTextComponent(TextFormatting.DARK_PURPLE + "Mode: " + TextFormatting.BLUE + getMode(stack).toUpperCase()));
            }
            if(stack.getTag().get("X") != null)
            {
                BlockPos pos = new BlockPos(stack.getTag().getInt("X"), stack.getTag().getInt("Y"), stack.getTag().getInt("Z"));
                tooltip.add(new StringTextComponent("X :" + pos.getX() + " Y : " + pos.getY() + " Z : " + pos.getZ()));
            }
        }
        tooltip.add(new StringTextComponent("Sneak Left-Click to change mode"));
    }

    public boolean isBlockValid(World world, BlockPos pos)
    {
        return (pos != null && world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileQsu);
    }
}
