package com.example.kbmonly;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GamelistFragment extends Fragment implements RecyclerViewInterface{

    // Json ссылка: https://run.mocky.io/v3/de1fb24f-5bbc-454a-99d8-b13bf02a8fc5 (outdated)
    // https://run.mocky.io/v3/5c1be9c0-791c-4be5-affc-0504c4f143f1

    private static String JSON_URL = "https://run.mocky.io/v3/b6049781-b637-41c6-8422-26347354b71b";


    List<GameModelClass> gameList;
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gameList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rvGamelist);

        GetData getData = new GetData();
        getData.execute();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        com.example.kbmonly.GameModelClass clickedItem = gameList.get(position);

        intent.putExtra("title", clickedItem.getTitle());
        intent.putExtra("description", clickedItem.getDescription());
        intent.putExtra("genre", clickedItem.getGenre());
        intent.putExtra("release_year", clickedItem.getReleaseYear());
        intent.putExtra("image_url", clickedItem.getImageUrl());
        intent.putExtra("video_url", clickedItem.getVideoUrl());

        startActivity(intent);
    }

    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            String current = "";

            try{
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while(data != -1){
                        current += (char) data;
                        data = isr.read();

                    }
                    return current;

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }


            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(String s){

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("games");

                for (int i = 0; i < jsonArray.length(); i++){

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                    GameModelClass model = new GameModelClass();
                    model.setTitle(jsonObject1.getString("title"));
                    model.setDescription(jsonObject1.getString("description"));
                    model.setGenre(jsonObject1.getString("genre"));
                    model.setReleaseYear(jsonObject1.getString("release_year"));
                    model.setImageUrl(jsonObject1.getString("image_url"));
                    model.setVideoUrl(jsonObject1.getString("video_url"));

                    gameList.add(model);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            PutDataIntoRecyclerView(gameList);

        }
    }

    private void PutDataIntoRecyclerView(List<GameModelClass> gameList){

        Adaptery adaptery = new Adaptery(getActivity(), gameList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adaptery);
    }

    public GamelistFragment() {
        super(R.layout.fragment_gamelist);
    }

    public static GamelistFragment newInstance() {
        return new GamelistFragment();
    }
}
