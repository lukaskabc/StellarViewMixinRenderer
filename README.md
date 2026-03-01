# StellarView mixin renderer
<a href="https://curseforge.com/minecraft/mc-mods/stellar-view-mixin-renderer" target="_blank"><img src="https://img.shields.io/curseforge/dt/1474053?style=for-the-badge&logo=curseforge&color=626e7b&label=CurseForge" alt="Curseforge"></a>
[![Modrinth](https://img.shields.io/modrinth/dt/stellar-view-mixin-renderer?style=for-the-badge&logo=modrinth&color=626e7b&label=Modrinth)](https://modrinth.com/mod/stellar-view-mixin-renderer)

Renders the Stellar View sky using a mixin.

This allows setting custom [View Centers](https://moddedmc.wiki/en/project/stellarview/latest/docs/resourcepacks/view_centers) to dimensions with a resourcepack.
Otherwise, it is required for a mod that overrides the dimension type and implements custom [dimension special effects](https://moddedmc.wiki/en/project/stellarview/latest/docs/modding/custom_dimension_sky).

[StellarView](https://github.com/Povstalec/StellarView) created by Povstalec

## Drawbacks
If a mod implements its own compatibility for Stellar View using the dimension special effect and adds custom logic
(beyond a simple render of Stellar View sky), this mixin will override it.

## Dimension Special Effects overrides
Some Dimensions Special Effects can be overridden using a resourcepack.

To override effects for a dimension `dimension_namespace>:<dimension_name>` e.g. `minecraft:overworld`:  
create resourcepack entry:  
```
assets/<dimension_namespace>/stellarview_mixin_renderer/dimension_special_effects/<dimension_name>.json
```
```
assets/minecraft/stellarview_mixin_renderer/dimension_special_effects/overworld.json
```

Supported options are (with default values):
```
"disable_override": false, // when true, the override will not be applied and all other options are ignored
"cloud_height": null, // float
"has_ground": null, // boolean
"sky_type": null, // "NONE", "NORMAL", "END"
"force_bright_lightmap": null, // boolean
"constant_ambient_light": null, // boolean
"has_snow_and_rain": true, // when false, snow/rain rendering will be skipped
"disable_fog": false, // when true, fog rendering will be skipped, and fog_always option will be ignored
"fog_always": false, // when true, fog will be always rendered
"has_sunrise": true, // when false, sunrise color will not be provided and the rendering will be skipped
"has_clouds": true // when false, cloud rendering will be skipped

```