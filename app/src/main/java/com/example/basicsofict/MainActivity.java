package com.example.basicsofict;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
            loadFragment(new HomeFragment());
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
                loadFragment(new HomeFragment());
                navigationView.setCheckedItem(R.id.nav_home);
                return true;
            } else if (itemId == R.id.bottom_nav_chapters) {
                loadFragment(new ChaptersFragment());
                navigationView.setCheckedItem(R.id.nav_chapters);
                return true;
            } else if (itemId == R.id.bottom_nav_progress) {
                loadFragment(new ProgressFragment());
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
            loadFragment(new HomeFragment());
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
        } else if (itemId == R.id.nav_chapters) {
            loadFragment(new ChaptersFragment());
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_chapters);
        } else if (itemId == R.id.nav_progress) {
            loadFragment(new ProgressFragment());
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_progress);
        } else if (itemId == R.id.nav_activities) {
            loadFragment(new ActivitiesFragment());
        } else if (itemId == R.id.nav_settings) {
            loadFragment(new SettingsFragment());
        } else if (itemId == R.id.nav_help) {
            loadFragment(new HelpFragment());
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // CHANGE FROM PRIVATE TO PUBLIC
    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
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