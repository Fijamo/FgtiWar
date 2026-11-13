package com.duartefijamo.Fgti_war.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.duartefijamo.Fgti_war.utils.Constants;

/**
 * Gerencia a renderização do fundo em Parallax de 3 camadas.
 * Cada camada se move com uma velocidade proporcional ao movimento da câmera,
 * criando um efeito realista de profundidade 2.5D para os cenários.
 */
public class ParallaxBackground {

    private final TextureRegion[] camadas;
    private final float[] fatoresVelocidade; // Multiplicador de velocidade de rolagem (0.1, 0.3, 0.6)

    /**
     * @param camadas Array com exatamente 3 camadas de textura.
     */
    public ParallaxBackground(TextureRegion[] camadas) {
        this.camadas = camadas;
        this.fatoresVelocidade = new float[]{0.1f, 0.3f, 0.6f};
    }

    /**
     * Desenha as 3 camadas de fundo com deslocamento suave de acordo com a posição da câmera.
     * @param batch SpriteBatch para renderizar as texturas.
     * @param camera Câmera do jogo.
     */
    public void desenhar(SpriteBatch batch, OrthographicCamera camera) {
        float cameraX = camera.position.x - camera.viewportWidth / 2f;
        float cameraY = camera.position.y - camera.viewportHeight / 2f;

        for (int i = 0; i < camadas.length; i++) {
            if (camadas[i] == null) continue;

            float fator = fatoresVelocidade[i];
            float offsetX = (cameraX * fator) % Constants.LARGURA_VIRTUAL;
            float startX = cameraX - offsetX - Constants.LARGURA_VIRTUAL;

            // Renderiza 3 repetições para cobrir toda a largura da viewport durante o movimento
            for (int r = 0; r < 3; r++) {
                float posX = startX + (r * Constants.LARGURA_VIRTUAL);
                batch.draw(camadas[i], posX, cameraY, Constants.LARGURA_VIRTUAL, Constants.ALTURA_VIRTUAL);
            }
        }
    }
}
