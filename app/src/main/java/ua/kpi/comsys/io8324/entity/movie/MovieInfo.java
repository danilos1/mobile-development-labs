package ua.kpi.comsys.io8324.entity.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class MovieInfo implements Serializable {
    @JsonProperty("imdbID")
    private String imdbID;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Writer")
    private String writer;

    @JsonProperty("Actors")
    private String actors;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Language")
    private String lang;

    @JsonProperty("Production")
    private String prod;

    @JsonProperty("Released")
    private String released;

    @JsonProperty("Runtime")
    private String runtime;

    @JsonProperty("Awards")
    private String awards;

    @JsonProperty("Rated")
    private String rating;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("Poster")
    private String poster;

    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("imdbVotes")
    private String imdbVotes;

    @JsonIgnore
    private MovieInfoRatings ratings;


    public MovieInfo() {
    }

    public MovieInfo(String imdbID, String type, String title, String year, String genre,
                     String director, String writer, String actors, String country, String lang,
                     String prod, String released, String runtime, String awards, String rating,
                     String plot, String poster, String imdbRating, String imdbVotes) {
        this.imdbID = imdbID;
        this.type = type;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.country = country;
        this.lang = lang;
        this.prod = prod;
        this.released = released;
        this.runtime = runtime;
        this.awards = awards;
        this.rating = rating;
        this.plot = plot;
        this.poster = poster;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
    }

    public String getWriter() {
        return writer;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getCountry() {
        return country;
    }

    public String getLang() {
        return lang;
    }

    public String getProd() {
        return prod;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getAwards() {
        return awards;
    }

    public String getRating() {
        return rating;
    }

    public String getPlot() {
        return plot;
    }

    public String getPoster() {
        return poster;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    @Override
    public String toString() {
        return "MovieInfo{" +
                "imdbID='" + imdbID + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", writer='" + writer + '\'' +
                ", actors='" + actors + '\'' +
                ", country='" + country + '\'' +
                ", lang='" + lang + '\'' +
                ", prod='" + prod + '\'' +
                ", released='" + released + '\'' +
                ", runtime='" + runtime + '\'' +
                ", awards='" + awards + '\'' +
                ", rating='" + rating + '\'' +
                ", plot='" + plot + '\'' +
                ", poster='" + poster + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                ", imdbVotes='" + imdbVotes + '\'' +
                '}';
    }

    private class MovieInfoRatings {
        private class Ratings {
            private String source;
            private String value;

            public Ratings() {
            }

            public Ratings(String source, String value) {
                this.source = source;
                this.value = value;
            }

            @JsonProperty("Source")
            public String getSource() {
                return source;
            }

            @JsonProperty("Value")
            public String getValue() {
                return value;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
        private Ratings ratings;

        public Ratings getRatings() {
            return ratings;
        }
    }
}
