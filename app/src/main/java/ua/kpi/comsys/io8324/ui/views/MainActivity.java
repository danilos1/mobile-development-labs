package ua.kpi.comsys.io8324.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.ui.adapter.ViewPagerAdapter;
import ua.kpi.comsys.io8324.ui.views.drawing.DrawingFragment;
import ua.kpi.comsys.io8324.ui.views.general.GeneralFragment;
import ua.kpi.comsys.io8324.ui.views.images.ImagesFragment;
import ua.kpi.comsys.io8324.ui.views.movie.MoviesFragment;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private GeneralFragment generalFragment;
    private DrawingFragment drawingFragment;
    private MoviesFragment moviesFragment;
    private ImagesFragment imagesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        generalFragment = new GeneralFragment();
        drawingFragment = new DrawingFragment();
        moviesFragment = new MoviesFragment();
        imagesFragment = new ImagesFragment();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(), 0
        );
        viewPagerAdapter.addFragment(generalFragment, "General");
        viewPagerAdapter.addFragment(drawingFragment, "Charts");
        viewPagerAdapter.addFragment(moviesFragment, "Movies");
        viewPagerAdapter.addFragment(imagesFragment, "Image list");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_house_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.statistics_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.movie_icon);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_image_24);
    }
}