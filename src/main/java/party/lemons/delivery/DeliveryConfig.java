package party.lemons.delivery;

import net.minecraftforge.common.config.Config;
import party.lemons.delivery.store.DeliveryType;

/**
 * Created by Sam on 7/11/2018.
 */
@Config(modid =Delivery.MODID)
public class DeliveryConfig
{
    @Config.Name("Use Keybinding")
    @Config.Comment("If this is true, the player can use the store keybinding to open the GUI. If it is false, the store block is required.")
    public static boolean useKey = true;

    @Config.Name("Delivery Method")
    @Config.Comment("How should the bought items be delivered to the player?")
    public static DeliveryType deliveryType = DeliveryType.SKY_DROP;

    @Config.Name("Close GUI after trade")
    @Config.Comment("Should the store GUI close after a successful purchace?")
    public static boolean closeGui = true;

    @Config.Name("Store title")
    @Config.Comment("Title that will be displayed at the top of the store, if no title set will use the default translation")
    public static String guiTitle = "";
}
