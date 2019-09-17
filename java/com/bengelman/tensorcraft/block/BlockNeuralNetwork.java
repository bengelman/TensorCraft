package com.bengelman.tensorcraft.block;

import com.bengelman.tensorcraft.tileentity.TileEntityNeuralNetwork;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNeuralNetwork extends BlockContainer{

	public BlockNeuralNetwork(Material materialIn) {
		super(materialIn);
		// TODO Auto-generated constructor stub
	}
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new TileEntityNeuralNetwork();
	}
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!worldIn.isRemote)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityNeuralNetwork)
            {
                TileEntityNeuralNetwork nn = (TileEntityNeuralNetwork)tileentity;
                boolean flag = worldIn.isBlockPowered(pos);
                
                nn.setPowered(flag);
            }
        }
    }

}
