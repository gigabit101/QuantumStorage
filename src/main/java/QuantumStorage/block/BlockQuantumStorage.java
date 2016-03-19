package QuantumStorage.block;

import QuantumStorage.CreativeTabQuantumStorage;
import me.modmuss50.jsonDestroyer.api.ITexturedBlock;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import reborncore.RebornCore;

public class BlockQuantumStorage extends BlockContainer implements ITexturedBlock
{
	private String top = "quantumstorage:blocks/dsutop";
	private String side = "quantumstorage:blocks/dsuside";
	private String front = "quantumstorage:blocks/dsufront";
	private String bottom = "quantumstorage:blocks/dsubottom";
	
	public BlockQuantumStorage() 
	{
		super(Material.iron);
		setCreativeTab(CreativeTabQuantumStorage.instance);
		setHardness(2.0F);
		RebornCore.jsonDestroyer.registerObject(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return null;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
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
		else 
			return front;
	}
}
