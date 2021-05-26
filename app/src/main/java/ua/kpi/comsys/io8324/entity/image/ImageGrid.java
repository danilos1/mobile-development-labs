package ua.kpi.comsys.io8324.entity.image;

import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ImageGrid {
    private int N;
    public static Stack<GridCycle> images = new Stack<>();

    {
        images.push(new GridCycle(N));
    }

    public int getGridResolution() {
        return N;
    }

    public static class GridCycle {
        private List<Uri> images;

        public GridCycle(int N){
            images = new ArrayList<>(N);
        }

        public void addImageToGrid(Uri image){
            images.add(image);
        }

        public Uri getImageFromGrid(int index){
            return index < images.size() ? images.get(index) : null;
        }

        public int size(){
            return images.size();
        }
    }

    public ImageGrid(int N){
        this.N = N;
    }

    public boolean isGridCycleFull() {
        return images.peek().size() == N;
    }

    public void addImage(Uri image){
        if (isGridCycleFull()) {
            images.push(new GridCycle(N));
        }

        images.peek().addImageToGrid(image);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addAll(List<Uri> imageUris) {
        imageUris.forEach(this::addImage);
    }

    public int size() {
        return images.size();
    }
}
