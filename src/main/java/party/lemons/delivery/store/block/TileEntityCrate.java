package party.lemons.delivery.store.block;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 7/11/2018.
 */
public class TileEntityCrate extends TileEntity
{
    private ItemStack stack = ItemStack.EMPTY;

    public TileEntityCrate()
    {

    }

    public ItemStack getStack()
    {
        return stack;
    }

    public void setStack(@Nonnull ItemStack stack)
    {
        this.stack = stack;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        stack = new ItemStack(compound.getCompoundTag("item"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("item", stack.writeToNBT(new NBTTagCompound()));
        return super.writeToNBT(compound);
    }

    public NBTTagCompound saveToNbt(NBTTagCompound compound)
    {
        compound.setTag("item", stack.writeToNBT(new NBTTagCompound()));
        return compound;
    }
}