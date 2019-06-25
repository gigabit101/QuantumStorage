package QuantumStorage.tiles.chests;

import QuantumStorage.QuantumStorage;
import QuantumStorage.tiles.AdvancedTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 03/04/2017.
 */
public class TileChestDiamond extends AdvancedTileEntity implements INamedContainerProvider
{
    public TileChestDiamond()
    {
        super(QuantumStorage.tileChestDiamond);
        this.inv = new ItemStackHandler(91);
    }
    
    @Override
    public String getName()
    {
        return "chest_diamond";
    }
    
    protected static final AxisAlignedBB CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);

    @Override
    public AxisAlignedBB getRenderBoundingBox()
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
    
//    @SideOnly(Side.CLIENT)
//    @Override
//    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop, int xSize, int ySize, AdvancedGui gui)
//    {
//        getBuilder().drawDefaultBackground(gui, guiLeft, guiTop, xSize, ySize);
//        getBuilder().drawPlayerSlots(gui, guiLeft + xSize / 2, guiTop + 150, true);
//        if (getSlots() != null)
//        {
//            for (Slot s : getSlots())
//            {
//                getBuilder().drawSlot(gui, guiLeft + s.xPos - 1, guiTop + s.yPos - 1);
//            }
//        }
//    }
    
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
    public TileEntity createNewTileEntity(IBlockReader world)
    {
        return new TileChestDiamond();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
    {
        openGui(player, (AdvancedTileEntity) world.getTileEntity(pos));
        return true;
    }
    
    @Override
    public Block getBlock()
    {
        return null;
    }
    
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        compound = super.write(compound);
        compound.merge(inv.serializeNBT());
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inv.deserializeNBT(compound);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return LazyOptional.of(() -> getInv()).cast();
        }
        return super.getCapability(capability);
    }

    
    @Override
    public void addRecipe()
    {
//        if (!ConfigQuantumStorage.disableChests)
//        {
//            RebornCraftingHelper.addShapedOreRecipe(new ItemStack(ModBlocks.CHEST_DIAMOND),
//                    "WXW",
//                    "ICI",
//                    "WXW",
//                    'W', "plankWood",
//                    'X', "chest",
//                    'I', new ItemStack(Items.DIAMOND),
//                    'C', new ItemStack(ModBlocks.CHEST_GOLD));
//        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag advanced) {
//        tooltip.add("Keeps Inventory when broken");
        super.addInformation(stack, world, tooltip, advanced);
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return null;
    }
}
