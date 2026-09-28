# Implementation Plan - Complete Character Animation States & 3x Scrolling Background

This plan covers updating the player character to support all sprite sheet animation states (`Idle`, `Walk`, `Run`, `Jump`, `Attack`, `Hurt`, `Dead`, `Shield`) using `TextureRegion` and `Animation`, and expanding the game world width to 3 times the virtual screen width with a scrolling camera and repeating background.

## User Review Required

> [!IMPORTANT]
> - **Animation States**: We will support Idle, Walk, Run, Jump, Attack, Hurt, Dead, and Shield states, mapped to their respective sprite sheet assets (`Idle.png`, `Walk.png`, `Run.png`, `Jump.png`, `Attack_1.png`, `Hurt.png`, `Dead.png`, `Shield.png`, etc.).
> - **World Width**: The world width will be set to `3 * Constants.LARGURA_VIRTUAL` (2400 pixels), with the background repeated 3 times and the camera following the player.

## Proposed Changes

### Core Game Package (`com.duartefijamo.Fgti_war`)

#### [MODIFY] [Constants.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/utils/Constants.java)
- Add world width constant (`LARGURA_MUNDO = LARGURA_VIRTUAL * 3f`).

#### [MODIFY] [Player.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/entities/Player.java)
- Load texture sheets for all states (`Idle.png`, `Walk.png`, `Run.png`, `Jump.png`, `Attack_1.png`, `Hurt.png`, `Dead.png`, `Shield.png`).
- Create LibGDX `Animation<TextureRegion>` for each state.
- Manage state switching (Idle, Walking, Running, Jumping, Attacking, etc.) based on input and physics.
- Restrict player movement within `[0, Constants.LARGURA_MUNDO - largura]`.

#### [MODIFY] [GameScreen.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/screens/GameScreen.java)
- Update camera position to follow the player horizontally, clamped between `0` and `Constants.LARGURA_MUNDO - Constants.LARGURA_VIRTUAL`.
- Draw the background texture 3 times across the world width (`0`, `LARGURA_VIRTUAL`, `LARGURA_VIRTUAL * 2`).

## Verification Plan

### Automated Tests
- Run Gradle build (`classes`) to ensure clean compilation.

### Manual Verification
- Run the desktop version (`lwjgl3`) to verify:
  1. Animations play correctly per state (Idle, Walk, Jump, Attack, etc.) with proper frame cropping.
  2. The background repeats 3 times and the camera scrolls as the player moves across the 2400-pixel world.
