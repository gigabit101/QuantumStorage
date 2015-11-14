package QuantumStorage.block.qst;

import QuantumStorage.CreativeTabQuantumStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockQuantumTank extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;
	
	public BlockQuantumTank(Material material) 
	{
		super(material);
		setHardness(2.0F);
		setCreativeTab(CreativeTabQuantumStorage.instance);
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
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		return null;
	}
	
	@Override
	protected void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack) {}
}
