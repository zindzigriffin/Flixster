package models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel //annotation indicates class is parcelable

public class Movie {
    //create instance variables
    String backdropPath;
    String posterPaths;
    String title;
    String overview;
    String movieID;
    Double voteAverage;
    // no argument constructor required for Parceler
    public Movie(){}

    //create a constructor
    public Movie(JSONObject jsonObject) throws JSONException {
        //get the jsonObject of each string
        backdropPath = jsonObject.getString("backdrop_path");
        posterPaths = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        movieID = jsonObject.getString("id");
    }
    //This method is responsible for iterating through the Jsonarray and
    //constructing a movie for each element in the JsonArray
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        //iterate through the length of the array
        for(int i = 0; i<movieJsonArray.length(); i++){
            //adds a movie at each position in the Json Array
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        // return the list of movies
        return movies;

    }
    //getter method to get the poster paths
    public String getPosterPaths() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPaths);
    }
    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }
    //getter method to get the title of the movie
    public String getTitle() {
        return title;
    }
    //getter method to get the overview
    public String getOverview() {
        return overview;
    }
    public Double getVoteAverage() {
        return voteAverage;
    }
    //getter method for movieID
    public String getMovieID(){
        return movieID;
    }
}
