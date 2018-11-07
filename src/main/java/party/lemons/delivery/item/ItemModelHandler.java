package party.lemons.delivery.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.delivery.Delivery;
import party.lemons.delivery.block.DeliveryBlocks;

/**
 * Created by Sam on 7/11/2018.
 */
@Mod.EventBusSubscriber(modid = Delivery.MODID, value = Side.CLIENT)
public class ItemModelHandler
{
    @SubscribeEvent
    public static void onModelRegistery(ModelRegistryEvent event)
    {
        registerModel(DeliveryBlocks.STORE);
        registerModel(DeliveryBlocks.CRATE);
        registerModel(DeliveryBlocks.SHIPPING_CRATE);

        registerModel(DeliveryItems.STORE_BOOK);
        registerModel(DeliveryItems.STORE_TECH);
    }

    private static void registerModel(Block block)
    {
        registerModel(Item.getItemFromBlock(block));
    }

    private static void registerModel(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
