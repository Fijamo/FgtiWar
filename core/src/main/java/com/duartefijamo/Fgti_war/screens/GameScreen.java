package com.duartefijamo.Fgti_war.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.duartefijamo.Fgti_war.FgtiWar;
import com.duartefijamo.Fgti_war.entities.Player;
import com.duartefijamo.Fgti_war.utils.Constants;

/**
 * Tela principal do jogo (Fase 1).
 * Gerencia a renderização do mundo, do jogador e da interface de usuário (UI).
 */
public class GameScreen implements Screen {
    private final FgtiWar jogo;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private ShapeRenderer desenhadorFormas;
    private Player jogador;

    private Stage palcoUI; // Palco para os botões de controle
    private Skin skin;
    private boolean pausado = false; // Controle de estado de pausa

    // Textura para o cenário de fundo
    private Texture texturaCenario;

    /**
     * Construtor da tela de jogo.
     * @param jogo Instância principal do jogo.
     */
    public GameScreen(FgtiWar jogo) {
        this.jogo = jogo;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.LARGURA_VIRTUAL, Constants.ALTURA_VIRTUAL, camera);
        desenhadorFormas = new ShapeRenderer();
        jogador = new Player(100, 100);

        // Carrega a imagem do cenário (Laboratório)
        texturaCenario = new Texture("05_Laboratorio_de_Informatica.png");

        criarInterface();
    }

    /**
     * Cria a interface de usuário com botões direcionais e controles de navegação.
     */
    private void criarInterface() {
        palcoUI = new Stage(new FitViewport(Constants.LARGURA_VIRTUAL, Constants.ALTURA_VIRTUAL));

        // Permite que o palco processe toques na tela
        InputMultiplexer multiplexador = new InputMultiplexer();
        multiplexador.addProcessor(palcoUI);
        Gdx.input.setInputProcessor(multiplexador);

        prepararSkin();

        // Tabela para botões direcionais (Canto inferior esquerdo)
        Table tabelaControles = new Table();
        tabelaControles.left().bottom();
        tabelaControles.setFillParent(true);

        TextButton botaoEsquerda = new TextButton("<", skin);
        TextButton botaoDireita = new TextButton(">", skin);
        TextButton botaoSaltar = new TextButton("SALTAR", skin);

        // Configura ouvintes de toque para os botões direcionais
        botaoEsquerda.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jogador.movendoEsquerda = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jogador.movendoEsquerda = false;
            }
        });

        botaoDireita.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jogador.movendoDireita = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jogador.movendoDireita = false;
            }
        });

        botaoSaltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogador.pular();
            }
        });

        // Adiciona botões à tabela (Seta Esquerda, Seta Direita ... Espaço ... Saltar)
        tabelaControles.add(botaoEsquerda).size(60, 60).pad(10);
        tabelaControles.add(botaoDireita).size(60, 60).pad(10);
        tabelaControles.add().expandX(); // Empurra o botão saltar para a direita
        tabelaControles.add(botaoSaltar).size(100, 60).pad(10);

        palcoUI.addActor(tabelaControles);

        // Tabela para elementos do topo (Voltar, Nível, Pausa/Play)
        Table tabelaTopo = new Table();
        tabelaTopo.top().setFillParent(true);
        palcoUI.addActor(tabelaTopo);

        // Botão Voltar (Canto superior esquerdo)
        TextButton botaoVoltar = new TextButton("VOLTAR", skin);
        botaoVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogo.setScreen(new MainMenuScreen(jogo));
            }
        });

        // Informação de Nível (Centralizado no topo)
        Label nivelLabel = new Label("NIVEL: 1", skin, "padrao");

        // Botões de Pausa e Play (Canto superior direito)
        Table tabelaPausaPlay = new Table();
        TextButton botaoPausa = new TextButton("PAUSA", skin);
        TextButton botaoPlay = new TextButton("PLAY", skin);

        botaoPausa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pausado = true;
            }
        });

        botaoPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pausado = false;
            }
        });

        tabelaPausaPlay.add(botaoPausa).size(70, 40).padRight(5);
        tabelaPausaPlay.add(botaoPlay).size(70, 40);

        // Organiza a linha do topo: Voltar | Nível | Pausa+Play
        tabelaTopo.add(botaoVoltar).size(80, 40).pad(10).left();
        tabelaTopo.add(nivelLabel).expandX().center();
        tabelaTopo.add(tabelaPausaPlay).pad(10).right();
    }

    /**
     * Define o estilo visual dos botões e textos.
     */
    private void prepararSkin() {
        skin = new Skin();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("branco", new Texture(pixmap));

        BitmapFont fonte = new BitmapFont();
        skin.add("padrao", fonte);

        TextButton.TextButtonStyle estiloBotao = new TextButton.TextButtonStyle();
        estiloBotao.up = skin.newDrawable("branco", Color.DARK_GRAY);
        estiloBotao.down = skin.newDrawable("branco", Color.NAVY);
        estiloBotao.font = skin.getFont("padrao");
        skin.add("default", estiloBotao);

        Label.LabelStyle estiloLabel = new Label.LabelStyle();
        estiloLabel.font = skin.getFont("padrao");
        skin.add("padrao", estiloLabel);
    }

    @Override
    public void render(float delta) {
        // Se não estiver pausado, atualiza a lógica do jogador
        if (!pausado) {
            jogador.atualizar(delta);
        }

        // Limpa a tela
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Atualiza a posição da câmera para seguir o jogador (com limites do mundo)
        float cameraX = jogador.getPosicao().x + 32;
        float meiaLarguraTela = Constants.LARGURA_VIRTUAL / 2f;
        if (cameraX < meiaLarguraTela) {
            cameraX = meiaLarguraTela;
        } else if (cameraX > Constants.LARGURA_MUNDO - meiaLarguraTela) {
            cameraX = Constants.LARGURA_MUNDO - meiaLarguraTela;
        }
        camera.position.x = cameraX;
        camera.position.y = Constants.ALTURA_VIRTUAL / 2f;
        camera.update();

        // --- 1. Renderização de Texturas (Cenário e Jogador) ---
        jogo.batch.setProjectionMatrix(camera.combined);
        jogo.batch.begin();

        // Desenha o cenário de fundo repetido 3 vezes para cobrir a largura do mundo
        for (int i = 0; i < 3; i++) {
            jogo.batch.draw(texturaCenario, i * Constants.LARGURA_VIRTUAL, 0, Constants.LARGURA_VIRTUAL, Constants.ALTURA_VIRTUAL);
        }

        // Desenha o jogador com sua animação atual
        jogador.desenhar(jogo.batch);

        jogo.batch.end();

        // --- 2. Renderização da Interface ---
        palcoUI.act(delta);
        palcoUI.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        palcoUI.getViewport().update(width, height, true);
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        desenhadorFormas.dispose();
        palcoUI.dispose();
        skin.dispose();
        texturaCenario.dispose();
        jogador.liberarRecursos();
    }
}
