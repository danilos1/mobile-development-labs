package ua.kpi.comsys.io8324;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import ua.kpi.comsys.io8324.tabfragments.DrawingFragment;
import ua.kpi.comsys.io8324.tabfragments.GeneralFragment;
import ua.kpi.comsys.io8324.tabfragments.MoviesFragment;
import ua.kpi.comsys.io8324.utils.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private GeneralFragment generalFragment;
    private DrawingFragment drawingFragment;
    private MoviesFragment moviesFragment;

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

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(), 0
        );
        viewPagerAdapter.addFragment(generalFragment, "General");
        viewPagerAdapter.addFragment(drawingFragment, "Charts");
        viewPagerAdapter.addFragment(moviesFragment, "Movies");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_house_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.statistics_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.movie_icon);


    }
}