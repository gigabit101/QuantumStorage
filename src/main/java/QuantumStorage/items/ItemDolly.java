package QuantumStorage.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by Gigabit101 on 24/10/2016.
 */
public class ItemDolly extends ItemQuantumStorage
{
    public ItemDolly()
    {
        setRegistryName("dolly");
        setUnlocalizedName("dolly");
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(!hasStack(itemStackIn))
        {

        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    public boolean hasStack(ItemStack stack)
    {
        if(stack.getItemDamage() == 1)
        {
            return true;
        }
        return false;
    }

    public void setHasStack(ItemStack stack)
    {
        if(stack.getItemDamage() == 0)
            stack.setItemDamage(1);
    }
}
