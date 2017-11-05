package QuantumStorage.tiles.trashcans;

import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.tiles.AdvancedTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.common.util.RebornCraftingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 24/06/2017.
 */
public class TileTrashcan extends AdvancedTileEntity implements ITickable
{
    public TileTrashcan()
    {
        this.inv = new ItemStackHandler(1);
    }

    @Override
    public String getName()
    {
        return "trashcan";
    }

    @Override
    public List<Slot> getSlots()
    {
        List<Slot> slots = new ArrayList<>();
        slots.add(new SlotItemHandler(inv, 0, 80, 50));
        return slots;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileTrashcan();
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
        return ModBlocks.TRASH_CAN;
    }

    @Override
    public void addRecipe()
    {
        if(!ConfigQuantumStorage.disableTrashcan)
        {
            RebornCraftingHelper.addShapedOreRecipe(new ItemStack(getBlock()),
                    "SSS",
                    "CHC",
                    "CCC",
                    'C', new ItemStack(Blocks.COBBLESTONE),
                    'H', new ItemStack(Blocks.CHEST),
                    'S', new ItemStack(Blocks.STONE));
        }

    }

    @Override
    public void update()
    {
        if(!inv.getStackInSlot(0).isEmpty())
        {
            inv.setStackInSlot(0, ItemStack.EMPTY);
        }
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
}
