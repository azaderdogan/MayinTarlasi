package com.azaderdogan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MayinTarlasi {
    public  int SATIR;
    public  int SUTUN;
    public int mayinSayisi;
    public Random random;
    public List<Kareler> harita;

    public List<Kareler> mayinlar;


    public MayinTarlasi(){}
    public MayinTarlasi(int satir, int sutun,int mayinSayisi) {
        this.mayinSayisi = mayinSayisi;
        SATIR = satir;
        SUTUN = sutun;
        this.mayinlar = new ArrayList<>();
        this.harita = new ArrayList<>();
    }

    public int getSATIR() {
        return SATIR;
    }

    public void setSATIR(int SATIR) {
        this.SATIR = SATIR;
    }

    public int getSUTUN() {
        return SUTUN;
    }

    public void setSUTUN(int SUTUN) {
        this.SUTUN = SUTUN;
    }

    public int getMayinSayisi() {
        return mayinSayisi;
    }

    public void setMayinSayisi(int mayinSayisi) {
        this.mayinSayisi = mayinSayisi;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public List<Kareler> getHarita() {
        return harita;
    }

    public List<Kareler> getMayinlar() {
        return mayinlar;
    }

    public void setMayinlar(List<Kareler> mayinlar) {
        this.mayinlar = mayinlar;
    }

    public void setHarita(){

        for (int x = 0; x <AnaEkran.paneSatirSutun ; x+=(AnaEkran.paneSatirSutun/getSUTUN())) {
            for (int y = 0; y <AnaEkran.paneSatirSutun ; y+=(AnaEkran.paneSatirSutun/getSUTUN())) {
                Konum konum = new Konum(x,y);
                Kareler kare = new Kareler(konum);
                harita.add(kare);

            }

        }
    }
    public void mayinDose(List<Kareler> kareler){
        int sayac = 0;
        int boyut = kareler.size();
        while (sayac < mayinSayisi ){

            int rnd = (int)(Math.random()*boyut);
            Kareler kare = kareler.get(rnd);
            if (!kare.isKareyeBakildimi()){
                kare.setKareyeBakildimi(true);
                kare.setMayinmi(true);
                sayac ++;
            }
        }

    }

    public Kareler konumaGoreKareAl(Konum konum){
        for (Kareler item : harita){
            if (item.konumlarEsitmi(konum)){
                return item;
            }
        }
        return null;
    }

    public void MayinlariGoster(){

    }


}
