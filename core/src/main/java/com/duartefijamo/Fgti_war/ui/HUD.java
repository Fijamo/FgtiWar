package com.duartefijamo.Fgti_war.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.duartefijamo.Fgti_war.assets.GameAssets;
import com.duartefijamo.Fgti_war.entities.Player;
import com.duartefijamo.Fgti_war.utils.Constants;

/**
 * Heads-Up Display (HUD) do jogo com botões touch usando ícones em PNG.
 */
public class HUD implements Disposable {

    private final Stage stage;
    private final Skin skin;
    private final Label labelEcts;
    private final Label labelChave;
    private boolean pausePressed = false;

    public HUD(final Player player, GameAssets assets) {
        stage = new Stage(new FitViewport(Constants.LARGURA_VIRTUAL, Constants.ALTURA_VIRTUAL));

        skin = new Skin();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("branco", new Texture(pixmap));
        BitmapFont font = new BitmapFont();
        skin.add("padrao", font);

        Label.LabelStyle styleLabel = new Label.LabelStyle();
        styleLabel.font = skin.getFont("padrao");
        skin.add("default", styleLabel);

        // --- TABELA SUPERIOR (INFO E BOTAO PAUSA) ---
        Table topTable = new Table();
        topTable.top().setFillParent(true);

        labelEcts = new Label("ECTS: 0", skin);
        labelChave = new Label("PENDRIVE: NAO", skin);

        ImageButton btnPausa = new ImageButton(new TextureRegionDrawable(assets.btnPause));
        btnPausa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pausePressed = true;
            }
        });

        topTable.add(labelEcts).pad(10).left();
        topTable.add(labelChave).pad(10).expandX();
        topTable.add(btnPausa).size(48, 48).pad(10).right();

        stage.addActor(topTable);

        // --- TABELA INFERIOR (CONTROLES TOUCH COM ÍCONES) ---
        Table bottomTable = new Table();
        bottomTable.bottom().setFillParent(true);

        ImageButton btnLeft = new ImageButton(new TextureRegionDrawable(assets.btnLeft));
        ImageButton btnRight = new ImageButton(new TextureRegionDrawable(assets.btnRight));
        ImageButton btnJump = new ImageButton(new TextureRegionDrawable(assets.btnJump));

        btnLeft.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.movendoEsquerda = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.movendoEsquerda = false;
            }
        });

        btnRight.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.movendoDireita = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.movendoDireita = false;
            }
        });

        btnJump.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.puloPressionado = true;
                player.movendoPulo = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.movendoPulo = false;
            }
        });

        Table dpadTable = new Table();
        dpadTable.add(btnLeft).size(60, 60).padRight(10);
        dpadTable.add(btnRight).size(60, 60);

        bottomTable.add(dpadTable).left().pad(10);
        bottomTable.add().expandX();
        bottomTable.add(btnJump).size(60, 60).right().pad(10);

        stage.addActor(bottomTable);
    }

    public void update(int ects, boolean temChave) {
        labelEcts.setText("ECTS: " + ects);
        labelChave.setText("PENDRIVE: " + (temChave ? "SIM" : "NAO"));
    }

    public void draw(SpriteBatch batch, GameAssets assets, int vidaAtual, int vidaMax) {
        stage.act();
        stage.draw();

        // Desenha os corações de vida
        batch.begin();
        for (int i = 0; i < vidaMax; i++) {
            boolean cheia = i < vidaAtual;
            batch.draw(cheia ? assets.heartFull : assets.heartEmpty, 10 + (i * 28), Constants.ALTURA_VIRTUAL - 70, 24, 24);
        }
        batch.end();
    }

    public Stage getStage() { return stage; }
    public boolean isPausePressed() { return pausePressed; }
    public void resetPausePressed() { pausePressed = false; }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
