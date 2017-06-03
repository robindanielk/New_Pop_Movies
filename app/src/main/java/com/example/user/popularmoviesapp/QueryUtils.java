package com.example.user.popularmoviesapp;

import android.media.Image;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/30/2017.
 */
/*Helper Methods related to requesting and receiving PopularMovies data from MovieDb */

public final class QueryUtils
{
    private static final String TAG = QueryUtils.class.getSimpleName();
    /*Poster path url for relative appending during parsing*/
    private static final String POSTER_PATH = "https://image.tmdb.org/t/p/w500/";
    /*Return a list of <PopularMovies> that has been built upon from parsing a
    *JSON response */
   public static List<PopularMovies> extractPopularMovies(String requestUrl)
   {

       /*Creating a URL object*/
       URL url  = createUrl(requestUrl);

       /*Performing a Http Request and getting a JSON response*/
       String jsonResponse = null;
       try
       {
           jsonResponse = makeHttpRequest(url);
       }catch (IOException e){
           Log.e(TAG,"Error in making the Http request");
       }

       /*Extracting the Json response and storing it in the relevant List of
        * PopularMovies object */
       return extractJsonResponse(jsonResponse);
   }

   /*Creating a new URL object*/
   private static URL createUrl(String urlString)
   {
       URL url = null;
       try {
           url = new URL(urlString);
       } catch (MalformedURLException e) {
           Log.e(TAG,"Error in creating an URL object");
       }
       return url;
   }

   /*Making the Http Request*/
   private static String makeHttpRequest(URL url) throws IOException
   {
       String jsonResponse = "";
       if(url == null)
       {
           Log.e(TAG,"Json response is null due to null URL");
           return jsonResponse;}

       HttpURLConnection urlConnection = null;
       InputStream inputStream = null;
       try
       {
           urlConnection = (HttpURLConnection) url.openConnection();
           urlConnection.setConnectTimeout(15000);
           urlConnection.setReadTimeout(10000);
           urlConnection.setRequestMethod("GET");
           urlConnection.connect();
           if(urlConnection.getResponseCode() == 200)
           {
               inputStream = urlConnection.getInputStream();
               jsonResponse = readJsonResponse(inputStream);
           }else
           {
               Log.e(TAG,"Error in the UrlConnection " +urlConnection.getResponseCode());
           }
       }catch (IOException e){
           Log.e(TAG,"Problem retrieving the Json response");
       }finally {
           if(urlConnection != null) { urlConnection.disconnect();}
           if(inputStream != null){inputStream.close();}
       }
       return jsonResponse;
   }


   /*Converting the entire input stream of Json to String*/

   private static String readJsonResponse(InputStream inputStream) throws IOException
   {
       StringBuilder output = new StringBuilder();
       if(inputStream != null)
       {
           InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                   Charset.forName("UTF-8"));
           BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
           String line = bufferedReader.readLine();
           if(line != null)
           {
               output.append(line);
               line =bufferedReader.readLine();

           }
       }
       return output.toString();
   }

   private static List<PopularMovies> extractJsonResponse(String jsonResponse)
   {
       /*If string is empty or null*/
       if(TextUtils.isEmpty(jsonResponse))
       {
           Log.e(TAG,"PopularMovies Json is empty");
           return null;
       }

       List<PopularMovies> popularMoviesList = new ArrayList<>();
       try
       {
           JSONObject root = new JSONObject(jsonResponse);
           JSONArray results = root.getJSONArray("results");
           for(int i =0; i < results.length(); i++)
           {
            JSONObject currentResults = results.getJSONObject(i);
               String posterPath = POSTER_PATH +currentResults.getString("poster_path");
               String overVIew = currentResults.getString("overview");
               String releaseDate = currentResults.getString("release_date");
               String originalTitle = currentResults.getString("title");
               double voteAverage = currentResults.getDouble("vote_average");
               String backDropPath = POSTER_PATH+currentResults.getString("backdrop_path");

               popularMoviesList.add(new PopularMovies(originalTitle,posterPath,overVIew,voteAverage,releaseDate,backDropPath));
           }
       }catch (JSONException e){Log.e(TAG,"Error parsing the Json response");}
       return popularMoviesList;
   }
}
