package QuantumStorage.block;

import QuantumStorage.CreativeTabQuantumStorage;
import QuantumStorage.QuantumStorage;
import QuantumStorage.block.tile.TileQuantumDsuMk1;
import QuantumStorage.block.tile.TileQuantumDsuMk3;
import QuantumStorage.block.tile.TileQuantumDsuMk4;
import QuantumStorage.client.GuiHandler;
import QuantumStorage.init.ModBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockQuantumDsuMk3 extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;
	
	public TileQuantumDsuMk3 dsu;

	public BlockQuantumDsuMk3(Material material) 
	{
		super(material);
		setBlockName("quantumdsumk3");
		setCreativeTab(CreativeTabQuantumStorage.instance);
		setHardness(2.0F);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) 
	{
		this.blockIcon = icon.registerIcon("quantumstorage:dsuside");
		this.iconFront = icon.registerIcon("quantumstorage:dsufront");
		this.iconTop = icon.registerIcon("quantumstorage:dsutop");
		this.iconBottom = icon.registerIcon("quantumstorage:dsubottom");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) 
	{
		return metadata == 0 && side == 3 ? this.iconFront
				: side == 1 ? this.iconTop
						: side == 0 ? this.iconBottom
								: (side == 0 ? this.iconTop : (side == metadata ? this.iconFront : this.blockIcon));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) 
	{
		if (!player.isSneaking())
			player.openGui(QuantumStorage.INSTANCE, GuiHandler.dsuMk3, world, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) 
	{
		return new TileQuantumDsuMk3();
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block blockId, int meta)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileQuantumDsuMk3)
		{
			if (((TileQuantumDsuMk3) te).getStackInSlot(1) != null){
				float xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
				float yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
				float zOffset = world.rand.nextFloat() * 0.8F + 0.1F;
						
				ItemStack stacknbt = ((TileQuantumDsuMk3) te).getDropWithNBT();
				int amountToDrop = Math.min(world.rand.nextInt(21) + 10, stacknbt.stackSize);

				EntityItem entityitem = new EntityItem(world,
						x + xOffset, y + yOffset, z + zOffset,
						stacknbt.splitStack(amountToDrop));
				world.spawnEntityInWorld(entityitem);
			}
			else 
			{
				float xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
				float yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
				float zOffset = world.rand.nextFloat() * 0.8F + 0.1F;
				ItemStack stack = new ItemStack(ModBlocks.QuantumDsuMk3);
				
				EntityItem entityitem = new EntityItem(world,
						x + xOffset, y + yOffset, z + zOffset, stack);
				world.spawnEntityInWorld(entityitem);
			}
		}
	}
	@Override
	protected void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack) {}
}
