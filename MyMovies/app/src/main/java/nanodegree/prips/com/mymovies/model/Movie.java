package nanodegree.prips.com.mymovies.model;

import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pseth on 8/28/15.
 */
public class Movie {
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";

    @SerializedName("id")
    String id;

    @SerializedName("original_title")
    String title;

    @SerializedName("release_date")
    String releaseDate;

    @SerializedName("overview")
    String overview;

    @SerializedName("poster_path")
    String posterURL;

    @SerializedName("vote_average")
    String rating;

    @SerializedName("genre_ids")
    List<String> genres;

    @SerializedName("backdrop_path")
    String backdrop;

    @SerializedName("popularity")
    String popularity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterURL(String size) {
        String posterUrl = POSTER_BASE_URL.concat(size).concat(posterURL);
        Log.d("Poster URL", posterUrl);
        return posterUrl;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", overview='" + overview + '\'' +
                ", posterURL='" + posterURL + '\'' +
                ", rating='" + rating + '\'' +
                ", genres=" + genres +
                ", backdrop='" + backdrop + '\'' +
                ", popularity='" + popularity + '\'' +
                '}';
    }

    public interface PosterSize {
        String W92 = "w92";
        String W154 = "w154";
        String W185 = "w185";
        String W342 = "w342";
        String W500 = "w500";
        String W780 = "w780";
        String ORIGINAL = "original";
    }
}
