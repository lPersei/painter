/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject;

import java.awt.image.BufferedImage;

/**
 *
 * @author Persei
 * Klasa zawiera filtry
 */
public class Filters {
    //Konstruktor klasy
    public Filters() {       
    }
    /*
    Nakłada szaro-odcieniowy filtr.
    Bierzę piksel po pikselu podanego obrazka i wylicza wartość każdego kanału oprócz Alpha.
    Każdy kanał zajmuje 8 bitów Alpha z indeksu 24 do indeksu 31, Red z 15 do 23, Green z 8 do 15 i Blue z 0 do 7.
    I teraz róbmy right shift 32 bity na 16 pozycij z operacjej ADD 0xff(255 w hex) dla Red, 32 bity na 8 pozycij ADD 0xff dla Green i róbmy tylko ADD 0xff dla Blue
    Każdy kanał jest mnożony przez stałe wartości dla otrzymania szaro-odcieniowych pikseli
    */
    public BufferedImage GrayFilter(BufferedImage originalImage){
        BufferedImage newImage = originalImage;
        int Width = originalImage.getWidth();
        int Height = originalImage.getHeight();
        int pixel,r,g,b;
        int gray;
        for (int x = 0; x < Width; x++) {
            for (int y = 0; y < Height; y++) {
                pixel = originalImage.getRGB(x,y);
                //a = (pixel>>24) & 0xff;
                r = (pixel>>16) & 0xff;
                g = (pixel>>8) & 0xff;
                b = pixel & 0xff;
                gray = (int)(r*0.3+g*0.59+b*0.11);
                //Zabiezpieczenie
                if (gray<0) {
                    gray =0;
                }
                if (gray>255) {
                    gray = 255;
                }
                //Wstawianie pikseli
                newImage.setRGB(x, y, MakePixel(255, gray, gray, gray));
            }
        }
        return newImage;
    }
    /*
    Nakłada filtr Sepia
    */
    public BufferedImage SepiaFilter(BufferedImage originalImage){
        BufferedImage newImage = originalImage;
        int Width = originalImage.getWidth();
        int Height = originalImage.getHeight();
        int pixel,r,g,b;
        int Sr,Sg,Sb;
        for (int x = 0; x < Width; x++) {
            for (int y = 0; y < Height; y++) {
                pixel = originalImage.getRGB(x,y);
                //A = (pixel>>24) & 0xff;
                r = (pixel>>16) & 0xff;
                g = (pixel>>8) & 0xff;
                b = pixel & 0xff;
                Sr = (int)(0.393*r + 0.769*g + 0.189*b);
                Sg = (int)(0.349*r + 0.686*g + 0.168*b);
                Sb = (int)(0.272*r + 0.534*g + 0.131*b);
                if (Sr>255) {
                    r = 255;
                } else  r = Sr;
                
                if (Sg>255) {
                    g = 255;
                } else  g = Sg;
                
                if (Sb>255) {
                    b = 255;
                } else  b = Sb;

                newImage.setRGB(x, y, MakePixel(255, r, g, b));
            }
        }
        return newImage;
    }
    /*
    Nakłada filtr negatywnych kolorów
    */
    public BufferedImage NegativeFilter(BufferedImage originalImage){
        BufferedImage newImage = originalImage;
        int Width = originalImage.getWidth();
        int Height = originalImage.getHeight();
        int pixel,r,g,b;
        for (int x = 0; x < Width; x++) {
            for (int y = 0; y < Height; y++) {
                pixel = originalImage.getRGB(x,y);
                //A = (pixel>>24) & 0xff;
                r = (pixel>>16) & 0xff;
                g = (pixel>>8) & 0xff;
                b = pixel & 0xff;
                newImage.setRGB(x, y, MakePixel(255, 255-r, 255-g, 255-b));
            }
        }
        return newImage;
    }
    /*
    Nakłada Change Filtr
    */
    public BufferedImage ChangeFilter(BufferedImage originalImage, int fromColor, int toColor){
        BufferedImage newImage = originalImage;
        int Width = originalImage.getWidth();
        int Height = originalImage.getHeight();
        int pixel,r,g,b;
        for (int x = 0; x < Width; x++) {
            for (int y = 0; y < Height; y++) {
                pixel = originalImage.getRGB(x,y);
                if (pixel == fromColor) {
                    pixel = toColor;
                    r = (pixel>>16) & 0xff;
                    g = (pixel>>8) & 0xff;
                    b = pixel & 0xff;
                    newImage.setRGB(x, y, MakePixel(255, r, g, b));
                }                
            }
        }
        return newImage;
    }
    /*
    Służy do otrzymania ARGB wartości.
    Shift Alpha na 24 pozycji oraz OR z Red przeniesioną o 16 pozycji, Green o 8 i Blue
    */
    public int MakePixel(int A, int R, int G, int B){
        return (A<<24) | (R<<16) | (G<<8) | B;
    }
}
