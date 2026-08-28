package com.duartefijamo.Fgti_war.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.duartefijamo.Fgti_war.FgtiWar;
import com.duartefijamo.Fgti_war.utils.Constants;

/**
 * Tela do Menu Principal do jogo.
 */
public class MainMenuScreen implements Screen {
    private final FgtiWar jogo;
    private Stage palco;
    private Skin skin;

    /**
     * Construtor do Menu.
     * @param jogo Instância principal do jogo.
     */
    public MainMenuScreen(final FgtiWar jogo) {
        this.jogo = jogo;
        // Cria o palco com a resolução virtual definida nas constantes
        palco = new Stage(new FitViewport(Constants.LARGURA_VIRTUAL, Constants.ALTURA_VIRTUAL));
        // Define o palco para receber entradas (toques/cliques)
        Gdx.input.setInputProcessor(palco);

        criarSkin();
        criarInterface();
    }

    /**
     * Cria o visual básico (Skin) para os botões e textos.
     */
    private void criarSkin() {
        skin = new Skin();

        // Cria uma textura branca simples para o fundo dos botões
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("branco", new Texture(pixmap));

        // Adiciona uma fonte padrão
        BitmapFont fonte = new BitmapFont();
        skin.add("padrao", fonte);

        // Define o estilo dos botões de texto
        TextButton.TextButtonStyle estiloBotao = new TextButton.TextButtonStyle();
        estiloBotao.up = skin.newDrawable("branco", Color.DARK_GRAY);
        estiloBotao.down = skin.newDrawable("branco", Color.NAVY);
        estiloBotao.font = skin.getFont("padrao");
        skin.add("padrao", estiloBotao);

        // Define o estilo dos rótulos (Labels)
        Label.LabelStyle estiloLabel = new Label.LabelStyle();
        estiloLabel.font = skin.getFont("padrao");
        skin.add("padrao", estiloLabel);
    }

    /**
     * Organiza os elementos visuais na tela usando uma tabela.
     */
    private void criarInterface() {
        Table tabela = new Table();
        tabela.setFillParent(true); // Faz a tabela ocupar a tela inteira
        palco.addActor(tabela);

        // Título do Jogo
        Label titulo = new Label("FGTIWAR", skin, "padrao");
        titulo.setFontScale(2.0f); // Deixa o título maior

        // Texto do Menu
        Label menuTexto = new Label("MENU", skin, "padrao");

        // Botão Jogar
        TextButton botaoJogar = new TextButton("JOGAR", skin, "padrao");
        botaoJogar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Muda para a tela de jogo quando clicado
                jogo.setScreen(new GameScreen(jogo));
            }
        });

        // Indicador de Nível
        Label nivelLabel = new Label("NIVEL: 1", skin, "padrao");

        // Adiciona os elementos à tabela organizados em linhas
        tabela.add(titulo).padBottom(30).row();
        tabela.add(menuTexto).padBottom(20).row();
        tabela.add(botaoJogar).size(200, 60).padBottom(20).row();
        tabela.add(nivelLabel);
    }

    @Override
    public void render(float delta) {
        // Limpa a tela com uma cor azul escura
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Desenha os elementos do palco
        palco.act(delta);
        palco.draw();
    }

    @Override
    public void resize(int width, int height) {
        palco.getViewport().update(width, height, true);
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        palco.dispose();
        skin.dispose();
    }
}
