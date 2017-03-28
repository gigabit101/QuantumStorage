package QuantumStorage.tiles;

import QuantumStorage.client.AdvancedGui;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.inventory.DsuInventoryHandler;
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
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.common.util.CraftingHelper;
import reborncore.common.util.ItemUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public class TileQuantumStorageUnit extends AdvancedTileEntity implements ITickable
{
    public DsuInventoryHandler inv = new DsuInventoryHandler();

    int STORAGE = 0;
    int INPUT = 1;
    int OUTPUT = 2;

    @Override
    public void update()
    {
        try
        {
            if (inv.getStackInSlot(INPUT) != ItemStack.EMPTY)
            {
                if (inv.getStackInSlot(STORAGE) == ItemStack.EMPTY || inv.getStackInSlot(STORAGE).getCount() == 0)
                {
                    inv.setStackInSlot(STORAGE, inv.getStackInSlot(INPUT).copy());
                    inv.setStackInSlot(INPUT, ItemStack.EMPTY);
                    sync();
                } else if (inv.getStackInSlot(STORAGE).getCount() != 0 && ItemUtils.isItemEqual(inv.getStackInSlot(INPUT), inv.getStackInSlot(STORAGE), true, true))
                {
                    inv.getStackInSlot(STORAGE).grow(inv.getStackInSlot(INPUT).getCount());
                    inv.setStackInSlot(INPUT, ItemStack.EMPTY);
                    sync();
                }
            }

            if (inv.getStackInSlot(STORAGE) != ItemStack.EMPTY)
            {
                int size = inv.getStackInSlot(STORAGE).getMaxStackSize();
                if (inv.getStackInSlot(OUTPUT) == ItemStack.EMPTY || inv.getStackInSlot(OUTPUT).getCount() == 0)
                {
                    if (inv.getStackInSlot(STORAGE).getCount() >= size)
                    {
                        inv.setStackInSlot(OUTPUT, inv.getStackInSlot(STORAGE).copy());
                        inv.getStackInSlot(OUTPUT).setCount(size);
                        inv.getStackInSlot(STORAGE).shrink(size);
                        sync();
                    } else
                    {
                        inv.setStackInSlot(OUTPUT, inv.getStackInSlot(STORAGE));
                        inv.setStackInSlot(STORAGE, ItemStack.EMPTY);
                        sync();
                    }
                }
                if (inv.getStackInSlot(STORAGE).getCount() != 0 && ItemUtils.isItemEqual(inv.getStackInSlot(STORAGE), inv.getStackInSlot(OUTPUT), true, true) && inv.getStackInSlot(OUTPUT).getCount() <= size - 1)
                {
                    inv.getStackInSlot(OUTPUT).grow(1);
                    inv.getStackInSlot(STORAGE).shrink(1);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        sync();
    }

    @Override
    public String getName()
    {
        return "quantum_storage_unit";
    }

    @Override
    public DsuInventoryHandler getInv()
    {
        return inv;
    }

    @Override
    public int getInvSize()
    {
        return 0;
    }

    @Override
    public List<Slot> getSlots()
    {
        List<Slot> slots = new ArrayList<>();
        slots.add(new SlotItemHandler(inv, 1, 80, 20));
        slots.add(new SlotItemHandler(inv, 2, 80, 70));
        return slots;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileQuantumStorageUnit();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        openGui(playerIn, (AdvancedTileEntity) worldIn.getTileEntity(pos));
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY, GuiContainer gui, int guiLeft, int guiTop)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY, gui, guiLeft, guiTop);
        if (this.getInv().getStackInSlot(STORAGE) != ItemStack.EMPTY && this.getInv().getStackInSlot(OUTPUT) != null)
        {
            this.builder.drawBigBlueBar((AdvancedGui) gui, 31, 43, this.getInv().getStackInSlot(STORAGE).getCount() + this.getInv().getStackInSlot(OUTPUT).getCount(), Integer.MAX_VALUE, mouseX - guiLeft, mouseY - guiTop, "Stored", getInv().getStackInSlot(OUTPUT).getDisplayName(),
                    formatQuantity(this.getInv().getStackInSlot(STORAGE).getCount() + this.getInv().getStackInSlot(OUTPUT).getCount()));
        }
        if (this.getInv().getStackInSlot(STORAGE) == ItemStack.EMPTY && this.getInv().getStackInSlot(OUTPUT) != ItemStack.EMPTY)
        {
            this.builder.drawBigBlueBar((AdvancedGui) gui, 31, 43, this.getInv().getStackInSlot(OUTPUT).getCount(), Integer.MAX_VALUE, mouseX - guiLeft, mouseY - guiTop, "Stored", getInv().getStackInSlot(OUTPUT).getDisplayName(),
                    formatQuantity(this.getInv().getStackInSlot(STORAGE).getCount() + this.getInv().getStackInSlot(OUTPUT).getCount()));
        }
    }

    //TODO move to RC
    public static final DecimalFormat QUANTITY_FORMATTER = new DecimalFormat("####0.#", DecimalFormatSymbols.getInstance(Locale.US));

    //TODO move to RC
    public static String formatQuantity(int qty)
    {
        if (qty >= 1000000)
        {
            return QUANTITY_FORMATTER.format((float) qty / 1000000F) + "M";
        }
        else if (qty >= 1000)
        {
            return QUANTITY_FORMATTER.format((float) qty / 1000F) + "K";
        }
        return String.valueOf(qty);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        inv.deserializeNBT(compound);
    }

    @Override
    public Block getBlock()
    {
        return ModBlocks.DSU;
    }

    @Override
    public void addRecipe()
    {
        CraftingHelper.addShapedOreRecipe(new ItemStack(ModBlocks.DSU),
                "OOO",
                "ICI",
                "III",
                'I', new ItemStack(Items.IRON_INGOT),
                'O', new ItemStack(Blocks.OBSIDIAN),
                'C', "chest");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.merge(inv.serializeNBT());
        return compound;
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
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv);
        }
        return super.getCapability(capability, facing);
    }
}
