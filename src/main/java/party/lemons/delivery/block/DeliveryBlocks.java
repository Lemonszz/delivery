package party.lemons.delivery.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.delivery.Delivery;

/**
 * Created by Sam on 7/11/2018.
 */
@Mod.EventBusSubscriber(modid = Delivery.MODID)
@GameRegistry.ObjectHolder(value = Delivery.MODID)
public class DeliveryBlocks
{
	public static final Block CRATE = Blocks.AIR;
	public static final Block STORE = Blocks.AIR;
	public static final Block SHIPPING_CRATE = Blocks.AIR;

	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(createBlock(new BlockStoreCrate(), "crate", 0.25F));
		event.getRegistry().register(createBlock(new BlockStore(), "store", 1.25F));
		event.getRegistry().register(createBlock(new BlockShippingCrate(), "shipping_crate", 1.25F));
	}

	private static Block createBlock(Block block, String name, float hardness)
	{
		block.setRegistryName(new ResourceLocation(Delivery.MODID, name));
		block.setTranslationKey(Delivery.MODID + "." + name);
		block.setHardness(hardness);

		return block;
	}
}
