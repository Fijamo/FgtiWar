# Plano de Implementação - Refatoração para Português, Ajustes de UI e Menu

Este plano detalha a tradução do código para português, inclusão de comentários, ajustes nos botões móveis, melhoria no menu inicial e adição de controles de pausa e navegação.

## Mudanças Propostas

### Utilitários

#### [MODIFY] [Constantes.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/utils/Constants.java)
- Traduzir constantes: `LARGURA_V`, `ALTURA_V`, `VELOCIDADE_JOGADOR`, `FORCA_PULO`, `GRAVIDADE`.

### Entidades

#### [MODIFY] [Player.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/entities/Player.java)
- Renomear variáveis: `posicao`, `velocidade`, `largura`, `altura`, `estaNoChao`, `movendoEsquerda`, `movendoDireita`.
- Renomear métodos: `atualizar`, `processarEntrada`, `desenhar`, `pular`.
- Adicionar comentários detalhados em português.
- Implementar desenho do personagem com cabeça, tronco e membros (já solicitado anteriormente, manteremos a lógica refinada).

### Telas (Screens)

#### [MODIFY] [MainMenuScreen.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/screens/MainMenuScreen.java)
- Exibir o nome do jogo: "A JORNADA DO ESTUDANTE DE TI".
- Adicionar botão **JOGAR** centralizado.
- Adicionar indicador/botão **NÍVEL: 1**.
- Traduzir toda a lógica interna e adicionar comentários.

#### [MODIFY] [GameScreen.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/screens/GameScreen.java)
- **Controles Inferiores:**
    - Manter botões direcionais (`<` e `>`) menores (aprox. 60x60).
    - Remover botão de pulo da tela (o pulo continuará via teclado ou podemos adicionar uma área de toque invisível se necessário, mas por enquanto removeremos o botão físico).
- **Controles Superiores (Nova Barra):**
    - Adicionar botão **PAUSA** no topo.
    - Adicionar botão **VOLTAR** no topo (retorna ao menu principal).
- Renomear variáveis e métodos para português (ex: `palcoUI`, `criarInterface`, `renderizar`).
- Adicionar comentários em português.

## Plano de Verificação

### Verificação Manual
- **Menu Inicial:** Verificar se o título, botão "JOGAR" e "NÍVEL: 1" estão visíveis e funcionais.
- **Jogo:** 
    - Verificar se os botões direcionais estão menores.
    - Verificar se os botões "PAUSA" e "VOLTAR" aparecem no topo.
    - Verificar se "VOLTAR" retorna ao menu.
- **Código:** Revisar se 100% das funções, variáveis e comentários estão em português.
