package QuantumStorage.block;

import QuantumStorage.QuantumStorage;
import QuantumStorage.client.GuiHandler;
import QuantumStorage.tile.TileMultiTank;
import QuantumStorage.tile.TileQuantumTank;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * Created by Gigabit101 on 16/11/2016.
 */
public class BlockMultiTank extends BlockQuantumStorage
{
    public BlockMultiTank()
    {
        setUnlocalizedName("multitank");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileMultiTank();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!player.isSneaking())
        {
            if(!fillBlockWithFluid(world, pos, player, heldItem, side))
                player.openGui(QuantumStorage.INSTANCE, GuiHandler.multitank, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    public boolean fillBlockWithFluid(World worldIn, BlockPos pos, EntityPlayer playerIn, ItemStack heldItem, EnumFacing side)
    {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile == null || !tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side))
        {
            return false;
        }

        IFluidHandler fluidHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
        boolean inserted = FluidUtil.interactWithFluidHandler(heldItem, fluidHandler, playerIn);
        if(!worldIn.isRemote){
            TileMultiTank tank = (TileMultiTank) tile;
            tank.syncWithAll();
        }

        return inserted;
    }
}
