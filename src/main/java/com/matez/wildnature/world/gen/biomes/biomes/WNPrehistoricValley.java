package com.matez.wildnature.world.gen.biomes.biomes;

import com.matez.wildnature.blocks.FloweringBushBase;
import com.matez.wildnature.lists.WNBlocks;
import com.matez.wildnature.world.gen.biomes.setup.WNBiome;
import com.matez.wildnature.world.gen.biomes.setup.WNBiomeBuilder;
import com.matez.wildnature.world.gen.biomes.setup.WNBiomeFeatures;
import com.matez.wildnature.world.gen.structures.nature.SchemFeature;
import com.matez.wildnature.world.gen.structures.nature.woods.fir.tree_fir6;
import com.matez.wildnature.world.gen.structures.nature.woods.spruce.*;
import com.matez.wildnature.world.gen.surface.SurfaceRegistry;
import com.matez.wildnature.world.gen.surface.configs.Noise2SurfaceBuilderConfig;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WNPrehistoricValley extends WNBiome {
    public WNPrehistoricValley(String name) {
        super(name, (new WNBiomeBuilder())
                .surfaceBuilder(SurfaceRegistry.NOISE_SURFACE_BUILDER, new Noise2SurfaceBuilderConfig(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG, new SurfaceBuilderConfig(Blocks.COARSE_DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.DIRT.getDefaultState())))
                .precipitation(RainType.RAIN)
                .category(Category.FOREST)
                .topography(WNBiomeBuilder.Topography.LOWLANDS)
                .climate(WNBiomeBuilder.Climate.CONTINENTAL_WARM)
                .depth(0.15F)
                .scale(0.03F)
                .temperature(0.6F)
                .downfall(0.8F)
                .waterColor(4159204)
                .waterFogColor(329011)
                .parent(null));


        WNBiomeFeatures.addMineshafts(this, MineshaftStructure.Type.NORMAL);
        WNBiomeFeatures.addStrongholds(this);
        WNBiomeFeatures.addVillages(this, "village/plains/town_centers", 6);

        WNBiomeFeatures.addCarvers(this);
        WNBiomeFeatures.addStructures(this);
        WNBiomeFeatures.addLakes(this);
        WNBiomeFeatures.addMonsterRooms(this);
        WNBiomeFeatures.addDoubleFlowers(this);
        WNBiomeFeatures.addStoneVariants(this);
        WNBiomeFeatures.addOres(this);
        WNBiomeFeatures.addSedimentDisks(this);
        WNBiomeFeatures.addDefaultFlowers(this);
        WNBiomeFeatures.addGrass(this, 18);

        WNBiomeFeatures.addReedsAndPumpkins(this);
        WNBiomeFeatures.addSprings(this);

        WNBiomeFeatures.addPlant(this, WNBlocks.PASQUE_WHITE.getDefaultState().with(FloweringBushBase.FLOWERING, true), 1);
        WNBiomeFeatures.addPlant(this, WNBlocks.LUPINE_YELLOW.getDefaultState().with(FloweringBushBase.FLOWERING, true), 1);
        WNBiomeFeatures.addPlant(this, WNBlocks.LUPINE_BLUE.getDefaultState().with(FloweringBushBase.FLOWERING, true), 1);
        WNBiomeFeatures.addPlant(this, WNBlocks.GRASS_FLOWER.getDefaultState().with(FloweringBushBase.FLOWERING, true), 4);
        WNBiomeFeatures.addPlant(this, WNBlocks.WILD_WHEAT.getDefaultState().with(FloweringBushBase.FLOWERING, true), 4);
        WNBiomeFeatures.addPlant(this, WNBlocks.GRASS_WHEAT.getDefaultState().with(FloweringBushBase.FLOWERING, true), 4);

        WNBiomeFeatures.addTree(this, new tree_taiga11().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(Blocks.SPRUCE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_taiga12().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(Blocks.SPRUCE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_taiga14().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(Blocks.SPRUCE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_taiga15().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(Blocks.SPRUCE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_taiga5().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(Blocks.SPRUCE_LEAVES)), 1);
        WNBiomeFeatures.addTree(this, new tree_fir6().setCustomLog(Blocks.SPRUCE_LOG.getDefaultState()).setCustomLeaf(SchemFeature.notDecayingLeaf(Blocks.SPRUCE_LEAVES)), 1);

        WNBiomeFeatures.addGeysers(this, 35);
        WNBiomeFeatures.addRocks(this);

        plantRate = 4;
        treeRate = 2;

        applyPlants();
        applyTrees();

        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.PIG, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
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
        return 0x619D5D;
    }


}