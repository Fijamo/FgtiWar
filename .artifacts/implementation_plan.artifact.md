# Plano de Implementação - FgtiWar (Expansão de Fases, Física, Assets e Arquitetura)

Este plano descreve a reestruturação e expansão do jogo **FgtiWar** de acordo com as especificações solicitadas para as 3 fases temáticas, sistema de física avançado, parallax, gerenciamento de assets procedurais/reais e persistência de progresso.

---

## 1. Estrutura de Pacotes e Módulos

Organização do código no módulo `:core` (`com.duartefijamo.Fgti_war`):

- **`assets/`**: `GameAssets.java` (Gerenciador centralizado via `AssetManager`, gerando atlas e texturas em pixel art procedurais 32x32 e áudios temporários).
- **`entities/`**:
  - `Player.java` (Jogador com máquina de estados: PARADO, CORRENDO, PULANDO, CAINDO, DANO; física com coyote time e jump variable).
  - `Enemy.java` (Inimigos patrulheiros/bugs/guardas com colisão e dano).
  - `Item.java` (Coletáveis: Café/Cura, PenDrive/Chave, ECTS/Moeda).
  - `Door.java` (Porta de saída da fase requerendo chave).
  - `Checkpoint.java` (Ponto de salvamento intermediário na fase).
- **`world/`**:
  - `GameWorld.java` (Gerenciador do mundo, entidades, colisões AABB e eventos).
  - `LevelMap.java` (Construção programática de `TiledMap` de 32x32 tiles para 3 fases distintas com camadas de Fundo, Tiles Sólidos, Decorações e Objetos).
  - `ParallaxBackground.java` (Fundo parallax com 3 camadas com velocidades diferentes por fase).
- **`ui/`**:
  - `HUD.java` (Interface de jogo: corações de vida, pontuação/nota, botões touch mobile, chave coletada).
  - `PauseOverlay.java` (Menu de pausa com retomar, reiniciar e menu principal).
- **`screens/`**:
  - `MainMenuScreen.java` (Menu principal reformulado com botão de Seleção de Fase, Configurações e Sair).
  - `LevelSelectScreen.java` (Tela de seleção das 3 fases com indicadores de bloqueio/desbloqueio e melhor nota).
  - `GameScreen.java` (Tela de gameplay com suporte a 3 fases, câmera suave com limites do mapa).
  - `GameOverScreen.java` & `VictoryScreen.java` (Telas de fim de jogo e vitória).
- **`utils/`**:
  - `Constants.java` (Constantes do jogo, dimensões de tile 32x32, forças de física).
  - `PreferencesManager.java` (Gerenciador de progresso usando `Gdx.app.getPreferences()`).

---

## 2. Detalhamento dos Módulos

### A. Gerenciamento de Assets (`GameAssets.java`)
- Criação e registro de `TextureAtlas` e `Pixmap` procedurais com paleta de pixel art consistente (tilesets 32x32 para Lab, Servidores e Reitoria).
- Animações do jogador: Parado (Idle), Correr (Run), Saltar (Jump), Cair (Fall), Dano (Damage).
- Efeitos sonoros procedurais gerados via `Sound` / `Audio` sintéticos temporários (placeholder) registrados no `AssetManager`.
- Instruções claras no código indicando onde substituir os arquivos temporários por arquivos finais PNG/WAV (ex: Kenney.nl, CC0).

### B. Cenários e Parallax (`LevelMap.java` & `ParallaxBackground.java`)
- **Fase 1 - Laboratório/Campus**:
  - Decorações: Quadros brancos, secretárias, PCs.
  - Parallax 3 camadas: Céu/Prédios ao fundo, Árvores/Estrutura, Detalhes próximos.
- **Fase 2 - Data Center/Servidores**:
  - Decorações: Racks de servidores, cabos, luzes a piscar.
  - Parallax 3 camadas: Paredes escuras, Tubulações/Luzes, Grelhas de ventilação.
- **Fase 3 - Gabinete da Reitoria/Chefe**:
  - Decorações: Diplomas, estantes de livros, mesas executivas.
  - Parallax 3 camadas: Janelas da cidade, Cortinas/Colunas, Tapetes/Molduras.
- **Câmera**: Seguimento do jogador com interpolação suave (lerp) e limitação (clamping) aos limites do mapa.

