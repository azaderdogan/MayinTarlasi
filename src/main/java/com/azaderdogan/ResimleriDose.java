package com.azaderdogan;

import javafx.scene.image.Image;

public class ResimleriDose {

    private static final String baseDir = "";
    public static final Image BLANK = resimYukle("blank.png");
    public static final Image FLAG = resimYukle("flag.png");
    public static final Image EXPOSED = resimYukle("exposed.png");
    public static final Image MINE = resimYukle("mine.png");
    public static final Image HITMINE = resimYukle("hitmine.png");
    public static final Image WRONGMINE = resimYukle("wrongmine.png");
    private static final Image[] sayi = new Image[9];

    static {
        sayi[0] = EXPOSED;

        for (int i = 1; i < sayi.length; i++) {
            sayi[i] = resimYukle(String.format("number%d.png", i));
        }
    }

    private ResimleriDose() {
    }

    public static Image getImage(KareDurumu kareDurumu) {
        switch (kareDurumu) {
            case VURULMAMIS_KARE:
                return ResimleriDose.BLANK;
            case BAYRAK:
                return ResimleriDose.FLAG;
            case MAYIN:
                return ResimleriDose.MINE;
            case VURULMUS_KARE:
                return ResimleriDose.EXPOSED;
            case VURULMUŞ_MAYIN:
                return ResimleriDose.HITMINE;
            default:
                throw new AssertionError("Bilinmeyen tip: " + kareDurumu);
        }
    }

    public static Image rakamAl(int index) {

        return sayi[index];
    }

    private static Image resimYukle(String path) {
        return new Image(baseDir + path);
    }
}
