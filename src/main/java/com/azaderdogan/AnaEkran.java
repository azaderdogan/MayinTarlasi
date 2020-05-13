package com.azaderdogan;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.*;
import java.net.URL;
import java.util.*;

public class AnaEkran implements Initializable {

    private MayinTarlasi mayinTarlasi;
    private OyunZorlugu oyunZorlugu;
    private List<Kareler> oyundakiMayinlar;
    private int xy;
    private List<Kareler> etraftakiKareler;
    public static int paneSatirSutun ;
    private int widhtHeight;

    @FXML
    private Label saat;
    private Timer timer;
    @FXML
    private Label skor;
    private Thread currentThread;
    private String gecenSure= null;
    @FXML
    private Pane paneOyunAlani;

    int saniye = 0;
    int dakika =00;
    int gecenSaat = 0;
    public void zamanlayici(){

         timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                saniye++;

                if (saniye >= 60){
                    dakika++;
                    saniye = 0;
                }
                if (dakika >= 60){
                    dakika = 0;
                    gecenSaat++;
                }
                String saatimiz = gecenSaat+ " : "+dakika +" : "+ saniye;
                Platform.runLater(()->{saat.setText(saatimiz);
               });

                currentThread = Thread.currentThread();
            }
        };
        timer.schedule(timerTask,1000,2000);

    }
    private Button yeniden;

    @FXML
    void yirmi(ActionEvent event) {
    ozelOyun(event,20,150);
    }

    @FXML
    void yirmi4(ActionEvent event) {
        ozelOyun(event,24,170);
    }

    @FXML
    void yirmi5(ActionEvent event) {
        ozelOyun(event,25,180);
    }
    @FXML
    void sekiz(ActionEvent event) {
        ozelOyun(event,8,8);
    }
    @FXML
    void oniki(ActionEvent event) {
        ozelOyun(event,12,25);
    }

    @FXML
    void otuz(ActionEvent event) {
        ozelOyun(event,30,200);
    }

    @FXML
    void alti(ActionEvent event) {
        ozelOyun(event,6,5);
    }
    @FXML
    void normalSeviye(ActionEvent event) {
        xy = 15;
        widhtHeight = 600 / xy;
        oyunZorlugu = OyunZorlugu.ORTA;
        haritaolustur(xy, 60);
        System.out.println(currentThread);

    }

    @FXML
    void oyundanCik(ActionEvent event) {
        Platform.exit();
    }
    @FXML
    public void uzmanSeviyesi(ActionEvent actionEvent) {
        xy = 20;
        widhtHeight = 600 / xy;
        oyunZorlugu = OyunZorlugu.ZOR;
        haritaolustur(xy, 100);
    }

    @FXML
    void ozelOyun(ActionEvent event, int i,int mayinsayisi) {
        xy = i;
        widhtHeight = 600 / xy;
        oyunZorlugu = OyunZorlugu.CUSTOM;
        haritaolustur(xy, mayinsayisi);
        System.out.println(currentThread);
    }

    @FXML
    void yardim(ActionEvent event) {

    }

    @FXML
    void yeniOyun(ActionEvent event) {
        switch (oyunZorlugu){
            case BASİT:
                baslangicSeviyesi(event);
                break;
            case ORTA:
                normalSeviye(event);
                break;
            case ZOR:
            uzmanSeviyesi(event);
            break;
            default:
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneOyunAlani.setMinSize(600, 600);
        paneOyunAlani.setMaxSize(600, 600);
        zamanlayici();
        paneSatirSutun = 600;
        baslangicSeviyesi(new ActionEvent());
        //scoreWrite("3456");
        skor.setText("Skor : 3434");
        System.out.println(widhtHeight);

    }

    public void haritaolustur(int xy, int mayinSayisi) {

        mayinTarlasi = new MayinTarlasi(xy, xy, mayinSayisi);
        mayinTarlasi.setHarita();
        List<Kareler> karelers = mayinTarlasi.getHarita();
        mayinTarlasi.mayinDose(karelers);
        kareleriEkle(xy, xy);
    }

    public void scoreWrite(String score){
        if (Integer.parseInt(score) >= scoreRead()){
            try (
                    BufferedWriter writer = new BufferedWriter(new FileWriter("score.txt",true))
            ){
                writer.write(score);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            skor.setText(score);
        }

    }

    private int scoreRead() {
        String score=null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("score.txt"))){

            Scanner scanner = new Scanner(bufferedReader.readLine());
                score = scanner.nextLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Integer.parseInt(score);
    }

    @FXML
    void baslangicSeviyesi(ActionEvent event) {
        xy = 10;
        widhtHeight = 600 / xy;
        oyunZorlugu = OyunZorlugu.BASİT;
        haritaolustur(xy, 12);
        zamanlayici();

    }

    private void kareleriEkle(int satir, int sutun) {
        int satirx = paneSatirSutun / satir;
        int sutuny = paneSatirSutun / sutun;
        for (int x = 0; x < paneSatirSutun; x += satirx) {
            for (int y = 0; y < paneSatirSutun; y += sutuny) {
                imageEkle(x, y, oyunZorlugu);

            }

        }
    }


    private void imageEkle(int x, int y, OyunZorlugu oyunZorlugu) {

        Image image = ResimleriDose.getImage(KareDurumu.VURULMAMIS_KARE);

        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setId(x + "" + y);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageViewTiklaninca(imageView);
            }
        });
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
        imageView.setX(x);
        imageView.setY(y);

        paneOyunAlani.getChildren().add(imageView);
    }

    public void etraftakiBoslariAyarla(List<Kareler> karelers) {
        ImageView imageView1 = null;

        int boyut = etraftakiKareler.size();
        for (int i = 0; i < karelers.size(); i++) {
            Kareler kareler = etraftakiKareler.get(i);
            Konum konum1 = kareler.getKonum();

            if (!kareler.isKareyeBakildimi() && !kareler.isMayinmi()) {
                int yakindakiMayinalar = getYakindakiMayinlar(kareler.getKonum());
                System.out.println("etraftaki mayınlar="+yakindakiMayinalar);
                if (yakindakiMayinalar == 0) {
                    imageView1 = imageViewAyarla(konum1, ResimleriDose.EXPOSED);
                    imageView1.setFitHeight(widhtHeight);
                    imageView1.setFitWidth(widhtHeight);
                    paneOyunAlani.getChildren().add(imageView1);
                    cevresindekileriEkle(konum1);
                } else {
                    imageView1 = imageViewAyarla(konum1, ResimleriDose.rakamAl(yakindakiMayinalar));
                    imageView1.setFitHeight(widhtHeight);
                    imageView1.setFitWidth(widhtHeight);
                    paneOyunAlani.getChildren().add(imageView1);
                }
                kareler.setKareyeBakildimi(true);
            }

        }

    }

    private void imageViewTiklaninca(ImageView imageView) {
        etraftakiKareler = new ArrayList<>();
        Konum konum = new Konum((int) imageView.getX(), (int) imageView.getY());
        ImageView imageView1 = null;
        Kareler mayin = mayinTarlasi.konumaGoreKareAl(konum);
        if (mayin.isMayinmi()) {
            imageView1 = imageViewAyarla(imageView, ResimleriDose.HITMINE);
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION, "Elendin.", ButtonType.OK);
            alert.showAndWait();
            mayinlariGoster();

        } else {
            int yakindakimayinlar = getYakindakiMayinlar(konum);
            if (yakindakimayinlar == 0) {
                etraftakiKareler.add(mayin);
                etraftakiBoslariAyarla(etraftakiKareler);
            } else {
                imageView1 = imageViewAyarla(imageView, ResimleriDose.rakamAl(yakindakimayinlar));
            }
        }
        paneOyunAlani.getChildren().add(imageView1);
    }

    public void mayinlariGoster() {

            for (int i = 0; i < mayinTarlasi.getHarita().size(); i++) {
                Kareler item = mayinTarlasi.getHarita().get(i);

            if (item.isMayinmi()) {
                Konum konum = new Konum(item.getKonum().X, item.getKonum().Y);
                ImageView retryimageView = new ImageView();
                retryimageView.setImage(ResimleriDose.getImage(KareDurumu.MAYIN));
                retryimageView.setX(konum.X);
                retryimageView.setY(konum.Y);
                retryimageView.setFitHeight(widhtHeight);
                retryimageView.setFitWidth(widhtHeight);
                paneOyunAlani.getChildren().add(retryimageView);

            }
        }
    }

    public ImageView imageViewAyarla(ImageView imageView, Image image) {
        Konum konum = new Konum((int) imageView.getX(), (int) imageView.getY());
        ImageView retryimageView = new ImageView();
        retryimageView.setImage(image);
        retryimageView.setX(konum.X);
        retryimageView.setY(konum.Y);
        retryimageView.setFitHeight(widhtHeight);
        retryimageView.setFitWidth(widhtHeight);
        return retryimageView;
    }

    public ImageView imageViewAyarla(Konum konum, Image image) {
        ImageView retryimageView = new ImageView();
        retryimageView.setImage(image);
        retryimageView.setX(konum.X);
        retryimageView.setY(konum.Y);

        return retryimageView;
    }

    public int getYakindakiMayinlar(Konum konum) {
        int x = konum.X;
        int y = konum.Y;
        int etraf = widhtHeight ;

        int etraftakiMayinlar = 0;

        etraftakiMayinlar += kontrolEt(x - etraf, y - etraf);
        etraftakiMayinlar += kontrolEt(x, y - etraf);
        etraftakiMayinlar += kontrolEt(x + etraf, y - etraf);

        etraftakiMayinlar += kontrolEt(x - etraf, y);
        etraftakiMayinlar += kontrolEt(x + etraf, y);

        etraftakiMayinlar += kontrolEt(x - etraf, y + etraf);
        etraftakiMayinlar += kontrolEt(x, y + etraf);
        etraftakiMayinlar += kontrolEt(x + etraf, y + etraf);


        return etraftakiMayinlar;
    }

    private int kontrolEt(int x, int y) {
        if (x < 0 || x >= paneSatirSutun) {
            return 0;
        }
        if (y < 0 || y >= paneSatirSutun) {
            return 0;
        }
        if (mayinTarlasi.konumaGoreKareAl(new Konum(x, y)).isMayinmi()) {
            return 1;
        } else {
            return 0;
        }
    }

    public void cevresindekileriEkle(Konum konum) {
        int x = konum.X;
        int y = konum.Y;
        int etraf = widhtHeight;

        int etraftakiMayinlar = 0;

        etraftakiMayinlar += kontrolEt2(x - etraf, y - etraf);
        etraftakiMayinlar += kontrolEt2(x, y - etraf);
        etraftakiMayinlar += kontrolEt2(x + etraf, y - etraf);

        etraftakiMayinlar += kontrolEt2(x - etraf, y);
        etraftakiMayinlar += kontrolEt2(x + etraf, y);

        etraftakiMayinlar += kontrolEt2(x - etraf, y + etraf);
        etraftakiMayinlar += kontrolEt2(x, y + etraf);
        etraftakiMayinlar += kontrolEt2(x + etraf, y + etraf);


    }

    private int kontrolEt2(int x, int y) {
        System.err.println(x+ "  "+ y);
        if (x < 0 || x >= paneSatirSutun) {
            return 0;
        }
        if (y < 0 || y >= paneSatirSutun) {
            return 0;
        }
        if (mayinTarlasi.konumaGoreKareAl(new Konum(x, y)).isMayinmi()) {
            etraftakiKareler.add(mayinTarlasi.konumaGoreKareAl(new Konum(x, y)));
            return 1;
        } else {
            etraftakiKareler.add(mayinTarlasi.konumaGoreKareAl(new Konum(x, y)));
            return 0;
        }

    }

}