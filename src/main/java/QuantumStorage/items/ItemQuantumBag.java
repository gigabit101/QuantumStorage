package QuantumStorage.items;

import QuantumStorage.api.IColorable;
import QuantumStorage.items.prefab.ItemBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class ItemQuantumBag extends ItemBase implements IColorable
{
    public static final String[] COLOURS = new String[]{"white", "orange", "magenta", "lightBlue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};
    
    public ItemQuantumBag(Item.Properties properties)
    {
        super(properties);
        setRegistryName("quantum_bag");
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int tintIndex)
    {
        return 0;
    }

//    @Override
//    public String getUnlocalizedName(ItemStack itemStack)
//    {
//        int meta = itemStack.getItemDamage();
//        if (meta < 0 || meta >= COLOURS.length)
//        {
//            meta = 0;
//        }
//        return super.getUnlocalizedName() + "." + COLOURS[meta];
//    }


    //
//    @Override
//    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
//    {
//        if (this.isInCreativeTab(tab))
//        {
//            for (int meta = 0; meta < COLOURS.length; ++meta)
//            {
//                items.add(new ItemStack(this, 1, meta));
//            }
//        }
//    }
//
//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
//    {
//        if (!worldIn.isRemote)
//        {
//            playerIn.openGui(QuantumStorage.INSTANCE, GuiHandler.BAG_ID, worldIn, 0, 0, 0);
//        }
//        return super.onItemRightClick(worldIn, playerIn, handIn);
//    }
//
//    @Override
//    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
//    {
//        if (par1ItemStack.getItemDamage() >= EnumDyeColor.values().length)
//        {
//            return 0xFFFFFF;
//        }
//        return EnumDyeColor.byMetadata(par1ItemStack.getItemDamage()).getColorValue();
//    }
}
