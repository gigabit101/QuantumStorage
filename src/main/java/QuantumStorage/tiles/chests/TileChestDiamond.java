package QuantumStorage.tiles.chests;

import QuantumStorage.client.AdvancedGui;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.tiles.AdvancedTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.common.util.RebornCraftingHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 03/04/2017.
 */
public class TileChestDiamond extends AdvancedTileEntity
{
    public TileChestDiamond()
    {
        this.inv = new ItemStackHandler(91);
    }
    
    @Override
    public String getName()
    {
        return "chest_diamond";
    }
    
    protected static final AxisAlignedBB CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CHEST_AABB;
    }
    
    @Override
    public List<Slot> getSlots()
    {
        List<Slot> slots = new ArrayList<>();
        int i = 0;
        for (int l = 0; l < 7; ++l)
        {
            for (int j1 = 0; j1 < 13; ++j1)
            {
                slots.add(new SlotItemHandler(getInv(), i, 8 + j1 * 18, 11 + l * 18));
                i++;
            }
        }
        return slots;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop, int xSize, int ySize, AdvancedGui gui)
    {
        getBuilder().drawDefaultBackground(gui, guiLeft, guiTop, xSize, ySize);
        getBuilder().drawPlayerSlots(gui, guiLeft + xSize / 2, guiTop + 150, true);
        if (getSlots() != null)
        {
            for (Slot s : getSlots())
            {
                getBuilder().drawSlot(gui, guiLeft + s.xPos - 1, guiTop + s.yPos - 1);
            }
        }
    }
    
    @Override
    public int getXSize()
    {
        return 250;
    }
    
    @Override
    public int getYsize()
    {
        return 240;
    }
    
    @Override
    public int inventoryOffsetX()
    {
        return 45;
    }
    
    @Override
    public int inventoryOffsetY()
    {
        return 151;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileChestDiamond();
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
        return ModBlocks.CHEST_DIAMOND;
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
    public void addRecipe()
    {
        if (!ConfigQuantumStorage.disableChests)
        {
            RebornCraftingHelper.addShapedOreRecipe(new ItemStack(ModBlocks.CHEST_DIAMOND),
                    "WXW",
                    "ICI",
                    "WXW",
                    'W', "plankWood",
                    'X', "chest",
                    'I', new ItemStack(Items.DIAMOND),
                    'C', new ItemStack(ModBlocks.CHEST_GOLD));
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced)
    {
        tooltip.add("Keeps Inventory when broken");
        super.addInformation(stack, world, tooltip, advanced);
    }
}
