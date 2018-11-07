package party.lemons.delivery.store.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
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
    public int page = 0;

    public ContainerStore(EntityPlayer player, int page)
    {
        this.page = page;
        this.player = player;

        List<Trade> trades = Trades.getTrades(player);

        if (page * PER_PAGE > trades.size())
        {
            page = 0;
            DeliveryClient.LAST_PAGE = page;
        }

        if (page < 0)
        {
            page = trades.size() / PER_PAGE;
            DeliveryClient.LAST_PAGE = page;
        }

        int startIndex = page * PER_PAGE;

        int ind = 0;
        for (int i = startIndex; i < Math.min(startIndex + PER_PAGE, trades.size()); i++)
        {
            Trade trade = trades.get(i);

            addSlotToContainer(new SlotStoreTrade(trade.result, 120, 20 + (ind * 20)));
            for (int j = 0; j < trade.cost.length; j++)
            {
                int xPos = 12 + (j * 20);

                addSlotToContainer(new SlotStoreTradeCost(trade.cost[j], xPos, 20 + (ind * 20), player.getRNG()));
            }

            ind++;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return !playerIn.isDead;
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
            if (time > 20)
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
