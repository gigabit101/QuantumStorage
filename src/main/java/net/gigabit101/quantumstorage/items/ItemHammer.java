package net.gigabit101.quantumstorage.items;

import net.gigabit101.quantumstorage.client.CreativeTabQuantumStorage;
import net.gigabit101.quantumstorage.tiles.TileController;
import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ItemHammer extends Item
{
    public ItemHammer()
    {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).tab(CreativeTabQuantumStorage.INSTANCE));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if(!stack.hasTag())
        {
            stack.setTag(new CompoundTag());
            stack.getTag().putString("mode", "link");
        }
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity)
    {
        if(entity.isCrouching())
        {
            switchMode(stack);
            return true;
        }
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        ItemStack hammerStack = context.getItemInHand();

        if(!context.getLevel().isClientSide)
        {
            if(getMode(hammerStack).equalsIgnoreCase("link"))
            {
                if (isBlockValid(context.getLevel(), context.getClickedPos())) {
                    BlockPos blockPos = context.getClickedPos();

                    if (!hammerStack.hasTag()) {
                        hammerStack.setTag(new CompoundTag());
                    }
                    hammerStack.getTag().putInt("X", blockPos.getX());
                    hammerStack.getTag().putInt("Y", blockPos.getY());
                    hammerStack.getTag().putInt("Z", blockPos.getZ());
                    context.getPlayer().sendMessage(new TextComponent("X :" + blockPos.getX() + "Y : " + blockPos.getY() + "Z : " + blockPos.getZ()), Util.NIL_UUID);
                    return InteractionResult.PASS;
                } else if (context.getLevel().getBlockEntity(context.getClickedPos()) != null && context.getLevel().getBlockEntity(context.getClickedPos()) instanceof TileController) {
                    if (!hammerStack.hasTag()) return super.useOn(context);

                    TileController tileController = (TileController) context.getLevel().getBlockEntity(context.getClickedPos());
                    BlockPos hammerPos = new BlockPos(hammerStack.getTag().getInt("X"), hammerStack.getTag().getInt("Y"), hammerStack.getTag().getInt("Z"));
                    boolean connected = tileController.connectTileToController(hammerPos);
                    context.getPlayer().sendMessage(new TextComponent("Connected: " + connected), Util.NIL_UUID);
                    clearConnections(hammerStack);
                    return InteractionResult.PASS;
                }
            }
            else if(getMode(hammerStack).equalsIgnoreCase("lock"))
            {
                if(context.getLevel().getBlockEntity(context.getClickedPos()) instanceof TileQsu)
                {
                    TileQsu tileQsu = (TileQsu) context.getLevel().getBlockEntity(context.getClickedPos());
                    if(tileQsu != null)
                    {
                        if (!tileQsu.isLocked)
                        {
                            tileQsu.setLocked(tileQsu);
                            return InteractionResult.PASS;
                        }
                        tileQsu.setUnlocked(tileQsu);
                        return InteractionResult.PASS;
                    }
                }
            }
        }
        return super.useOn(context);
    }

    public void clearConnections(ItemStack stack)
    {
        if(stack.hasTag())
        {
            stack.removeTagKey("X");
            stack.removeTagKey("Y");
            stack.removeTagKey("Z");
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
    public boolean isFoil(ItemStack stack)
    {
        return stack.hasTag() && stack.getTag().get("X") != null;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        if(stack.hasTag())
        {
            if(stack.getTag().get("mode") != null)
            {
                tooltip.add(new TextComponent(ChatFormatting.DARK_PURPLE + "Mode: " + ChatFormatting.BLUE + getMode(stack).toUpperCase()));
            }
            if(stack.getTag().get("X") != null)
            {
                BlockPos pos = new BlockPos(stack.getTag().getInt("X"), stack.getTag().getInt("Y"), stack.getTag().getInt("Z"));
                tooltip.add(new TextComponent("X :" + pos.getX() + " Y : " + pos.getY() + " Z : " + pos.getZ()));
            }
        }
        tooltip.add(new TextComponent("Sneak Left-Click to change mode"));
    }

    public boolean isBlockValid(Level world, BlockPos pos)
    {
        return (pos != null && world.getBlockEntity(pos) != null && world.getBlockEntity(pos) instanceof TileQsu);
    }
}
