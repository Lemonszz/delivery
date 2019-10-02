package party.lemons.delivery.block.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

public class GuiTabDown extends GuiTab
{
	public GuiTabDown(int id, int xPos, int yPos, String store, String storeID, ItemStack display, GuiStore storeGui)
	{
		super(id, xPos, yPos, store, storeID, display, storeGui);
	}

	public void drawButtonUnder(Minecraft mc, int mouseX, int mouseY , float partialTicks)
	{
		if (this.visible)
		{
			mc.renderEngine.bindTexture(GuiStore.BG);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			if(hovered != hoverPrev)
				_time = 0;

			int drawX = y;
			if(selected)
				drawX = y + 10;
			else if(hovered)
				drawX = y + 4;

			this.drawTexturedModalRect(this.x, drawX - 8, 196, 98, 21, 27);
			GlStateManager.color(1F, 1F, 1F, 1F);

			float currentScale = updateScale(partialTicks);
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 4 + 8, drawX + 8, 0);
			GlStateManager.scale(currentScale, currentScale, 0);
			GlStateManager.translate( -18, -8, 0);

			mc.getRenderItem().renderItemAndEffectIntoGUI(display, 8, 1);
			GlStateManager.translate(0, 0, 0);
			GlStateManager.scale(1,1,1);

			GlStateManager.popMatrix();
			hoverPrev = hovered;
		}
	}
}
