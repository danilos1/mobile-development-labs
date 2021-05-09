package ua.kpi.comsys.io8324.entity.image;

import android.net.Uri;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ImageResults {

    private List<JSONImage> images;

    public ImageResults(List<JSONImage> images) {
        this.images = images;
    }

    public ImageResults() {
    }

    @JsonProperty("hits")
    public List<JSONImage> getImages() {
        return images;
    }

    public void setImages(List<JSONImage> images) {
        this.images = images;
    }
}
