package com.duartefijamo.Fgti_war.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

/**
 * Utilitário cross-platform (Desktop + Android) para salvar arquivos PNG em assets/.
 * Utiliza LibGDX Pixmap e PixmapIO sem dependências de java.awt.
 */
public class AssetGenerator {

    public static void gerarAssetsSeNecessario() {
        if (Gdx.files.internal("sprites/player_idle.png").exists()) {
            return;
        }

        try {
            // --- SPRITES JOGADOR ---
            salvar("sprites/player_idle.png", criarSpriteJogador(Color.CYAN, Color.BLUE, false));
            salvar("sprites/player_run1.png", criarSpriteJogador(Color.CYAN, Color.NAVY, false));
            salvar("sprites/player_run2.png", criarSpriteJogador(Color.CYAN, Color.ROYAL, true));
            salvar("sprites/player_jump.png", criarSpriteJogador(Color.YELLOW, Color.BLUE, false));
            salvar("sprites/player_fall.png", criarSpriteJogador(Color.ORANGE, Color.BLUE, false));
            salvar("sprites/player_hurt.png", criarSpriteJogador(Color.RED, Color.MAROON, false));

            // --- INIMIGOS ---
            salvar("sprites/enemy_bug.png", criarSpriteBug());
            salvar("sprites/enemy_guard.png", criarSpriteGuarda());
            salvar("sprites/enemy_boss.png", criarSpriteChefe());

            // --- ITENS E OBJETOS ---
            salvar("sprites/item_coffee.png", criarSpriteCafe());
            salvar("sprites/item_ects.png", criarSpriteEcts());
            salvar("sprites/item_key.png", criarSpriteChave());
            salvar("sprites/door_closed.png", criarSpritePortaFechada());
            salvar("sprites/door_open.png", criarSpritePortaAberta());
            salvar("sprites/checkpoint_off.png", criarSpriteCheckpoint(false));
            salvar("sprites/checkpoint_on.png", criarSpriteCheckpoint(true));

            // --- TILESETS (32x32) ---
            salvar("tilesets/fase1_solid.png", criarTileSolid(Color.GRAY, Color.LIGHT_GRAY));
            salvar("tilesets/fase1_bg.png", criarTileBg(Color.TEAL, Color.DARK_GRAY));
            salvar("tilesets/fase1_pc.png", criarTilePC());
            salvar("tilesets/fase1_board.png", criarTileBoard());

            salvar("tilesets/fase2_solid.png", criarTileSolid(Color.SLATE, Color.BLUE));
            salvar("tilesets/fase2_bg.png", criarTileBg(Color.BLACK, Color.NAVY));
            salvar("tilesets/fase2_rack.png", criarTileRack());
            salvar("tilesets/fase2_cable.png", criarTileCable());

            salvar("tilesets/fase3_solid.png", criarTileSolid(Color.BROWN, Color.GOLD));
            salvar("tilesets/fase3_bg.png", criarTileBg(Color.MAROON, Color.CORAL));
            salvar("tilesets/fase3_desk.png", criarTileDesk());
            salvar("tilesets/fase3_shelf.png", criarTileShelf());

            // --- BACKGROUNDS PARALLAX ---
            salvar("backgrounds/fase1_bg1.png", criarParallax(Color.SKY, Color.WHITE, 0));
            salvar("backgrounds/fase1_bg2.png", criarParallax(Color.TEAL, Color.CYAN, 1));
            salvar("backgrounds/fase1_bg3.png", criarParallax(Color.FOREST, Color.GREEN, 2));

            salvar("backgrounds/fase2_bg1.png", criarParallax(Color.BLACK, Color.DARK_GRAY, 0));
            salvar("backgrounds/fase2_bg2.png", criarParallax(Color.NAVY, Color.BLUE, 1));
            salvar("backgrounds/fase2_bg3.png", criarParallax(Color.DARK_GRAY, Color.LIGHT_GRAY, 2));

            salvar("backgrounds/fase3_bg1.png", criarParallax(Color.MAROON, Color.CORAL, 0));
            salvar("backgrounds/fase3_bg2.png", criarParallax(Color.CORAL, Color.GOLD, 1));
            salvar("backgrounds/fase3_bg3.png", criarParallax(Color.GOLD, Color.WHITE, 2));

            // --- UI ---
            salvar("ui/heart_full.png", criarCoracao(true));
            salvar("ui/heart_empty.png", criarCoracao(false));
            salvar("ui/btn_left.png", criarBotaoIcone(1));
            salvar("ui/btn_right.png", criarBotaoIcone(2));
            salvar("ui/btn_jump.png", criarBotaoIcone(3));
            salvar("ui/btn_pause.png", criarBotaoIcone(4));
            salvar("ui/panel.png", criarPainelUI());

        } catch (Exception e) {
            // Em caso de limitação de escrita no ambiente de leitura apenas, prossegue normalmente
        }
    }

