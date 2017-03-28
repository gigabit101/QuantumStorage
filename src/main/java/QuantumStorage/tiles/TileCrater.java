package QuantumStorage.tiles;

import QuantumStorage.init.ModBlocks;
import QuantumStorage.init.ModItems;
import QuantumStorage.items.ItemCrate;
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
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.common.util.CraftingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 28/03/2017.
 */
public class TileCrater extends AdvancedTileEntity implements ITickable
{
    int progress = 0;

    @Override
    public void update()
    {
        if(canWork())
        {
            ++progress;
            if(progress >= 100)
            {
                ItemStack copy = getInv().getStackInSlot(1).copy();
                ItemStack blankCrate = new ItemStack(ModItems.CRATE, 1);

                NBTTagCompound compound = new NBTTagCompound();
                copy.writeToNBT(compound);
                blankCrate.setTagCompound(compound);

                getInv().getStackInSlot(0).shrink(1);
                getInv().setStackInSlot(1, ItemStack.EMPTY);
                getInv().setStackInSlot(2, blankCrate);
                progress = 0;
            }
            sync();
        }
    }

    public boolean canWork()
    {
        if(getInv().getStackInSlot(0) != ItemStack.EMPTY && getInv().getStackInSlot(0).getItem() instanceof ItemCrate && getInv().getStackInSlot(2) == ItemStack.EMPTY)
        {
            if(getInv().getStackInSlot(1) != ItemStack.EMPTY && getInv().getStackInSlot(1).getCount() == getInv().getStackInSlot(1).getMaxStackSize())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getName()
    {
        return "crater";
    }

    @Override
    public int getInvSize()
    {
        return 3;
    }

    @Override
    public List<Slot> getSlots()
    {
        List<Slot> slots = new ArrayList<>();
        slots.add(new SlotItemHandler(getInv(), 0, 40, 40));
        slots.add(new SlotItemHandler(getInv(), 1, 60, 40));
        slots.add(new SlotItemHandler(getInv(), 2, 120, 40));

        return slots;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileCrater();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        openGui(playerIn, (AdvancedTileEntity) worldIn.getTileEntity(pos));
        return true;
    }

    @Override
    public Block getBlock()
    {
        return ModBlocks.CRATER;
    }

    @Override
    public void addRecipe()
    {
        CraftingHelper.addShapedOreRecipe(new ItemStack(ModBlocks.CRATER),
                "IXI",
                "ICI",
                "IPI",
                'I', new ItemStack(Items.IRON_INGOT),
                'P', new ItemStack(Blocks.PISTON),
                'X', new ItemStack(ModItems.CRATE),
                'C', "chest");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY, GuiContainer gui, int guiLeft, int guiTop)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY, gui, guiLeft, guiTop);
        getBuilder().drawProgressBar(gui, progress, 100, 90, 42, mouseX - guiLeft, mouseY - guiTop);
        getBuilder().drawString(gui, TextFormatting.BLACK + "Crating Machine", 50, 5);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        getInv().deserializeNBT(compound);
        progress = compound.getInteger("progress");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.merge(getInv().serializeNBT());
        compound.setInteger("progress", progress);
        return compound;
    }
}
