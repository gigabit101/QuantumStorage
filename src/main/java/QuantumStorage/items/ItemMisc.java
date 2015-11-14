package QuantumStorage.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemMisc extends ItemQuantumStorage
{
    public static final String[] types = new String[] {"storagecore", "blankcard"};

    private IIcon[] textures;
    
	public ItemMisc()
	{
		setMaxStackSize(64);
        setHasSubtypes(true);
        setUnlocalizedName("quantumstorage.misc");
	}
	
    @Override
    // Registers Textures For All Items
    public void registerIcons(IIconRegister iconRegister)
    {
        textures = new IIcon[types.length];
        for (int i = 0; i < types.length; ++i) 
        {
            textures[i] = iconRegister.registerIcon("quantumstorage:" + types[i]);
        }
    }

    @Override
    // Adds Texture what match's meta data
    public IIcon getIconFromDamage(int meta) {
        if (meta < 0 || meta >= textures.length) 
        {
            meta = 0;
        }
        return textures[meta];
    }

    @Override
    // gets Unlocalized Name depending on meta data
    public String getUnlocalizedName(ItemStack itemStack) 
    {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= types.length) 
        {
            meta = 0;
        }
        return super.getUnlocalizedName() + "." + types[meta];
    }

    // Adds Dusts SubItems To Creative Tab
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int meta = 0; meta < types.length; ++meta) 
        {
            list.add(new ItemStack(item, 1, meta));
        }
    }
}
