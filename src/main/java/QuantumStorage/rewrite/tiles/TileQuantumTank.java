package QuantumStorage.rewrite.tiles;

import QuantumStorage.init.ModBlocks;
import QuantumStorage.rewrite.AdvancedTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.common.util.CraftingHelper;
import reborncore.common.util.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public class TileQuantumTank extends AdvancedTileEntity
{
    Tank tank = new Tank("", Integer.MAX_VALUE, this);

    @Override
    public String getName()
    {
        return "quantum_tank";
    }

    @Override
    public int getInvSize()
    {
        return 2;
    }

    @Override
    public List<Slot> getSlots()
    {
        List<Slot> slots = new ArrayList<>();
        slots.add(new SlotItemHandler(inv, 0, 100, 20));
        slots.add(new SlotItemHandler(inv, 1, 100, 60));
        return slots;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileQuantumTank();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(fillBlockWithFluid(worldIn, pos, playerIn, hand, side) == FluidActionResult.FAILURE)
            openGui(playerIn, (AdvancedTileEntity) worldIn.getTileEntity(pos));
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY, GuiContainer gui, int guiLeft, int guiTop)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY, gui, guiLeft, guiTop);
        getBuilder().drawTank(gui, tank, 10, 10, 5.0F, 20, 80, mouseX - guiLeft, mouseY - guiTop);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        tank.readFromNBT(compound);
        getInv().deserializeNBT(compound);
    }

    @Override
    public Block getBlock()
    {
        return ModBlocks.TANK;
    }

    @Override
    public void addRecipe()
    {
        CraftingHelper.addShapedOreRecipe(new ItemStack(ModBlocks.TANK),
                "OOO",
                "IBI",
                "III",
                'I', new ItemStack(Items.IRON_INGOT),
                'O', new ItemStack(Blocks.OBSIDIAN),
                'B', new ItemStack(Items.BUCKET));
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
        super.readFromNBTWithoutCoords(compound);
        tank.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.merge(tank.writeToNBT(compound));
        compound.merge(getInv().serializeNBT());
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }
        return super.getCapability(capability, facing);
    }

    public FluidActionResult fillBlockWithFluid(World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side)
    {
        try
        {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile == null || !tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side))
            {
                return FluidActionResult.FAILURE;
            }

            IFluidHandler fluidHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
            FluidActionResult inserted = FluidUtil.interactWithFluidHandler(playerIn.getHeldItem(hand), fluidHandler, playerIn);
            if(inserted != FluidActionResult.FAILURE)
            {
                playerIn.setHeldItem(hand, inserted.getResult());
            }

            if (!worldIn.isRemote)
            {
                sync();
            }
            return inserted;
        }
        catch (Exception e) {}
        return FluidActionResult.FAILURE;
    }
}
