package com.duartefijamo.Fgti_war.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.duartefijamo.Fgti_war.assets.GameAssets;
import com.duartefijamo.Fgti_war.utils.Constants;

/**
 * Constrói e gerencia os mapas em formato TiledMap de 32x32 tiles para as 3 fases.
 * Organizado em camadas: Fundo, Tiles Sólidos, Decorações e Posições de Objetos.
 */
public class LevelMap {

    private final int numeroFase;
    private final TiledMap tiledMap;
    private final int larguraTiles = 60; // 60 * 32 = 1920 pixels de largura
    private final int alturaTiles = 15;  // 15 * 32 = 480 pixels de altura

    private Vector2 playerSpawn;
    private Vector2 doorSpawn;
    private Vector2 checkpointSpawn;
    private Vector2 keySpawn;
    private final Array<Vector2> enemySpawns = new Array<>();
    private final Array<Vector2> itemSpawns = new Array<>();

    private TiledMapTileLayer camadaSolida;

    public LevelMap(int numeroFase, GameAssets assets) {
        this.numeroFase = numeroFase;
        this.tiledMap = new TiledMap();
        construirMapa(assets);
    }

    /**
     * Gera o TiledMap da fase informada preenchendo as camadas programaticamente.
     */
    private void construirMapa(GameAssets assets) {
        MapLayers layers = tiledMap.getLayers();

        TiledMapTileLayer camadaFundo = new TiledMapTileLayer(larguraTiles, alturaTiles, Constants.TILE_SIZE, Constants.TILE_SIZE);
        camadaFundo.setName("fundo");

        camadaSolida = new TiledMapTileLayer(larguraTiles, alturaTiles, Constants.TILE_SIZE, Constants.TILE_SIZE);
        camadaSolida.setName("solido");

        TiledMapTileLayer camadaDecoracao = new TiledMapTileLayer(larguraTiles, alturaTiles, Constants.TILE_SIZE, Constants.TILE_SIZE);
        camadaDecoracao.setName("decoracao");

        // Seleciona texturas temáticas de acordo com a fase
        TextureRegion texSolid, texBg, texDeco1, texDeco2;

        if (numeroFase == Constants.FASE_2_SERVIDORES) {
            texSolid = assets.tileFase2Solid;
            texBg = assets.tileFase2Bg;
            texDeco1 = assets.tileFase2Rack;
            texDeco2 = assets.tileFase2Cable;
        } else if (numeroFase == Constants.FASE_3_REITORIA) {
            texSolid = assets.tileFase3Solid;
            texBg = assets.tileFase3Bg;
            texDeco1 = assets.tileFase3Desk;
            texDeco2 = assets.tileFase3Shelf;
        } else {
            // Fase 1: Laboratório
            texSolid = assets.tileFase1Solid;
            texBg = assets.tileFase1Bg;
            texDeco1 = assets.tileFase1PC;
            texDeco2 = assets.tileFase1Board;
        }

        Cell cellSolid = new Cell();
        cellSolid.setTile(new StaticTiledMapTile(texSolid));

        Cell cellBg = new Cell();
        cellBg.setTile(new StaticTiledMapTile(texBg));

        Cell cellDeco1 = new Cell();
        cellDeco1.setTile(new StaticTiledMapTile(texDeco1));

        Cell cellDeco2 = new Cell();
        cellDeco2.setTile(new StaticTiledMapTile(texDeco2));

        // Preenche o fundo e paredes laterais
        for (int x = 0; x < larguraTiles; x++) {
            for (int y = 0; y < alturaTiles; y++) {
                camadaFundo.setCell(x, y, cellBg);

                // Paredes laterais e teto
                if (x == 0 || x == larguraTiles - 1 || y == alturaTiles - 1) {
                    camadaSolida.setCell(x, y, cellSolid);
                }
            }
        }

        // --- CHÃO E PLATAFORMAS DO MUNDO ---
        // Chão principal com alguns buracos/desafios
        for (int x = 0; x < larguraTiles; x++) {
            // Buracos de plataforma nos pontos x=20..22 e x=40..42
            if ((x >= 20 && x <= 22) || (x >= 40 && x <= 42)) {
                continue;
            }
            camadaSolida.setCell(x, 0, cellSolid);
        }

        // Plataformas intermediárias
        montarPlataforma(camadaSolida, cellSolid, 6, 4, 6);
        montarPlataforma(camadaSolida, cellSolid, 16, 7, 7);
        montarPlataforma(camadaSolida, cellSolid, 28, 5, 8);
        montarPlataforma(camadaSolida, cellSolid, 40, 8, 6);
        montarPlataforma(camadaSolida, cellSolid, 50, 4, 6);

        // --- DECORAÇÕES TEMÁTICAS (Quadros, Servidores, Mesas) ---
        camadaDecoracao.setCell(8, 5, cellDeco1);
        camadaDecoracao.setCell(18, 8, cellDeco2);
        camadaDecoracao.setCell(30, 6, cellDeco1);
        camadaDecoracao.setCell(42, 9, cellDeco2);
        camadaDecoracao.setCell(52, 5, cellDeco1);

        // Adiciona camadas ao mapa
        layers.add(camadaFundo);
        layers.add(camadaSolida);
        layers.add(camadaDecoracao);

        // --- POSIÇÕES DOS OBJETOS E SPAWNS ---
        playerSpawn = new Vector2(2 * Constants.TILE_SIZE, 1 * Constants.TILE_SIZE);
        checkpointSpawn = new Vector2(28 * Constants.TILE_SIZE, 6 * Constants.TILE_SIZE);
        doorSpawn = new Vector2(56 * Constants.TILE_SIZE, 1 * Constants.TILE_SIZE);
        keySpawn = new Vector2(42 * Constants.TILE_SIZE, 9 * Constants.TILE_SIZE);

        // Inimigos
        enemySpawns.add(new Vector2(12 * Constants.TILE_SIZE, 1 * Constants.TILE_SIZE));
        enemySpawns.add(new Vector2(32 * Constants.TILE_SIZE, 6 * Constants.TILE_SIZE));
        enemySpawns.add(new Vector2(48 * Constants.TILE_SIZE, 1 * Constants.TILE_SIZE));

        // Itens
        itemSpawns.add(new Vector2(8 * Constants.TILE_SIZE, 5 * Constants.TILE_SIZE));
        itemSpawns.add(new Vector2(18 * Constants.TILE_SIZE, 8 * Constants.TILE_SIZE));
        itemSpawns.add(new Vector2(52 * Constants.TILE_SIZE, 5 * Constants.TILE_SIZE));
    }

