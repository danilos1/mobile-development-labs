package ua.kpi.comsys.io8324.ui.views.images;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.ui.adapter.ImageAdapter;
import ua.kpi.comsys.io8324.entity.image.ImageGrid;

import static android.app.Activity.RESULT_OK;


public class ImagesFragment extends Fragment {
    private static final int GRID_RESOLUTION = 6;
    private static final int IMAGE_PICKER = 1;

    private RecyclerView imageRecycleView;
    private ImageAdapter imageAdapter;
    private FloatingActionButton imagePickerButton;
    private ImageGrid imageGrid;
    private static TextView noImageTextView;

    public static void setNoImageNotificationInvisible() {
        noImageTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);
        ImagesFragment.noImageTextView = view.findViewById(R.id.noImagesYetTextView);
        this.imageRecycleView = view.findViewById(R.id.imageRecycler);
        this.imagePickerButton = view.findViewById(R.id.imagePickerButton);
        this.imagePickerButton.setOnClickListener(
            v -> {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_PICKER);
            }
        );
        this.imageGrid = new ImageGrid(GRID_RESOLUTION);
        imageAdapter = new ImageAdapter(getContext(), imageGrid, getActivity());
        imageRecycleView.setAdapter(imageAdapter);
        imageRecycleView.invalidate();

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER  && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            imageGrid.addImage(data.getData());
            imageAdapter.notifyDataSetChanged();
        }
    }
}