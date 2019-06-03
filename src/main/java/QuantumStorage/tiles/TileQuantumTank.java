package QuantumStorage.tiles;

import QuantumStorage.client.AdvancedGui;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.common.util.FluidUtils;
import reborncore.common.util.ItemUtils;
import reborncore.common.util.RebornCraftingHelper;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public class TileQuantumTank extends AdvancedTileEntity implements ITickable
{
    FluidTank tank = new FluidTank(Integer.MAX_VALUE);
    
    public TileQuantumTank() {}
    
    @Override
    public String getName()
    {
        return "quantum_tank";
    }
    
    @Override
    public List<Slot> getSlots()
    {
        return null;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileQuantumTank();
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        sync();
        if (FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, side))
        {
            return true;
        }
        else if (worldIn.getTileEntity(pos).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side))
        {
            IFluidTank handler = (IFluidTank) worldIn.getTileEntity(pos).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
            if(!playerIn.getHeldItem(hand).isEmpty() && ItemUtils.isItemEqual(playerIn.getHeldItem(hand), new ItemStack(Blocks.CONCRETE_POWDER), false, false) && handler.getFluid() != null && handler.getFluid().getFluid() == FluidRegistry.WATER)
            {
                ItemStack stackinhand = playerIn.getHeldItem(hand);
                ItemStack out = new ItemStack(Blocks.CONCRETE, 1, stackinhand.getItemDamage());
    
                playerIn.getHeldItem(hand).shrink(1);
                if (!worldIn.isRemote)
                {
                    worldIn.spawnEntity(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, out));
                }
                return true;
            }
            else
            {
                if(!playerIn.isSneaking())
                    openGui(playerIn, (AdvancedTileEntity) worldIn.getTileEntity(pos));
                return true;
            }
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY, GuiContainer gui, int guiLeft, int guiTop)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY, gui, guiLeft, guiTop);
        int amount = 0;
        String name = "Empty";
        
        if (tank.getFluid() != null)
        {
            amount = tank.getFluidAmount();
            name = tank.getFluid().getFluid().getName();
        }
        
        getBuilder().drawString(gui, "Quantum Tank", 56, 8);
        
        getBuilder().drawBigBlueBar((AdvancedGui) gui, 30, 50, amount, tank.getCapacity(), mouseX - guiLeft, mouseY - guiTop, "", "Fluid Type: " + name, amount + " mb " + name);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        tank.readFromNBT(compound);
    }
    
    @Override
    public Block getBlock()
    {
        return ModBlocks.TANK;
    }
    
    @Override
    public void addRecipe()
    {
        if (!ConfigQuantumStorage.disableQuantumTank)
        {
            RebornCraftingHelper.addShapedOreRecipe(new ItemStack(ModBlocks.TANK),
                    "OOO",
                    "IBI",
                    "III",
                    'I', new ItemStack(Items.IRON_INGOT),
                    'O', new ItemStack(Blocks.OBSIDIAN),
                    'B', new ItemStack(Items.BUCKET));
            
            RebornCraftingHelper.addShapelessRecipe(new ItemStack(ModBlocks.TANK), new ItemStack(ModBlocks.TANK));
        }
    }
    
    @Override
    public void writeToNBTWithoutCoords(NBTTagCompound tagCompound)
    {
        tagCompound = super.writeToNBT(tagCompound);
        tank.writeToNBT(tagCompound);
    }
    
    @Override
    public void readFromNBTWithoutCoords(NBTTagCompound compound)
    {
        tank.readFromNBT(compound);
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.merge(tank.writeToNBT(compound));
        return compound;
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return true;
        }
        return false;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }
        return null;
    }
    
    
    @Override
    public void update()
    {
        sync();
        handleUpgrades();
        pushFluid(world, getPos(), tank, EnumFacing.UP, EnumFacing.DOWN);
    }
    
    
    public static FluidStack pushFluid(World world, BlockPos pos, IFluidHandler fluid, EnumFacing... sides)
    {
        try
        {
            if (!world.isRemote)
            {
                for (EnumFacing side : sides)
                {
                    TileEntity tile = world.getTileEntity(pos.offset(side));
                    if (tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite()))
                    {
                        IFluidHandler other = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
                        if (other != null && !(tile instanceof TileQuantumTank))
                        {
                            return fluid.drain(other.fill(fluid.drain(1000, false), true), true);
                        }
                    }
                }
            }
        } catch (Exception e) {}
        return null;
    }
    
    public void handleUpgrades()
    {
        if (this.getTileData().hasKey("infin_water") && this.tank.getFluid() != null && this.tank.getFluid().getFluid() == FluidRegistry.WATER)
        {
            if (tank.canFill())
            {
                tank.fill(tank.getFluid(), true);
            }
        }
    }
    
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced)
    {
        if (stack != null && stack.hasTagCompound())
        {
            if (stack.getTagCompound().getCompoundTag("tileEntity") != null)
            {
                String fluidname = stack.getTagCompound().getCompoundTag("tileEntity").getString("FluidName");
                int fluidamount = stack.getTagCompound().getCompoundTag("tileEntity").getInteger("Amount");
                
                if (fluidamount != 0)
                {
                    tooltip.add(TextFormatting.GOLD + "Stored Fluid type: " + fluidamount + "mb " + fluidname);
                }
            }
        }
    }
}
