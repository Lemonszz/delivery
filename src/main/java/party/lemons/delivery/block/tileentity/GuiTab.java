package party.lemons.delivery.block.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;
import party.lemons.delivery.Delivery;
import party.lemons.delivery.DeliveryConfig;

import java.util.ArrayList;
import java.util.List;

public class GuiTab extends GuiButtonExt
{
	protected static final ResourceLocation BG = new ResourceLocation(Delivery.MODID, "textures/store.png");
	protected final ItemStack display;
	protected String store, storeId;
	protected GuiStore storeGui;
	protected boolean selected;
	protected boolean hoverPrev = false;
	protected float _targetScale = 1;
	protected float _scale = 1;
	protected float _time = 40;

	public GuiTab(int id, int xPos, int yPos, String store, String storeID, ItemStack display, GuiStore storeGui)
	{
		super(id, xPos, yPos, 17, 21, "");

		this.display = display;
		this.store = store;
		this.storeGui = storeGui;
		this.storeId = storeID;
	}

	public void drawButtonUnder(Minecraft mc, int mouseX, int mouseY , float partialTicks)
	{
		if (this.visible)
		{
			mc.renderEngine.bindTexture(BG);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			if(hovered != hoverPrev)
				_time = 0;

			int drawX = x;
			if(selected)
				drawX = x - 10;
			else if(hovered)
				drawX = x - 4;

			this.drawTexturedModalRect(drawX, this.y, 196, 21, 28, this.height);
			GlStateManager.color(1F, 1F, 1F, 1F);

			float currentScale = updateScale(partialTicks);

			GlStateManager.pushMatrix();
			GlStateManager.translate(drawX + 8, y + 4 + 8, 0);
			GlStateManager.scale(currentScale, currentScale, 0);
			GlStateManager.translate( -8, -12, 0);

			mc.getRenderItem().renderItemAndEffectIntoGUI(display, 2, 2);
			GlStateManager.translate(0, 0, 0);
			GlStateManager.scale(1,1,1);

			GlStateManager.popMatrix();
			hoverPrev = hovered;
		}
	}

	private final List<String> li = new ArrayList<>();
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{

	}

	public void setSelected()
	{
		this.selected = true;
	}

	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		if(this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height)
		{
			storeGui.storeTabClick(store);
			return true;
		}

		return false;
	}

	protected float updateScale(float delta)
	{
		if(hovered)
		{
			_targetScale = 0.2F;
			_scale = 1;
		}
		else
		{
			_targetScale = -0.2F;
			_scale = 1.2F;
		}

		float currentScale = Elastic.easeOut(_time, _scale, _targetScale,  40);
		if(_time < 40)
			_time += delta;

		return currentScale;
	}

	public void drawButtonOver(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{
		if(li.isEmpty())
		{
			String title = DeliveryConfig.guiTitle.isEmpty() ? I18n.format("container." + Delivery.MODID + ".store") : DeliveryConfig.guiTitle;
			if(!store.equals("_store"))
				title = store;
			li.add(title);
		}

		if(isMouseOver())
		{
			ScaledResolution res = new ScaledResolution(mc);
			GuiUtils.drawHoveringText(li, mouseX, mouseY, res.getScaledWidth(), res.getScaledHeight(), 500, mc.fontRenderer);
		}
	}

	public String getStoreId()
	{
		return storeId;
	}

	public static class Elastic {

		public static float  easeOut(float t,float b , float c, float d) {
			if (t==0) return b;  if ((t/=d)==1) return b+c;
			float p=d*.3f;
			float a=c;
			float s=p/4;
			return (a*(float)Math.pow(2,-10*t) * (float)Math.sin( (t*d-s)*(2*(float)Math.PI)/p ) + c + b);
		}
	}
}
