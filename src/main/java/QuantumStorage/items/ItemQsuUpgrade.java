package QuantumStorage.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemQsuUpgrade extends ItemQuantumStorage
{
	public static final String[] types = new String[] {"mk2", "mk3", "mk4"};
//	private IIcon[] textures;
	
	public ItemQsuUpgrade()
	{
		setMaxStackSize(64);
		setHasSubtypes(true);
		setUnlocalizedName("quantumstorage.qsuupgrade");
	}
	
//    @Override
//    // Registers Textures For All Items
//    public void registerIcons(IIconRegister iconRegister)
//    {
//        textures = new IIcon[types.length];
//        for (int i = 0; i < types.length; ++i) 
//        {
//            textures[i] = iconRegister.registerIcon("quantumstorage:" + types[i]);
//        }
//    }

//    @Override
//    public IIcon getIconFromDamage(int meta) 
//    {
//        if (meta < 0 || meta >= textures.length) 
//        {
//            meta = 0;
//        }
//        return textures[meta];
//    }

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

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int meta = 0; meta < types.length; ++meta) 
        {
            list.add(new ItemStack(item, 1, meta));
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			if (stack != null) {
				int meta = stack.getItemDamage();
				list.add(StatCollector
						.translateToLocal(getUnlocalizedName().toLowerCase() + "." + types[meta] + ".tooltip"));
			}
		} else
			list.add(StatCollector.translateToLocal("quantumstorage.holdshiftmessage"));
	}
}
