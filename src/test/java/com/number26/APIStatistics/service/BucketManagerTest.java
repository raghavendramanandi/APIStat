package com.number26.APIStatistics.service;

import static org.junit.Assert.*;

import com.number26.APIStatistics.model.Transaction;
import com.sun.deploy.net.HttpResponse;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;


public class BucketManagerTest {
//    @Autowired
//    private StatisticsService statisticsService;


    @BeforeClass
    public static void setProxy()
    {
        System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "8080");
    }

    @Test
    public void addCourse() {
        try {
            URL url = new URL("http://localhost:8080");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException(" HTTP error code : "
                        + conn.getResponseCode());
            }

            Scanner scan = new Scanner(url.openStream());
            String entireResponse = new String();
            while (scan.hasNext())
                entireResponse += scan.nextLine();

            System.out.println("Response : " + entireResponse);

            scan.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}