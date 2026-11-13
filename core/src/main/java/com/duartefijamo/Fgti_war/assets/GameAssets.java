package com.duartefijamo.Fgti_war.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.duartefijamo.Fgti_war.utils.AssetGenerator;

/**
 * Gerenciador de Assets do FgtiWar com suporte seguro e resiliente para Desktop e Android.
 * Resolve arquivos internos do APK ou arquivos locais gerados em tempo de execução.
 */
public class GameAssets implements Disposable {

    private final AssetManager assetManager;

    // Jogador
    public TextureRegion playerIdle;
    public Animation<TextureRegion> playerRun;
    public TextureRegion playerJump;
    public TextureRegion playerFall;
    public TextureRegion playerHurt;

    // Inimigos
    public TextureRegion enemyBug;
    public TextureRegion enemyGuard;
    public TextureRegion enemyBoss;

    // Itens e Objetos
    public TextureRegion itemCoffee;
    public TextureRegion itemEcts;
    public TextureRegion itemKey;
    public TextureRegion doorClosed;
    public TextureRegion doorOpen;
    public TextureRegion checkpointOff;
    public TextureRegion checkpointOn;

    // Tiles por Fase (32x32)
    public TextureRegion tileFase1Solid;
    public TextureRegion tileFase1Bg;
    public TextureRegion tileFase1PC;
    public TextureRegion tileFase1Board;

    public TextureRegion tileFase2Solid;
    public TextureRegion tileFase2Bg;
    public TextureRegion tileFase2Rack;
    public TextureRegion tileFase2Cable;

    public TextureRegion tileFase3Solid;
    public TextureRegion tileFase3Bg;
    public TextureRegion tileFase3Desk;
    public TextureRegion tileFase3Shelf;

    // Camadas de Parallax (3 por fase)
    public TextureRegion[] parallaxFase1;
    public TextureRegion[] parallaxFase2;
    public TextureRegion[] parallaxFase3;

    // UI
    public TextureRegion heartFull;
    public TextureRegion heartEmpty;
    public TextureRegion btnLeft;
    public TextureRegion btnRight;
    public TextureRegion btnJump;
    public TextureRegion btnPause;
    public TextureRegion uiPanel;

    public GameAssets() {
        AssetGenerator.gerarAssetsSeNecessario();

        // Resolver de arquivos com busca híbrida: tenta APK (internal) e depois armazenamento local
        FileHandleResolver resolver = new FileHandleResolver() {
            @Override
            public FileHandle resolve(String fileName) {
                FileHandle internal = Gdx.files.internal(fileName);
                if (internal.exists()) return internal;

                FileHandle local = Gdx.files.local(fileName);
                if (local.exists()) return local;

                return internal;
            }
        };

        assetManager = new AssetManager(resolver);
        carregarRecursos();
    }

