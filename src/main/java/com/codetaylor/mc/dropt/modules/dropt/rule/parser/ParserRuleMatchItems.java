package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import com.codetaylor.mc.dropt.modules.dropt.rule.ILogger;
import com.codetaylor.mc.dropt.modules.dropt.rule.ItemMatcher;
import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public class ParserRuleMatchItems
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger, LogFileWrapper logFileWrapper
  ) {

    if (rule.match == null) {

      if (rule.debug) {
        logFileWrapper.debug("[PARSE] Match object not defined, skipped parsing item match");
      }
      return;
    }

    if (rule.debug && (rule.match.items == null || rule.match.items.length == 0)) {
      logFileWrapper.debug("[PARSE] No item matches defined, skipped parsing block match");
    }

    for (String string : rule.match.items) {

      String[] split = string.split(",");

      ParseResult parse;

      try {
        parse = parser.parse(split[0]);

      } catch (MalformedRecipeItemException e) {
        logger.error("[PARSE] Unable to parse item <" + split[0] + "> in file: " + ruleList._filename, e);
        continue;
      }

      if (rule.debug) {
        logFileWrapper.debug("[PARSE] Parsed item match: " + parse);
      }

      if ("ore".equals(parse.getDomain())) {
        NonNullList<ItemStack> ores = OreDictionary.getOres(parse.getPath());

        if (ores.isEmpty()) {
          logger.warn("[PARSE] No ore dict entries found for: " + parse);

        } else if (rule.debug) {
          logFileWrapper.debug("[PARSE] Expanding oreDict entry: " + parse);
        }

        for (ItemStack ore : ores) {
          ResourceLocation registryName = ore.getItem().getRegistryName();

          if (registryName == null) {
            logger.warn("[PARSE] Missing registry name for: " + ore);
            continue;
          }

          ItemMatcher itemMatcher = new ItemMatcher(
              registryName.getResourceDomain(),
              registryName.getResourcePath(),
              ore.getMetadata(),
              new int[0]
          );

          rule.match._items.add(itemMatcher);

          if (rule.debug) {
            logFileWrapper.debug("[PARSE] Added item matcher: " + itemMatcher);
          }
        }

      } else { // not an ore dict entry

        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

        if (item == null) {
          logger.error("[PARSE] Unable to find registered item: " + parse.toString());
          continue;
        }

        if (rule.debug) {
          logFileWrapper.debug("[PARSE] Found registered item: " + item);
        }

        int meta = parse.getMeta();
        int[] metas = new int[split.length];

        for (int i = 1; i < split.length; i++) {

          if ("*".equals(split[i].trim())) {
            meta = OreDictionary.WILDCARD_VALUE;
            metas = new int[0];
            break;
          }

          try {
            metas[i - 1] = Integer.valueOf(split[i].trim());

          } catch (Exception e) {
            logger.error("[PARSE] Unable to parse extra meta for <" + string + "> in file: " + ruleList._filename, e);
          }
        }

        ItemMatcher itemMatcher = new ItemMatcher(parse.getDomain(), parse.getPath(), meta, metas);
        rule.match._items.add(itemMatcher);

        if (rule.debug) {
          logFileWrapper.debug("[PARSE] Added item matcher: " + itemMatcher);
        }
      }
    }
  }
}