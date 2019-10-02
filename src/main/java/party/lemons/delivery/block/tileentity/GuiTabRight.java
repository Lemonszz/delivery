package party.lemons.delivery.block.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

public class GuiTabRight extends GuiTab
{
	public GuiTabRight(int id, int xPos, int yPos, String store, String storeID, ItemStack display, GuiStore storeGui)
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

			int drawX = x;
			if(selected)
				drawX = x + 10;
			else if(hovered)
				drawX = x + 4;

			this.drawTexturedModalRect(drawX - 8, this.y, 196, 74, 28, this.height);
			GlStateManager.color(1F, 1F, 1F, 1F);

			float currentScale = updateScale(partialTicks);
			GlStateManager.pushMatrix();
			GlStateManager.translate(drawX + 8, y + 4 + 8, 0);
			GlStateManager.scale(currentScale, currentScale, 0);
			GlStateManager.translate( -8, -12, 0);

			mc.getRenderItem().renderItemAndEffectIntoGUI(display, 1, 2);
			GlStateManager.translate(0, 0, 0);
			GlStateManager.scale(1,1,1);

			GlStateManager.popMatrix();
			hoverPrev = hovered;
		}
	}
}
