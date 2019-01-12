package QuantumStorage.tiles;

import QuantumStorage.client.AdvancedGui;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.client.gui.builder.GuiBase;
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
        if (!FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, side))
            openGui(playerIn, (AdvancedTileEntity) worldIn.getTileEntity(pos));
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY, AdvancedGui gui, int guiLeft, int guiTop)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY, gui, guiLeft, guiTop);
        int amount = 0;
        String name = "Empty";

        if (tank.getFluid() != null)
        {
            amount = tank.getFluidAmount();
            name = tank.getFluid().getFluid().getName();
        }

        getBuilder().drawString(gui,  "Quantum Tank",  56, 8);

        getBuilder().drawBigBlueBar(gui, 30, 50, amount, tank.getCapacity(), mouseX, mouseY, "", "Fluid Type: " + name, amount + " mb " + name, GuiBase.Layer.FOREGROUND);
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
    }

    public void handleUpgrades()
    {
        if(this.getTileData().hasKey("infin_water") && this.tank.getFluid() != null && this.tank.getFluid().getFluid() == FluidRegistry.WATER)
        {
            if(tank.canFill())
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

                if(fluidamount != 0)
                {
                    tooltip.add(TextFormatting.GOLD + "Stored Fluid type: " + fluidamount + "mb " + fluidname);
                }
            }
        }
    }
}
