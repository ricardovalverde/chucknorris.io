package com.example.chucknorrisio.datasource;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class CategoryRemoteDataSource {

    public void findAll(ListCategoriesCallback callback) {
        new CategoryTask(callback).execute();
    }


    public interface ListCategoriesCallback {

        void onSuccess(List<String> reponse);

        void onError(String errorMessage);

        void onComplete();
    }

    private static class CategoryTask extends AsyncTask<Void, Void, List<String>> {
        private final ListCategoriesCallback callback;
        private String errorMessage;

        public CategoryTask(ListCategoriesCallback callback) {
            this.callback = callback;
        }


        @Override
        protected List<String> doInBackground(Void... voids) {

            List<String> categoryItems = new ArrayList<>();

            try {
                URL url = new URL(Endpoint.GET_CATEGORIES);

                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setReadTimeout(2000);
                httpsURLConnection.setConnectTimeout(2000);

                int responseCode = httpsURLConnection.getResponseCode();
                if (responseCode > 400) {
                    throw new Exception("Erro na conexão com o servidor");
                }

                InputStream inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());

                JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
                jsonReader.beginArray();

                while (jsonReader.hasNext()) {
                    categoryItems.add(jsonReader.nextString());
                }
                jsonReader.endArray();

            } catch (MalformedURLException e) {
                errorMessage = e.getMessage();
            } catch (IOException e) {
                errorMessage = e.getMessage();
            } catch (Exception e) {
                errorMessage = e.getMessage();

            }


            return categoryItems;
        }


        @Override
        protected void onPostExecute(List<String> strings) {
            if (errorMessage != null) {

                callback.onError(errorMessage);

            } else {

                callback.onSuccess(strings);
            }
            callback.onComplete();
            super.onPostExecute(strings);
        }
    }
}
