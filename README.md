# StellarView mixin renderer
<a href="https://curseforge.com/minecraft/mc-mods/stellar-view-mixin-renderer" target="_blank"><img src="https://img.shields.io/curseforge/dt/1474053?style=for-the-badge&logo=curseforge&color=626e7b&label=CurseForge" alt="Curseforge"></a>
[![Modrinth](https://img.shields.io/modrinth/dt/stellar-view-mixin-renderer?style=for-the-badge&logo=modrinth&color=626e7b&label=Modrinth)](https://modrinth.com/mod/stellar-view-mixin-renderer)

Renders the Stellar View sky using a mixin.

This allows setting custom [View Centers](https://moddedmc.wiki/en/project/stellarview/latest/docs/resourcepacks/view_centers) to dimensions with a datapack.
Otherwise, it is required for a mod that overrides the dimension type and implements custom [dimension special effects](https://moddedmc.wiki/en/project/stellarview/latest/docs/modding/custom_dimension_sky).

[StellarView](https://github.com/Povstalec/StellarView) created by Povstalec

## Drawbacks
If a mod implements its own compatibility for Stellar View using the dimension special effect and adds custom logic
(beyond a simple render of Stellar View sky), this mixin will override it.
