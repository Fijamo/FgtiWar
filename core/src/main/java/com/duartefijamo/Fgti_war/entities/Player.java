package com.duartefijamo.Fgti_war.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.duartefijamo.Fgti_war.assets.GameAssets;
import com.duartefijamo.Fgti_war.utils.Constants;
import com.duartefijamo.Fgti_war.world.LevelMap;

/**
 * Personagem principal (Estudante) com máquina de estados e física de plataforma completa:
 * Coyote Time, Pulo Variável, Jump Buffer, Animação e Colisão AABB precisa por tiles.
 */
public class Player {

    public enum State { IDLE, RUNNING, JUMPING, FALLING, HURT }

    private final Vector2 posicao;
    private final Vector2 velocidade;
    private final Rectangle bounds;
    private State estadoAtual = State.IDLE;

    private float largura = 24f;
    private float altura = 30f;

    // Estado de Saúde e Vidas
    private int vidaMax = 3;
    private int vidaAtual = 3;
    private float tempoInvencivel = 0f;

    // Sinalizadores Físicos
    private boolean estaNoChao = false;
    private float coyoteTimer = 0f;
    private float jumpBufferTimer = 0f;
    private float stateTime = 0f;
    private boolean estavaPressionadoPulo = false;

    // Controles Mobile Touch
    public boolean movendoEsquerda = false;
    public boolean movendoDireita = false;
    public boolean movendoPulo = false;
    public boolean puloPressionado = false;

    public Player(float x, float y) {
        posicao = new Vector2(x, y);
        velocidade = new Vector2(0, 0);
        bounds = new Rectangle(x, y, largura, altura);
    }

    public void atualizar(float dt, LevelMap mapa) {
        float safeDt = Math.min(dt, 0.033f);
        stateTime += safeDt;

        if (tempoInvencivel > 0) tempoInvencivel -= safeDt;
        if (jumpBufferTimer > 0) jumpBufferTimer -= safeDt;

        if (estaNoChao) {
            coyoteTimer = Constants.COYOTE_TIME_DURATION;
        } else {
            if (coyoteTimer > 0) coyoteTimer -= safeDt;
        }

        processarEntradas();
        aplicarFisicaEColisoes(safeDt, mapa);
        atualizarEstado();
    }

