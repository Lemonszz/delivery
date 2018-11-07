package party.lemons.delivery.item;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.delivery.Delivery;
import party.lemons.delivery.block.DeliveryBlocks;

/**
 * Created by Sam on 8/11/2018.
 */
@Mod.EventBusSubscriber(modid = Delivery.MODID)
@GameRegistry.ObjectHolder(value = Delivery.MODID)
public class DeliveryItems
{
    public static final Item STORE_BOOK = Items.AIR;
    public static final Item STORE_TECH = Items.AIR;

    @SubscribeEvent
    public static void onRegisterItem(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(createItemBlock(DeliveryBlocks.STORE));
        event.getRegistry().register(createItemBlock(DeliveryBlocks.CRATE));
        event.getRegistry().register(createItemBlock(DeliveryBlocks.SHIPPING_CRATE));

        event.getRegistry().register(new ItemStore().setTranslationKey(Delivery.MODID + ".store_book").setRegistryName(Delivery.MODID, "store_book"));
        event.getRegistry().register(new ItemStore().setTranslationKey(Delivery.MODID + ".store_tech").setRegistryName(Delivery.MODID, "store_tech"));
    }

    private static ItemBlock createItemBlock(Block block)
    {
        ItemBlock ib = new ItemBlock(block);
        ib.setRegistryName(block.getRegistryName());

        return ib;
    }
}
