package QuantumStorage.tiles;

import QuantumStorage.client.AdvancedGui;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.inventory.DsuInventoryHandler;
import QuantumStorage.inventory.slot.SlotOutputItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.common.util.ItemUtils;
import reborncore.common.util.RebornCraftingHelper;

import javax.annotation.Nullable;
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
    int STORAGE = 0;
    int INPUT = 1;
    int OUTPUT = 2;

    public TileQuantumStorageUnit()
    {
        this.inv = new DsuInventoryHandler();
    }

    @Override
    public void update()
    {
        try
        {
            if (!inv.getStackInSlot(INPUT).isEmpty())
            {
                if (inv.getStackInSlot(STORAGE).isEmpty())
                {
                    inv.setStackInSlot(STORAGE, inv.getStackInSlot(INPUT).copy());
                    inv.setStackInSlot(INPUT, ItemStack.EMPTY);
                } else if (!inv.getStackInSlot(STORAGE).isEmpty() && ItemUtils.isItemEqual(inv.getStackInSlot(INPUT), inv.getStackInSlot(STORAGE), true, true))
                {
                    inv.getStackInSlot(STORAGE).grow(inv.getStackInSlot(INPUT).getCount());
                    inv.setStackInSlot(INPUT, ItemStack.EMPTY);
                }
            }

            if (!inv.getStackInSlot(STORAGE).isEmpty())
            {
                int size = inv.getStackInSlot(STORAGE).getMaxStackSize();
                if (inv.getStackInSlot(OUTPUT) == ItemStack.EMPTY || inv.getStackInSlot(OUTPUT).getCount() == 0)
                {
                    if (inv.getStackInSlot(STORAGE).getCount() >= size)
                    {
                        inv.setStackInSlot(OUTPUT, inv.getStackInSlot(STORAGE).copy());
                        inv.getStackInSlot(OUTPUT).setCount(size);
                        inv.getStackInSlot(STORAGE).shrink(size);
                    } else
                    {
                        inv.setStackInSlot(OUTPUT, inv.getStackInSlot(STORAGE));
                        inv.setStackInSlot(STORAGE, ItemStack.EMPTY);
                    }
                }
                if (inv.getStackInSlot(STORAGE).getCount() != 0 && ItemUtils.isItemEqual(inv.getStackInSlot(STORAGE), inv.getStackInSlot(OUTPUT), true, true) && inv.getStackInSlot(OUTPUT).getCount() <= size - 1)
                {
                    inv.getStackInSlot(OUTPUT).grow(1);
                    inv.getStackInSlot(STORAGE).shrink(1);
                }
            }
            handleUpgrades();
            sync();
        } catch (Exception e)
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
    public List<Slot> getSlots()
    {
        List<Slot> slots = new ArrayList<>();
        slots.add(new SlotItemHandler(inv, 1, 80, 20));
        slots.add(new SlotOutputItemHandler(inv, 2, 80, 70));

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
        } else if (qty >= 1000)
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
        if (!ConfigQuantumStorage.disableQuantumStorageUnit)
        {
            RebornCraftingHelper.addShapedOreRecipe(new ItemStack(ModBlocks.DSU),
                    "OOO",
                    "ICI",
                    "III",
                    'I', new ItemStack(Items.IRON_INGOT),
                    'O', new ItemStack(Blocks.OBSIDIAN),
                    'C', new ItemStack(ModBlocks.CHEST_DIAMOND));

            RebornCraftingHelper.addShapelessRecipe(new ItemStack(ModBlocks.DSU), new ItemStack(ModBlocks.DSU));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.merge(inv.serializeNBT());
        return compound;
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv);
        }
        return super.getCapability(capability, facing);
    }

    public void handleUpgrades() {}

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced)
    {
        if (!stack.isEmpty() && stack.hasTagCompound())
        {
            if (stack.getTagCompound().getCompoundTag("tileEntity") != null)
            {
                NBTTagList tagList = stack.getTagCompound().getCompoundTag("tileEntity").getTagList("Items", Constants.NBT.TAG_COMPOUND);
                ItemStack stack1 = ItemStack.EMPTY;

                NBTTagCompound itemTags = tagList.getCompoundTagAt(0);
                NBTTagCompound itemTags2 = tagList.getCompoundTagAt(2);

                int count = itemTags.getInteger("SizeSpecial") + itemTags2.getInteger("SizeSpecial");

                stack1 = new ItemStack(itemTags);
                stack1.setCount(count);

                if(!stack1.isEmpty())
                {
                    tooltip.add(TextFormatting.GOLD + "Stored Item Type: " + stack1.getCount() + " " + stack1.getDisplayName());
                }
            }
        }
    }
}
