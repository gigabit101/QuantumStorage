package net.gigabit101.quantumstorage.items;

import net.gigabit101.quantumstorage.client.CreativeTabQuantumStorage;
import net.gigabit101.quantumstorage.tiles.TileController;
import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemHammer extends Item
{
    public ItemHammer()
    {
        super(new Item.Properties().rarity(Rarity.EPIC).maxStackSize(1).group(CreativeTabQuantumStorage.INSTANCE));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        ItemStack hammerStack = context.getItem();

        if(!context.getWorld().isRemote)
        {
            if (isBlockValid(context.getWorld(), context.getPos()))
            {
                BlockPos blockPos = context.getPos();

                if (!hammerStack.hasTag())
                {
                    hammerStack.setTag(new CompoundNBT());
                }
                hammerStack.getTag().putInt("X", blockPos.getX());
                hammerStack.getTag().putInt("Y", blockPos.getY());
                hammerStack.getTag().putInt("Z", blockPos.getZ());
                context.getPlayer().sendStatusMessage(new StringTextComponent("X :" + blockPos.getX() + "Y : " + blockPos.getY() + "Z : " + blockPos.getZ()), true);
                return ActionResultType.PASS;
            }
            else if (context.getWorld().getTileEntity(context.getPos()) != null && context.getWorld().getTileEntity(context.getPos()) instanceof TileController)
            {
                if (!hammerStack.hasTag()) return super.onItemUse(context);

                TileController tileController = (TileController) context.getWorld().getTileEntity(context.getPos());
                BlockPos hammerPos = new BlockPos(hammerStack.getTag().getInt("X"), hammerStack.getTag().getInt("Y"), hammerStack.getTag().getInt("Z"));
                boolean connected = tileController.connectTileToController(hammerPos);
                context.getPlayer().sendStatusMessage(new StringTextComponent("" + connected), true);
                clearConnections(hammerStack);
                return ActionResultType.PASS;
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

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return stack.hasTag();
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new StringTextComponent(TextFormatting.DARK_PURPLE + "MODE: " + "LINK"));

        if(stack.hasTag())
        {
            if(stack.getChildTag("X") != null)
            {
                BlockPos pos = new BlockPos(stack.getTag().getInt("X"), stack.getTag().getInt("Y"), stack.getTag().getInt("Z"));
                tooltip.add(new StringTextComponent("X :" + pos.getX() + " Y : " + pos.getY() + " Z : " + pos.getZ()));
            }
        }
    }

    public boolean isBlockValid(World world, BlockPos pos)
    {
        return (pos != null && world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileQsu);
    }
}
