package QuantumStorage.block.qst;

import net.minecraft.block.material.Material;

public class BlockQuantumTankMk1 extends BlockQuantumTank
{
	public BlockQuantumTankMk1(Material material) 
	{
		super(material);
		setUnlocalizedName("quantumtank");
	}
//	
//	@Override
//	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) 
//	{
//		if (!player.isSneaking())
//			player.openGui(QuantumStorage.INSTANCE, GuiHandler.tank, world, pos.getX(), pos.getY(), pos.getZ());
//		return true;	
//	}


//	@Override
//	public TileEntity createNewTileEntity(World world, int p_149915_2_) 
//	{
//		return new TileQuantumTankMk1();
//	}
	
//	@Override
//	public void breakBlock(World world, BlockPos pos, IBlockState state) 
//	{
//		TileEntity te = world.getTileEntity(pos);
//		if(te instanceof TileQuantumTankMk1)
//		{
//			if (((TileQuantumTank) te).tank.getFluid() != null)
//			{
//				float xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
//				float yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
//				float zOffset = world.rand.nextFloat() * 0.8F + 0.1F;
//						
//				ItemStack stacknbt = ((TileQuantumTankMk1) te).getDropWithNBT();
//				int amountToDrop = Math.min(world.rand.nextInt(21) + 10, stacknbt.stackSize);
//
//				EntityItem entityitem = new EntityItem(world,
//						x + xOffset, y + yOffset, z + zOffset,
//						stacknbt.splitStack(amountToDrop));
//				world.spawnEntityInWorld(entityitem);
//			}
//			else 
//			{
//				float xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
//				float yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
//				float zOffset = world.rand.nextFloat() * 0.8F + 0.1F;
//				ItemStack stack = new ItemStack(ModBlocks.QuantumTankMk1);
//				
//				EntityItem entityitem = new EntityItem(world,
//						x + xOffset, y + yOffset, z + zOffset, stack);
//				world.spawnEntityInWorld(entityitem);
//			}
//		}
//	}
}
