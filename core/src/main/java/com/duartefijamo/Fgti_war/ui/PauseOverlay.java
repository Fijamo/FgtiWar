package com.duartefijamo.Fgti_war.ui;

import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.duartefijamo.Fgti_war.utils.Constants;

/**
 * Menu de Pausa sobreposto.
 * Oferece opções de Retomar o jogo, Reiniciar a fase ou Voltar ao Menu Principal.
 */
public class PauseOverlay implements Disposable {

    public interface PauseListener {
        void onResume();
        void onRestart();
        void onMainMenu();
    }

    private final Stage stage;
    private final Skin skin;

    public PauseOverlay(final PauseListener listener) {
        stage = new Stage(new FitViewport(Constants.LARGURA_VIRTUAL, Constants.ALTURA_VIRTUAL));

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

        Label title = new Label("JOGO PAUSADO", skin);
        title.setFontScale(1.5f);

        TextButton btnResume = new TextButton("CONTINUAR", skin);
        TextButton btnRestart = new TextButton("REINICIAR", skin);
        TextButton btnMenu = new TextButton("MENU PRINCIPAL", skin);

        btnResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                listener.onResume();
            }
        });

        btnRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                listener.onRestart();
            }
        });

        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                listener.onMainMenu();
            }
        });

        table.add(title).padBottom(30).row();
        table.add(btnResume).size(200, 48).padBottom(10).row();
        table.add(btnRestart).size(200, 48).padBottom(10).row();
        table.add(btnMenu).size(200, 48);
    }

    public void draw() {
        stage.act();
        stage.draw();
    }

    public Stage getStage() { return stage; }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
