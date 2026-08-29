# Plano de Implementação - Ajuste Fino da Grama (Chão)

Este plano visa ajustar a geometria do chão (grama verde) para que seja menos espesso verticalmente e não ocupe a largura total da tela, criando um efeito de plataforma centralizada.

## Mudanças Propostas

### Utilitários

#### [MODIFY] [Constants.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/utils/Constants.java)
- Alterar `ALTURA_CHAO` de 80 para 40.

### Entidades

#### [MODIFY] [Player.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/entities/Player.java)
- Atualizar a lógica de colisão para que o jogador apenas fique "no chão" se estiver dentro dos limites horizontais da grama (entre 10 e 790 pixels).
- Se o jogador sair das bordas, ele deve cair (a gravidade o puxará para baixo da tela).

### Telas (Screens)

#### [MODIFY] [GameScreen.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/screens/GameScreen.java)
- Alterar o desenho do retângulo da grama: `desenhadorFormas.rect(10, 0, 780, Constants.ALTURA_CHAO)`.
- Isso cria uma margem de 10 pixels em cada lado da tela (800 - 20 = 780).

## Plano de Verificação

### Verificação Manual
- **Visual:** Confirmar que a grama está mais fina (40px) e que existem pequenos espaços vazios nos cantos esquerdo e direito.
- **Física:** Verificar se o personagem caminha sobre a grama e se ele cai no "buraco" caso chegue ao final das bordas laterais.
