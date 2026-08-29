package com.duartefijamo.Fgti_war.utils;

/**
 * Classe de constantes globais do jogo.
 * Armazena valores fixos para facilitar a manutenção e o ajuste do jogo.
 */
public class Constants {
    // Dimensões da tela virtual (Resolução interna)
    public static final int LARGURA_VIRTUAL = 800;
    public static final int ALTURA_VIRTUAL = 480;

    // Configurações físicas do jogador
    public static final float VELOCIDADE_JOGADOR = 300f; // Velocidade de movimento lateral
    public static final float FORCA_PULO = 800f;         // Força aplicada no salto
    public static final float GRAVIDADE = -2000f;        // Força da gravidade constante

    // Configurações do cenário
    public static final float ALTURA_CHAO = 30f;        // Altura da grama (mais fina)
    public static final float MARGEM_LATERAL = 20f;     // Espaço vazio nos cantos da tela
}
