package com.matez.wildnature.world.gen.feature.configs;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.matez.wildnature.other.TreeWeighList;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class TreeWeightListConfig implements IFeatureConfig {
   public final TreeWeighList list;

   public TreeWeightListConfig(TreeWeighList list) {
      this.list = list;
   }

   public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
      Gson gson = new Gson();
      return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("trees"), ops.createString(gson.toJson(list)))));
   }

   public static <T> TreeWeightListConfig deserialize(Dynamic<T> dynamic) {
      String json = dynamic.get("trees").asString("{}");
      Gson gson = new Gson();
      return new TreeWeightListConfig(gson.fromJson(json,TreeWeighList.class));
   }
}