package com.duartefijamo.Fgti_war.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.duartefijamo.Fgti_war.utils.Constants;

/**
 * Representa o personagem principal do jogo (o Estudante).
 * Controla a física, movimentação e renderização do personagem.
 */
public class Player {
    // Variáveis de posição e física
    private Vector2 posicao;      // Posição (x, y) no mundo
    private Vector2 velocidade;   // Velocidade atual (x, y)
    private float largura = 24;   // Largura do corpo do personagem
    private float altura = 48;    // Altura total do personagem
    private boolean estaNoChao = false; // Indica se o personagem está tocando o chão

    // Sinalizadores para controles mobile (botões na tela)
    public boolean movendoEsquerda = false;
    public boolean movendoDireita = false;

    /**
     * Construtor do Jogador.
     * @param x Posição inicial no eixo X.
     * @param y Posição inicial no eixo Y.
     */
    public Player(float x, float y) {
        posicao = new Vector2(x, y);
        velocidade = new Vector2(0, 0);
    }

    /**
     * Atualiza o estado do jogador a cada frame.
     * @param dt Tempo decorrido desde o último frame (delta time).
     */
    public void atualizar(float dt) {
        processarEntrada();

        // Aplica a gravidade à velocidade vertical
        velocidade.y += Constants.GRAVIDADE * dt;

        // Aplica a velocidade à posição
        posicao.add(velocidade.x * dt, velocidade.y * dt);

        // Colisão simples com o chão (limitada à altura do cenário definida em Constantes)
        if (posicao.y <= Constants.ALTURA_CHAO) {
            posicao.y = Constants.ALTURA_CHAO;
            velocidade.y = 0;
            estaNoChao = true;
        } else {
            estaNoChao = false;
        }

        // Garante que o jogador não saia das bordas da grama (quase o final da tela)
        if (posicao.x < Constants.MARGEM_LATERAL) {
            posicao.x = Constants.MARGEM_LATERAL;
        }
        if (posicao.x > Constants.LARGURA_VIRTUAL - Constants.MARGEM_LATERAL - largura) {
            posicao.x = Constants.LARGURA_VIRTUAL - Constants.MARGEM_LATERAL - largura;
        }
    }

    /**
     * Verifica as entradas de teclado e sinalizadores mobile para definir a velocidade.
     */
    private void processarEntrada() {
        velocidade.x = 0; // Reseta a velocidade horizontal

        // Movimento para esquerda (Teclado ou Botão Mobile)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || movendoEsquerda) {
            velocidade.x = -Constants.VELOCIDADE_JOGADOR;
        }

        // Movimento para direita (Teclado ou Botão Mobile)
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || movendoDireita) {
            velocidade.x = Constants.VELOCIDADE_JOGADOR;
        }

        // Comando de pulo (Apenas teclado ou chamado via função pular())
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && estaNoChao) {
            pular();
        }
    }

    /**
     * Executa a ação de pular se o personagem estiver no chão.
     */
    public void pular() {
        if (estaNoChao) {
            velocidade.y = Constants.FORCA_PULO;
        }
    }

    /**
     * Desenha o personagem usando formas geométricas.
     * @param desenhador Ferramenta de desenho de formas.
     */
    public void desenhar(ShapeRenderer desenhador) {
        float x = posicao.x;
        float y = posicao.y;

        // Desenha a Cabeça
        desenhador.setColor(Color.BROWN);
        desenhador.rect(x + 4, y + 36, 16, 12);

        // Desenha o Tronco (Camisa)
        desenhador.setColor(Color.BLUE);
        desenhador.rect(x + 4, y + 16, 16, 20);

        // Desenha as Pernas (Calças)
        desenhador.setColor(Color.BLACK);
        desenhador.rect(x + 4, y, 6, 16);    // Perna Esquerda
        desenhador.rect(x + 14, y, 6, 16);   // Perna Direita

        // Desenha os Braços
        desenhador.setColor(Color.BROWN);
        desenhador.rect(x - 2, y + 20, 6, 12); // Braço Esquerdo
        desenhador.rect(x + 20, y + 20, 6, 12); // Braço Direito
    }
}
