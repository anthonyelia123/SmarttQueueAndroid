package me.queue.smartqueue.main.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import me.queue.smartqueue.R;
import me.queue.smartqueue.common.async.GetCurrentUser;
import me.queue.smartqueue.databinding.ActivityMainBinding;
import me.queue.smartqueue.login.LoginActivity;
import me.queue.smartqueue.main.ui.fragment.AddQueueFragment;
import me.queue.smartqueue.main.ui.fragment.QueuesFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    private FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        firebaseAuth = FirebaseAuth.getInstance();

        moveToFirst();

        binding.tabMain.addTab(binding.tabMain.newTab().setText("My Queues"));

        new GetCurrentUser(models -> {
           if (models.getType().equals("admin"))
               binding.tabMain.addTab(binding.tabMain.newTab().setText("AddQueue"));
        });
        binding.tabMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    moveToFirst();
                } else {
                    moveToSecond();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    private void moveToSecond() {
        Fragment newFragment = new AddQueueFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentView, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void moveToFirst() {
        Fragment newFragment = new QueuesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentView, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}