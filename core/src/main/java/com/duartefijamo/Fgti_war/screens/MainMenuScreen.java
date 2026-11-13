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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.duartefijamo.Fgti_war.FgtiWar;
import com.duartefijamo.Fgti_war.utils.Constants;

/**
 * Tela de Menu Principal do jogo FgtiWar.
 */
public class MainMenuScreen implements Screen {

    private final FgtiWar jogo;
    private final Stage stage;
    private Skin skin;

    public MainMenuScreen(final FgtiWar jogo) {
        this.jogo = jogo;
        this.stage = new Stage(new FitViewport(Constants.LARGURA_VIRTUAL, Constants.ALTURA_VIRTUAL));
        Gdx.input.setInputProcessor(stage);

        criarSkin();
        criarInterface();
    }

    private void criarSkin() {
        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("branco", new Texture(pixmap));

        BitmapFont font = new BitmapFont();
        skin.add("padrao", font);

        TextButton.TextButtonStyle styleButton = new TextButton.TextButtonStyle();
        styleButton.up = skin.newDrawable("branco", Color.DARK_GRAY);
        styleButton.down = skin.newDrawable("branco", Color.NAVY);
        styleButton.font = skin.getFont("padrao");
        skin.add("default", styleButton);

        Label.LabelStyle styleLabel = new Label.LabelStyle();
        styleLabel.font = skin.getFont("padrao");
        skin.add("default", styleLabel);
    }

    private void criarInterface() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("FGTIWAR", skin);
        title.setFontScale(2.5f);
        title.setColor(Color.CYAN);

        Label subTitle = new Label("A JORNADA ACADEMICA", skin);
        subTitle.setFontScale(1.2f);

        TextButton btnPlay = new TextButton("JOGAR", skin);
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogo.setScreen(new LevelSelectScreen(jogo));
            }
        });

        TextButton btnExit = new TextButton("SAIR", skin);
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(title).padBottom(5).row();
        table.add(subTitle).padBottom(30).row();
        table.add(btnPlay).size(220, 54).padBottom(15).row();
        table.add(btnExit).size(220, 54);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
