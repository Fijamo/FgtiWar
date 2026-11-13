package com.duartefijamo.Fgti_war.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.duartefijamo.Fgti_war.FgtiWar;
import com.duartefijamo.Fgti_war.assets.GameAssets;
import com.duartefijamo.Fgti_war.ui.HUD;
import com.duartefijamo.Fgti_war.ui.PauseOverlay;
import com.duartefijamo.Fgti_war.utils.Constants;
import com.duartefijamo.Fgti_war.world.GameWorld;

/**
 * Tela principal de gameplay do jogo FgtiWar.
 * Gerencia a renderização do mapa de tiles, câmera com movimento suave e travamento nos limites,
 * fundo parallax, atualização de entidades e interface de usuário (HUD/Pausa).
 */
public class GameScreen implements Screen {

    private final FgtiWar jogo;
    private final int numeroFase;

    private OrthographicCamera camera;
    private FitViewport viewport;

    private GameAssets assets;
    private GameWorld world;
    private OrthogonalTiledMapRenderer mapRenderer;

    private HUD hud;
    private PauseOverlay pauseOverlay;
    private boolean pausado = false;

    public GameScreen(FgtiWar jogo) {
        this(jogo, 1);
    }

    public GameScreen(FgtiWar jogo, int numeroFase) {
        this.jogo = jogo;
        this.numeroFase = numeroFase;

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.LARGURA_VIRTUAL, Constants.ALTURA_VIRTUAL, camera);

        assets = new GameAssets();
        world = new GameWorld(numeroFase, assets);

        mapRenderer = new OrthogonalTiledMapRenderer(world.getLevelMap().getTiledMap(), 1f, jogo.batch);

        hud = new HUD(world.getPlayer(), assets);

        pauseOverlay = new PauseOverlay(new PauseOverlay.PauseListener() {
            @Override
            public void onResume() {
                pausado = false;
                configurarProcessadorEntrada();
            }

            @Override
            public void onRestart() {
                jogo.setScreen(new GameScreen(jogo, numeroFase));
            }

            @Override
            public void onMainMenu() {
                jogo.setScreen(new MainMenuScreen(jogo));
            }
        });

        configurarProcessadorEntrada();
    }

    private void configurarProcessadorEntrada() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        if (pausado) {
            multiplexer.addProcessor(pauseOverlay.getStage());
        } else {
            multiplexer.addProcessor(hud.getStage());
        }
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        // Verifica botão de pausa no HUD
        if (hud.isPausePressed()) {
            hud.resetPausePressed();
            pausado = true;
            configurarProcessadorEntrada();
        }

        if (!pausado) {
            world.update(delta);
            atualizarCamera();

            // Condições de término da fase
            if (world.isGameOver()) {
                jogo.setScreen(new GameOverScreen(jogo, numeroFase));
                return;
            } else if (world.isLevelCompleted()) {
                jogo.setScreen(new VictoryScreen(jogo, numeroFase, world.getScoreECTS()));
                return;
            }
        }

        // Limpa a tela
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 1. Renderiza Parallax de Fundo
        jogo.batch.setProjectionMatrix(camera.combined);
        jogo.batch.begin();
        world.drawParallax(jogo.batch, camera);
        jogo.batch.end();

        // 2. Renderiza Mapa de Tiles
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

        // 3. Renderiza Entidades (Jogador, Inimigos, Objetos)
        jogo.batch.begin();
        world.drawEntities(jogo.batch, assets);
        jogo.batch.end();

        // 4. Renderiza Interface
        hud.update(world.getScoreECTS(), world.hasKey());
        hud.draw(jogo.batch, assets, world.getPlayer().getVidaAtual(), world.getPlayer().getVidaMax());

        if (pausado) {
            pauseOverlay.draw();
        }
    }

    private void atualizarCamera() {
        float playerX = world.getPlayer().getPosicao().x;
        float playerY = world.getPlayer().getPosicao().y;

        // Movimento suave da câmera (lerp)
        float targetX = MathUtils.lerp(camera.position.x, playerX, 0.1f);
        float targetY = MathUtils.lerp(camera.position.y, playerY + 32, 0.1f);

        // Trava a câmera dentro dos limites do mapa
        float minX = camera.viewportWidth / 2f;
        float maxX = world.getLevelMap().getLarguraTotalPixels() - (camera.viewportWidth / 2f);

        float minY = camera.viewportHeight / 2f;
        float maxY = world.getLevelMap().getAlturaTotalPixels() - (camera.viewportHeight / 2f);

        camera.position.x = MathUtils.clamp(targetX, minX, maxX);
        camera.position.y = MathUtils.clamp(targetY, minY, maxY);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hud.resize(width, height);
        pauseOverlay.resize(width, height);
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() { pausado = true; }
    @Override public void resume() {}

    @Override
    public void dispose() {
        mapRenderer.dispose();
        world.dispose();
        assets.dispose();
        hud.dispose();
        pauseOverlay.dispose();
    }
}
