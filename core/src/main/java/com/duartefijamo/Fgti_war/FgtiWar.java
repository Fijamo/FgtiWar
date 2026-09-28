package com.duartefijamo.Fgti_war;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.duartefijamo.Fgti_war.screens.MainMenuScreen;
import com.duartefijamo.Fgti_war.utils.Constants;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FgtiWar extends Game {
    public SpriteBatch batch;
    public Music musicaFundo;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // Inicializa e toca a música de fundo
        musicaFundo = Gdx.audio.newMusic(Gdx.files.internal(Constants.MUSICA_FUNDO));
        musicaFundo.setLooping(true); // Repete a música infinitamente
        musicaFundo.setVolume(0.5f);  // Volume em 50%
        musicaFundo.play();           // Começa a tocar

        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (musicaFundo != null) {
            musicaFundo.dispose(); // Libera memória do áudio
        }
    }
}
