package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import com.codetaylor.mc.dropt.modules.dropt.rule.ILogger;
import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ParserRuleMatchHarvesterHeldItemMainHand
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger, LogFileWrapper logFileWrapper
  ) {

    if (rule.match == null) {

      if (rule.debug) {
        logFileWrapper.debug("Match object not defined, skipped parsing heldItemMainHand match");
      }
      return;
    }

    for (String string : rule.match.harvester.heldItemMainHand) {
      ParseResult parse;

      try {
        parse = parser.parse(string);

      } catch (MalformedRecipeItemException e) {
        logger.error("Unable to parse item <" + string + "> in file: " + ruleList._filename, e);
        continue;
      }

      if (rule.debug) {
        logFileWrapper.debug("Parsed item match: " + parse);
      }

      Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

      if (item == null) {
        logger.error("Unable to find registered item: " + parse.toString());
        continue;
      }

      if (rule.debug) {
        logFileWrapper.debug("Found registered item: " + item);
      }

      ItemStack itemStack = new ItemStack(item, 1, parse.getMeta());
      rule.match.harvester._heldItemMainHand.add(itemStack);

      if (rule.debug) {
        logFileWrapper.debug("Added itemStack to match: " + itemStack);
      }
    }
  }
}
