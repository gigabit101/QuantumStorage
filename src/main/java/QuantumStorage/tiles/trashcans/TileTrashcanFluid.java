//package QuantumStorage.tiles.trashcans;
//
//import QuantumStorage.config.ConfigQuantumStorage;
//import QuantumStorage.init.ModBlocks;
//import QuantumStorage.tiles.AdvancedTileEntity;
//import net.minecraft.block.Block;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Blocks;
//import net.minecraft.init.Items;
//import net.minecraft.inventory.Slot;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.fluids.FluidStack;
//import net.minecraftforge.fluids.FluidTank;
//import net.minecraftforge.fluids.FluidUtil;
//import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
//import reborncore.common.util.RebornCraftingHelper;
//
//import java.util.List;
//
///**
// * Created by Gigabit101 on 24/06/2017.
// */
//public class TileTrashcanFluid extends AdvancedTileEntity
//{
//    FluidTank tank = new FluidTank(Integer.MAX_VALUE)
//    {
//        @Override
//        public int fill(FluidStack resource, boolean doFill)
//        {
//            return resource.amount;
//        }
//    };
//
//    @Override
//    public String getName()
//    {
//        return "trashcanfluid";
//    }
//
//    @Override
//    public List<Slot> getSlots()
//    {
//        return null;
//    }
//
//    @Override
//    public TileEntity createNewTileEntity(World world, int meta)
//    {
//        return new TileTrashcanFluid();
//    }
//
//    @Override
//    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
//    {
//        if (FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, side))
//        {
//            return true;
//        }
//        return true;
//    }
//
//    @Override
//    public Block getBlock()
//    {
//        return ModBlocks.TRASH_CAN_FLUID;
//    }
//
//    @Override
//    public void addRecipe()
//    {
//        if (!ConfigQuantumStorage.disableTrashcanFluid)
//        {
//            RebornCraftingHelper.addShapedOreRecipe(new ItemStack(getBlock()),
//                    "SSS",
//                    "CHC",
//                    "CCC",
//                    'C', new ItemStack(Blocks.COBBLESTONE),
//                    'H', new ItemStack(Items.BUCKET),
//                    'S', new ItemStack(Blocks.STONE_SLAB));
//        }
//    }
//
//    @Override
//    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
//    {
//        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
//        {
//            return true;
//        }
//        return super.hasCapability(capability, facing);
//    }
//
//    @Override
//    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
//    {
//        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
//        {
//            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
//        }
//        return super.getCapability(capability, facing);
//    }
//
//    @Override
//    public NBTTagCompound writeToNBT(NBTTagCompound compound)
//    {
//        return compound;
//    }
//
//    @Override
//    public void readFromNBT(NBTTagCompound compound) {}
//
//    @Override
//    public ItemStack getDropWithNBT()
//    {
//        return new ItemStack(getBlock(), 1);
//    }
//}
