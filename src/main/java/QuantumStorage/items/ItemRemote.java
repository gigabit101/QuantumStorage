package QuantumStorage.items;

import QuantumStorage.QuantumStorage;
import QuantumStorage.items.prefab.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRemote extends ItemBase
{
    public ItemRemote()
    {
        setUnlocalizedName(QuantumStorage.MOD_ID + ".remote");
        setRegistryName("remote");
        setMaxStackSize(1);
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking())
        {
            try
            {
                ItemStack stack = player.getHeldItem(hand);
                ItemRemote remote = (ItemRemote) stack.getItem();
                NBTTagCompound compound = remote.getNBTShareTag(stack);
                if(compound == null)
                {
                    compound = new NBTTagCompound();
                    
                    compound.setString("linkedname", worldIn.getBlockState(pos).getBlock().getLocalizedName());
                    
                    compound.setString("facing", facing.getName());
                    compound.setInteger("world", worldIn.provider.getDimension());
    
                    compound.setInteger("posX", pos.getX());
                    compound.setInteger("posY", pos.getY());
                    compound.setInteger("posZ", pos.getZ());
    
                    compound.setInteger("hitX", pos.getX());
                    compound.setInteger("hitY", pos.getY());
                    compound.setInteger("hitZ", pos.getZ());
                    
                    remote.updateItemStackNBT(compound);
                    stack.setTagCompound(compound);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            return EnumActionResult.PASS;
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        
        if(!playerIn.isSneaking() && getPosFromNBT(stack) != null && worldIn.isBlockLoaded(getPosFromNBT(stack)))
        {
            try
            {
                BlockPos pos = getPosFromNBT(stack);
    
                Block block = worldIn.getBlockState(pos).getBlock();
                
                block.onBlockActivated(worldIn, pos, worldIn.getBlockState(pos), playerIn, handIn, EnumFacing.byName(stack.getTagCompound().getString("facing")),
                        stack.getTagCompound().getInteger("hitX"), stack.getTagCompound().getInteger("hitY"), stack.getTagCompound().getInteger("hitZ"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    
    public BlockPos getPosFromNBT(ItemStack stack)
    {
        if(stack.hasTagCompound())
        {
            int x = stack.getTagCompound().getInteger("posX");
            int y = stack.getTagCompound().getInteger("posY");
            int z = stack.getTagCompound().getInteger("posZ");
            
            return new BlockPos(x, y, z);
        }
        return null;
    }
    
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(getPosFromNBT(stack) != null)
        {
            BlockPos pos = getPosFromNBT(stack);
            tooltip.add(TextFormatting.GOLD + stack.getTagCompound().getString("linkedname"));
            tooltip.add("X " + pos.getX() + ", Y " + pos.getY() + ", Z " + pos.getZ());
        }
        else
        {
            tooltip.add(TextFormatting.RED + "Shift click on a container to add to the remote");
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
    
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        if(stack.hasTagCompound())
        {
            return true;
        }
        return super.hasEffect(stack);
    }
}
