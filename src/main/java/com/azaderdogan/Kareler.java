package com.azaderdogan;

public class Kareler {

    private Konum konum;
    private KareDurumu durumu;
    private int yakindakiMayinlar;
    private boolean kareyeBakildimi;
    private boolean mayinmi;

    public Kareler() {
    }

    public Kareler(Konum konum) {
        this.konum = konum;
        this.kareyeBakildimi = false;
        this.mayinmi = false;
    }

    public Kareler(KareDurumu durumu) {
        this.durumu = durumu;
    }

    public boolean konumlarEsitmi(Konum konum){
        if (this.konum.X == konum.X && this.konum.Y ==konum.Y){
            return true;
        }else {
            return false;
        }
    }

    public Konum getKonum() {
        return konum;
    }

    public void setKonum(Konum konum) {
        this.konum = konum;
    }

    public KareDurumu getDurumu() {
        return durumu;
    }

    public void setDurumu(KareDurumu durumu) {
        this.durumu = durumu;
    }

    public int getYakindakiMayinlar() {
        return yakindakiMayinlar;
    }

    public void setYakindakiMayinlar(int yakindakiMayinlar) {
        this.yakindakiMayinlar = yakindakiMayinlar;
    }

    public boolean isKareyeBakildimi() {
        return kareyeBakildimi;
    }

    public void setKareyeBakildimi(boolean kareyeBakildimi) {
        this.kareyeBakildimi = kareyeBakildimi;
    }


    public boolean isMayinmi() {
        return mayinmi;
    }

    public void setMayinmi(boolean mayinmi) {
        this.mayinmi = mayinmi;
    }
}