    private void carregarRecursos() {
        // --- JOGADOR ---
        loadTexture("sprites/player_idle.png");
        loadTexture("sprites/player_run1.png");
        loadTexture("sprites/player_run2.png");
        loadTexture("sprites/player_jump.png");
        loadTexture("sprites/player_fall.png");
        loadTexture("sprites/player_hurt.png");

        // --- INIMIGOS ---
        loadTexture("sprites/enemy_bug.png");
        loadTexture("sprites/enemy_guard.png");
        loadTexture("sprites/enemy_boss.png");

        // --- ITENS E OBJETOS ---
        loadTexture("sprites/item_coffee.png");
        loadTexture("sprites/item_ects.png");
        loadTexture("sprites/item_key.png");
        loadTexture("sprites/door_closed.png");
        loadTexture("sprites/door_open.png");
        loadTexture("sprites/checkpoint_off.png");
        loadTexture("sprites/checkpoint_on.png");

        // --- TILESETS ---
        loadTexture("tilesets/fase1_solid.png");
        loadTexture("tilesets/fase1_bg.png");
        loadTexture("tilesets/fase1_pc.png");
        loadTexture("tilesets/fase1_board.png");

        loadTexture("tilesets/fase2_solid.png");
        loadTexture("tilesets/fase2_bg.png");
        loadTexture("tilesets/fase2_rack.png");
        loadTexture("tilesets/fase2_cable.png");

        loadTexture("tilesets/fase3_solid.png");
        loadTexture("tilesets/fase3_bg.png");
        loadTexture("tilesets/fase3_desk.png");
        loadTexture("tilesets/fase3_shelf.png");

        // --- BACKGROUNDS ---
        loadTexture("backgrounds/fase1_bg1.png");
        loadTexture("backgrounds/fase1_bg2.png");
        loadTexture("backgrounds/fase1_bg3.png");

        loadTexture("backgrounds/fase2_bg1.png");
        loadTexture("backgrounds/fase2_bg2.png");
        loadTexture("backgrounds/fase2_bg3.png");

        loadTexture("backgrounds/fase3_bg1.png");
        loadTexture("backgrounds/fase3_bg2.png");
        loadTexture("backgrounds/fase3_bg3.png");

        // --- UI ---
        loadTexture("ui/heart_full.png");
        loadTexture("ui/heart_empty.png");
        loadTexture("ui/btn_left.png");
        loadTexture("ui/btn_right.png");
        loadTexture("ui/btn_jump.png");
        loadTexture("ui/btn_pause.png");
        loadTexture("ui/panel.png");

        try {
            assetManager.finishLoading();
        } catch (Exception ignored) {
        }

        // --- ATRIBUIÇÃO DAS REGIONS COM FALLBACK SEGURO ---
        playerIdle = getRegion("sprites/player_idle.png");
        TextureRegion run1 = getRegion("sprites/player_run1.png");
        TextureRegion run2 = getRegion("sprites/player_run2.png");
        playerRun = new Animation<>(0.15f, run1, run2);
        playerJump = getRegion("sprites/player_jump.png");
        playerFall = getRegion("sprites/player_fall.png");
        playerHurt = getRegion("sprites/player_hurt.png");

        enemyBug = getRegion("sprites/enemy_bug.png");
        enemyGuard = getRegion("sprites/enemy_guard.png");
        enemyBoss = getRegion("sprites/enemy_boss.png");

        itemCoffee = getRegion("sprites/item_coffee.png");
        itemEcts = getRegion("sprites/item_ects.png");
        itemKey = getRegion("sprites/item_key.png");
        doorClosed = getRegion("sprites/door_closed.png");
        doorOpen = getRegion("sprites/door_open.png");
        checkpointOff = getRegion("sprites/checkpoint_off.png");
        checkpointOn = getRegion("sprites/checkpoint_on.png");

        tileFase1Solid = getRegion("tilesets/fase1_solid.png");
        tileFase1Bg = getRegion("tilesets/fase1_bg.png");
        tileFase1PC = getRegion("tilesets/fase1_pc.png");
        tileFase1Board = getRegion("tilesets/fase1_board.png");

        tileFase2Solid = getRegion("tilesets/fase2_solid.png");
        tileFase2Bg = getRegion("tilesets/fase2_bg.png");
        tileFase2Rack = getRegion("tilesets/fase2_rack.png");
        tileFase2Cable = getRegion("tilesets/fase2_cable.png");

        tileFase3Solid = getRegion("tilesets/fase3_solid.png");
        tileFase3Bg = getRegion("tilesets/fase3_bg.png");
        tileFase3Desk = getRegion("tilesets/fase3_desk.png");
        tileFase3Shelf = getRegion("tilesets/fase3_shelf.png");

        parallaxFase1 = new TextureRegion[]{
            getRegion("backgrounds/fase1_bg1.png"),
            getRegion("backgrounds/fase1_bg2.png"),
            getRegion("backgrounds/fase1_bg3.png")
        };

        parallaxFase2 = new TextureRegion[]{
            getRegion("backgrounds/fase2_bg1.png"),
            getRegion("backgrounds/fase2_bg2.png"),
            getRegion("backgrounds/fase2_bg3.png")
        };

        parallaxFase3 = new TextureRegion[]{
            getRegion("backgrounds/fase3_bg1.png"),
            getRegion("backgrounds/fase3_bg2.png"),
            getRegion("backgrounds/fase3_bg3.png")
        };

        heartFull = getRegion("ui/heart_full.png");
        heartEmpty = getRegion("ui/heart_empty.png");
        btnLeft = getRegion("ui/btn_left.png");
        btnRight = getRegion("ui/btn_right.png");
        btnJump = getRegion("ui/btn_jump.png");
        btnPause = getRegion("ui/btn_pause.png");
        uiPanel = getRegion("ui/panel.png");
    }

    private void loadTexture(String path) {
        try {
            if (assetManager.getFileHandleResolver().resolve(path).exists()) {
                assetManager.load(path, Texture.class);
            }
        } catch (Exception ignored) {
        }
    }

    private TextureRegion getRegion(String path) {
        try {
            if (assetManager.isLoaded(path, Texture.class)) {
                return new TextureRegion(assetManager.get(path, Texture.class));
            }
        } catch (Exception ignored) {
        }

        // Textura procedural de contingência para evitar tela preta ou crash no Android
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.WHITE);
        p.fill();
        Texture t = new Texture(p);
        p.dispose();
        return new TextureRegion(t);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
