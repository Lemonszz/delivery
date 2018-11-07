package party.lemons.delivery.store.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import party.lemons.delivery.DeliveryClient;

/**
 * Created by Sam on 7/11/2018.
 */
public class ItemStore extends Item
{
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if(worldIn.isRemote)
        {
            DeliveryClient.sendStoreMessage(false);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
