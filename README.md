# chit
A vanilla-like implementation of Optifine's CIT, shaped how I believe it would be/like.

```
conditional: has_enchantment
    - fields:
        - enchantment id (required)
        - level (not required, will work on any level)
      
range_dispatch: enchantment_level
    - value based on enchantment level
    - fields:
        - enchantment id (required)
```