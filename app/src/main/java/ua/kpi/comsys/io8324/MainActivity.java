package ua.kpi.comsys.io8324;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import ua.kpi.comsys.io8324.tabfragments.ExampleFragment;
import ua.kpi.comsys.io8324.tabfragments.GeneralFragment;
import ua.kpi.comsys.io8324.utils.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private GeneralFragment generalFragment;
    private ExampleFragment exampleFragment;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        generalFragment = new GeneralFragment();
        exampleFragment = new ExampleFragment();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(generalFragment, "General");
        viewPagerAdapter.addFragment(exampleFragment, "Example Tab");

        viewPager.setAdapter(viewPagerAdapter);
    }
}