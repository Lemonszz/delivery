package party.lemons.delivery.block.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import party.lemons.delivery.Delivery;
import party.lemons.delivery.DeliveryClient;
import party.lemons.delivery.DeliveryConfig;
import party.lemons.delivery.network.MessageBuyTrade;
import party.lemons.delivery.store.Store;
import party.lemons.delivery.store.Trade;
import party.lemons.delivery.store.Trades;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sam on 5/11/2018.
 */
public class GuiStore extends GuiContainer
{
	private ContainerStore store;
	private ConfirmButton lastButton = null;

	public GuiStore(ContainerStore store)
	{
		super(store);
		this.store = store;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.buttonList.clear();

		GuiButton bNext = addButton(new GuiButtonExt(0, guiLeft + (xSize - 60), guiTop + 140, 20, 20, ">"));
		GuiButton bPrev = addButton(new GuiButtonExt(1, guiLeft + 40, guiTop + 140, 20, 20, "<"));

		int st = 1;
		Store def = Trades.getStore("_store");
		Gui tab = addButton(new GuiTab(3, guiLeft - 17, 5 + guiTop + (0 * 22), def.getName(), def.getName(), def.getItemStack(), this));

		for(Store store : Trades.trades.keySet())
		{
			if(!store.getName().equals("_store"))
			{
				if(st <= 6)
				{
					GuiTab t = addButton(new GuiTab(3, guiLeft - 17, 5 + guiTop + (st * 22), store.getName(), store.getName(), store.getItemStack(), this));
				}
				else if(st <= 13)
				{
					GuiTab t = addButton(new GuiTabRight(3, (guiLeft + xSize) - 2, 5 + guiTop + ((st - 7) * 22), store.getName(), store.getName(), store.getItemStack(), this));
				}
				else if(st <= 20)
				{
					GuiTab t = addButton(new GuiTabDown(3, 12 + guiLeft + ((st - 14) * 22), guiTop + ySize - 2, store.getName(), store.getName(), store.getItemStack(), this));
				}
				else
				{
					GuiTab t = addButton(new GuiTabUp(3, 12 + guiLeft + ((st - 21) * 22), guiTop- 8, store.getName(), store.getName(), store.getItemStack(), this));
				}
				st++;
			}
		}

		if(store.getFinalPageNumber() == 0)
		{
			bNext.enabled = false;
			bPrev.enabled = false;
		}

		List<Trade> trades = Trades.getTrades(Minecraft.getMinecraft().player, store.getStore());
		int page = DeliveryClient.LAST_PAGE;
		int PER_PAGE = 6;
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
			int yPos = guiTop + 20 + (ind * 20);
			addButton(new ConfirmButton(2, i, guiLeft + xSize - 20, yPos));

			ind++;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		switch(button.id)
		{
			case 0:
				DeliveryClient.LAST_PAGE++;
				if(DeliveryClient.LAST_PAGE > store.getFinalPageNumber()) DeliveryClient.LAST_PAGE = 0;

				DeliveryClient.sendStoreMessage(false);
				break;
			case 1:
				DeliveryClient.LAST_PAGE--;
				if(DeliveryClient.LAST_PAGE < 0) DeliveryClient.LAST_PAGE = store.getFinalPageNumber();

				DeliveryClient.sendStoreMessage(false);
				break;
			case 3:
				break;
			case 2:
				Delivery.NETWORK.sendToServer(new MessageBuyTrade(store.getStore(), ((ConfirmButton) button).storeIndex));
				lastButton = (ConfirmButton) button;
				break;
		}

		super.actionPerformed(button);
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		if(store.needClientRefresh)
		{
			initGui();
			store.needClientRefresh = false;
		}

		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		String title = DeliveryConfig.guiTitle.isEmpty() ? I18n.format("container." + Delivery.MODID + ".store") : DeliveryConfig.guiTitle;
		if(!store.getStore().equals("_store")) title = store.getStore();

		fontRenderer.drawString(title, -fontRenderer.getStringWidth(title) / 2 + guiLeft + (xSize / 2), guiTop + 5, 4210752);

		String pageText = (DeliveryClient.LAST_PAGE + 1) + "/" + (store.getFinalPageNumber() + 1);
		int pageTextWidth = -fontRenderer.getStringWidth(pageText);
		fontRenderer.drawString(pageText, pageTextWidth / 2 + (guiLeft + (xSize / 2)), guiTop + 145, 4210752);

		this.renderHoveredToolTip(mouseX, mouseY);

		for(Slot slot : store.inventorySlots)
		{
			if(slot instanceof ContainerStore.SlotStoreTradeCost)
			{
				((ContainerStore.SlotStoreTradeCost) slot).doUpdate(partialTicks);
			}
		}
		buttonList.stream().filter(b->b instanceof GuiTab).forEach(b->
		{
			((GuiTab) b).drawButtonOver(mc, mouseX, mouseY, partialTicks);
			if(store.getStore().equalsIgnoreCase(((GuiTab) b).getStoreId())) ((GuiTab) b).setSelected();
		});
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		buttonList.stream().filter(b->b instanceof GuiTab).forEach(b->((GuiTab) b).drawButtonUnder(mc, mouseX, mouseY, partialTicks));

		mc.renderEngine.bindTexture(BG);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	private static final ResourceLocation BG = new ResourceLocation(Delivery.MODID, "textures/store.png");

	public void setButtonDisabled()
	{
		if(lastButton != null) lastButton.setDisabled();
	}

	public void storeTabClick(String store)
	{
		DeliveryClient.LAST_PAGE = 0;
		DeliveryClient.LAST_STORE = store;
		DeliveryClient.sendStoreMessage(false);
	}

	static class Button extends GuiButton
	{
		private final int iconX;
		private final int iconY;
		private float disableTime;

		protected Button(int buttonId, int x, int y, int iconXIn, int iconYIn)
		{
			super(buttonId, x, y, 17, 17, "");
			this.iconX = iconXIn;
			this.iconY = iconYIn;
		}

		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
		{
			if(this.visible)
			{
				mc.renderEngine.bindTexture(BG);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
				if(this.hovered)
				{
					GlStateManager.color(0.258823529F, 0.525490196F, 0.956862745F, 1F);
				}
				if(!enabled)
				{
					GlStateManager.color(109F / 255F, 109F / 255F, 109F / 255F, 0.8F);
				}

				this.drawTexturedModalRect(this.x, this.y, 0, 167, this.width, this.height);
				GlStateManager.color(1F, 1F, 1F, 1F);
				this.drawTexturedModalRect(this.x - 1, this.y, this.iconX, this.iconY, 17, 17);
			}
			disableTime -= partialTicks;
			if(disableTime <= 0)
			{
				enabled = true;
			}

		}

		public void setDisabled()
		{
			this.enabled = false;
			this.disableTime = 15;
		}
	}


	class ConfirmButton extends Button
	{
		public int storeIndex;

		public ConfirmButton(int buttonId, int storeIndex, int x, int y)
		{
			super(buttonId, x, y, 17, 167);

			this.storeIndex = storeIndex;
		}

		public void drawButtonForegroundLayer(int mouseX, int mouseY)
		{
			GuiStore.this.drawHoveringText(I18n.format("gui.done"), mouseX, mouseY);
		}
	}

}
