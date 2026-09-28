package com.duartefijamo.Fgti_war.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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

    // Texturas para o fundo e logo
    private Texture texturaFundo;
    private Texture texturaLogo;

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

        // Carrega as novas texturas
        texturaFundo = new Texture("01_Entrada_da_Universidade.png");
        texturaLogo = new Texture("FGTI_WAR_Logo.png");

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

        // Define o estilo dos rótulos (Labels) padrão
        Label.LabelStyle estiloLabel = new Label.LabelStyle();
        estiloLabel.font = skin.getFont("padrao");
        skin.add("padrao", estiloLabel);

        // Define estilo para rótulo preto no topo
        Label.LabelStyle estiloLabelPreto = new Label.LabelStyle();
        estiloLabelPreto.font = skin.getFont("padrao");
        estiloLabelPreto.fontColor = Color.BLACK;
        skin.add("preto", estiloLabelPreto);
    }

    /**
     * Organiza os elementos visuais na tela usando tabelas.
     */
    private void criarInterface() {
        // Adiciona a imagem de fundo primeiro para ficar atrás de tudo
        Image fundo = new Image(texturaFundo);
        fundo.setSize(Constants.LARGURA_VIRTUAL, Constants.ALTURA_VIRTUAL);
        palco.addActor(fundo);

        // Tabela do Topo para o título "MENU"
        Table tabelaTopo = new Table();
        tabelaTopo.top().setFillParent(true);
        palco.addActor(tabelaTopo);

        Label menuTexto = new Label("MENU", skin, "preto");
        tabelaTopo.add(menuTexto).padTop(15).top();

        // Tabela Central para Logo e Botões
        Table tabelaCentro = new Table();
        tabelaCentro.center().setFillParent(true);
        palco.addActor(tabelaCentro);

        // Logo do Jogo
        Image logo = new Image(texturaLogo);

        // Botão Jogar
        TextButton botaoJogar = new TextButton("JOGAR", skin, "padrao");
        botaoJogar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Muda para a tela de jogo quando clicado
                jogo.setScreen(new GameScreen(jogo));
            }
        });

        // Botão Sair
        TextButton botaoSair = new TextButton("SAIR", skin, "padrao");
        botaoSair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Fecha o aplicativo
                Gdx.app.exit();
            }
        });

        // Adiciona os elementos à tabela central
        tabelaCentro.add(logo).size(400, 200).padBottom(30).row();
        tabelaCentro.add(botaoJogar).size(200, 60).padBottom(15).row();
        tabelaCentro.add(botaoSair).size(200, 60);
    }

    @Override
    public void render(float delta) {
        // Limpa a tela
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        texturaFundo.dispose();
        texturaLogo.dispose();
    }
}