    private static void salvar(String caminho, Pixmap pixmap) {
        try {
            FileHandle handle = Gdx.files.local(caminho);
            PixmapIO.writePNG(handle, pixmap);
        } catch (Exception ignored) {
        } finally {
            pixmap.dispose();
        }
    }

    private static Pixmap criarSpriteJogador(Color corpo, Color roupa, boolean alt) {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.BROWN);
        p.fillRectangle(10, 2, 12, 10);
        p.setColor(Color.WHITE);
        p.fillRectangle(18, 4, 3, 3);
        p.setColor(corpo);
        p.fillRectangle(8, 12, 16, 10);
        p.setColor(roupa);
        if (!alt) {
            p.fillRectangle(8, 22, 6, 10);
            p.fillRectangle(18, 22, 6, 10);
        } else {
            p.fillRectangle(6, 22, 6, 10);
            p.fillRectangle(20, 22, 6, 10);
        }
        return p;
    }

    private static Pixmap criarSpriteBug() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.GREEN);
        p.fillRectangle(4, 8, 24, 16);
        p.setColor(Color.LIME);
        p.fillRectangle(8, 12, 16, 8);
        p.setColor(Color.RED);
        p.fillCircle(10, 14, 2);
        p.fillCircle(22, 14, 2);
        return p;
    }

    private static Pixmap criarSpriteGuarda() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.DARK_GRAY);
        p.fillRectangle(6, 4, 20, 24);
        p.setColor(Color.LIGHT_GRAY);
        p.fillRectangle(10, 8, 12, 8);
        p.setColor(Color.BLUE);
        p.fillRectangle(6, 2, 20, 4);
        return p;
    }

    private static Pixmap criarSpriteChefe() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.PURPLE);
        p.fillRectangle(4, 2, 24, 28);
        p.setColor(Color.GOLD);
        p.fillRectangle(8, 6, 16, 6);
        p.setColor(Color.BLACK);
        p.fillCircle(12, 16, 2);
        p.fillCircle(20, 16, 2);
        return p;
    }

    private static Pixmap criarSpriteCafe() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.BROWN);
        p.fillRectangle(10, 10, 12, 14);
        p.setColor(Color.WHITE);
        p.fillRectangle(12, 8, 8, 4);
        p.setColor(Color.LIGHT_GRAY);
        p.drawRectangle(22, 14, 4, 6);
        return p;
    }

    private static Pixmap criarSpriteEcts() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.GOLD);
        p.fillCircle(16, 16, 10);
        p.setColor(Color.ORANGE);
        p.drawCircle(16, 16, 10);
        p.setColor(Color.BLACK);
        p.fillRectangle(14, 10, 4, 12);
        return p;
    }

    private static Pixmap criarSpriteChave() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.BLACK);
        p.fillRectangle(8, 12, 12, 8);
        p.setColor(Color.RED);
        p.fillRectangle(20, 14, 8, 4);
        p.setColor(Color.GOLD);
        p.fillCircle(10, 16, 3);
        return p;
    }

    private static Pixmap criarSpritePortaFechada() {
        Pixmap p = new Pixmap(32, 64, Pixmap.Format.RGBA8888);
        p.setColor(Color.FIREBRICK);
        p.fillRectangle(0, 0, 32, 64);
        p.setColor(Color.BROWN);
        p.drawRectangle(2, 2, 28, 60);
        p.setColor(Color.GOLD);
        p.fillCircle(26, 32, 3);
        return p;
    }

    private static Pixmap criarSpritePortaAberta() {
        Pixmap p = new Pixmap(32, 64, Pixmap.Format.RGBA8888);
        p.setColor(Color.BLACK);
        p.fillRectangle(0, 0, 32, 64);
        p.setColor(Color.GREEN);
        p.drawRectangle(2, 2, 28, 60);
        return p;
    }

    private static Pixmap criarSpriteCheckpoint(boolean ativo) {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.GRAY);
        p.fillRectangle(14, 4, 4, 24);
        p.setColor(ativo ? Color.GREEN : Color.DARK_GRAY);
        p.fillRectangle(18, 6, 10, 8);
        return p;
    }

    private static Pixmap criarTileSolid(Color c1, Color c2) {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(c1);
        p.fill();
        p.setColor(c2);
        p.drawRectangle(0, 0, 32, 32);
        p.drawRectangle(2, 2, 28, 28);
        return p;
    }

    private static Pixmap criarTileBg(Color c1, Color c2) {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(c1);
        p.fill();
        p.setColor(c2);
        p.drawLine(0, 0, 32, 0);
        p.drawLine(0, 0, 0, 32);
        return p;
    }

    private static Pixmap criarTilePC() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.DARK_GRAY);
        p.fillRectangle(6, 12, 20, 14);
        p.setColor(Color.SKY);
        p.fillRectangle(8, 14, 16, 10);
        p.setColor(Color.GRAY);
        p.fillRectangle(4, 26, 24, 4);
        return p;
    }

    private static Pixmap criarTileBoard() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.BROWN);
        p.fillRectangle(2, 4, 28, 20);
        p.setColor(Color.WHITE);
        p.fillRectangle(4, 6, 24, 16);
        p.setColor(Color.BLACK);
        p.drawLine(6, 10, 20, 10);
        p.drawLine(6, 16, 22, 16);
        return p;
    }

    private static Pixmap criarTileRack() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.DARK_GRAY);
        p.fill();
        p.setColor(Color.GREEN);
        p.fillRectangle(4, 4, 4, 4);
        p.fillRectangle(4, 12, 4, 4);
        p.setColor(Color.RED);
        p.fillRectangle(4, 20, 4, 4);
        p.setColor(Color.GRAY);
        p.fillRectangle(12, 4, 16, 24);
        return p;
    }

    private static Pixmap criarTileCable() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.YELLOW);
        p.drawLine(0, 16, 32, 16);
        p.setColor(Color.RED);
        p.drawLine(16, 0, 16, 32);
        return p;
    }

    private static Pixmap criarTileDesk() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.BROWN);
        p.fillRectangle(2, 10, 28, 6);
        p.fillRectangle(4, 16, 4, 16);
        p.fillRectangle(24, 16, 4, 16);
        return p;
    }

    private static Pixmap criarTileShelf() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(Color.BROWN);
        p.fill();
        p.setColor(Color.RED);
        p.fillRectangle(4, 4, 6, 10);
        p.setColor(Color.BLUE);
        p.fillRectangle(12, 4, 6, 10);
        p.setColor(Color.YELLOW);
        p.fillRectangle(20, 4, 6, 10);
        return p;
    }

    private static Pixmap criarParallax(Color c1, Color c2, int tipo) {
        Pixmap p = new Pixmap(800, 480, Pixmap.Format.RGBA8888);
        p.setColor(c1);
        p.fill();
        p.setColor(c2);

        if (tipo == 1) {
            for (int x = 0; x < 800; x += 100) {
                p.fillRectangle(x, 200, 60, 280);
            }
        } else if (tipo == 2) {
            for (int x = 0; x < 800; x += 150) {
                p.fillRectangle(x + 20, 320, 80, 160);
            }
        }
        return p;
    }

    private static Pixmap criarCoracao(boolean cheio) {
        Pixmap p = new Pixmap(24, 24, Pixmap.Format.RGBA8888);
        p.setColor(cheio ? Color.RED : Color.DARK_GRAY);
        p.fillCircle(7, 7, 6);
        p.fillCircle(17, 7, 6);
        p.fillTriangle(1, 9, 23, 9, 12, 22);
        return p;
    }

    private static Pixmap criarBotaoIcone(int tipo) {
        Pixmap p = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        p.setColor(new Color(0.2f, 0.2f, 0.2f, 0.8f));
        p.fillCircle(32, 32, 30);
        p.setColor(Color.WHITE);
        p.drawCircle(32, 32, 30);

        if (tipo == 1) { // Esquerda
            p.fillTriangle(18, 32, 42, 18, 42, 46);
        } else if (tipo == 2) { // Direita
            p.fillTriangle(46, 32, 22, 18, 22, 46);
        } else if (tipo == 3) { // Pulo
            p.fillTriangle(32, 18, 18, 42, 46, 42);
        } else if (tipo == 4) { // Pausa
            p.fillRectangle(22, 20, 6, 24);
            p.fillRectangle(36, 20, 6, 24);
        }
        return p;
    }

    private static Pixmap criarPainelUI() {
        Pixmap p = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        p.setColor(Color.DARK_GRAY);
        p.fill();
        p.setColor(Color.LIGHT_GRAY);
        p.drawRectangle(0, 0, 64, 64);
        p.drawRectangle(2, 2, 60, 60);
        return p;
    }
}
