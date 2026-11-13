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
import com.duartefijamo.Fgti_war.utils.PreferencesManager;

/**
 * Tela de Vitória exibida ao concluir com sucesso uma fase.
 * Salva a nota acumulada (ECTS) e desbloqueia a próxima fase no Preferences.
 */
public class VictoryScreen implements Screen {

    private final FgtiWar jogo;
    private final int numeroFase;
    private final int scoreECTS;
    private final Stage stage;
    private final Skin skin;

    public VictoryScreen(final FgtiWar jogo, final int numeroFase, final int scoreECTS) {
        this.jogo = jogo;
        this.numeroFase = numeroFase;
        this.scoreECTS = scoreECTS;
        this.stage = new Stage(new FitViewport(Constants.LARGURA_VIRTUAL, Constants.ALTURA_VIRTUAL));
        Gdx.input.setInputProcessor(stage);

        // Salva progresso no armazenamento local
        PreferencesManager.salvarMelhorNota(numeroFase, scoreECTS);
        if (numeroFase < Constants.TOTAL_FASES) {
            PreferencesManager.desbloquearFase(numeroFase + 1);
        }

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

        criarInterface();
    }

    private void criarInterface() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("FASE CONCLUIDA!", skin);
        title.setFontScale(2.0f);
        title.setColor(Color.GOLD);

        Label labelEcts = new Label("ECTS CONQUISTADOS: " + scoreECTS, skin);

        table.add(title).padBottom(15).row();
        table.add(labelEcts).padBottom(25).row();

        if (numeroFase < Constants.TOTAL_FASES) {
            TextButton btnProxima = new TextButton("PROXIMA FASE", skin);
            btnProxima.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    jogo.setScreen(new GameScreen(jogo, numeroFase + 1));
                }
            });
            table.add(btnProxima).size(220, 48).padBottom(10).row();
        }

        TextButton btnFases = new TextButton("SELECAO DE FASES", skin);
        btnFases.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogo.setScreen(new LevelSelectScreen(jogo));
            }
        });

        TextButton btnMenu = new TextButton("MENU PRINCIPAL", skin);
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogo.setScreen(new MainMenuScreen(jogo));
            }
        });

        table.add(btnFases).size(220, 48).padBottom(10).row();
        table.add(btnMenu).size(220, 48);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.2f, 0.1f, 1);
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
