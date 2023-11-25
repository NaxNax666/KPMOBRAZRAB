package com.example.kpmobrazrab.data;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

public class CoreSystem {

    public static final int max_x = 2000;//Максимальная ширина изображения в пикселях
    public static final int max_y = 1100;//Максимальная высота изображения в пикселях
    public static int x,y;//Ширина и высота изображения в пикселях (которые есть у файла)
    public static int offset;//Смещение массива пикселей от начала файла (вообще в 24-битном беспалитровом файле должно быть 54, но почему-то некоторые программы создают файлы с другим значением, поэтому надо такие случаи тоже обрабатывать)
    public static int xbytes;//Ширина изображения в байтах (BMP файл выравнивает строки до кратности 4 байтам)
    public static final int max_offset=1078;//Максимальное смещение массива пикселей от начала файла
    public static final int max_size = max_offset+(max_x+3)*max_y*3;//Максимальный размер изображения в байтах
    public static int filesize;//Размер BMP файла в байтах
    public static byte[] bytes1 = new byte[max_size];//Массив всех байт входного BMP файла
    public static byte[] bytes2 = new byte[max_size];//Массив всех байт выходного BMP файла
    public static byte[][] px1red = new byte[max_y][max_x];//Массив пикселей входного файла (Red)
    public static byte[][] px1green = new byte[max_y][max_x];//Массив пикселей входного файла (Green)
    public static byte[][] px1blue = new byte[max_y][max_x];//Массив пикселей входного файла (Blue)
    public static byte[][] px2red = new byte[max_y][max_x];//Массив пикселей выходного файла (Red)
    public static byte[][] px2green = new byte[max_y][max_x];//Массив пикселей выходного файла (Green)
    public static byte[][] px2blue = new byte[max_y][max_x];//Массив пикселей выходного файла (Blue)
    public static int[][] pxbuf = new int[max_y][max_x];//Массив для хранения и обработки промежуточных значений
    public static int[][] pxbuf1 = new int[max_y][max_x];//Массив для хранения и обработки промежуточных значений
    public static int[][] pxbuf2 = new int[max_y][max_x];//Массив для хранения и обработки промежуточных значений
    public static int[][] pxbuf3 = new int[max_y][max_x];//Массив для хранения и обработки промежуточных значений
    public static double[][] pxbuf4 = new double[max_y][max_x];//Массив для хранения и обработки промежуточных значений
    public static double[][] pxbuf5 = new double[max_y][max_x];//Массив для хранения и обработки промежуточных значений
    public static double[][] pxbuf6 = new double[max_y][max_x];//Массив для хранения и обработки промежуточных значений
    public static double[][] pxbuf7 = new double[max_y][max_x];//Массив для хранения и обработки промежуточных значений
    public static double[][] pxbuf8 = new double[max_y][max_x];//Массив для хранения и обработки промежуточных значений
    public static double[][] pxbuf9 = new double[max_y][max_x];//Массив для хранения и обработки промежуточных значений
    //public static double[][] pxbuf10 = new double[max_y][max_x];//Массив для хранения и обработки промежуточных значений
    //public static double[][] pxbuf11 = new double[max_y][max_x];//Массив для хранения и обработки промежуточных значений
    //public static double[][] pxbuf12 = new double[max_y][max_x];//Массив для хранения и обработки промежуточных значений

    public static double[][] gausmatr = new double[max_y][max_x];//Матрица для размытия по Гауссу

    public static double[][] convmatr0 = {{0.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 0.0}};//Исходное изображение
    public static double[][] convmatr1 = {{0.0, -1.0, 0.0}, {-1.0, 5.0, -1.0}, {0.0, -1.0, 0.0}};//Заострение (Sharpen)
    public static double[][] convmatr2 = {{0.0, -1.0, 0.0}, {-1.0, 4.0, -1.0}, {0.0, -1.0, 0.0}};//Обнаружение краёв (Ridge)
    public static double[][] convmatr3 = {{-1.0, -1.0, -1.0}, {-1.0, 8.0, -1.0}, {-1.0, -1.0, -1.0}};//Обнаружение краёв (Edge)
    public static double[][] convmatr4 = {{1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}};//Коробочное размытие (Box Blur)
    public static double[][] convmatr5 = {{-1.0, -1.0, -1.0}, {-1.0, 9.0, -1.0}, {-1.0, -1.0, -1.0}};//Повышение чёткости (ПОВВС ЛР4 ВАР1)
    public static double[][] convmatr6 = {{-2.0, -1.0, 0.0}, {-1.0, 1.0, 1.0}, {0.0, 1.0, 2.0}};//Тиснение
    public static double[][] convmatr7 = {{1.0, 0.0, -1.0}, {1.0, 0.0, -1.0}, {1.0, 0.0, -1.0}};//Выделение границ
    public static double[][] convmatr8 = {{0.0, 1.0, 0.0}, {1.0, -4.0, 1.0}, {0.0, 1.0, 0.0}};//Краевой фильтр Лапласа
    public static double[][] convmatr9 = {{1.0, 0.0, -1.0}, {0.0, 0.0, 0.0}, {-1.0, 0.0, 1.0}};//Обнаружение краёв
    public static Bitmap.Config bmapConf;

    public static int PixelInBigRange(int yvalue, int xvalue) {//Вовращает значение пикселя даже если он выходит за границы изображения (возвращает ближайший)
        if(yvalue<0){yvalue=0;}
        if(yvalue>y){yvalue=y;}
        if(xvalue<0){xvalue=0;}
        if(xvalue>x){xvalue=x;}
        return pxbuf[yvalue][xvalue];
    }

