package party.lemons.delivery;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.delivery.store.DeliveryType;

/**
 * Created by Sam on 7/11/2018.
 */
@Config(modid = Delivery.MODID)
@Mod.EventBusSubscriber(modid = Delivery.MODID)
public class DeliveryConfig
{
	@Config.Name("Use Keybinding")
	@Config.Comment("If this is true, the player can use the store keybinding to open the GUI. If it is false, the store inventory is required.")
	public static boolean useKey = true;

	@Config.Name("Delivery Method")
	@Config.Comment("How should the bought items be delivered to the player?")
	public static DeliveryType deliveryType = DeliveryType.SKY_DROP;

	@Config.Name("Close GUI after trade")
	@Config.Comment("Should the store GUI close after a successful purchace?")
	public static boolean closeGui = false;

	@Config.Name("Store title")
	@Config.Comment("Title that will be displayed at the top of the store, if no title set will use the default translation")
	public static String guiTitle = "";

	@Config.Name("Dimension Blacklist")
	@Config.Comment("Dimensions that that player cant NOT use the store, defaulted to just be the nether since the drops can't go anywhere :(")
	public static int[] dimensionBlacklist = new int[]{-1};

	@Config.Name("Purchase Success Sound")
	@Config.Comment("The sound that will play when successfully buying an item, set blank for no sound")
	public static String purchaceSuccessSound = "entity.villager.yes";

	@Config.Name("Purchase Fail Sound")
	@Config.Comment("The sound that will play when failing to buy an item, set blank for no sound")
	public static String purchaceFailSound = "entity.villager.no";

	@Config.Name("Landing Sound")
	@Config.Comment("The sound that will play when the crate lands, set blank for no sound")
	public static String landSound = "entity.irongolem.step";

	@Config.Name("Drop Radius")
	@Config.Comment("What radius should the drop spawn around the player be? Set to 0 for no spread")
	@Config.RangeInt(min = 0)
	public static int dropRadius = 5;

	@SubscribeEvent
	public static void onConfigReload(ConfigChangedEvent event)
	{
		if(event.getModID().equals(Delivery.MODID))
		{
			ConfigManager.sync(Delivery.MODID, Config.Type.INSTANCE);
		}
	}

}
