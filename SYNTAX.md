All parameters are required unless explicitly marked *Optional*.

## IRuleList

This is the main object of any `.json` rule files.

<big><pre>
{
  "priority": int,
  "rules": [IRule](#irule)[]
}
</pre></big>

* `priority`: `int`
  * &#x1F539;*Optional* - if omitted, defaults to `0`.
  * Files with a higher priority will have their rules matched first.

* `rules`: <code>[IRule](#irule)[]</code>
  * This array contains objects which define match and replace behavior.
  * Each entry is matched in the same order it is defined the file. 

## IRule

The `IRule` defines match and replace behavior.

<big><pre>
{
  "match": [IRuleMatch](#irulematch),
  "mergeStrategy": "REPLACE" | "ADD" | "REPLACE_IF_SELECTED",
  "dropCount": [IRandomFortuneInt](#irandomfortuneint),
  "drops": [IRuleDrop](#iruledrop)[]
}
</pre></big>

* `match`: <code>[IRuleMatch](#irulematch)</code>
  * &#x1F539;*Optional* - if omitted, all blocks will be matched.
  * This object defines the conditions used to match this rule. 

* `mergeStrategy`: `enum`
  * &#x1F539;*Optional* - if omitted, defaults to `ADD`.
  * `REPLACE`: All block drops will be replaced by drops from this rule.
  * `ADD`: Any selected drops from this rule will be added to the block's existing drops. If this rule is matched and no drops are selected, the block will drop nothing.
  * `REPLACE_IF_SELECTED`: All block drops will be replaced by drops from this rule if and only if drops are selected from this rule.

* `dropCount`: <code>[IRandomFortuneInt](#irandomfortuneint)</code>
  * &#x1F539;*Optional* - if omitted, defaults to `1`.
  * This object defines how many times the weighted picker will be queried for drops.

* `drops`: <code>[IRuleDrop](#iruledrop)[]</code>
  * This array lists the potential drops for this rule.

## IRuleMatch

The `IRuleMatch` is responsible for matching the rule to the block. When a block is broken, the first <code>[IRule](#irule)</code> to successfully satisfy the requirements of the `IRuleMatch` is selected.

<big><pre>
{
  "blocks": String[],
  "harvester": [IRuleMatchHarvester](#irulematchharvester),
  "biomes": [IRuleMatchBiome](#irulematchbiome),
  "dimensions": [IRuleMatchDimension](#irulematchdimension)
}
</pre></big>

* `blocks`: `String[]`
  * &#x1F539;*Optional* - if omitted, all blocks will be matched.
  * Syntax: `domain:path:meta`
  * Example: `minecraft:dye:4`

* `harvester`: <code>[IRuleMatchHarvester](#irulematchharvester)</code>
  * &#x1F539;*Optional* - if omitted, any harvester will be matched.
  * This object defines conditions specific to the entity that broke the block.
  
* `biomes`: <code>[IRuleMatchBiome](#irulematchbiome)</code>
  * &#x1F539;*Optional* - if omitted, all biomes will match.
  * This object defines biome conditions.
  
* `dimensions`: <code>[IRuleMatchDimension](#irulematchdimension)</code>
  * &#x1F539;*Optional* - if omitted, all dimensions will match. 
  * This object defines dimension conditions.

## IRuleMatchHarvester

This object defines conditions specific to the entity that broke the block.

<big><pre>
{
  "type": "PLAYER" | "NON_PLAYER" | "ANY",
  "heldItemMainHand": String[],
  "gamestages": [IRuleMatchHarvesterGameStage](#irulematchharvestergamestage)
}
</pre></big>

* `type`: `enum`
  * &#x1F539;*Optional* - if omitted, defaults to `ANY`.
  * `PLAYER`: The block must be broken by a player to match.
  * `NON_PLAYER`: The block must not be broken by a player to match.
  * `ANY`: The matcher does not care what broke the block. If a player broke the block and either `heldItemMainHand` or `gamestages` is provided, they will be checked.
  
* `heldItemMainHand`: `String[]`
  * &#x1F539;*Optional* - if omitted, all held items will be matched.
  * Syntax: `domain:path:meta`, `meta` can be a wildcard `*`. It is advised to use the meta wildcard `*` when matching tools.

* `gamestages`: <code>[IRuleMatchHarvesterGameStage](#irulematchharvestergamestage)</code>
  * &#x1F539;*Optional* - if omitted, any combination of gamestages will be matched.
  * This object defines conditions for matching gamestages.

## IRuleMatchHarvesterGameStage

This object defines conditions for matching gamestages.

<big><pre>
{
  "type": "ALL" | "ANY",
  "stages": String[]
}
</pre></big>

* `type`: `enum`
  * &#x1F539;*Optional* - if omitted, defaults to `ANY`.
  * `ANY`: The player must have any of the listed gamestages to match.
  * `ALL`: The player must have all of the listed gamestages to match.
  
* `stages`: `String[]`
  * This string array lists the gamestages required to match.

## IRuleMatchBiome

This object defines conditions for matching biomes.

<big><pre>
{
  "type": "WHITELIST" | "BLACKLIST",
  "ids": String[]
}
</pre></big>

* `type`: `enum`
  * &#x1F539;*Optional* - if omitted, defaults to `WHITELIST`.
  * `WHITELIST`: If the block is broken in a biome in the list, the match passes.
  * `BLACKLIST`: If the block is broken in a biome not in the list, the match passes.
  
* `ids`: `String[]`
  * This string array contains the biome ids required to match.
  * Syntax: `domain:path`
  * Example: `minecraft:birch_forest_hills`
  
## IRuleMatchDimension

This object defines conditions for matching dimensions.

<big><pre>
{
  "type": "WHITELIST" | "BLACKLIST",
  "ids": int[]
}
</pre></big>

* `type`: `enum`
  * &#x1F539;*Optional* - if omitted, defaults to `WHITELIST`.
  * `WHITELIST`: If the block is broken in a dimension in the list, the match passes.
  * `BLACKLIST`: If the block is broken in a dimension not in the list, the match passes.
  
* `ids`: `int[]`
  * This array contains the integer id's of the dimensions required to match.
  
## IRandomFortuneInt

This object defines a range used to select a fortune modified random number.

<big><pre>
{
  "min": int,
  "max": int,
  "fortuneModifier": int
}
</pre></big>

Calculation: `random.nextInt(max - min) + min + fortuneModifier * fortuneLevel`

* `min`: `int`
  * &#x1F539;*Optional* - if omitted, defaults to `1`.
  * The minimum value that will be randomly selected (inclusive).
  
* `max`: `int`
  * &#x1F539;*Optional* - if omitted, defaults to `1`.
  * The maximum value that will be randomly selected (inclusive).
  
* `fortuneModifier`
  * &#x1F539;*Optional* - if omitted, defaults to `0`.
  * This value will be added to the result of the random value for each level of fortune passed into the `BlockEvent.HarvestDropsEvent`.

## IRuleDrop

This object defines conditions used to select and weight a drop as well as the dropped item itself. 

If the drop is a valid candidate it will be placed into the weighted picker using the fortune modified weight described in the `IRuleDropSelector`.

<big><pre>
{
  "selector": [IRuleDropSelector](#iruledropselector),
  "item": [IRuleDropItem](#iruledropitem)
}
</pre></big>

* `selector`: <code>[IRuleDropSelector](#iruledropselector)</code>
  * *Optional* - if omitted, no selection conditions will apply to the drop's candidacy and the weight will default to `1`.
  * This object defines conditions for the drop's candidacy as well as its chance to drop.
  
* `item`: <code>[IRuleDropItem](#iruledropitem)</code>
  * This object defines the item to drop.

## IRuleDropSelector

This object acts as a predicate to identify drop candidates. 

If a rule is matched, the `IRuleDropSelector` is queried to determine a drop's candidacy for the weighted picker. If selected, it also supplies the fortune modified weight to use when adding the drop to the weighted picker.

<big><pre>
{
  "weight": [IRuleDropSelectorWeight](#iruledropselectorweight),
  "silktouch": "REQUIRED" | "EXCLUDED" | "ANY",
  "fortuneLevelRequired": int
}
</pre></big>

* `weight`: <code>[IRuleDropSelectorWeight](#iruledropselectorweight)</code>
  * &#x1F539;*Optional* - if omitted, defaults to `1`.
  * This object defines the fortune modified weight to use when adding this drop to the weighted picker.

* `silktouch`: `enum`
  * &#x1F539;*Optional* - if omitted, defaults to `ANY`.
  * `REQUIRED`: Silk touch is required on the tool the player is using to break the block to have a chance of getting this drop.
  * `EXCLUDED`: Silk touch must not be on the tool the player is using to break the block to have a chance of getting this drop.
  * `ANY`: Silk touch is overlooked.
  
* `fortuneLevelRequired`: `int`
  * &#x1F539;*Optional* - if omitted, defaults to `0`.
  * The minimum fortune level required for this drop to be a candidate.

## IRuleDropSelectorWeight

This object defines the fortune modified weight to use when adding a drop to the weighted picker

<big><pre>
{
  "value": int,
  "fortuneModifier": int
}
</pre></big>

Calculation: `value + fortuneModifier * fortuneLevel`

* `value`: `int`
  * &#x1F539;*Optional* - if omitted, defaults to `1`.
  * This is the weight used when adding this drop to the weighted picker.
  
* `fortuneModifier`: `int`
  * &#x1F539;*Optional* - if omitted, defaults to `0`.
  * This value will be added to weight value for each level of fortune passed into the `BlockEvent.HarvestDropsEvent`.
  
## IRuleDropItem

<big><pre>
{
  "item": String[]
  "quantity": [IRandomFortuneInt](#irandomfortuneint)
}
</pre></big>

This defines the item for the `IRuleDrop`.

* `item`: `String[]`
  * This array contains a list of items, of which one will be randomly selected for the drop. Each item in the list has an equal chance of being selected for the drop.
  * Syntax: `domain:path:meta`, *meta must not be a wildcard*.
  * &#x1F538;*Experimental*: OreDict entries in this array, like `ore:logWood`, will be expanded to the best of the system's ability. This feature is experimental and may not produce the desired results.

* `quantity`: [IRandomFortuneInt](#irandomfortuneint)
  * &#x1F539;*Optional* - if omitted, defaults to `1`
  * This uses a random, fortune modified range to determine how many of this item will be dropped if selected.