### C. Física de Plataforma (`Player.java` & Colisões AABB)
- Gravidade contínua e controle de velocidade máxima de queda (terminal velocity).
- **Coyote Time**: Permite pular até 0.15s após sair da borda de uma plataforma.
- **Pulo Variável**: Soltar o botão de pulo reduz a velocidade vertical ascendente para um pulo mais curto.
- **Jump Buffer**: Registra a intenção de pulo pressionada um pouco antes de tocar o chão.
- Colisões AABB precisas calculadas por tiles sólidos no `TiledMap`.

### D. Persistência de Progresso (`PreferencesManager.java`)
- Chaves salvas: `fase_desbloqueada` (1 a 3), `melhor_nota_fase_1`, `melhor_nota_fase_2`, `melhor_nota_fase_3`, `volume_som`.

---

## 3. Modificações nos Arquivos Existentes e Novos Arquivos

### [MODIFY] [Constants.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/utils/Constants.java)
- Adicionar constantes para tamanho de tile (32px), gravidade, coyote time, tempo de invencibilidade, nome da preferência e IDs de fases.

### [NEW] [PreferencesManager.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/utils/PreferencesManager.java)
- Métodos para obter/definir fase máxima desbloqueada, notas/pontuações e configurações.

### [NEW] [GameAssets.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/assets/GameAssets.java)
- Gerenciador de assets com gerador de texturas procedurais Pixel Art em Pixmap de 32x32 para tiles, entidades e UI.
- Carregamento de sons temporários sintetizados.

### [NEW] [ParallaxBackground.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/world/ParallaxBackground.java)
- Renderização de 3 camadas com fator de deslocamento (scrollSpeed) dinâmico por fase.

### [NEW] [LevelMap.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/world/LevelMap.java)
- Construção de `TiledMap` para Fases 1, 2 e 3 com camadas: Fundo, Tiles Sólidos, Decorações, Objetos.

### [NEW] [Door.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/entities/Door.java), [Item.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/entities/Item.java), [Enemy.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/entities/Enemy.java), [Checkpoint.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/entities/Checkpoint.java)
- Entidades do jogo com retângulos de colisão, animação/desenho e lógica de comportamento.

### [MODIFY] [Player.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/entities/Player.java)
- Reformulação com física completa (Coyote Time, Jump Buffer, Pulo Variável, Animações, Colisão AABB por tiles).

### [NEW] [GameWorld.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/world/GameWorld.java)
- Integração do jogador, mapa, inimigos, itens e gerenciamento de colisões e estados de fase.

### [NEW] [HUD.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/ui/HUD.java)
- Interface com corações de vida, barra de progresso/ECTS, aviso de chave e controles virtuais touch.

### [NEW] [LevelSelectScreen.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/screens/LevelSelectScreen.java)
- Tela de seleção de fase (1, 2, 3) com bloqueio e visualização de pontuação máxima.

### [MODIFY] [MainMenuScreen.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/screens/MainMenuScreen.java)
- Atualização para direcionar para Seleção de Fase e usar Skin/Assets centralizados.

### [MODIFY] [GameScreen.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/screens/GameScreen.java)
- Integração com `GameWorld`, `LevelMap`, `ParallaxBackground`, `HUD`, suporte a fases e transição de telas.

### [NEW] [GameOverScreen.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/screens/GameOverScreen.java) & [VictoryScreen.java](file:///C:/Users/Age/Desktop/School/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/screens/VictoryScreen.java)
- Telas de Game Over e Vitória ao concluir a fase.

---

## 4. Plano de Verificação

### Teste de Compilação e Execução
- Executar `./gradlew lwjgl3:run` ou `gradle_build` para garantir zero erros de compilação.
- Testar a troca de telas: Menu -> Seleção de Fase -> Fase 1 -> Vitória/Fase 2 -> Pause -> Menu.

### Teste Funcional de Física e Gameplay
1. **Coyote Time**: Caminhar fora de uma plataforma e pressionar pulo imediatamente (dentro de 0.15s) para garantir que o pulo é executado.
2. **Pulo Variável**: Pressionar rapidamente o botão de pulo para um pulo baixo, e segurar para pulo completo.
3. **Colisões**: Testar andar contra paredes sólidas de tiles para garantir sem entravamento ou "tunneling".
4. **Coleta de Itens e Porta**: Pegar moedas/ECTS, chave para destravar a porta e passar para a próxima fase.
5. **Persistência**: Concluir a Fase 1, voltar ao menu principal, e verificar se a Fase 2 está desbloqueada ao reentrar.
