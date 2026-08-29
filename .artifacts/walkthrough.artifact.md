# Walkthrough - Ajuste Fino da Grama e Movimento

Fiz os ajustes solicitados na grama e na movimentação do personagem para garantir que ele não caia e respeite os limites visuais.

## Mudanças Realizadas

### 1. Novo Botão no Menu
- **Botão SAIR:** Adicionei o botão para fechar o aplicativo diretamente do menu inicial. Ao clicar nele, o jogo é encerrado corretamente.

### 2. Visual da Grama (Chão)
- **Altura Reduzida:** A grama agora é mais fina (baixei para **30 pixels** de altura), deixando o cenário mais elegante.
- **Comprimento Ajustado:** A grama não ocupa mais 100% da largura. Adicionei uma margem de **20 pixels** em cada lado, fazendo com que ela termine "quase no final da tela".

### 2. Bloqueio de Movimento (Sem Quedas)
- Conforme solicitado, o **Estudante** agora está bloqueado pelos limites da grama. 
- Se você tentar andar para fora da parte verde, o personagem irá parar exatamente na borda. **Ele não cai mais no vazio**, garantindo que ele ande apenas onde há grama.

### 3. Sincronização de Física
- A colisão foi ajustada para a nova altura de 30 pixels.
- Os limites laterais de movimento agora usam a mesma constante da grama (`MARGEM_LATERAL`), garantindo que se você mudar o tamanho da grama no futuro, o limite de movimento do personagem mudará automaticamente.

## Como Testar
1.  Tente levar o personagem até o canto esquerdo ou direito da tela. 
2.  Observe que ele para antes de sair da grama verde.
3.  Note que a grama está mais baixa e não toca as bordas da tela.

Tudo continua devidamente comentado e em português!
