# Chit

A vanilla-like implementation of Optifine's CIT, shaped how I believe it would be/like.

## Types

#### Conditional:

- has_enchantment (if stack has the enchantment/stored enchantment)
    - fields:
        - enchantment: Enchantment identifier [REQUIRED]
        - level [OPTIONAL]
- has_enchantments (if stack has all the listed enchantments/stored enchantments)
    - fields:
        - enchantments: List of enchantment identifier [REQUIRED]
- predicate
    - fields
        - predicate: A item sub predicate [REQUIRED]
- custom_name
    - fields
        - regex: The regex in string form, to test on the item's custom name

#### Range Dispatch:

- enchantment_level (value based on enchantment level)
    - fields:
        - enchantment: Enchantment identifier [REQUIRED]

#### Select:

- rarity (value of the stack rarity component)
- "minecraft:component" (Backported from 25w03a, self-explanatory)

## Examples

diamond_sword.json

```json
{
	"model": {
		"type": "minecraft:condition",
		"property": "chit:has_enchantment",
		"enchantment": "minecraft:fire_aspect",
		"on_false": {
			"type": "minecraft:model",
			"model": "minecraft:item/diamond_sword"
		},
		"on_true": {
			"type": "minecraft:model",
			"model": "minecraft:item/golden_sword"
		}
	}
}
```

enchanted_book.json

```json
{
	"model": {
		"type": "minecraft:range_dispatch",
		"property": "chit:enchantment_level",
		"enchantment": "minecraft:bane_of_arthropods",
		"entries": [
			{
				"threshold": 3,
				"model": {
					"type": "minecraft:model",
					"model": "minecraft:item/book"
				}
			}
		],
		"fallback": {
			"type": "minecraft:model",
			"model": "minecraft:item/enchanted_book"
		}
	}
}
```

golden_apple.json

```json
{
	"model": {
		"type": "minecraft:select",
		"property": "chit:rarity",
		"cases": [
			{
				"when": "rare",
				"model": {
					"type": "minecraft:model",
					"model": "minecraft:item/apple"
				}
			}
		],
		"fallback": {
			"type": "minecraft:model",
			"model": "minecraft:item/golden_apple"
		}
	}
}
```

bone.json

```json
{
	"model": {
		"type": "minecraft:condition",
		"property": "chit:has_enchantments",
		"enchantments": [
			"minecraft:unbreaking",
			"minecraft:sharpness"
		],
		"on_true": {
			"type": "minecraft:model",
			"model": "minecraft:item/stick"
		},
		"on_false": {
			"type": "minecraft:model",
			"model": "minecraft:item/bone"
		}
	}
}
```

iron_sword.json

```json
{
	"model": {
		"type": "minecraft:condition",
		"property": "chit:predicate",
		"predicate": {
			"minecraft:damage": {
				"damage": {
					"min": 125
				}
			}
		},
		"on_true": {
			"type": "minecraft:model",
			"model": "minecraft:item/stone_sword"
		},
		"on_false": {
			"type": "minecraft:model",
			"model": "minecraft:item/iron_sword"
		}
	}
}
```

bone.json

```json
{
	"model": {
		"type": "minecraft:condition",
		"property": "chit:custom_name",
		"regex": "lol",
		"on_true": {
			"type": "minecraft:model",
			"model": "minecraft:item/snowball"
		},
		"on_false": {
			"type": "minecraft:model",
			"model": "minecraft:item/bone"
		}
	}
}
```

stick.json

```json
{
	"model": {
		"type": "minecraft:select",
		"property": "minecraft:component",
		"component": "minecraft:custom_name",
		"cases": [
			{
				"when": "{\"color\":\"yellow\",\"italic\":false,\"bold\":true,\"text\":\"Hello world\"}",
				"model": {
					"type": "minecraft:model",
					"model": "minecraft:item/blaze_rod"
				}
			}
		],
		"fallback": {
			"type": "minecraft:model",
			"model": "minecraft:item/stick"
		}
	}
}
```