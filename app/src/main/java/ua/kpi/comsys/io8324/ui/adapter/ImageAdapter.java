package ua.kpi.comsys.io8324.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.image.ImageGrid;
import ua.kpi.comsys.io8324.entity.image.JSONImage;
import ua.kpi.comsys.io8324.http.image.ImageHttpRepository;
import ua.kpi.comsys.io8324.ui.views.images.ImagesFragment;
import ua.kpi.comsys.io8324.utils.CustomGridLayout;


@RequiresApi(api = Build.VERSION_CODES.N)
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private static final String TAG = "ImageAdapter";
    private static final int GRID_RESOLUTION = 6;

    private LayoutInflater inflater;
    private Activity activity;
    private static ImageGrid imageGrid = new ImageGrid(GRID_RESOLUTION);
    private CustomGridLayout grid;

    static {
        ImageHttpRepository imageHttpRepository = new ImageHttpRepository();
        List<Uri> images = imageHttpRepository.fetchImages()
                .stream()
                .map(JSONImage::getUri)
                .map(Uri::parse)
                .collect(Collectors.toList());

        imageGrid.addAll(images);
    }

    public void addImage(Uri image) {
        imageGrid.addImage(image);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ImageAdapter(Context context, Activity activity){
        inflater = LayoutInflater.from(context);
        this.activity = activity;
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView[] imageViewGrid;
        private LinearLayout[] linearLayoutGrid;

        public ImageView[] getImageViewGrid() {
            return imageViewGrid;
        }

        public LinearLayout[] getLinearLayoutGrid() {
            return linearLayoutGrid;
        }

        ViewHolder(View view){
            super(view);
            grid = new CustomGridLayout(imageGrid.getGridResolution(), activity, view);
            this.imageViewGrid = grid.getImageGrid();
            this.linearLayoutGrid = grid.getLinearLayoutGrid();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.image_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageGrid.GridCycle gridCycle = ImageGrid.images.get(position);

        for (int i = 0; i < holder.getImageViewGrid().length; i++) {
            if (gridCycle.getImageFromGrid(i) != null) {
                Picasso.get().load(gridCycle.getImageFromGrid(i))
                        .placeholder(R.drawable.image_animation)
                        .into(holder.imageViewGrid[i]);
                ImagesFragment.setNoImageNotificationInvisible();
                holder.imageViewGrid[i].setBackgroundColor(Color.GRAY);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    grid.getWidthDimension()[i],
                    grid.getHeightDimension()[i]
                );
                holder.imageViewGrid[i].setLayoutParams(layoutParams);
                layoutParams.setMargins(1, 1, 0, 0);
            }
        }
    }

    @Override
    public int getItemCount() {
        return imageGrid.size();
    }
}