    private void montarPlataforma(TiledMapTileLayer layer, Cell cell, int inicioX, int y, int tamanho) {
        for (int x = inicioX; x < inicioX + tamanho; x++) {
            if (x < larguraTiles) {
                layer.setCell(x, y, cell);
            }
        }
    }

    /**
     * Verifica se a célula na coordenada do tile é sólida para colisão AABB.
     */
    public boolean isTileSolid(int tileX, int tileY) {
        if (tileX < 0 || tileX >= larguraTiles || tileY >= alturaTiles) {
            return true; // Paredes e teto
        }
        if (tileY < 0) {
            return false; // Buracos/abismo abaixo do mapa não são sólidos
        }
        return camadaSolida.getCell(tileX, tileY) != null;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public float getLarguraTotalPixels() {
        return larguraTiles * Constants.TILE_SIZE;
    }

    public float getAlturaTotalPixels() {
        return alturaTiles * Constants.TILE_SIZE;
    }

    public Vector2 getPlayerSpawn() { return playerSpawn; }
    public Vector2 getDoorSpawn() { return doorSpawn; }
    public Vector2 getCheckpointSpawn() { return checkpointSpawn; }
    public Vector2 getKeySpawn() { return keySpawn; }
    public Array<Vector2> getEnemySpawns() { return enemySpawns; }
    public Array<Vector2> getItemSpawns() { return itemSpawns; }
    public int getNumeroFase() { return numeroFase; }

    public void dispose() {
        tiledMap.dispose();
    }
}
