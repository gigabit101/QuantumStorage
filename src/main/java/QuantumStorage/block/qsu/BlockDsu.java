package QuantumStorage.block.qsu;

import QuantumStorage.CreativeTabQuantumStorage;
import me.modmuss50.jsonDestroyer.api.ITexturedBlock;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDsu extends BlockContainer implements ITexturedBlock
{
	private String top = "quantumstorage:blocks/dsutop";
	private String side = "quantumstorage:blocks/dsuside";
	private String front = "quantumstorage:blocks/dsufront";
	private String bottom = "quantumstorage:blocks/dsubottom";
	
	protected BlockDsu(Material material) 
	{
		super(material);
		setCreativeTab(CreativeTabQuantumStorage.instance);
		setHardness(2.0F);
	}	
//	@Override
//	protected void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack) {}

	@Override
	public int getRenderType() 
	{
		return 3;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		return null;
	}

	@Override
	public int amountOfStates() 
	{
		return 1;
	}

	@Override
	public String getTextureNameFromState(IBlockState arg0, EnumFacing arg1)
	{
		if(arg1 == arg1.DOWN)
			return bottom;
		if(arg1 == arg1.UP)
			return top;
		else return front;
	}
}
