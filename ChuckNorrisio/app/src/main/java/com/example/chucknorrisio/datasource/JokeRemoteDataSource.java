package com.example.chucknorrisio.datasource;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;

import com.example.chucknorrisio.model.Joke;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class JokeRemoteDataSource {


    public void findJokeBy(JokeCallback jokeCallback, String category) {
        new JokeTask(jokeCallback, category).execute();

    }


    public interface JokeCallback {
        public void onSuccess(Joke joke);

        public void onError(String message);

        public void onComplete();

    }

    private class JokeTask extends AsyncTask<Void, Void, Joke> {
        private final JokeCallback jokecallback;
        private final String category;
        private String errorMessage;

        public JokeTask(JokeCallback jokeCallback, String category) {
            this.jokecallback = jokeCallback;
            this.category = category;
        }


        @Override
        protected Joke doInBackground(Void... voids) {
            Joke joke = null;
            String endPoint = String.format("%s?category=%s", Endpoint.GET_JOKE, category);


            try {
                URL url = new URL(endPoint);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setReadTimeout(2000);
                httpsURLConnection.setConnectTimeout(2000);
                int responseCode = httpsURLConnection.getResponseCode();
                if (responseCode > 400) throw new IOException("Erro comunicação com servidor");
                InputStream inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());

                JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));

                jsonReader.beginObject();

                String iconUrl = null;
                String value = null;

                while (jsonReader.hasNext()) {

                    JsonToken token = jsonReader.peek();

                    if (token == JsonToken.NAME) {

                        String name = jsonReader.nextName();

                        if (name.equals("category"))
                            jsonReader.skipValue();

                        else if (name.equals("icon_url"))
                            iconUrl = jsonReader.nextString();

                        else if (name.equals("value"))
                            value = jsonReader.nextString();

                        else jsonReader.skipValue();


                    }

                }
                joke = new Joke(value, iconUrl);
                jsonReader.endObject();


            } catch (MalformedURLException e) {
                errorMessage = e.getMessage();
            } catch (IOException e) {
                errorMessage = e.getMessage();

            } catch (Exception e) {
                errorMessage = e.getMessage();

            }


            return joke;
        }

        @Override
        protected void onPostExecute(Joke joke) {
            if (errorMessage != null) {
                jokecallback.onError(errorMessage);

            } else {
                jokecallback.onSuccess(joke);
            }
            jokecallback.onComplete();
        }
    }


}
