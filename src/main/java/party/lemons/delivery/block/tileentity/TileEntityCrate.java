package party.lemons.delivery.block.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;


/**
 * Created by Sam on 7/11/2018.
 */
public class TileEntityCrate extends TileEntity
{
    private ItemStackHandler inventory = new ItemStackHandler(4);

    public TileEntityCrate()
    {

    }

    public ItemStackHandler getInventory()
    {
        return inventory;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        if(compound.hasKey("items"))
        {
            inventory.deserializeNBT(compound.getCompoundTag("items"));
        }
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("items", inventory.serializeNBT());
        return super.writeToNBT(compound);
    }

    public NBTTagCompound saveToNbt(NBTTagCompound compound)
    {
        compound.setTag("items", inventory.serializeNBT());
        return compound;
    }
}