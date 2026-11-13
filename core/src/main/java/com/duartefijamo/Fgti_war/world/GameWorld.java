package com.duartefijamo.Fgti_war.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.duartefijamo.Fgti_war.assets.GameAssets;
import com.duartefijamo.Fgti_war.entities.Checkpoint;
import com.duartefijamo.Fgti_war.entities.Door;
import com.duartefijamo.Fgti_war.entities.Enemy;
import com.duartefijamo.Fgti_war.entities.Item;
import com.duartefijamo.Fgti_war.entities.Player;

/**
 * Gerenciador principal do mundo do jogo.
 * Integra o mapa, o jogador, inimigos, itens, porta, checkpoint e parallax.
 */
public class GameWorld {

    private final LevelMap levelMap;
    private final Player player;
    private final ParallaxBackground parallax;
    private final Door door;
    private final Checkpoint checkpoint;
    private final Item keyItem;
    private final Array<Enemy> enemies = new Array<>();
    private final Array<Item> items = new Array<>();

    private int scoreECTS = 0;
    private boolean hasKey = false;
    private boolean levelCompleted = false;
    private boolean gameOver = false;

    public GameWorld(int numeroFase, GameAssets assets) {
        this.levelMap = new LevelMap(numeroFase, assets);

        Vector2 spawn = levelMap.getPlayerSpawn();
        this.player = new Player(spawn.x, spawn.y);

        // Seleciona as 3 camadas de parallax de acordo com a fase
        TextureRegion[] camadasParallax;
        if (numeroFase == 2) camadasParallax = assets.parallaxFase2;
        else if (numeroFase == 3) camadasParallax = assets.parallaxFase3;
        else camadasParallax = assets.parallaxFase1;

        this.parallax = new ParallaxBackground(camadasParallax);

        // Instancia Porta e Checkpoint
        Vector2 doorPos = levelMap.getDoorSpawn();
        this.door = new Door(doorPos.x, doorPos.y, assets.doorClosed, assets.doorOpen);

        Vector2 checkPos = levelMap.getCheckpointSpawn();
        this.checkpoint = new Checkpoint(checkPos.x, checkPos.y, assets.checkpointOff, assets.checkpointOn);

        // Instancia Chave/PenDrive
        Vector2 keyPos = levelMap.getKeySpawn();
        this.keyItem = new Item(Item.Type.KEY, keyPos.x, keyPos.y, assets.itemKey);
        items.add(keyItem);

        // Instancia Inimigos
        TextureRegion texEnemy = (numeroFase == 3) ? assets.enemyBoss : (numeroFase == 2 ? assets.enemyGuard : assets.enemyBug);
        for (Vector2 pos : levelMap.getEnemySpawns()) {
            enemies.add(new Enemy(pos.x, pos.y, texEnemy));
        }

        // Instancia Coletáveis (Café e ECTS)
        for (int i = 0; i < levelMap.getItemSpawns().size; i++) {
            Vector2 pos = levelMap.getItemSpawns().get(i);
            Item.Type type = (i % 2 == 0) ? Item.Type.ECTS : Item.Type.COFFEE;
            TextureRegion texItem = (type == Item.Type.ECTS) ? assets.itemEcts : assets.itemCoffee;
            items.add(new Item(type, pos.x, pos.y, texItem));
        }
    }

    public void update(float delta) {
        if (gameOver || levelCompleted) return;

        player.atualizar(delta, levelMap);

        // Se o jogador cair abaixo do mapa
        if (player.getPosicao().y < -50) {
            player.receberDano(player.getVidaMax());
        }

        if (!player.estaVivo()) {
            gameOver = true;
            return;
        }

        // Atualiza Inimigos e verifica colisão
        for (Enemy enemy : enemies) {
            enemy.update(delta);
            if (player.getBounds().overlaps(enemy.getBounds())) {
                player.receberDano(1);
            }
        }

        // Atualiza Itens e verifica coleta
        for (Item item : items) {
            item.update(delta);
            if (!item.isCollected() && player.getBounds().overlaps(item.getBounds())) {
                item.setCollected(true);
                if (item.getType() == Item.Type.KEY) {
                    hasKey = true;
                    door.setLocked(false);
                } else if (item.getType() == Item.Type.COFFEE) {
                    player.curar(1);
                } else if (item.getType() == Item.Type.ECTS) {
                    scoreECTS += 10;
                }
            }
        }

        // Checkpoint
        if (!checkpoint.isActivated() && player.getBounds().overlaps(checkpoint.getBounds())) {
            checkpoint.setActivated(true);
        }

        // Porta de conclusão de fase
        if (player.getBounds().overlaps(door.getBounds())) {
            if (!door.isLocked()) {
                levelCompleted = true;
            }
        }
    }

    public void drawParallax(SpriteBatch batch, OrthographicCamera camera) {
        parallax.desenhar(batch, camera);
    }

    public void drawEntities(SpriteBatch batch, GameAssets assets) {
        door.draw(batch);
        checkpoint.draw(batch);

        for (Item item : items) {
            item.draw(batch);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }

        player.desenhar(batch, assets);
    }

    public LevelMap getLevelMap() { return levelMap; }
    public Player getPlayer() { return player; }
    public int getScoreECTS() { return scoreECTS; }
    public boolean hasKey() { return hasKey; }
    public boolean isLevelCompleted() { return levelCompleted; }
    public boolean isGameOver() { return gameOver; }

    public void dispose() {
        levelMap.dispose();
    }
}
