# Plano de Implementação - Reorganização da UI e Controle de Pausa

Este plano detalha a remoção da informação de nível do menu principal, sua adição na tela de jogo e a reestruturação dos botões de controle superiores.

## Mudanças Propostas

### Telas (Screens)

#### [MODIFY] [MainMenuScreen.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/screens/MainMenuScreen.java)
- Remover o rótulo (Label) "NIVEL: 1" da interface do menu.

#### [MODIFY] [GameScreen.java](file:///C:/Users/alvar/Desktop/All/11111FACULDADE/Chande/FgtiWar/core/src/main/java/com/duartefijamo/Fgti_war/screens/GameScreen.java)
- **Informação de Nível:** Adicionar um rótulo "NIVEL: 1" na parte superior central da tela.
- **Botão Voltar:** Mover o botão "VOLTAR" para o canto superior esquerdo.
- **Botões de Pausa/Play:** 
    - Adicionar o botão "PLAY" ao lado do botão "PAUSA".
    - O botão "PAUSA" definirá o estado de pausa como verdadeiro.
    - O botão "PLAY" definirá o estado de pausa como falso.
- **Layout Superior:** Utilizar uma tabela estruturada para alinhar corretamente os elementos no topo da tela.
- **Comentários:** Atualizar os comentários em português para refletir as novas funcionalidades.

## Plano de Verificação

### Verificação Manual
- **Menu Inicial:** Confirmar que o texto "NIVEL: 1" não aparece mais.
- **Tela de Jogo:**
    - Verificar se "VOLTAR" está no canto superior esquerdo.
    - Verificar se "NIVEL: 1" está centralizado no topo.
    - Verificar se os botões "PLAY" e "PAUSA" estão lado a lado no canto superior direito.
    - Testar se o botão "PAUSA" congela o jogo e o botão "PLAY" retoma o movimento do estudante.
    - Confirmar que o botão "VOLTAR" ainda funciona corretamente.
