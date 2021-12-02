package com.example.uni_hub.services;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequester {

    OkHttpClient client = new OkHttpClient();
//    private String url;
    Gson gson ;

    public HttpRequester() {
    }


    public List<LatLng> run(String url) throws IOException {
        gson = new Gson();
        List<LatLng> latLngs = new ArrayList<>();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {


           Root root = gson.fromJson(Objects.requireNonNull(response.body()).string(),Root.class);

           List<Root.Step> steps = root.routes.get(0).legs.get(0).steps;
           for (Root.Step step : steps){
               latLngs.add(new LatLng(step.start_location.lat,step.start_location.lng));
               latLngs.add(new LatLng(step.end_location.lat,step.end_location.lng));
           }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLngs;
    }
}
