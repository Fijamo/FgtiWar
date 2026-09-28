package com.duartefijamo.Fgti_war.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.duartefijamo.Fgti_war.utils.Constants;

/**
 * Representa o personagem principal do jogo (o Estudante/Guerreiro).
 * Controla a física, movimentação, animações (Idle, Walk, Run, Jump, Attack, Hurt, Dead, Shield) e renderização.
 */
public class Player {
    private final Vector2 posicao;
    private final Vector2 velocidade;
    private final float largura = 220f;   // Largura exibida
    private final float altura = 220f;    // Altura exibida
    private boolean estaNoChao = false;

    // Estados de animação
    public enum Estado {
        IDLE, WALK, RUN, JUMP, ATTACK, HURT, DEAD, SHIELD
    }
    private Estado estadoAtual = Estado.IDLE;
    private float stateTime = 0f;
    private boolean ultimoOlhandoEsquerda = false;

    // Animações
    private final Animation<TextureRegion> animIdle;
    private final Animation<TextureRegion> animWalk;
    private final Animation<TextureRegion> animRun;
    private final Animation<TextureRegion> animJump;
    private final Animation<TextureRegion> animAttack;
    private final Animation<TextureRegion> animHurt;
    private final Animation<TextureRegion> animDead;
    private final Animation<TextureRegion> animShield;

    // Sinalizadores para controles
    public boolean movendoEsquerda = false;
    public boolean movendoDireita = false;
    private boolean atacando = false;
    private boolean ferido = false;
    private boolean morto = false;
    private boolean defendendo = false;

    public Player(float x, float y) {
        posicao = new Vector2(x, y);
        velocidade = new Vector2(0, 0);

        // Carrega todas as animações a partir dos assets
        animIdle = carregarAnimacao("Idle.png", 0.1f, true);
        animWalk = carregarAnimacao("Walk.png", 0.1f, true);
        animRun = carregarAnimacao("Run.png", 0.08f, true);
        animJump = carregarAnimacao("Jump.png", 0.15f, false);
        animAttack = carregarAnimacao("Attack_1.png", 0.08f, false);
        animHurt = carregarAnimacao("Hurt.png", 0.1f, false);
        animDead = carregarAnimacao("Dead.png", 0.15f, false);
        animShield = carregarAnimacao("Shield.png", 0.1f, true);
    }

    private Animation<TextureRegion> carregarAnimacao(String caminho, float frameDuration, boolean looping) {
        Texture tex = new Texture(caminho);
        int frameHeight = tex.getHeight();
        int numFrames = tex.getWidth() / frameHeight;
        if (numFrames < 1) numFrames = 1;

        TextureRegion[][] tmp = TextureRegion.split(tex, frameHeight, frameHeight);
        TextureRegion[] frames = new TextureRegion[numFrames];
        int index = 0;
        for (TextureRegion[] textureRegions : tmp) {
            for (int j = 0; j < textureRegions.length && index < numFrames; j++) {
                frames[index++] = textureRegions[j];
            }
        }
        Animation<TextureRegion> animacao = new Animation<>(frameDuration, frames);
        animacao.setPlayMode(looping ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);
        return animacao;
    }

    public void atualizar(float dt) {
        stateTime += dt;
        processarEntrada();

        // Aplica gravidade
        velocidade.y += Constants.GRAVIDADE * dt;

        // Atualiza posição
        posicao.add(velocidade.x * dt, velocidade.y * dt);

        // Colisão com o chão
        if (posicao.y <= Constants.ALTURA_CHAO) {
            posicao.y = Constants.ALTURA_CHAO;
            velocidade.y = 0;
            estaNoChao = true;
        } else {
            estaNoChao = false;
        }

        // Limites do mundo (0 até LARGURA_MUNDO)
        if (posicao.x < 0) {
            posicao.x = 0;
        }
        if (posicao.x > Constants.LARGURA_MUNDO - largura) {
            posicao.x = Constants.LARGURA_MUNDO - largura;
        }

        // Atualiza direção para qual o personagem olha
        if (velocidade.x < 0) {
            ultimoOlhandoEsquerda = true;
        } else if (velocidade.x > 0) {
            ultimoOlhandoEsquerda = false;
        }

        // Determina o estado atual para animação
        atualizarEstado();
    }