    public static int PixelShifted(int yvalue, int xvalue) {//Вовращает значение пикселя даже если он выходит за границы изображения (циклический сдвиг)
        int bufval=xvalue;
        if(bufval<0){ bufval=bufval+x*(((-1*bufval)/x)+2);}
        xvalue=bufval%x;
        bufval=yvalue;
        if(bufval<0){ bufval=bufval+y*(((-1*bufval)/y)+2);}
        yvalue=bufval%y;
        return pxbuf[yvalue][xvalue];
    }

    //in->int[] srcPixels, height,wight ->
    //int[] dstPixels

    public static int calculateSquare(int a) {//Просто проверка работы функций в Java
        int b;
        b=10;
        return a * a+b-b;
    }

    public static String CutFileExt(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            // Если найдена точка, обрезаем строку до нее
            return fileName.substring(0, lastDotIndex);
        }
        // Если точка не найдена, возвращаем исходное название файла
        return fileName;
    }

    public static int ByteToInt(byte a) {//Перевод из типа Byte (8 бит со знаком) в тип int так, чтобы это было
        int b;//ЭКВИВАЛЕНТНО числу в типе 8 бит БЕЗ ЗНАКА (0-255), т.к. в Java такого типа нет:(
        if (a < 0) {b=a+256;//Числа от 80h до FFh
        } else {b=a;}//Числа от 00h до 7Fh
        return b;
    }

    public static byte IntToByte(int a) {//Перевод из типа int (числа ЭКВИВАЛЕНТНЫЕ формату 8 бит БЕЗ ЗНАКА)
        byte b;//в тип byte (8 бит со знаком)
        if (a<0){a=0;}//Приведение к диапазону 0-255
        if (a>255){a=255;}
        if (a > 127) {b=(byte)(a-256);//Числа от 80h до FFh
        } else {b=(byte)(a);}//Числа от 00h до 7Fh
        return b;
    }

    public static int CheckIntByte() {//Проверка перевода всех чисел типа byte в int и обратно, успешно:)
        for (int i = -128; i < 128; i++) {
            byte a,c; int b;
            a=(byte)(i);
            b=ByteToInt(a);
            c=IntToByte(b);
            System.out.println(i+")byte: "+a+"|int: "+b+"|byte again: "+c+"|HEX: "+Integer.toHexString(b));
        }
        return 0;
    }

    public static int WriteAllByteValuesToFile() {//Запись всех чисел типа byte (-128..127) в файл для просмотра HEX редактором
        byte[] allbytevalues = new byte[256];
        for (int i = 0; i < 256; i++) { allbytevalues[i] = (byte) (i - 128); }
        try (FileOutputStream fos = new FileOutputStream("byte-128-127.bin")) {
            fos.write(allbytevalues);fos.close(); }
        catch (IOException e) { e.printStackTrace(); }
        return 0;
    }

    public static int ReadBMPIntoArray(Bitmap bitmap) {//Считывание BMP файла и запись массивов пикселей px1r, px1g, px1b
        x = bitmap.getWidth();
        y = bitmap.getHeight();
        bmapConf = bitmap.getConfig();
        Log.d("Reading", "Start reading");
        for(int i=0;i<y;i++){

            for(int j=0;j<x;j++){
                px1blue[y-i-1][j]=IntToByte((bitmap.getPixel(j, i)>>0)&0xFF);
                px1green[y-i-1][j]=IntToByte((bitmap.getPixel(j, i)>>8)&0xFF);
                px1red[y-i-1][j]=IntToByte((bitmap.getPixel(j, i)>>16)&0xFF);

                ;}
            ;}
        Log.d("Reading", "Finish reading");
        Log.d("Processing", "Start Processing");
        return 0;
    }

    public static Bitmap WriteArrayIntoBMP() {//Запись массивов px2r, px2g, px2b и заголовка в выходной BMP файл
        Log.d("Processing", "End Processing");
        Bitmap dest = Bitmap.createBitmap(
                x, y, bmapConf);
        Log.d("Writing", "Start writing");
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                dest.setPixel(j,i, Color.argb(0xFF, ByteToInt(px2red[y-i-1][j]), ByteToInt(px2green[y-i-1][j]),ByteToInt(px2blue[y-i-1][j])));


                ;}
            ;}
        Log.d("Writing", "End writing");
        return dest;
    }

    public static Bitmap DoNothing(Bitmap bitmap){
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                int red=ByteToInt(px1red[i][j]);
                int green=ByteToInt(px1green[i][j]);
                int blue=ByteToInt(px1blue[i][j]);
                px2red[i][j]=IntToByte(red);
                px2green[i][j]=IntToByte(green);
                px2blue[i][j]=IntToByte(blue);
                ;}
            ;}
        return WriteArrayIntoBMP();
    }

    public static Bitmap ConvertToBlackWhite(Bitmap bitmap) {//Конвертация изображения в оттенки серого
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                int red=ByteToInt(px1red[i][j]);
                int green=ByteToInt(px1green[i][j]);
                int blue=ByteToInt(px1blue[i][j]);
                int lum=(int)(0.299*red+0.587*green+0.114*blue);
                red= lum;
                green= lum;
                blue= lum;
                px2red[i][j]=IntToByte(red);
                px2green[i][j]=IntToByte(green);
                px2blue[i][j]=IntToByte(blue);
                ;}
            ;}

        return WriteArrayIntoBMP();

    }

    public static Bitmap AddWhiteNoise(Bitmap bitmap, int middle, int strength) {//Добавление белого шума к изображению
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                int red=ByteToInt(px1red[i][j]);
                int green=ByteToInt(px1green[i][j]);
                int blue=ByteToInt(px1blue[i][j]);
                red= (int) (red+(Math.random()-0.5)*strength+middle);
                green= (int) (green+(Math.random()-0.5)*strength+middle);
                blue= (int) (blue+(Math.random()-0.5)*strength+middle);
                px2red[i][j]=IntToByte(red);
                px2green[i][j]=IntToByte(green);
                px2blue[i][j]=IntToByte(blue);
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", White Noise, middle="+middle+", strength="+strength+".bmp";
        return WriteArrayIntoBMP();

    }

    public static Bitmap CutColors(Bitmap bitmap, int strength) {//Прореживание цветов
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                int red=ByteToInt(px1red[i][j]);
                int green=ByteToInt(px1green[i][j]);
                int blue=ByteToInt(px1blue[i][j]);
                red=((int)(red/strength))*strength;
                green=((int)(green/strength))*strength;
                blue=((int)(blue/strength))*strength;
                px2red[i][j]=IntToByte(red);
                px2green[i][j]=IntToByte(green);
                px2blue[i][j]=IntToByte(blue);
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Cut Colors, strength="+strength+".bmp";
        return WriteArrayIntoBMP();

    }

    public static Bitmap Pixelization(Bitmap bitmap, int pixel_size) {//Пикселизация
        ReadBMPIntoArray(bitmap);
        int nx=(int)(Math.floor(1.0*x/pixel_size)-0);//Количество больших пикселей по горизонтали
        int ny=(int)(Math.floor(1.0*y/pixel_size)-0);//Округление до целого в меньшую сторону (-1 чтобы от 0 считать, как и пиксели)
        //Пикселизация для Red
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1red[i][j]);};}
        for(int a=0;a<ny+1;a++){
            for(int b=0;b<nx+1;b++){
                int res=0;
                int num=0;
                for(int i=0;i<pixel_size;i++){
                    for(int j=0;j<pixel_size;j++){
                        if((a*pixel_size+i<y+1)&&(b*pixel_size+j<x+1)){res=res+pxbuf[a*pixel_size+i][b*pixel_size+j];num=num+1;}
                        ;}
                    ;}
                res=(int)(res/num);
                for(int i=0;i<pixel_size;i++){
                    for(int j=0;j<pixel_size;j++){
                        if((a*pixel_size+i<y+1)&&(b*pixel_size+j<x+1)){pxbuf[a*pixel_size+i][b*pixel_size+j]=res;num=num+1;}
                        ;}
                    ;}
                ;}
            ;}
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){px2red[i][j]=IntToByte(pxbuf[i][j]);};}

        //Пикселизация для Green
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1green[i][j]);};}
        for(int a=0;a<ny+1;a++){
            for(int b=0;b<nx+1;b++){
                int res=0;
                int num=0;
                for(int i=0;i<pixel_size;i++){
                    for(int j=0;j<pixel_size;j++){
                        if((a*pixel_size+i<y+1)&&(b*pixel_size+j<x+1)){res=res+pxbuf[a*pixel_size+i][b*pixel_size+j];num=num+1;}
                        ;}
                    ;}
                res=(int)(res/num);
                for(int i=0;i<pixel_size;i++){
                    for(int j=0;j<pixel_size;j++){
                        if((a*pixel_size+i<y+1)&&(b*pixel_size+j<x+1)){pxbuf[a*pixel_size+i][b*pixel_size+j]=res;num=num+1;}
                        ;}
                    ;}
                ;}
            ;}
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){px2green[i][j]=IntToByte(pxbuf[i][j]);};}

        //Пикселизация для Blue
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1blue[i][j]);};}
        for(int a=0;a<ny+1;a++){
            for(int b=0;b<nx+1;b++){
                int res=0;
                int num=0;
                for(int i=0;i<pixel_size;i++){
                    for(int j=0;j<pixel_size;j++){
                        if((a*pixel_size+i<y+1)&&(b*pixel_size+j<x+1)){res=res+pxbuf[a*pixel_size+i][b*pixel_size+j];num=num+1;}
                        ;}
                    ;}
                res=(int)(res/num);
                for(int i=0;i<pixel_size;i++){
                    for(int j=0;j<pixel_size;j++){
                        if((a*pixel_size+i<y+1)&&(b*pixel_size+j<x+1)){pxbuf[a*pixel_size+i][b*pixel_size+j]=res;num=num+1;}
                        ;}
                    ;}
                ;}
            ;}
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){px2blue[i][j]=IntToByte(pxbuf[i][j]);};}
        //String newfilename=CutFileExt(filename)+", Pixelization, pixel_size="+pixel_size+".bmp";
        //WriteArrayIntoBMP(newfilename);
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap GaussianBlur(Bitmap bitmap, int size, double sigma) {//Размытие по Гауссу
        ReadBMPIntoArray(bitmap);
        //Заполнение матрицы Гаусса
        double A=1.0;
        double sum=0.0;
        double bufexp;//Степень, в которую возводится число e
        //System.out.println("Матрица Гаусса до приведения суммы всех её элементов к 1.0:");
        for(int i=0;i<size*2+1;i++){
            for(int j=0;j<size*2+1;j++){
                bufexp=(Math.pow(i-(size-0),2.0)+Math.pow(j-(size-0),2.0))/(2*sigma*sigma);
                gausmatr[i][j]=A*Math.exp(-1.0*bufexp);
                sum=sum+gausmatr[i][j];
                //System.out.printf(gausmatr[i][j]+" ");
                ;}
            //System.out.println("");
            ;}
        //System.out.println("Матрица Гаусса после приведения суммы всех её элементов к 1.0:");
        for(int i=0;i<size*2+1;i++){
            for(int j=0;j<size*2+1;j++){
                bufexp=(Math.pow(i-(size-0),2.0)+Math.pow(j-(size-0),2.0))/(2*sigma*sigma);
                gausmatr[i][j]=gausmatr[i][j]/sum;
                //System.out.printf(gausmatr[i][j]+" ");
                ;}
            //System.out.println("");
            ;}
        //Матрица Гаусса заполняется ПРАВИЛЬНО (проверено при size=3, sigma=0.84089642, "https://en.wikipedia.org/wiki/Gaussian_blur")
        //Размытие для Red
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1red[i][j]);};}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double res=0.0;
                for(int a1=-1*size;a1<1*size+1;a1++){
                    for(int b1=-1*size;b1<1*size+1;b1++){
                        res=res+PixelInBigRange(i+a1,j+b1)*gausmatr[a1+size][b1+size];
                        ;}
                    ;}
                px2red[i][j]=IntToByte((int)(res));
                ;}
            ;}

        //Размытие для Green
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1green[i][j]);};}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double res=0.0;
                for(int a1=-1*size;a1<1*size+1;a1++){
                    for(int b1=-1*size;b1<1*size+1;b1++){
                        res=res+PixelInBigRange(i+a1,j+b1)*gausmatr[a1+size][b1+size];
                        ;}
                    ;}
                px2green[i][j]=IntToByte((int)(res));
                ;}
            ;}

        //Размытие для Blue
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1blue[i][j]);};}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double res=0.0;
                for(int a1=-1*size;a1<1*size+1;a1++){
                    for(int b1=-1*size;b1<1*size+1;b1++){
                        res=res+PixelInBigRange(i+a1,j+b1)*gausmatr[a1+size][b1+size];
                        ;}
                    ;}
                px2blue[i][j]=IntToByte((int)(res));
                ;}
            ;}

        //String newfilename=CutFileExt(filename)+", Gaussian Blur, size="+size+", sigma="+sigma+".bmp";
        //WriteArrayIntoBMP(newfilename);
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap MirrorVertAxis(Bitmap bitmap) {//Развернуть относительно вертикальной оси
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                px2red[i][j]=px1red[i][x-j];
                px2green[i][j]=px1green[i][x-j];
                px2blue[i][j]=px1blue[i][x-j];
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Mirror Vert Axis.bmp";
        //WriteArrayIntoBMP(newfilename);
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap MirrorHorizAxis(Bitmap bitmap) {//Развернуть относительно горизонтальной оси
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                px2red[i][j]=px1red[y-i][j];
                px2green[i][j]=px1green[y-i][j];
                px2blue[i][j]=px1blue[y-i][j];
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Mirror Horiz Axis.bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap MirrorBothAxis(Bitmap bitmap) {//Развернуть относительно обоих осей
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                px2red[i][j]=px1red[y-i][x-j];
                px2green[i][j]=px1green[y-i][x-j];
                px2blue[i][j]=px1blue[y-i][x-j];
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Mirror Both Axis.bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap ChangeRGB(Bitmap bitmap, int RedDif, int GreenDif, int BlueDif) {//Увеличить или уменьшить Red, Green, Blue
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                px2red[i][j]=IntToByte(ByteToInt(px1red[i][j])+RedDif);
                px2green[i][j]=IntToByte(ByteToInt(px1green[i][j])+GreenDif);
                px2blue[i][j]=IntToByte(ByteToInt(px1blue[i][j])+BlueDif);
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Change RGB, Red="+RedDif+", Green="+GreenDif+", BlueDif="+BlueDif+".bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap SetRed(Bitmap bitmap, int RedNew) {//Установить фиксированное значение Red
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                px2red[i][j]=px1red[i][j];
                px2green[i][j]=px1green[i][j];
                px2blue[i][j]=px1blue[i][j];
                px2red[i][j]=IntToByte(RedNew);//Меняем Red
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Set Red, New Red="+RedNew+".bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap SetGreen(Bitmap bitmap, int GreenNew) {//Установить фиксированное значение Green
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                px2red[i][j]=px1red[i][j];
                px2green[i][j]=px1green[i][j];
                px2blue[i][j]=px1blue[i][j];
                px2green[i][j]=IntToByte(GreenNew);//Меняем Green
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Set Green, New Green="+GreenNew+".bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap SetBlue(Bitmap bitmap, int BlueNew) {//Установить фиксированное значение Blue
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                px2red[i][j]=px1red[i][j];
                px2green[i][j]=px1green[i][j];
                px2blue[i][j]=px1blue[i][j];
                px2blue[i][j]=IntToByte(BlueNew);//Меняем Blue
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Set Blue, New Blue="+BlueNew+".bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap ChangeYUV(Bitmap bitmap, int Ydif, int Udif, int Vdif) {//Увеличить или уменьшить Y - яркость или UV - цветоразностные компоненты
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                int R=ByteToInt(px1red[i][j]);
                int G=ByteToInt(px1green[i][j]);
                int B=ByteToInt(px1blue[i][j]);
                double Y=0.299*R+0.587*G+0.114*B+Ydif;
                double U=-0.14713*R-0.28886*G+0.436*B+128+Udif;
                double V=0.615*R-0.51499*G-0.10001*B+128+Vdif;
                px2red[i][j]=IntToByte((int)(Y+1.13983*(V-128)));
                px2green[i][j]=IntToByte((int)(Y-0.39465*(U-128)-0.58060*(V-128)));
                px2blue[i][j]=IntToByte((int)(Y+2.03211*(U-128)));
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Change YUV, Y="+Ydif+", U="+Udif+", V="+Vdif+".bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap SetY(Bitmap bitmap, int newY) {//Установить Y - яркость
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                int R=ByteToInt(px1red[i][j]);
                int G=ByteToInt(px1green[i][j]);
                int B=ByteToInt(px1blue[i][j]);
                //double Y=0.299*R+0.587*G+0.114*B;
                double U=-0.14713*R-0.28886*G+0.436*B+128;
                double V=0.615*R-0.51499*G-0.10001*B+128;
                double Y=newY;
                px2red[i][j]=IntToByte((int)(Y+1.13983*(V-128)));
                px2green[i][j]=IntToByte((int)(Y-0.39465*(U-128)-0.58060*(V-128)));
                px2blue[i][j]=IntToByte((int)(Y+2.03211*(U-128)));
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Set Y, Y="+newY+".bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap SetU(Bitmap bitmap, int newU) {//Установить U - цветоразностный компонент
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                int R=ByteToInt(px1red[i][j]);
                int G=ByteToInt(px1green[i][j]);
                int B=ByteToInt(px1blue[i][j]);
                double Y=0.299*R+0.587*G+0.114*B;
                //double U=-0.14713*R-0.28886*G+0.436*B+128;
                double V=0.615*R-0.51499*G-0.10001*B+128;
                double U=newU;
                px2red[i][j]=IntToByte((int)(Y+1.13983*(V-128)));
                px2green[i][j]=IntToByte((int)(Y-0.39465*(U-128)-0.58060*(V-128)));
                px2blue[i][j]=IntToByte((int)(Y+2.03211*(U-128)));
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Set U, U="+newU+".bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap SetV(Bitmap bitmap, int newV) {//Установить V - цветоразностный компонент
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                int R=ByteToInt(px1red[i][j]);
                int G=ByteToInt(px1green[i][j]);
                int B=ByteToInt(px1blue[i][j]);
                double Y=0.299*R+0.587*G+0.114*B;
                double U=-0.14713*R-0.28886*G+0.436*B+128;
                //double V=0.615*R-0.51499*G-0.10001*B+128;
                double V=newV;
                px2red[i][j]=IntToByte((int)(Y+1.13983*(V-128)));
                px2green[i][j]=IntToByte((int)(Y-0.39465*(U-128)-0.58060*(V-128)));
                px2blue[i][j]=IntToByte((int)(Y+2.03211*(U-128)));
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Set V, V="+newV+".bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    //Функция ChangeCMYK работает очень криво и неправильно
    public static Bitmap ChangeCMYK(Bitmap bitmap, int Cdif, int Mdif, int Ydif, int Kdif) {//Увеличить или уменьшить компоненты в цветовой модели CMYK
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                int R=ByteToInt(px1red[i][j]);
                int G=ByteToInt(px1green[i][j]);
                int B=ByteToInt(px1blue[i][j]);
                double Rc=1.0*R/255;
                double Gc=1.0*G/255;
                double Bc=1.0*B/255;
                double max=Rc; if(Gc>max){max=Gc;} if(Bc>max){max=Bc;}//max=max(Rc,Gc,Bc)
                double K=1-max+Kdif;
                double C=1.0*(1-Rc-K)/(1-K)+Cdif;//C = (1 - Rc - K) ÷ (1 - K)
                double M=1.0*(1-Gc-K)/(1-K)+Mdif;//M = (1 - Gc - K) ÷ (1 - K)
                double Y=1.0*(1-Bc-K)/(1-K)+Ydif;//Y = (1 - Bc - K) ÷ ( 1 - K)
                double Red=255.0*(1-C/100.0)*(1-B/100.0);//Red = 255 × ( 1 - Cyan ÷ 100 ) × ( 1 - Black ÷ 100 )
                double Green=255.0*(1-M/100.0)*(1-B/100.0);//Green = 255 × ( 1 - Magenta ÷ 100 ) × ( 1 - Black ÷ 100 )
                double Blue=255.0*(1-Y/100.0)*(1-B/100.0);//Blue = 255 × ( 1 - Yellow ÷ 100 ) × ( 1 - Black ÷ 100 )
                px2red[i][j]=IntToByte((int)(Red));
                px2green[i][j]=IntToByte((int)(Green));
                px2blue[i][j]=IntToByte((int)(Blue));
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", Change CMYK, C="+Cdif+", M="+Mdif+", Y="+Ydif+", K="+Kdif+".bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap NegativeRGB(Bitmap bitmap, int NegR, int NegG, int NegB) {//Поменять значения R, G, B на неготив
        ReadBMPIntoArray(bitmap);//0 или меньше = без негатива, 1 или больше = негатив
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                int R=ByteToInt(px1red[i][j]);
                int G=ByteToInt(px1green[i][j]);
                int B=ByteToInt(px1blue[i][j]);
                if(NegR>0){R=255-R;}
                if(NegG>0){G=255-G;}
                if(NegB>0){B=255-B;}
                px2red[i][j]=IntToByte(R);
                px2green[i][j]=IntToByte(G);
                px2blue[i][j]=IntToByte(B);
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", NegativeRGB, R="+NegR+", G="+NegG+", B="+NegB+".bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap NegativeYUV(Bitmap bitmap, int NegY, int NegU, int NegV) {//Поменять значения Y, U, V на неготив
        ReadBMPIntoArray(bitmap);//0 или меньше = без негатива, 1 или больше = негатив
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                int R=ByteToInt(px1red[i][j]);
                int G=ByteToInt(px1green[i][j]);
                int B=ByteToInt(px1blue[i][j]);
                double Y=0.299*R+0.587*G+0.114*B;
                double U=-0.14713*R-0.28886*G+0.436*B+128;
                double V=0.615*R-0.51499*G-0.10001*B+128;
                if(NegY>0){Y=255-Y;}
                if(NegU>0){U=255-U;}
                if(NegV>0){V=255-V;}
                px2red[i][j]=IntToByte((int)(Y+1.13983*(V-128)));
                px2green[i][j]=IntToByte((int)(Y-0.39465*(U-128)-0.58060*(V-128)));
                px2blue[i][j]=IntToByte((int)(Y+2.03211*(U-128)));
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", NegativeYUV, Y="+NegY+", U="+NegU+", V="+NegV+".bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap ConvolutionWithMatr(Bitmap bitmap, int matrnum) {//Свёртка с матрицей
        ReadBMPIntoArray(bitmap);//Значения matrnum - от 0 до 9
        double[][] matr = {{0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}};
        String matrname="";
        //Выбираем матрицу свёртки
        if(matrnum==0){matr=convmatr0;matrname="Исходное изображение";}
        if(matrnum==1){matr=convmatr1;matrname="Заострение (Sharpen)";}
        if(matrnum==2){matr=convmatr2;matrname="Обнаружение краёв (Ridge)";}
        if(matrnum==3){matr=convmatr3;matrname="Обнаружение краёв (Edge)";}
        if(matrnum==4){matr=convmatr4;matrname="Коробочное размытие (Box Blur)";}
        if(matrnum==5){matr=convmatr5;matrname="Повышение чёткости";}
        if(matrnum==6){matr=convmatr6;matrname="Тиснение";}
        if(matrnum==7){matr=convmatr7;matrname="Выделение границ";}
        if(matrnum==8){matr=convmatr8;matrname="Краевой фильтр Лапласа";}
        if(matrnum==9){matr=convmatr9;matrname="Обнаружение краёв";}

        double sum=0.0;//Нормализация (чтобы сумма всех элементов матрицы свёртки была равна 1.0)
        for(int i=0;i<3;i++){//Чтобы яркость изображения не изменялась
            for(int j=0;j<3;j++){
                sum=sum+matr[i][j];
                ;}
            ;}
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){//Если сумма равна нулю, то НЕ НАДО делить но 0
                if((sum>0)||(sum<0)){matr[i][j]=matr[i][j]/sum;}
                ;}
            ;}

        //Применение свёртки для Red
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1red[i][j]);};}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double res=0.0;
                for(int a1=-1*1;a1<1*1+1;a1++){
                    for(int b1=-1*1;b1<1*1+1;b1++){
                        res=res+PixelInBigRange(i+a1,j+b1)*matr[a1+1][b1+1];
                        ;}
                    ;}
                px2red[i][j]=IntToByte((int)(res));
                ;}
            ;}

        //Применение свёртки для Green
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1green[i][j]);};}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double res=0.0;
                for(int a1=-1*1;a1<1*1+1;a1++){
                    for(int b1=-1*1;b1<1*1+1;b1++){
                        res=res+PixelInBigRange(i+a1,j+b1)*matr[a1+1][b1+1];
                        ;}
                    ;}
                px2green[i][j]=IntToByte((int)(res));
                ;}
            ;}

        //Применение свёртки для Blue
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1blue[i][j]);};}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double res=0.0;
                for(int a1=-1*1;a1<1*1+1;a1++){
                    for(int b1=-1*1;b1<1*1+1;b1++){
                        res=res+PixelInBigRange(i+a1,j+b1)*matr[a1+1][b1+1];
                        ;}
                    ;}
                px2blue[i][j]=IntToByte((int)(res));
                ;}
            ;}

        //String newfilename=CutFileExt(filename)+", Свёртка "+matrnum+" - "+matrname+".bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap SobelOperator(Bitmap bitmap) {//Оператор Собеля (выделение границ двумя свёртками)
        ReadBMPIntoArray(bitmap);
        double[][] verticalSobelKernel = {{-1.0, 0.0, 1.0}, {-2.0, 0.0, 2.0}, {-1.0, 0.0, 1.0}};
        double[][] matr = verticalSobelKernel;//Вертикальное ядро

        //Применение свёртки для Red
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1red[i][j]);};}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double res=0.0;
                for(int a1=-1*1;a1<1*1+1;a1++){
                    for(int b1=-1*1;b1<1*1+1;b1++){
                        res=res+PixelInBigRange(i+a1,j+b1)*matr[a1+1][b1+1];
                        ;}
                    ;}
                pxbuf4[i][j]=res;
                ;}
            ;}

        //Применение свёртки для Green
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1green[i][j]);};}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double res=0.0;
                for(int a1=-1*1;a1<1*1+1;a1++){
                    for(int b1=-1*1;b1<1*1+1;b1++){
                        res=res+PixelInBigRange(i+a1,j+b1)*matr[a1+1][b1+1];
                        ;}
                    ;}
                pxbuf5[i][j]=res;
                ;}
            ;}

        //Применение свёртки для Blue
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1blue[i][j]);};}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double res=0.0;
                for(int a1=-1*1;a1<1*1+1;a1++){
                    for(int b1=-1*1;b1<1*1+1;b1++){
                        res=res+PixelInBigRange(i+a1,j+b1)*matr[a1+1][b1+1];
                        ;}
                    ;}
                pxbuf6[i][j]=res;
                ;}
            ;}

        ////////////////////////////////////////////////////
        double[][] horizontalSobelKernel = {{-1.0, -2.0, -1.0}, {0.0, 0.0, 0.0}, {1.0, 2.0, 1.0}};
        matr = horizontalSobelKernel;//Горизонтальное ядро

        //Применение свёртки для Red
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1red[i][j]);};}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double res=0.0;
                for(int a1=-1*1;a1<1*1+1;a1++){
                    for(int b1=-1*1;b1<1*1+1;b1++){
                        res=res+PixelInBigRange(i+a1,j+b1)*matr[a1+1][b1+1];
                        ;}
                    ;}
                pxbuf7[i][j]=res;
                ;}
            ;}

        //Применение свёртки для Green
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1green[i][j]);};}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double res=0.0;
                for(int a1=-1*1;a1<1*1+1;a1++){
                    for(int b1=-1*1;b1<1*1+1;b1++){
                        res=res+PixelInBigRange(i+a1,j+b1)*matr[a1+1][b1+1];
                        ;}
                    ;}
                pxbuf8[i][j]=res;
                ;}
            ;}

        //Применение свёртки для Blue
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){pxbuf[i][j]=ByteToInt(px1blue[i][j]);};}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double res=0.0;
                for(int a1=-1*1;a1<1*1+1;a1++){
                    for(int b1=-1*1;b1<1*1+1;b1++){
                        res=res+PixelInBigRange(i+a1,j+b1)*matr[a1+1][b1+1];
                        ;}
                    ;}
                pxbuf9[i][j]=res;
                ;}
            ;}

        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double buf1=Math.sqrt(pxbuf4[i][j]*pxbuf4[i][j]+pxbuf7[i][j]*pxbuf7[i][j]);
                double buf2=Math.sqrt(pxbuf5[i][j]*pxbuf5[i][j]+pxbuf8[i][j]*pxbuf8[i][j]);
                double buf3=Math.sqrt(pxbuf6[i][j]*pxbuf6[i][j]+pxbuf9[i][j]*pxbuf9[i][j]);
                px2red[i][j]=IntToByte((int)(buf1));
                px2green[i][j]=IntToByte((int)(buf2));
                px2blue[i][j]=IntToByte((int)(buf3));
                ;}
            ;}

        //String newfilename=CutFileExt(filename)+", Оператор Собеля.bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap RobertsCross(Bitmap bitmap) {//Оператор Робертса (простейшее выделение границ)
        ReadBMPIntoArray(bitmap);
        double buf1,buf2,buf3;
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){ pxbuf[i][j]=ByteToInt(px1red[i][j]);;};}
        for(int i=0;i<y;i++){//Для Red
            for(int j=0;j<x;j++){
                //pxbuf[i][j]=ByteToInt(px1red[i][j]);
                buf1=PixelInBigRange(i,j)-PixelInBigRange(i+1,j+1);
                buf1=buf1*buf1;
                buf2=PixelInBigRange(i+1,j)-PixelInBigRange(i,j+1);
                buf2=buf2*buf2;
                buf3=Math.sqrt(buf1+buf2);
                px2red[i][j]=IntToByte((int)(buf3));
                ;}
            ;}
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){ pxbuf[i][j]=ByteToInt(px1green[i][j]);;};}
        for(int i=0;i<y;i++){//Для Green
            for(int j=0;j<x;j++){
                //pxbuf[i][j]=ByteToInt(px1green[i][j]);
                buf1=PixelInBigRange(i,j)-PixelInBigRange(i+1,j+1);
                buf1=buf1*buf1;
                buf2=PixelInBigRange(i+1,j)-PixelInBigRange(i,j+1);
                buf2=buf2*buf2;
                buf3=Math.sqrt(buf1+buf2);
                px2green[i][j]=IntToByte((int)(buf3));
                ;}
            ;}
        for(int i=0;i<y;i++){for(int j=0;j<x;j++){ pxbuf[i][j]=ByteToInt(px1blue[i][j]);;};}
        for(int i=0;i<y;i++){//Для Blue
            for(int j=0;j<x;j++){
                //pxbuf[i][j]=ByteToInt(px1blue[i][j]);
                buf1=PixelInBigRange(i,j)-PixelInBigRange(i+1,j+1);
                buf1=buf1*buf1;
                buf2=PixelInBigRange(i+1,j)-PixelInBigRange(i,j+1);
                buf2=buf2*buf2;
                buf3=Math.sqrt(buf1+buf2);
                px2blue[i][j]=IntToByte((int)(buf3));
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", RobertsCross.bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap AutoLumAndConReg(Bitmap bitmap) {//Автоматическая регулировка значений яркости и контраста
        ReadBMPIntoArray(bitmap);
        double maxlum=0.0;
        double minlum=255.0;
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                pxbuf1[i][j]=ByteToInt(px1red[i][j]); int R=pxbuf1[i][j];
                pxbuf2[i][j]=ByteToInt(px1green[i][j]); int G=pxbuf2[i][j];
                pxbuf3[i][j]=ByteToInt(px1blue[i][j]); int B=pxbuf3[i][j];
                double Y=0.299*R+0.587*G+0.114*B; pxbuf4[i][j]=Y;
                double U=-0.14713*R-0.28886*G+0.436*B+128; pxbuf5[i][j]=U;
                double V=0.615*R-0.51499*G-0.10001*B+128; pxbuf6[i][j]=V;
                if (Y>maxlum){maxlum=Y;}
                if (Y<minlum){minlum=Y;}
                //px2red[i][j]=IntToByte((int)(Y+1.13983*(V-128)));
                //px2green[i][j]=IntToByte((int)(Y-0.39465*(U-128)-0.58060*(V-128)));
                //px2blue[i][j]=IntToByte((int)(Y+2.03211*(U-128)));
                ;}
            ;}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                pxbuf4[i][j]=((pxbuf4[i][j]-minlum)*255.0)/maxlum;
                ;}
            ;}
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++){
                double Y=pxbuf4[i][j];
                double U=pxbuf5[i][j];
                double V=pxbuf6[i][j];
                px2red[i][j]=IntToByte((int)(Y+1.13983*(V-128)));
                px2green[i][j]=IntToByte((int)(Y-0.39465*(U-128)-0.58060*(V-128)));
                px2blue[i][j]=IntToByte((int)(Y+2.03211*(U-128)));
                ;}
            ;}
        //String newfilename=CutFileExt(filename)+", AutoLumAndConReg.bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    public static Bitmap ShiftImage(Bitmap bitmap, int addright, int addup) {//Циклически сдвинуть изображение
        ReadBMPIntoArray(bitmap);
        for(int i=0;i<y;i++){ for(int j=0;j<x;j++){ pxbuf[i][j]=ByteToInt(px1red[i][j]);};}
        for(int i=0;i<y;i++){ for(int j=0;j<x;j++){ px2red[i][j]=IntToByte(PixelShifted(i-addup,j-addright));};}
        for(int i=0;i<y;i++){ for(int j=0;j<x;j++){ pxbuf[i][j]=ByteToInt(px1green[i][j]);};}
        for(int i=0;i<y;i++){ for(int j=0;j<x;j++){ px2green[i][j]=IntToByte(PixelShifted(i-addup,j-addright));};}
        for(int i=0;i<y;i++){ for(int j=0;j<x;j++){ pxbuf[i][j]=ByteToInt(px1blue[i][j]);};}
        for(int i=0;i<y;i++){ for(int j=0;j<x;j++){ px2blue[i][j]=IntToByte(PixelShifted(i-addup,j-addright));};}
        //String newfilename=CutFileExt(filename)+", Сдвиг на "+addright+" вправо и на "+addup+" вверх.bmp";
        //WriteArrayIntoBMP();
        //System.out.println("Создан файл: " + newfilename);
       return WriteArrayIntoBMP();
    }

    //public static void CoreSystem(String[] args) {//ОСНОВНАЯ ПРОГРАММА
        // write your code here
        //System.out.println("Hello world");
        //int n = 10; // Здесь можно задать значение константы n
        //int sum = 0;
        //for (int i = 1; i <= n; i++) { sum = sum + i; }
        //System.out.println("Сумма всех чисел от 1 до " + n + " равна " + sum);
        //int input = 5; // Здесь можно задать входное значение a
        //int result = calculateSquare(input);
        //System.out.println("Результат вычисления a * a: " + result);
        //WriteAllByteValuesToFile();
        //System.out.println("Файл со всеми значениями типа byte создан");
        //CheckIntByte();
        //ReadBMPIntoArray("img1.bmp");

        //ReadBMPIntoArray("img2.bmp");

        //AddWhiteNoise("img1.bmp",0,59);//РАБОТАЕТ
        //ConvertToBlackWhite("img1.bmp");//РАБОТАЕТ
        //CutColors("img1.bmp",30);//РАБОТАЕТ
        //Pixelization("img1.bmp",3);//РАБОТАЕТ
        //GaussianBlur("img1.bmp",5,15.0);//РАБОТАЕТ
        //MirrorVertAxis("img1.bmp");//РАБОТАЕТ
        //MirrorHorizAxis("img2.bmp");//РАБОТАЕТ
        //MirrorBothAxis("img1.bmp");//РАБОТАЕТ
        //ChangeRGB("img1.bmp",50,-25,-25);//РАБОТАЕТ
        //SetRed("img1.bmp",0);//РАБОТАЕТ
        //SetGreen("img1.bmp",0);//РАБОТАЕТ
        //SetBlue("img1.bmp",0);//РАБОТАЕТ
        //ChangeYUV("img1.bmp",0,0,-50);//РАБОТАЕТ
        //SetY("img1.bmp",100);//РАБОТАЕТ
        //SetU("img1.bmp",150);//РАБОТАЕТ
        //SetV("img1.bmp",150);//РАБОТАЕТ
        //ChangeCMYK("img1.bmp",0,0,0,0);//НЕ РАБОТАЕТ!!!!!!!!!!!!!!!!!!!!!!
        //NegativeRGB("img1.bmp",1,1,1);//РАБОТАЕТ
        //NegativeYUV("img1.bmp",0,0,0);//РАБОТАЕТ
        //RobertsCross("img1.bmp");//РАБОТАЕТ (и на цветном и на ЧБ выглядит хорошо)
        //AutoLumAndConReg("img1, Change YUV, Y=-90, U=0, V=0.bmp");//РАБОТАЕТ, исправляет слишком низкую или слишком высокую яркость
        //Можно сделать циклический сдвиг изображения вверх/вниз/влево/вправо на заданное количество пикселей
        //ConvolutionWithMatr("img1.bmp",0);//РАБОТАЕТ, CRC64 исходного и выходного файлов совпадают
        //ConvolutionWithMatr("img1.bmp",0);//ВСЕ СВЁРТКИ РАБОТАЮТ
        //ConvolutionWithMatr("img1.bmp",1);
        //ConvolutionWithMatr("img1.bmp",2);
        //ConvolutionWithMatr("img1.bmp",3);
        //ConvolutionWithMatr("img1.bmp",4);
        //ConvolutionWithMatr("img1.bmp",5);
        //ConvolutionWithMatr("img0.bmp",0);
        //ConvolutionWithMatr("img0.bmp",1);
        //ConvolutionWithMatr("img0.bmp",2);
        //ConvolutionWithMatr("img0.bmp",3);
        //ConvolutionWithMatr("img0.bmp",4);
        //ConvolutionWithMatr("img0.bmp",5);
        //ConvolutionWithMatr("img0.bmp",6);
        //ConvolutionWithMatr("img1.bmp",6);
        //ConvolutionWithMatr("img0.bmp",7);
        //ConvolutionWithMatr("img1.bmp",7);
        //ConvolutionWithMatr("img0.bmp",8);
        //ConvolutionWithMatr("img1.bmp",8);
        //ConvolutionWithMatr("img0.bmp",9);
        //ConvolutionWithMatr("img1.bmp",9);
        //SobelOperator("img1.bmp");//РАБОТАЕТ, ЛУЧШЕЕ ВЫДЕЛЕНИЕ ГРАНИЦ ИЗ ВСЕХ
        //SobelOperator("img0.bmp");
        //ShiftImage("img1.bmp",-300,-200);//РАБОТАЕТ
}