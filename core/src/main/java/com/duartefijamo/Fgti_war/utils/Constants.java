package com.duartefijamo.Fgti_war.utils;

/**
 * Classe de constantes globais do jogo.
 * Armazena valores fixos para facilitar a manutenção e o ajuste do jogo.
 */
public class Constants {
    // Dimensões da tela virtual (Resolução interna)
    public static final int LARGURA_VIRTUAL = 800;
    public static final int ALTURA_VIRTUAL = 480;
    public static final float LARGURA_MUNDO = LARGURA_VIRTUAL * 3f;

    // Configurações físicas do jogador
    public static final float VELOCIDADE_JOGADOR = 200f; // Velocidade de movimento lateral
    public static final float FORCA_PULO = 800f;         // Força aplicada no salto
    public static final float GRAVIDADE = -2000f;        // Força da gravidade constante

    // Configurações do cenário
    public static final float ALTURA_CHAO = 30f;        // Altura do chão invisível para colisão
    public static final float MARGEM_LATERAL = 0f;      // Sem margem lateral

    // Arquivos de Áudio
    public static final String MUSICA_FUNDO = "magnific-ckt-rip.mp3";
}
