package QuantumStorage.tiles;

import QuantumStorage.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import reborncore.common.util.RebornCraftingHelper;

import java.util.List;

/**
 * Created by Gigabit101 on 24/06/2017.
 */
public class TileTrashcanFluid extends AdvancedTileEntity implements ITickable
{
    FluidTank tank = new FluidTank(Integer.MAX_VALUE);

    @Override
    public String getName()
    {
        return "trashcanfluid";
    }

    @Override
    public int getInvSize()
    {
        return 0;
    }

    @Override
    public List<Slot> getSlots()
    {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileTrashcanFluid();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, side))
            openGui(playerIn, (AdvancedTileEntity) worldIn.getTileEntity(pos));
        return true;
    }

    @Override
    public Block getBlock()
    {
        return ModBlocks.TRASH_CAN_FLUID;
    }

    @Override
    public void addRecipe()
    {
        RebornCraftingHelper.addShapedOreRecipe(new ItemStack(getBlock()),
                "SSS",
                "CHC",
                "CCC",
                'C', new ItemStack(Blocks.COBBLESTONE),
                'H', new ItemStack(Items.BUCKET),
                'S', new ItemStack(Blocks.STONE));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update()
    {
        if(tank.getFluid() != null)
        {
            tank.setFluid(null);
        }
    }

//    public FluidActionResult fillBlockWithFluid(World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side)
//    {
//        try
//        {
//            TileEntity tile = worldIn.getTileEntity(pos);
//            if (tile == null || !tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side))
//            {
//                return FluidActionResult.FAILURE;
//            }
//
//            IFluidHandler fluidHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
//            FluidActionResult inserted = FluidUtil.interactWithFluidHandler(playerIn.getHeldItem(hand), fluidHandler, playerIn);
//            if (inserted != FluidActionResult.FAILURE)
//            {
//                playerIn.setHeldItem(hand, inserted.getResult());
//            }
//
//            if (!worldIn.isRemote)
//            {
//                sync();
//            }
//            return inserted;
//        } catch (Exception e)
//        {
//        }
//        return FluidActionResult.FAILURE;
//    }
}
