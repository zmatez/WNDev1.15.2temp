package com.matez.wildnature.world.gen.biomes.biomes;

import com.matez.wildnature.Main;
import com.matez.wildnature.blocks.FloweringBushBase;
import com.matez.wildnature.lists.WNBlocks;
import com.matez.wildnature.world.gen.biomes.setup.WNBiome;
import com.matez.wildnature.world.gen.biomes.setup.WNBiomeBuilder;
import com.matez.wildnature.world.gen.biomes.setup.WNBiomeFeatures;
import com.matez.wildnature.world.gen.structures.nature.SchemFeature;
import com.matez.wildnature.world.gen.structures.nature.woods.another_spruce.*;
import com.matez.wildnature.world.gen.structures.nature.woods.aspen.*;
import com.matez.wildnature.world.gen.structures.nature.woods.beech.*;
import com.matez.wildnature.world.gen.structures.nature.woods.birch.*;
import com.matez.wildnature.world.gen.structures.nature.woods.cherry.wild_cherry3;
import com.matez.wildnature.world.gen.structures.nature.woods.deco.forsythia4;
import com.matez.wildnature.world.gen.structures.nature.woods.oak.*;
import com.matez.wildnature.world.gen.structures.nature.woods.orchard.pear1;
import com.matez.wildnature.world.gen.structures.nature.woods.orchard.pear2;
import com.matez.wildnature.world.gen.structures.nature.woods.pine.*;
import com.matez.wildnature.world.gen.surface.SurfaceRegistry;
import com.matez.wildnature.world.gen.surface.configs.CanyonSurfaceBuilderConfig;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WNMasuria extends WNBiome {
    public WNMasuria(String name) {
        super(name, (new WNBiomeBuilder())
                .surfaceBuilder(SurfaceRegistry.RIVER_SURFACE_BUILDER, new CanyonSurfaceBuilderConfig(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG, SurfaceBuilder.DEFAULT))
                .precipitation(RainType.RAIN)
                .category(Category.FOREST)
                .topography(WNBiomeBuilder.Topography.LOWLANDS)
                .climate(WNBiomeBuilder.Climate.CONTINENTAL_WARM)
                .depth(0.0F)
                .scale(0.02F)
                .temperature(0.6F)
                .downfall(0.8F)
                .waterColor(4159204)
                .waterFogColor(329011)
                .parent(null));


        WNBiomeFeatures.addMineshafts(this, MineshaftStructure.Type.NORMAL);
        WNBiomeFeatures.addStrongholds(this);
        WNBiomeFeatures.addCarvers(this);
        WNBiomeFeatures.addStructures(this);
        WNBiomeFeatures.addLakes(this);
        WNBiomeFeatures.addMonsterRooms(this);
        WNBiomeFeatures.addDoubleFlowers(this);
        WNBiomeFeatures.addStoneVariants(this);
        WNBiomeFeatures.addOres(this);
        WNBiomeFeatures.addSedimentDisks(this);
        WNBiomeFeatures.addGrass(this);

        WNBiomeFeatures.addReedsAndPumpkins(this);
        WNBiomeFeatures.addSprings(this);
        WNBiomeFeatures.addPlant(this, WNBlocks.YEW_BUSH.getDefaultState(), 4);
        WNBiomeFeatures.addPlant(this, WNBlocks.PASQUE_WHITE.getDefaultState().with(FloweringBushBase.FLOWERING, true), 3);
        WNBiomeFeatures.addPlant(this, WNBlocks.CLOVER.getDefaultState(), 3);
        WNBiomeFeatures.addPlant(this, WNBlocks.DAISY.getDefaultState(), 1);
        WNBiomeFeatures.addPlant(this, WNBlocks.LEAF_PILE.getDefaultState(), 3);
        WNBiomeFeatures.addPlant(this, WNBlocks.GLADIOLUS_YELLOW.getDefaultState().with(FloweringBushBase.FLOWERING, true), 1);
        WNBiomeFeatures.addPlant(this, WNBlocks.GERANIUM_PINK.getDefaultState().with(FloweringBushBase.FLOWERING, true), 1);
        WNBiomeFeatures.addPlant(this, WNBlocks.SCOTCHBROOM_YELLOW.getDefaultState().with(FloweringBushBase.FLOWERING, true), 1);
        WNBiomeFeatures.addPlant(this, WNBlocks.SCOTCHBROOM_PURPLE.getDefaultState().with(FloweringBushBase.FLOWERING, true), 1);
        WNBiomeFeatures.addPlant(this, WNBlocks.MARIGOLD_WHITE.getDefaultState().with(FloweringBushBase.FLOWERING, true), 1);
        WNBiomeFeatures.addPlant(this, WNBlocks.CHRYSANTHEMUM_PURPLE.getDefaultState().with(FloweringBushBase.FLOWERING, true), 1);
        WNBiomeFeatures.addPlant(this, WNBlocks.HYDRANGEA_BLUE.getDefaultState().with(FloweringBushBase.FLOWERING, true), 1);
        WNBiomeFeatures.addPlant(this, WNBlocks.HYACINTH_PINK.getDefaultState().with(FloweringBushBase.FLOWERING, true), 1);
        WNBiomeFeatures.addPlant(this, WNBlocks.BLUEBELL.getDefaultState().with(FloweringBushBase.FLOWERING, true), 2);

        WNBiomeFeatures.addTree(this, new tree_birch10().setCustomLog(Main.getBlockByID("minecraft:birch_log").getDefaultState()).setCustomLeaf(tree_birch1.notDecayingLeaf(Main.getBlockByID("minecraft:birch_leaves"))), 4);
        WNBiomeFeatures.addTree(this, new tree_birch1().setCustomLog(Main.getBlockByID("minecraft:dark_oak_log").getDefaultState()).setCustomLeaf(tree_birch1.notDecayingLeaf(Main.getBlockByID("minecraft:oak_leaves"))), 4);
        WNBiomeFeatures.addTree(this, new tree_pine1().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 2);
        WNBiomeFeatures.addTree(this, new tree_pine2().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 2);
        WNBiomeFeatures.addTree(this, new tree_pine3().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 2);
        WNBiomeFeatures.addTree(this, new tree_pine4().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_pine5().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_pine6().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_spruce_m1().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_spruce_m2().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_spruce_m3().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_spruce_m4().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_spruce_m5().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_spruce_m6().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 2);
        WNBiomeFeatures.addTree(this, new tree_spruce_m7().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 2);
        WNBiomeFeatures.addTree(this, new tree_spruce_m8().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 2);
        WNBiomeFeatures.addTree(this, new tree_spruce_m9().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(WNBlocks.PINE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new high_beech_1(), 1);
        WNBiomeFeatures.addTree(this, new high_beech_2(), 1);
        WNBiomeFeatures.addTree(this, new high_beech_3(), 1);
        WNBiomeFeatures.addTree(this, new high_beech_4(), 1);
        WNBiomeFeatures.addTree(this, new high_beech_5(), 1);
        WNBiomeFeatures.addTree(this, new thin_birch1(), 1);
        WNBiomeFeatures.addTree(this, new thin_birch2(), 1);
        WNBiomeFeatures.addTree(this, new thin_birch3(), 1);
        WNBiomeFeatures.addTree(this, new aspen1(), 1);
        WNBiomeFeatures.addTree(this, new aspen2(), 1);
        WNBiomeFeatures.addTree(this, new aspen3(), 1);
        WNBiomeFeatures.addTree(this, new aspen4(), 1);
        WNBiomeFeatures.addTree(this, new aspen5(), 1);
        WNBiomeFeatures.addTree(this, new oak1(), 2);
        WNBiomeFeatures.addTree(this, new oak2(), 2);
        WNBiomeFeatures.addTree(this, new oak3(), 2);
        WNBiomeFeatures.addTree(this, new tree_oak9(), 1);
        WNBiomeFeatures.addTree(this, new tree_oak3(), 1);
        WNBiomeFeatures.addTree(this, new high_oak_1(), 4);
        WNBiomeFeatures.addTree(this, new high_oak_2(), 4);
        WNBiomeFeatures.addTree(this, new high_oak_3(), 4);
        WNBiomeFeatures.addTree(this, new high_oak_4(), 4);
        WNBiomeFeatures.addTree(this, new pear1(), 1);
        WNBiomeFeatures.addTree(this, new pear2(), 1);
        WNBiomeFeatures.addTree(this, new forsythia4(), 1);
        WNBiomeFeatures.addTree(this, new wild_cherry3(), 1);
        WNBiomeFeatures.addTree(this, new tree_oak5().setCustomLog(Main.getBlockByID("wildnature:plum_log").getDefaultState()).setCustomLeaf(tree_birch1.notDecayingLeaf(Main.getBlockByID("wildnature:mirabelle_plum_leaves"))), 1);

        //TODO BigTree
        //WNBiomeFeatures.addTree(this, new BigTree(), 3);


        treeRate = 4;

        applyPlants();
        applyTrees();


        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.PIG, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.COW, 8, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.WOLF, 5, 4, 4));
        this.addSpawn(EntityClassification.AMBIENT, new SpawnListEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.WITCH, 5, 1, 1));

    }


    @OnlyIn(Dist.CLIENT)
    public int getGrassColor(BlockPos pos) {
        double noise = INFO_NOISE.noiseAt((double) pos.getX() * 0.0225D, (double) pos.getZ() * 0.0225D, false);
        return customColor(noise, -0.1D, 0x6CC511, 0x8DC557);
    }

    @OnlyIn(Dist.CLIENT)
    public int getFoliageColor(BlockPos pos) {
        double noise = INFO_NOISE.noiseAt((double) pos.getX() * 0.0225D, (double) pos.getZ() * 0.0225D, false);
        return customColor(noise, -0.1D, 0x65AC46, 0x7BAC3F);
    }

    @Override
    public int getSkyColor() {
        return 0xA8EBE8;
    }

}