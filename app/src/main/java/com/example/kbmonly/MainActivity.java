package com.example.kbmonly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.action_gamelist) {
                loadFragment(GamelistFragment.newInstance());
                return true;
            } else if (itemId == R.id.action_newslist) {
                loadFragment(NewslistFragment.newInstance());
                return true;
            } else if (itemId == R.id.action_profile) {
                loadFragment(ProfileFragment.newInstance());
                return true;
            }
//                case R.id.action_gamelist:
//                    loadFragment(GamelistFragment.newInstance());
//                    return true;
//                case R.id.action_newslist:
//                    loadFragment(NewslistFragment.newInstance());
//                    return true;
//                case R.id.action_profile:
//                    loadFragment(ProfileFragment.newInstance());
//                    return true;
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_view, fragment);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, new GamelistFragment())
                    .commit();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}