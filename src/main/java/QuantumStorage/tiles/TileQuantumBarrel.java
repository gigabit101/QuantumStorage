package QuantumStorage.tiles;

import QuantumStorage.init.ModBlocks;
import QuantumStorage.inventory.BarrelInventoryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import reborncore.common.util.ItemUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Gigabit101 on 18/03/2017.
 */
@Deprecated
public class TileQuantumBarrel extends AdvancedTileEntity implements ITickable
{
    public TileQuantumBarrel()
    {
        this.inv = new BarrelInventoryHandler();
    }

    @Override
    public ItemStackHandler getInv()
    {
        return inv;
    }

    @Override
    public String getName()
    {
        return "quantum_barrel";
    }

    @Override
    public List<Slot> getSlots()
    {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileQuantumBarrel();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        final ItemStackHandler stackHandler = (ItemStackHandler) worldIn.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);

        if (!worldIn.isRemote)
        {
            if (playerIn.isSneaking())
            {
                playerIn.sendMessage(new TextComponentString(stackHandler.getStackInSlot(0).getDisplayName() + " " + stackHandler.getStackInSlot(0).getCount()));
                return true;
            }
            if (playerIn.getHeldItem(hand) != ItemStack.EMPTY)
            {
                if (stackHandler.getStackInSlot(0) == ItemStack.EMPTY || stackHandler.getStackInSlot(0).getCount() == 0)
                {
                    stackHandler.setStackInSlot(0, playerIn.getHeldItem(hand));
                    playerIn.setHeldItem(hand, ItemStack.EMPTY);
                    return true;
                } else if (ItemUtils.isItemEqual(stackHandler.getStackInSlot(0), playerIn.getHeldItem(hand), true, true))
                {
                    int amout = playerIn.getHeldItem(hand).getCount();
                    if(stackHandler.insertItem(0, playerIn.getHeldItem(hand), true).getCount() == 0)
                    {
                        stackHandler.getStackInSlot(0).grow(amout);
                        playerIn.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
            }
        }
        sync();
        return true;
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        final ItemStackHandler stackHandler = (ItemStackHandler) worldIn.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (stackHandler.getStackInSlot(0) != ItemStack.EMPTY && !worldIn.isRemote)
        {
            boolean sneeking = playerIn.isSneaking();
            ItemStack stack = stackHandler.getStackInSlot(0).copy();
            if (sneeking)
            {
                stack.setCount(1);
                stackHandler.getStackInSlot(0).shrink(1);
            } else
            {
                if (stack.getCount() >= stack.getMaxStackSize())
                {
                    stack.setCount(stack.getMaxStackSize());
                    stackHandler.getStackInSlot(0).shrink(stack.getMaxStackSize());
                } else
                {
                    stackHandler.setStackInSlot(0, ItemStack.EMPTY);
                }
            }
            worldIn.spawnEntity(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.getPosition().getZ(), stack));
            sync();
        }
    }

    @Override
    public Block getBlock()
    {
        return ModBlocks.BARREL;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.merge(inv.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        inv.deserializeNBT(compound);
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
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getInv());
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void addRecipe() {}

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced)
    {
        tooltip.add(TextFormatting.RED + "Deprecated Barrels will be removed as of 1.13");
        super.addInformation(stack, world, tooltip, advanced);
    }

    @Override
    public void update()
    {
        sync();
    }
}
