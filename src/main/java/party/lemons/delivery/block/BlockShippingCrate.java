package party.lemons.delivery.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.delivery.DeliveryConfig;
import party.lemons.delivery.store.DeliveryUtil;
import party.lemons.delivery.store.Trade;
import party.lemons.delivery.store.Trades;

/**
 * Created by Sam on 8/11/2018.
 */
public class BlockShippingCrate extends Block
{
	public BlockShippingCrate()
	{
		super(Material.WOOD, MapColor.WOOD);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote)
		{
			ItemStack stack = playerIn.getHeldItem(hand);
			if(!stack.isEmpty())
			{
				for(Trade trade : Trades.getTrades(playerIn))
				{
					if(trade.result.isItemEqual(stack) && stack.getCount() >= trade.result.getCount())
					{
						stack.shrink(trade.result.getCount());

						int yPos = worldIn.getHeight() + 4;
						DeliveryUtil.dropDeliveryCrate(trade.getCostAsInventory(), worldIn, pos.getX() + 0.5F, yPos, pos.getZ() + 0.5F);
						((EntityPlayerMP) playerIn).connection.sendPacket(new SPacketCustomSound(DeliveryConfig.purchaceSuccessSound, SoundCategory.MASTER, playerIn.posX, playerIn.posY, playerIn.posZ, 0.5F, 1F));

						return true;
					}
				}
			}
			((EntityPlayerMP) playerIn).connection.sendPacket(new SPacketCustomSound(DeliveryConfig.purchaceFailSound, SoundCategory.MASTER, playerIn.posX, playerIn.posY, playerIn.posZ, 0.5F, 1F));
		}
		return true;
	}
}
