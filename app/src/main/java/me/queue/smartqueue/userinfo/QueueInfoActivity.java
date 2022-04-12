package me.queue.smartqueue.userinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.zip.Inflater;

import me.queue.smartqueue.R;
import me.queue.smartqueue.databinding.ActivityIntroBinding;
import me.queue.smartqueue.main.data.models.QueueModel;

public class QueueInfoActivity extends AppCompatActivity {
    private ActivityIntroBinding binding;
    private QueueModel queueModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        queueModel = (QueueModel) getIntent().getSerializableExtra("queue");
        if (queueModel != null) {


        }


    }
}