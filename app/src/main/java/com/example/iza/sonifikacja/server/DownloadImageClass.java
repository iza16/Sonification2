package com.example.iza.sonifikacja.server;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.iza.sonifikacja.pictures.filters.Original;
import com.example.iza.sonifikacja.pictures.test.OriginalMatSingleton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iza on 2016-05-07.
 */
public class DownloadImageClass {

    public Context context;
    public Integer doubleValue=0;
    String ServletSQL;
    public final List<String> listitems = new ArrayList<String>();
    public String[] arrayStrings;
    public String namePicture = "";

    public DownloadImageClass(Context c){
        context = c;

        isNetworkAvailable();
        if(isNetworkAvailable()==false){
            Toast.makeText(context, "BRAK POLACZENIA INTERNETOWEGO", Toast.LENGTH_SHORT).show();
        } else
        if(isNetworkAvailable()==true){
            Toast.makeText(context, "POLACZONO", Toast.LENGTH_SHORT).show();
        }

       DownloadList();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void DownloadList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
            if(isNetworkAvailable()==false){                        // brak polaczenia sieciowego
                    try {
                        listitems.clear();
                        BufferedReader br = null;
                        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ncn/images";
                        File mFile = new File(file_path + "/listaobrazow.txt");
                        RandomAccessFile file = new RandomAccessFile(mFile, "r");
                        try {
                            long longlenth = file.length();
                            int length = (int) longlenth;
                            if (length != longlenth)
                            {
                                throw new IOException("File size >= 2 GB");
                            }
                            byte[] data = new byte[length];
                            file.readFully(data);
                            StringBuilder sbuilder = new StringBuilder();
                            String currentLine;
                            br = new BufferedReader(new FileReader(mFile));
                            while ((currentLine = br.readLine()) != null) {
                                sbuilder.append(currentLine + "\n");       // odczyt linia po linii
                                listitems.add(currentLine);
                            }
                            arrayStrings = new String[listitems.size()];
                            listitems.toArray(arrayStrings);
                            Log.d("APLIKACJA", "ODCZYTANO PLIK");

                            int size = data.length;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                file.close();
                                if (br != null) {
                                    br.close();
                                }
                            } catch (IOException ee) {
                                ee.printStackTrace();
                            }
                        }
                    } catch (IOException x){
                        x.printStackTrace();
                    }
                }
                else
                if(isNetworkAvailable()==true) {
                    // polaczenie internetowe istnieje
                    try {
                        // http://sovie.synology.me:7070/select/androidTomcat                   //  synology
                        //http://10.0.2.2:8080/AndroidTomcat/AndroidTomcat                      //  localhost
                        listitems.clear();
                        URL mUrl = new URL("http://sovie.synology.me:7070/ListImageAndroid/androidTomcat");
                        URLConnection connection = mUrl.openConnection();
                        String inputString = "HEADER";                  // header metody $_POST
                        Log.d("inputString = ", inputString);
                        Log.d("APLIKACJA", "WEJSCIE--2--");
                        connection.setDoOutput(true);
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.writeUTF(inputString);
                        out.close();
                        String ResultString = "";
                        InputStream inputstream = connection.getInputStream();
                        BufferedReader in = null;
                        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line=null;
                        final StringBuilder responseData = new StringBuilder();
                        int noline=0;
                        while ((line = in.readLine()) != null) {
                            noline++;
                            responseData.append(line+"\n");
                            if(noline==1){
                                line =line.substring(2);
                                listitems.add(line);
                            } else {
                                listitems.add(line);
                            }
                        }
                        arrayStrings = new String[listitems.size()];
                        listitems.toArray(arrayStrings);
                        in.close();

                    } catch (Exception e) {
                        Log.d("Exception", e.toString());
                        Log.d("APLIKACJA", "CATCH");
                    }
                }
            }
        }).start();
    }

    public static Bitmap Read(String namePicture){
        try{
            // http://sovie.synology.me:7070/select/androidTomcat                   //synology
            //http://10.0.2.2:8080/AndroidTomcat/AndroidTomcat                      //  localhost
            String file_path =Environment.getExternalStorageDirectory().getAbsolutePath()+"/ncn/images";
            File mFile = new File(file_path+"/"+namePicture+".png");
            RandomAccessFile file = new RandomAccessFile(mFile,"r");

            long longlenth = file.length();
            int length = (int) longlenth;
            if(length!=longlenth){
                throw new IOException("File size >= 2 GB");
            }
            final byte[] data= new byte[length];
            file.readFully(data);

            int size = data.length;
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            OriginalMatSingleton.getInstance().setOriginalMat(bm);
            OriginalMatSingleton.getInstance().setCurretlyFilter(new Original());
            return bm;
        } catch(Exception e){
            Log.d("Exception",e.toString());
        }
        return null;
    }

    public static void SaveImage(final String ImageName)throws IOException{

        new Thread(new Runnable() {
            @Override
            public void run() {
                    try{
                        // http://sovie.synology.me:7070/select/androidTomcat                   //synology
                        //http://10.0.2.2:8080/AndroidTomcat/AndroidTomcat                      //  localhost
                        URL mUrl = new URL("http://sovie.synology.me:7070/OneImageAndroid/androidTomcat");
                        URLConnection connection = mUrl.openConnection();
                        String inputString = ImageName;
                        Log.d("inputString = ", inputString);
                        Log.d("APLIKACJA", "WEJSCIE--2--");
                        connection.setDoOutput(true);
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.writeUTF(inputString);
                        out.close();
                        String ResultString = "";
                        StringBuffer sb = new StringBuffer();
                        InputStream inputstream = connection.getInputStream();
                        //read from the stream
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] content = new byte[1024];
                        int bytesread = -1;
                        while ((bytesread = inputstream.read(content)) != -1) {
                            baos.write(content, 0, bytesread);
                        }
                        final byte[] imagepobrane = baos.toByteArray();
                        String file_path2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ncn/images";
                        File directory = new File(file_path2);
                        directory.mkdirs();
                        File mFile2 = new File(file_path2 + "/"+ImageName+".png");
                        if (!mFile2.exists()) {
                            try {

                                mFile2.createNewFile();
                                Log.d("APLIKACJA", "1--UTWORZENIE PLIKU");
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("APLIKACJA", "1-BLAD OTWORZENIA PLIKU");
                            }
                        }
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(mFile2);
                            byte[] b = {1, 2, 3, 4, 5, 6, 7, 8};
                            fos.write(imagepobrane);
                            Log.d("APLIKACJA", "2--ZAPIS DO PLIKU");

                            fos.flush();
                            fos.close();

                        } catch (Exception z) {
                            Log.d("APLIKACJA", "2--BLAD ZAPISU");
                            z.printStackTrace();
                        }


                        File mFile = new File(file_path2+"/listaobrazow.txt");
                        if(!mFile.exists()){
                            try{
                                mFile.createNewFile();
                                Log.d("APLIKACJA", "1--UTWORZENIE PLIKU");
                            } catch(IOException e){
                                e.printStackTrace();
                                Log.d("APLIKACJA", "1-BLAD OTWORZENIA PLIKU");
                            }
                        }
                        FileOutputStream fos2 = null;
                        try{
                            Writer outputwriter;
                            outputwriter = new BufferedWriter(new FileWriter(mFile,true));
                            outputwriter.append(ImageName);
                            outputwriter.append("\n");
                            outputwriter.flush();
                            outputwriter.close();
                            Log.d("APLIKACJA", "2--ZAPIS DO PLIKU");
                            fos.flush();
                            fos.close();

                        }
                        catch(Exception z){
                            Log.d("APLIKACJA", "2--BLAD ZAPISU");
                            z.printStackTrace();
                        }
                        final int sizeimage = imagepobrane.length;

                    } catch(Exception e){
                        Log.d("Exception",e.toString());
                        Log.d("APLIKACJA","CATCH");
                    }


                }

        }).start();
    }

}
