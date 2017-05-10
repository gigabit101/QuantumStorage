package QuantumStorage.tiles;

import QuantumStorage.api.RecipeQuantumCrafter;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.init.ModItems;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.common.util.CraftingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 10/05/2017.
 */
public class TileQuantumCrafter extends AdvancedTileEntity implements ITickable
{
    int progress = 0;

    @Override
    public void update()
    {
        final ItemStackHandler inventory = (ItemStackHandler) world.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        if(!world.isRemote)
        {
            if (!inventory.getStackInSlot(0).isEmpty() && inventory.getStackInSlot(1).isEmpty())
            {
                ItemStack input = inventory.getStackInSlot(0);

                if(RecipeQuantumCrafter.getOutputFrom(input) != null)
                {
                    progress++;
                    if(progress >= RecipeQuantumCrafter.getTimeFromStack(input))
                    {
                        inventory.setStackInSlot(1, RecipeQuantumCrafter.getOutputFrom(input));
                        inventory.setStackInSlot(0, ItemStack.EMPTY);
                        progress = 0;
                    }
                }
                sync();
            }
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY, GuiContainer gui, int guiLeft, int guiTop)
    {
        int max = 100;

        if(RecipeQuantumCrafter.getTimeFromStack(inv.getStackInSlot(0)) != 0)
        {
            max = RecipeQuantumCrafter.getTimeFromStack(inv.getStackInSlot(0));
        }
        getBuilder().drawProgressBar(gui, progress, max, 80, 40, mouseX - guiLeft, mouseY - guiTop);

        getBuilder().drawString(gui, TextFormatting.BLACK + "Progress = " + progress + " / " + max, 8, 8);

        super.drawGuiContainerForegroundLayer(mouseX, mouseY, gui, guiLeft, guiTop);
    }

    @Override
    public String getName()
    {
        return "quantumcrafter";
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
        slots.add(new SlotItemHandler(getInv(), 0, 40, 40));
        slots.add(new SlotItemHandler(getInv(), 1, 120, 40));
        return slots;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileQuantumCrafter();
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
        return ModBlocks.QUANTUM_CRAFTER;
    }

    @Override
    public void addRecipe()
    {
        if(!ConfigQuantumStorage.disableQuantumCrafter)
        {
            CraftingHelper.addShapedOreRecipe(new ItemStack(ModBlocks.QUANTUM_CRAFTER),
                    "IXI",
                    "ICI",
                    "III",
                    'I', new ItemStack(Items.IRON_INGOT),
                    'X', new ItemStack(Items.CLOCK),
                    'C', "chest");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.merge(inv.serializeNBT());
        compound.setInteger("progress", progress);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        inv.deserializeNBT(compound);
        progress = compound.getInteger("progress");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getInv());
        }
        return super.getCapability(capability, facing);
    }
}
