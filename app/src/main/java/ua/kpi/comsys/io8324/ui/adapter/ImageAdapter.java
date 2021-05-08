package ua.kpi.comsys.io8324.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.image.ImageGrid;
import ua.kpi.comsys.io8324.ui.views.images.ImagesFragment;
import ua.kpi.comsys.io8324.utils.CustomGridLayout;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private static final String TAG = "ImageAdapter";
    private LayoutInflater inflater;
    private Activity activity;
    private ImageGrid imageGrid;
    private CustomGridLayout grid;

    public ImageAdapter(Context context, ImageGrid imageGrid, Activity activity){
        inflater = LayoutInflater.from(context);
        this.imageGrid = imageGrid;
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
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(
                        inflater.getContext().getContentResolver(),
                        gridCycle.getImageFromGrid(i)
                    );
                    ImagesFragment.setNoImageNotificationInvisible();
                    holder.imageViewGrid[i].setImageBitmap(bmp);
                    holder.imageViewGrid[i].setBackgroundColor(Color.GRAY);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        grid.getWidthDimension()[i],
                        grid.getHeightDimension()[i]
                    );
                    holder.imageViewGrid[i].setLayoutParams(layoutParams);
                    layoutParams.setMargins(1, 1, 0, 0);
                } catch (IOException e) {
                    Log.e(TAG, "cannot load image: "+e.getMessage());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return imageGrid.size();
    }
}
