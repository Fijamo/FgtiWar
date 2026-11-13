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
 * Tela de Game Over exibida quando o jogador perde todas as vidas.
 */
public class GameOverScreen implements Screen {

    private final FgtiWar jogo;
    private final int numeroFase;
    private final Stage stage;
    private final Skin skin;

    public GameOverScreen(final FgtiWar jogo, final int numeroFase) {
        this.jogo = jogo;
        this.numeroFase = numeroFase;
        this.stage = new Stage(new FitViewport(Constants.LARGURA_VIRTUAL, Constants.ALTURA_VIRTUAL));
        Gdx.input.setInputProcessor(stage);

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

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("GAME OVER", skin);
        title.setFontScale(2.2f);
        title.setColor(Color.RED);

        TextButton btnRetry = new TextButton("TENTAR NOVAMENTE", skin);
        TextButton btnMenu = new TextButton("MENU PRINCIPAL", skin);

        btnRetry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogo.setScreen(new GameScreen(jogo, numeroFase));
            }
        });

        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogo.setScreen(new MainMenuScreen(jogo));
            }
        });

        table.add(title).padBottom(30).row();
        table.add(btnRetry).size(220, 50).padBottom(15).row();
        table.add(btnMenu).size(220, 50);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0f, 0f, 1);
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
