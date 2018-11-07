package party.lemons.delivery.store;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import party.lemons.delivery.block.DeliveryBlocks;
import party.lemons.delivery.block.tileentity.TileEntityCrate;
import party.lemons.delivery.entity.EntityFallingBlockExt;

/**
 * Created by Sam on 8/11/2018.
 */
public class DeliveryUtil
{
    public static void dropDeliveryCrate(ItemStackHandler handler, World world, double x, double y, double z)
    {
        EntityFallingBlockExt block = new EntityFallingBlockExt(world, x, y, z, DeliveryBlocks.CRATE.getDefaultState());

        TileEntityCrate crateTe = new TileEntityCrate();
        for(int i = 0; i < Math.min(crateTe.getInventory().getSlots(), handler.getSlots()); i++)
        {
            crateTe.getInventory().setStackInSlot(i, handler.getStackInSlot(i));
        }
        block.tileEntityData = crateTe.saveToNbt(new NBTTagCompound());
        world.spawnEntity(block);
        block.fallTime = 1;
    }
}
