package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class RuleMatcherFactory {

  private BlockMatcher blockMatcher;
  private DropMatcher dropMatcher;
  private HarvesterMatcher harvesterMatcher;
  private BiomeMatcher biomeMatcher;
  private DimensionMatcher dimensionMatcher;

  public RuleMatcherFactory(
      BlockMatcher blockMatcher,
      DropMatcher dropMatcher, HarvesterMatcher harvesterMatcher,
      BiomeMatcher biomeMatcher,
      DimensionMatcher dimensionMatcher
  ) {

    this.blockMatcher = blockMatcher;
    this.dropMatcher = dropMatcher;
    this.harvesterMatcher = harvesterMatcher;
    this.biomeMatcher = biomeMatcher;
    this.dimensionMatcher = dimensionMatcher;
  }

  public RuleMatcher create(
      World world,
      EntityPlayer harvester,
      BlockPos pos,
      IBlockState blockState,
      List<ItemStack> drops
  ) {

    return new RuleMatcher(
        world,
        harvester,
        pos,
        blockState,
        drops,
        this.blockMatcher,
        this.dropMatcher,
        this.harvesterMatcher,
        this.biomeMatcher,
        this.dimensionMatcher
    );
  }

}
