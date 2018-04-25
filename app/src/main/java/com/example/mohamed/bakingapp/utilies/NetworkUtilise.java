package com.example.mohamed.bakingapp.utilies;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mohamed on 4/17/18.
 */

public class NetworkUtilise {

    private static final String jsonUrl="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static String getJsonString()
    {
        HttpURLConnection connection=null;
        try {
            URL url=new URL(jsonUrl);
            connection=(HttpURLConnection)url.openConnection();
            InputStream in=connection.getInputStream();

            Scanner scanner=new Scanner(in);
            scanner.useDelimiter("//A");

            if (scanner.hasNext())
                return scanner.next();

            else return null;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null)
                connection.disconnect();
        }
        return null;
    }
}
