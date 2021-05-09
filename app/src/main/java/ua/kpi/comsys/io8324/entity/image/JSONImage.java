package ua.kpi.comsys.io8324.entity.image;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class JSONImage implements Serializable {
    @JsonProperty("previewURL")
    private String uri;

    public JSONImage(String uri) {
        this.uri = uri;
    }

    public JSONImage() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return uri;
    }
}
