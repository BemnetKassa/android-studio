package com.example.basicsofict;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.example.basicsofict.fragments.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupNavigation();

        // Load default fragment
        if (savedInstanceState == null) {
            // Pass a tag to the initial fragment as well
            loadFragment(new HomeFragment(), HomeFragment.class.getName());
            navigationView.setCheckedItem(R.id.nav_home);
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
        }
    }

    private void initializeViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set up navigation icon
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setupNavigation() {
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_nav_home) {
                loadFragment(new HomeFragment(), HomeFragment.class.getName());
                navigationView.setCheckedItem(R.id.nav_home);
                return true;
            } else if (itemId == R.id.bottom_nav_chapters) {
                loadFragment(new ChaptersFragment(), ChaptersFragment.class.getName());
                navigationView.setCheckedItem(R.id.nav_chapters);
                return true;
            } else if (itemId == R.id.bottom_nav_progress) {
                // *** FIX: Use the class name as the tag when loading ProgressFragment ***
                loadFragment(new ProgressFragment(), ProgressFragment.class.getName());
                navigationView.setCheckedItem(R.id.nav_progress);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            loadFragment(new HomeFragment(), HomeFragment.class.getName());
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
        } else if (itemId == R.id.nav_chapters) {
            loadFragment(new ChaptersFragment(), ChaptersFragment.class.getName());
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_chapters);
        } else if (itemId == R.id.nav_progress) {
            loadFragment(new ProgressFragment(), ProgressFragment.class.getName());
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_progress);
        } else if (itemId == R.id.nav_activities) {
            loadFragment(new ActivitiesFragment(), ActivitiesFragment.class.getName());
        } else if (itemId == R.id.nav_settings) {
            loadFragment(new SettingsFragment(), SettingsFragment.class.getName());
        } else if (itemId == R.id.nav_help) {
            loadFragment(new HelpFragment(), HelpFragment.class.getName());
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * This is the callback method for LessonDetailFragment to call when a lesson is completed.
     * It finds the ProgressFragment and tells it to update its UI.
     */
    public void onLessonCompleted() {
        // *** FIX: Find the ProgressFragment using its class name as the tag ***
        ProgressFragment progressFragment = (ProgressFragment) getSupportFragmentManager()
                .findFragmentByTag(ProgressFragment.class.getName());

        if (progressFragment != null && progressFragment.isAdded()) {
            // Call the public method on the fragment to refresh it
            progressFragment.updateProgress();
        }
    }

    /**
     * Overloaded method to handle fragments without a specific tag for backward compatibility.
     */
    public void loadFragment(Fragment fragment) {
        loadFragment(fragment, null); // Call the main method with a null tag
    }

    /**
     * Main method to load fragments, now with tag support.
     * @param fragment The fragment to load.
     * @param tag A unique string to identify the fragment.
     */
    public void loadFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment, tag) // Use the provided tag
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
