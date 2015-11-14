package QuantumStorage.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import QuantumStorage.block.BlockQuantumDsuMk1;
import QuantumStorage.block.BlockQuantumDsuMk2;
import QuantumStorage.block.BlockQuantumDsuMk3;
import QuantumStorage.block.tile.TileQuantumDsuMk1;
import QuantumStorage.block.tile.TileQuantumDsuMk2;
import QuantumStorage.block.tile.TileQuantumDsuMk3;
import QuantumStorage.block.tile.TileQuantumDsuMk4;
import QuantumStorage.init.ModBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemQsuUpgrade extends ItemQuantumStorage
{
	public static final String[] types = new String[] {"mk2", "mk3", "mk4"};
	private IIcon[] textures;
	
	public ItemQsuUpgrade()
	{
		setMaxStackSize(64);
		setHasSubtypes(true);
		setUnlocalizedName("quantumstorage.qsuupgrade");
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
    public IIcon getIconFromDamage(int meta) 
    {
        if (meta < 0 || meta >= textures.length) 
        {
            meta = 0;
        }
        return textures[meta];
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

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int meta = 0; meta < types.length; ++meta) 
        {
            list.add(new ItemStack(item, 1, meta));
        }
    }
    
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) 
	{
		Block block = world.getBlock(x, y, z);
		int meta = stack.getItemDamage();
		if(player.isSneaking())
		{
			if(block instanceof BlockQuantumDsuMk1 && meta == 0)
			{
				//mk1
				TileQuantumDsuMk1 te = (TileQuantumDsuMk1) world.getTileEntity(x, y, z);
				//stored items
				ItemStack storeditem = ((TileQuantumDsuMk1) te).storedItem;				
				//clear out old
				world.setBlockToAir(x, y, z);
				EntityItem eni = whatsNear(world, x, y, z);
				//Removes the old dsu drop
				if(eni != null)
					eni.setDead();
				//set to new tile
				world.setBlock(x, y, z, ModBlocks.QuantumDsuMk2);
				//new tile
				TileQuantumDsuMk2 te2 = (TileQuantumDsuMk2) world.getTileEntity(x, y, z);
				//set stack for old tile
				te2.storedItem = storeditem;
				ItemStack upgrade = stack;
				stack.stackSize -= 1;
			}
			if(block instanceof BlockQuantumDsuMk2 && meta == 1)
			{
				//mk1
				TileQuantumDsuMk2 te = (TileQuantumDsuMk2) world.getTileEntity(x, y, z);
				//stored items
				ItemStack storeditem = ((TileQuantumDsuMk2) te).storedItem;				
				//clear out old
				world.setBlockToAir(x, y, z);
				EntityItem eni = whatsNear(world, x, y, z);
				//Removes the old dsu drop
				if(eni != null)
					eni.setDead();
				//set to new tile
				world.setBlock(x, y, z, ModBlocks.QuantumDsuMk3);
				//new tile
				TileQuantumDsuMk3 te2 = (TileQuantumDsuMk3) world.getTileEntity(x, y, z);
				//set stack for old tile
				te2.storedItem = storeditem;
				ItemStack upgrade = stack;
				stack.stackSize -= 1;
			}
			if(block instanceof BlockQuantumDsuMk3 && meta == 2)
			{
				//mk1
				TileQuantumDsuMk3 te = (TileQuantumDsuMk3) world.getTileEntity(x, y, z);
				//stored items
				ItemStack storeditem = ((TileQuantumDsuMk3) te).storedItem;				
				//clear out old
				world.setBlockToAir(x, y, z);
				EntityItem eni = whatsNear(world, x, y, z);
				//Removes the old dsu drop
				if(eni != null)
					eni.setDead();
				//set to new tile
				world.setBlock(x, y, z, ModBlocks.QuantumDsuMk4);
				//new tile
				TileQuantumDsuMk4 te2 = (TileQuantumDsuMk4) world.getTileEntity(x, y, z);
				//set stack for old tile
				te2.storedItem = storeditem;
				ItemStack upgrade = stack;
				stack.stackSize -= 1;
			}
		}
		return false;
	}
	
	  List<EntityItem> getItems(World world, int X, int Y, int Z) 
	  {
		return world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(X - 5, Y - 5, Z - 5, X + 5, Y + 5, Z + 5));
	  }
	  
	  public EntityItem whatsNear(World world, int X, int Y, int Z)
	  {
	    List<EntityItem> items = getItems(world, X, Y, Z);
	    //Turns the list into single Item Entity's
	    for(EntityItem item : items) 
	    {
	    	EntityItem stack = item;  
	    	
	    	if(items != null)
	    	{
	    		return stack;
	    	}
	    }
		return null;
	  }
	  
		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) 
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			{
				if(stack != null)
				{
					int meta = stack.getItemDamage();
					list.add(StatCollector.translateToLocal(getUnlocalizedName().toLowerCase() + "." + types[meta] + ".tooltip"));
				}
			}
			else list.add(StatCollector.translateToLocal("quantumstorage.holdshiftmessage"));
		}
}
