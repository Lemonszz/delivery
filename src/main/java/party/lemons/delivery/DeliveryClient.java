package party.lemons.delivery;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.delivery.network.MessageOpenStore;
import party.lemons.delivery.proxy.ClientProxy;

/**
 * Created by Sam on 7/11/2018.
 */
@Mod.EventBusSubscriber(modid = Delivery.MODID, value = Side.CLIENT)
public class DeliveryClient
{
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.phase != TickEvent.Phase.START) return;

		if(ClientProxy.KEY_STORE.isPressed())
		{
			sendStoreMessage(true);
		}
	}

	public static int LAST_PAGE = 0;
	public static String LAST_STORE = "_store";

	public static void sendStoreMessage(boolean keybind)
	{
		Delivery.NETWORK.sendToServer(new MessageOpenStore(LAST_PAGE, LAST_STORE, keybind));
	}
}