    private void atualizarEstado() {
        if (morto) {
            if (estadoAtual != Estado.DEAD) {
                estadoAtual = Estado.DEAD;
                stateTime = 0f;
            }
            return;
        }

        if (atacando) {
            if (estadoAtual != Estado.ATTACK) {
                estadoAtual = Estado.ATTACK;
                stateTime = 0f;
            }
            if (animAttack.isAnimationFinished(stateTime)) {
                atacando = false;
            }
            return;
        }

        if (ferido) {
            if (estadoAtual != Estado.HURT) {
                estadoAtual = Estado.HURT;
                stateTime = 0f;
            }
            if (animHurt.isAnimationFinished(stateTime)) {
                ferido = false;
            }
            return;
        }

        if (defendendo) {
            estadoAtual = Estado.SHIELD;
            return;
        }

        if (!estaNoChao) {
            estadoAtual = Estado.JUMP;
        } else if (Math.abs(velocidade.x) > Constants.VELOCIDADE_JOGADOR * 1.2f) {
            estadoAtual = Estado.RUN;
        } else if (Math.abs(velocidade.x) > 0) {
            estadoAtual = Estado.WALK;
        } else {
            estadoAtual = Estado.IDLE;
        }
    }

    private void processarEntrada() {
        if (morto) {
            velocidade.x = 0;
            return;
        }

        velocidade.x = 0;

        // Movimento para esquerda
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || movendoEsquerda) {
            velocidade.x = -Constants.VELOCIDADE_JOGADOR;
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                velocidade.x *= 1.8f; // Correr
            }
        }

        // Movimento para direita
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || movendoDireita) {
            velocidade.x = Constants.VELOCIDADE_JOGADOR;
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                velocidade.x *= 1.8f; // Correr
            }
        }

        // Pular
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && estaNoChao) {
            pular();
        }

        // Atacar (Tecla A ou Z)
        if (Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            atacar();
        }

        // Defender (Tecla S)
        defendendo = Gdx.input.isKeyPressed(Input.Keys.S);
    }

    public void pular() {
        if (estaNoChao && !morto) {
            velocidade.y = Constants.FORCA_PULO;
        }
    }

    public void atacar() {
        if (!morto) {
            atacando = true;
            stateTime = 0f;
        }
    }

    public void tomarDano() {
        ferido = true;
        stateTime = 0f;
    }

    public void morrer() {
        morto = true;
        stateTime = 0f;
    }

    public Vector2 getPosicao() {
        return posicao;
    }

    public void desenhar(SpriteBatch batch) {
        TextureRegion frameAtual;

        switch (estadoAtual) {
            case WALK:
                frameAtual = animWalk.getKeyFrame(stateTime, true);
                break;
            case RUN:
                frameAtual = animRun.getKeyFrame(stateTime, true);
                break;
            case JUMP:
                frameAtual = animJump.getKeyFrame(stateTime, false);
                break;
            case ATTACK:
                frameAtual = animAttack.getKeyFrame(stateTime, false);
                break;
            case HURT:
                frameAtual = animHurt.getKeyFrame(stateTime, false);
                break;
            case DEAD:
                frameAtual = animDead.getKeyFrame(stateTime, false);
                break;
            case SHIELD:
                frameAtual = animShield.getKeyFrame(stateTime, true);
                break;
            case IDLE:
            default:
                frameAtual = animIdle.getKeyFrame(stateTime, true);
                break;
        }

        boolean flipX = ultimoOlhandoEsquerda;
        batch.draw(frameAtual,
                   posicao.x, posicao.y,
                   largura / 2f, altura / 2f,
                   largura, altura,
                   flipX ? -1f : 1f, 1f,
                   0f);
    }

    public void liberarRecursos() {
    }
}
