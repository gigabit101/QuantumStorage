package QuantumStorage.items;

import QuantumStorage.QuantumStorage;
import QuantumStorage.items.prefab.ItemBase;
import QuantumStorage.rewrite.tiles.TileQuantumStorageUnit;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Gigabit101 on 28/01/2017.
 */
public class ItemUpgrade extends ItemBase
{
    public static final String[] types = new String[]{"render", "void", "creative"};

    public ItemUpgrade()
    {
        setUnlocalizedName(QuantumStorage.MOD_ID + ".upgrade");
        setRegistryName("upgrade");
        setHasSubtypes(true);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileQuantumStorageUnit)
        {
            TileQuantumStorageUnit dsu = (TileQuantumStorageUnit) worldIn.getTileEntity(pos);
            int meta = player.getHeldItem(hand).getItemDamage();
            if(meta == 0)
            {
                dsu.getTileData().setInteger("facing", facing.getIndex());
            }
            if(meta == 2)//Creative
            {
                if(dsu.inv.getStackInSlot(0) != ItemStack.EMPTY)
                {//TODO change max int to config value
                    dsu.inv.getStackInSlot(0).setCount(Integer.MAX_VALUE - 64);
                }
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (int meta = 0; meta < types.length; meta++)
        {
            list.add(new ItemStack(item, 1, meta));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= types.length)
        {
            meta = 0;
        }
        return super.getUnlocalizedName() + "." + types[meta];
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("tooltip" + getUnlocalizedName(stack)));
    }
}
