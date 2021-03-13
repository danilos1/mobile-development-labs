package ua.kpi.comsys.io8324.entity.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {
    private String imdbID;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Poster")
    private String poster;

    public Movie() {
    }

    public Movie(String title, String year, String imdbID, String type, String poster) {
        this.imdbID = imdbID;
        this.title = title;
        this.year = year;
        this.type = type;
        this.poster = poster;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "imdbID='" + imdbID + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", type='" + type + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }
}
