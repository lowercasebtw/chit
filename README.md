# Chit

A vanilla-like implementation of Optifine's CIT, shaped how I believe it would be/like.

## Types

#### Conditional:

- has_enchantment (if stack has the enchantment)
    - fields:
        - enchantment: Enchantment Identifier [REQUIRED]
        - level [OPTIONAL]

#### Range Dispatch:

- enchantment_level (value based on enchantment level)
    - fields:
        - enchantment: Enchantment Identifier [REQUIRED]

#### Select:

- rarity (value of the stack rarity component)

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