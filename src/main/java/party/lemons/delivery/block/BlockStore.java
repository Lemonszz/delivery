package party.lemons.delivery.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.delivery.DeliveryClient;
import party.lemons.delivery.store.Trades;

/**
 * Created by Sam on 7/11/2018.
 */
public class BlockStore extends Block
{
	public BlockStore()
	{
		super(Material.WOOD, MapColor.WOOD);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(worldIn.isRemote)
		{
			DeliveryClient.sendStoreMessage(Trades.DEFAULT_PROFILE,false);
			return true;
		}

		return true;
	}
}
