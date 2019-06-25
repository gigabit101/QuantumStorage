package QuantumStorage.items;

import QuantumStorage.QuantumStorage;
import QuantumStorage.items.prefab.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Gigabit101 on 28/03/2017.
 */
public class ItemCrate extends ItemBase
{
    public ItemCrate(Item.Properties properties)
    {
        super(properties);
        setRegistryName(QuantumStorage.MOD_ID, "crate");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
//        if (!worldIn.isRemote && playerIn.getHeldItem(handIn).hasTagCompound())
//        {
//            ItemStack crate = playerIn.getHeldItem(handIn);
//            ItemStack stack = new ItemStack(crate.getTagCompound());
//            worldIn.spawnEntity(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, stack));
//            if (crate.getCount() == 1)
//            {
//                playerIn.setHeldItem(handIn, ItemStack.EMPTY);
//            } else
//            {
//                playerIn.getHeldItem(handIn).shrink(1);
//            }
//        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag advanced)
    {
//        if (stack.hasTagCompound())
//        {
//            ItemStack stack1 = new ItemStack(stack.getTagCompound());
//            tooltip.add(TextFormatting.RED + "Stored Item: " + stack1.getCount() + " " + stack1.getDisplayName());
//        }
        super.addInformation(stack, world, tooltip, advanced);
    }
}
