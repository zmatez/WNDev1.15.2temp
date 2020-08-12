package com.matez.wildnature.blocks;

import com.matez.wildnature.lists.WNBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWallTorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RedstoneTorchWallBase extends RedstoneWallTorchBlock {


    private static Properties Properties(Properties properties){

        return properties;

    }

    public RedstoneTorchWallBase(Properties properties, ResourceLocation regName) {
        super(Properties(properties));

        this.setRegistryName(regName);



        WNBlocks.BLOCKS.add(this);

    }



    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        //System.out.println("biome: " + BiomeColors.getGrassColor(worldIn, pos) + " foliage: " + GrassColors.get(0.5,0.5));


    }

}
