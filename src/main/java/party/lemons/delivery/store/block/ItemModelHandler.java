package party.lemons.delivery.store.block;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.delivery.Delivery;

/**
 * Created by Sam on 7/11/2018.
 */
@Mod.EventBusSubscriber(modid = Delivery.MODID, value = Side.CLIENT)
public class ItemModelHandler
{
    @SubscribeEvent
    public static void onModelRegistery(ModelRegistryEvent event)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(DeliveryBlocks.STORE), 0, new ModelResourceLocation(DeliveryBlocks.STORE.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(DeliveryBlocks.CRATE), 0, new ModelResourceLocation(DeliveryBlocks.CRATE.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(DeliveryBlocks.STORE_BOOK, 0, new ModelResourceLocation(DeliveryBlocks.STORE_BOOK.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(DeliveryBlocks.STORE_TECH, 0, new ModelResourceLocation(DeliveryBlocks.STORE_TECH.getRegistryName(), "inventory"));
    }
}
