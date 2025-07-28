package com.example.kbmonly;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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
public class NewslistFragment extends Fragment {

    // https://run.mocky.io/v3/b05dcd85-9270-4431-9986-c166434bc6b8

    private static String JSON_URL2 = "https://run.mocky.io/v3/b05dcd85-9270-4431-9986-c166434bc6b8";

    List<NewsModelClass> newsList;
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newsList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rvNewslist);

        NewslistFragment.GetData getData = new NewslistFragment.GetData();
        getData.execute();
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String current = "";

            try{
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL2);
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
                JSONArray jsonArray = jsonObject.getJSONArray("news");

                for (int i = 0; i < jsonArray.length(); i++){

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                    NewsModelClass model = new NewsModelClass();
                    model.setDescription(jsonObject1.getString("description"));
                    model.setImageUrl(jsonObject1.getString("image_url"));

                    newsList.add(model);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            PutDataIntoRecyclerView(newsList);

        }
    }

    private void PutDataIntoRecyclerView(List<NewsModelClass> newsList){

        NewsAdaptery newsAdaptery = new NewsAdaptery(getActivity(), newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(newsAdaptery);
    }

    public NewslistFragment() {
        super(R.layout.fragment_newslist);
    }

    public static NewslistFragment newInstance() {
        return new NewslistFragment();
    }
}