    private void processarEntradas() {
        velocidade.x = 0;

        // Movimento Esquerda (Setas, A/D, Touch)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A) || movendoEsquerda) {
            velocidade.x = -Constants.VELOCIDADE_JOGADOR;
        }
        // Movimento Direita
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D) || movendoDireita) {
            velocidade.x = Constants.VELOCIDADE_JOGADOR;
        }

        boolean estaPressionadoAgora = Gdx.input.isKeyPressed(Input.Keys.SPACE)
                                    || Gdx.input.isKeyPressed(Input.Keys.W)
                                    || Gdx.input.isKeyPressed(Input.Keys.UP)
                                    || movendoPulo;

        // Detecta acionamento do pulo
        boolean querPular = (estaPressionadoAgora && !estavaPressionadoPulo) || puloPressionado;

        if (querPular) {
            jumpBufferTimer = Constants.JUMP_BUFFER_DURATION;
            puloPressionado = false;
        }

        // Executa pulo se houver buffer e estiver no chão ou no Coyote Time
        if (jumpBufferTimer > 0 && coyoteTimer > 0) {
            pular();
        }

        // Pulo Variável: Se o botão de pulo foi solto durante a subida, corta a velocidade vertical
        if (estavaPressionadoPulo && !estaPressionadoAgora && velocidade.y > 0) {
            velocidade.y *= Constants.JUMP_CUT_FACTOR;
        }

        estavaPressionadoPulo = estaPressionadoAgora;
    }

    public void pular() {
        velocidade.y = Constants.FORCA_PULO;
        coyoteTimer = 0f;
        jumpBufferTimer = 0f;
        estaNoChao = false;
    }

    private void aplicarFisicaEColisoes(float dt, LevelMap mapa) {
        // Gravidade
        velocidade.y += Constants.GRAVIDADE * dt;
        if (velocidade.y < Constants.VELOCIDADE_QUEDA_MAX) {
            velocidade.y = Constants.VELOCIDADE_QUEDA_MAX;
        }

        // --- MOVIMENTO E COLISÃO HORIZONTAL (EIXO X) ---
        posicao.x += velocidade.x * dt;
        bounds.x = posicao.x;

        if (mapa != null) {
            int startX = (int) (bounds.x / Constants.TILE_SIZE);
            int endX = (int) ((bounds.x + bounds.width) / Constants.TILE_SIZE);
            int startY = (int) (bounds.y / Constants.TILE_SIZE);
            int endY = (int) ((bounds.y + bounds.height) / Constants.TILE_SIZE);

            for (int tileX = startX; tileX <= endX; tileX++) {
                for (int tileY = startY; tileY <= endY; tileY++) {
                    if (tileY >= 0 && mapa.isTileSolid(tileX, tileY)) {
                        if (velocidade.x > 0) {
                            posicao.x = tileX * Constants.TILE_SIZE - bounds.width;
                        } else if (velocidade.x < 0) {
                            posicao.x = (tileX + 1) * Constants.TILE_SIZE;
                        }
                        bounds.x = posicao.x;
                        velocidade.x = 0;
                    }
                }
            }
        }

        // --- MOVIMENTO E COLISÃO VERTICAL (EIXO Y) ---
        posicao.y += velocidade.y * dt;
        bounds.y = posicao.y;
        estaNoChao = false;

        if (mapa != null) {
            int startX = (int) (bounds.x / Constants.TILE_SIZE);
            int endX = (int) ((bounds.x + bounds.width) / Constants.TILE_SIZE);
            int startY = (int) (bounds.y / Constants.TILE_SIZE);
            int endY = (int) ((bounds.y + bounds.height) / Constants.TILE_SIZE);

            for (int tileX = startX; tileX <= endX; tileX++) {
                for (int tileY = startY; tileY <= endY; tileY++) {
                    if (tileY >= 0 && mapa.isTileSolid(tileX, tileY)) {
                        if (velocidade.y > 0) {
                            posicao.y = tileY * Constants.TILE_SIZE - bounds.height;
                            velocidade.y = 0;
                        } else if (velocidade.y < 0) {
                            posicao.y = (tileY + 1) * Constants.TILE_SIZE;
                            velocidade.y = 0;
                            estaNoChao = true;
                        }
                        bounds.y = posicao.y;
                    }
                }
            }
        } else {
            // Suporte legado se mapa for nulo
            if (posicao.y <= Constants.ALTURA_CHAO) {
                posicao.y = Constants.ALTURA_CHAO;
                velocidade.y = 0;
                estaNoChao = true;
            }
        }
    }

    private void atualizarEstado() {
        if (tempoInvencivel > 0.8f) {
            estadoAtual = State.HURT;
        } else if (!estaNoChao) {
            estadoAtual = velocidade.y > 0 ? State.JUMPING : State.FALLING;
        } else if (Math.abs(velocidade.x) > 0) {
            estadoAtual = State.RUNNING;
        } else {
            estadoAtual = State.IDLE;
        }
    }

    public void receberDano(int quantidade) {
        if (tempoInvencivel <= 0) {
            vidaAtual -= quantidade;
            tempoInvencivel = Constants.TEMPO_INVENCIBILIDADE;
            velocidade.y = Constants.FORCA_PULO * 0.6f; // Pequeno recuo
        }
    }

    public void curar(int quantidade) {
        vidaAtual = Math.min(vidaMax, vidaAtual + quantidade);
    }

    public void desenhar(SpriteBatch batch, GameAssets assets) {
        TextureRegion region;
        switch (estadoAtual) {
            case RUNNING:
                region = assets.playerRun.getKeyFrame(stateTime, true);
                break;
            case JUMPING:
                region = assets.playerJump;
                break;
            case FALLING:
                region = assets.playerFall;
                break;
            case HURT:
                region = assets.playerHurt;
                break;
            case IDLE:
            default:
                region = assets.playerIdle;
                break;
        }

        // Piscar durante invencibilidade
        if (tempoInvencivel > 0 && ((int) (tempoInvencivel * 20) % 2 == 0)) {
            return;
        }

        if (region != null) {
            batch.draw(region, bounds.x - 4, bounds.y, 32, 32);
        }
    }

    public void desenhar(ShapeRenderer desenhador) {
        desenhador.setColor(tempoInvencivel > 0 ? Color.RED : Color.CYAN);
        desenhador.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Vector2 getPosicao() { return posicao; }
    public Rectangle getBounds() { return bounds; }
    public int getVidaAtual() { return vidaAtual; }
    public int getVidaMax() { return vidaMax; }
    public boolean estaVivo() { return vidaAtual > 0; }
    public State getEstadoAtual() { return estadoAtual; }
}
