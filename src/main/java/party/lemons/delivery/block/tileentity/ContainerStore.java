package party.lemons.delivery.block.tileentity;

import com.blamejared.ctgui.api.SlotRecipeOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import party.lemons.delivery.DeliveryClient;
import party.lemons.delivery.store.Trade;
import party.lemons.delivery.store.Trades;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * Created by Sam on 7/11/2018.
 */
public class ContainerStore extends Container
{
	private static final int PER_PAGE = 6;

	private final EntityPlayer player;
	private List<Trade> trades;
	public int page = 0;
	private String store;
	private String profile;
	public boolean needClientRefresh = false;

	public ContainerStore(EntityPlayer player, int page, String store, String profile)
	{
		this.page = page;
		this.store = store;
		this.player = player;
		this.profile = profile;

		trades = Trades.getTrades(player, store, profile);
		addSlots();
	}

	public void addSlots()
	{
		this.inventorySlots.clear();
		this.inventoryItemStacks.clear();

		if(page * PER_PAGE > trades.size())
		{
			page = 0;
			DeliveryClient.LAST_PAGE = page;
		}

		if(page < 0)
		{
			page = trades.size() / PER_PAGE;
			DeliveryClient.LAST_PAGE = page;
		}

		int startIndex = page * PER_PAGE;

		int ind = 0;
		for(int i = startIndex; i < Math.min(startIndex + PER_PAGE, trades.size()); i++)
		{
			Trade trade = trades.get(i);

			addSlotToContainer(new SlotStoreTrade(trade.result, 120, 20 + (ind * 20)));
			for(int j = 0; j < trade.cost.length; j++)
			{
				int xPos = 12 + (j * 19);
				try
				{
					addSlotToContainer(new SlotStoreTradeCost(trade.cost[j], xPos, 20 + (ind * 20), player.getRNG()));
				}catch(IndexOutOfBoundsException e)
				{
					System.out.println("!!!NO ORE DICTIONARY ENTRY FOUND!!!");
					e.printStackTrace();
					System.out.println("!!!NO ORE DICTIONARY ENTRY FOUND!!!");
				}
			}

			ind++;
		}
	}

	public int getFinalPageNumber()
	{
		return trades.size() / PER_PAGE;
	}

	public String getStore()
	{
		return store;
	}

	protected void broadcastData(IContainerListener crafting)
	{
		crafting.sendWindowProperty(this, 0, Trades.getStoreIndex(store, profile));
	}

	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		this.broadcastData(listener);
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for(int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener icontainerlistener = this.listeners.get(i);
			this.broadcastData(icontainerlistener);
		}
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		int currentId = Trades.getStoreIndex(store, profile);
		if(currentId != data)
		{
			store = Trades.getStoreById(data, profile);
			trades = Trades.getTrades(player, store, profile);

			addSlots();
			needClientRefresh = true;
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return !playerIn.isDead;
	}

	public String getProfile()
	{
		return profile;
	}

	public static class SlotStoreTrade extends SlotItemHandler
	{
		public SlotStoreTrade(ItemStack display, int xPosition, int yPosition)
		{
			super(new ItemStackHandler(NonNullList.withSize(1, display)), 0, xPosition, yPosition);
		}

		@Override
		public boolean canTakeStack(EntityPlayer playerIn)
		{
			return false;
		}

		@Override
		public void putStack(@Nonnull ItemStack stack)
		{
		}

	}

	public static class SlotStoreTradeCost extends SlotItemHandler
	{
		private Ingredient display;
		private int ind;
		private float time = 0;

		public SlotStoreTradeCost(Ingredient display, int xPosition, int yPosition, Random random)
		{
			super(new ItemStackHandler(NonNullList.withSize(1, display.getMatchingStacks()[0])), 0, xPosition, yPosition);
			this.display = display;

			ind = random.nextInt(200) % display.getMatchingStacks().length;
		}

		public ItemStack getStack()
		{
			return display.getMatchingStacks()[ind % display.getMatchingStacks().length];
		}

		public void doUpdate(float partialTicks)
		{
			time += partialTicks;
			if(time > 20)
			{
				time = 0;
				ind++;
			}
		}

		@Override
		public boolean canTakeStack(EntityPlayer playerIn)
		{
			return false;
		}

		@Override
		public void putStack(@Nonnull ItemStack stack)
		{
		}
	}
}
