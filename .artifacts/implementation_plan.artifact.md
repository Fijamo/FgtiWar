# Implementation Plan - Android Mobile Controls and Character Details

This plan covers adding on-screen controls for Android and improving the character's visual representation.

## Proposed Changes

### Entities

#### [MODIFY] [Player.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/entities/Player.java)
- Add boolean flags `movingLeft`, `movingRight`, and `shouldJump`.
- Update `handleInput()` to check these flags in addition to keyboard keys.
- Update `draw(ShapeRenderer)` to render a character with a head, torso, arms, and legs using geometric shapes.

### Screens

#### [MODIFY] [GameScreen.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/screens/GameScreen.java)
- Add a `Stage` and `InputMultiplexer` to handle both UI touch events and game logic.
- Create on-screen buttons (Left, Right, Jump) using `TextButton` or simple `ImageButton` placeholders.
- Link button touch events to the `Player` movement flags.
- Update `render()` and `resize()` to draw and scale the UI stage.

## Verification Plan

### Manual Verification
- Deploy to an Android device or emulator.
- Verify that tapping/holding the Left and Right buttons moves the character.
- Verify that tapping the Jump button makes the character jump.
- Observe the new character appearance (head, limbs).
