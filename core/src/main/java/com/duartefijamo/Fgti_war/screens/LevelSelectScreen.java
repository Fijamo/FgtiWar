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
 * Tela de Seleção de Fases (Fases 1, 2 e 3).
 * Exibe estado de bloqueio/desbloqueio e a melhor nota registrada em cada fase.
 */
public class LevelSelectScreen implements Screen {

    private final FgtiWar jogo;
    private final Stage stage;
    private final Skin skin;

    public LevelSelectScreen(final FgtiWar jogo) {
        this.jogo = jogo;
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

        criarInterface();
    }

    private void criarInterface() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("SELECAO DE FASES", skin);
        title.setFontScale(1.8f);

        int faseDesbloqueada = PreferencesManager.getFaseDesbloqueada();

        table.add(title).padBottom(30).colspan(2).row();

        // Lista as 3 fases
        for (int f = 1; f <= Constants.TOTAL_FASES; f++) {
            final int numeroFase = f;
            boolean liberada = numeroFase <= faseDesbloqueada;
            float melhorNota = PreferencesManager.getMelhorNota(numeroFase);

            String textoBotao = "FASE " + numeroFase + (liberada ? "" : " [BLOQUEADO]");
            TextButton btnFase = new TextButton(textoBotao, skin);
            btnFase.setDisabled(!liberada);

            if (liberada) {
                btnFase.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        jogo.setScreen(new GameScreen(jogo, numeroFase));
                    }
                });
            }

            Label labelNota = new Label("Melhor ECTS: " + (int) melhorNota, skin);

            table.add(btnFase).size(260, 48).pad(10);
            table.add(labelNota).left().pad(10).row();
        }

        TextButton btnVoltar = new TextButton("VOLTAR", skin);
        btnVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogo.setScreen(new MainMenuScreen(jogo));
            }
        });

        table.add(btnVoltar).size(180, 44).padTop(20).colspan(2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
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
