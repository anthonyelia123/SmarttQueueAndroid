package me.queue.smartqueue.createeditqueue;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import me.queue.smartqueue.common.async.GetFieldsAsync;
import me.queue.smartqueue.common.async.SetQueueAsync;
import me.queue.smartqueue.databinding.ActivityCreateEditBinding;
import me.queue.smartqueue.main.data.models.QueueModel;
import me.queue.utils.LocalFunctions;

public class CreateEditActivity extends AppCompatActivity {

    private ActivityCreateEditBinding binding;

    private boolean isFinished;
    private String location;
    private QueueModel queueModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initView();

        queueModel = (QueueModel) getIntent().getSerializableExtra("queue");
        if (queueModel != null) {
            binding.etQueueName.setText(queueModel.getQueueName());
            binding.etMaxQty.setText(queueModel.getMaxSize());
            binding.tvLocation.setText(queueModel.getLocation());
            binding.spFields.setText(queueModel.getField());
            binding.etlambda.setText(queueModel.getLambda());
            binding.etMue.setText(queueModel.getMue());
            binding.etNbCounter.setText(queueModel.getLocation());
            binding.etMaxQty.setText(queueModel.getMaxSize());
            binding.etNbCounter.setText(queueModel.getCounter());
        } else {
            Log.e("q", "empty");
        }

    }

    private void initView() {
        new GetFieldsAsync(fields -> {
            ArrayList<String> allFields;
            allFields = new ArrayList<>(fields);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allFields);
            binding.spFields.setAdapter(adapter);
        });
        binding.btnSave.setOnClickListener(v -> {
            checkAllFields();
        });
        View.OnClickListener listener = v -> {
            //start activity for result
            startActivity(new Intent(this, MapsActivity.class));
        };

        binding.textField6.setOnClickListener(listener);
        binding.tvLocation.setOnClickListener(listener);
        binding.tvLocation.setOnClickListener(listener);
    }

    private void checkAllFields() {
        boolean status1 = true;
        boolean status2 = true;
        boolean status3 = true;
        boolean status4 = true;
        boolean status5 = true;
        boolean status6 = true;
        boolean status7 = true;

        if (TextUtils.isEmpty(binding.etQueueName.getText())) {
            Toast("Please fill Queue Name");
            status1 = false;
        }
        if (TextUtils.isEmpty(binding.etMaxQty.getText())) {
            Toast("Please fill Max Capacity");
            status2 = false;
        }
        if (TextUtils.isEmpty(binding.tvLocation.getText())) {
            Toast("Please fill Your location");
            status3 = false;
        }
        if (TextUtils.isEmpty(binding.etlambda.getText())) {
            Toast("Please a lambda value");
            status4 = false;
        }
        if (TextUtils.isEmpty(binding.etMue.getText())) {
            Toast("Please Mue value");
            status5 = false;
        }
        if (TextUtils.isEmpty(binding.etNbCounter.getText())) {
            Toast("Please enter counter number");
            status6 = false;
        }
        if (TextUtils.isEmpty(binding.spFields.getText())) {
            Toast("Please enter a field");
            status7 = false;
        }
        if (status1 && status2 && status3 && status4 && status5 && status6 && status7) {
            LocalFunctions localFunctions = new LocalFunctions();
            Map<String, Object> fieldsData = new HashMap<>();
            if (queueModel != null) {
                fieldsData.put("queueName", localFunctions.getTrimmedText(binding.etQueueName));
                fieldsData.put("isFinished", String.valueOf(queueModel.isFinished()));
                fieldsData.put("createdAt", queueModel.getCreatedAt());
                fieldsData.put("endedAt", queueModel.getEndedAt());
                fieldsData.put("queueId", queueModel.getQueueId());
                fieldsData.put("ownerId", queueModel.getOwnerId());
                fieldsData.put("maxSize", localFunctions.getTrimmedText(binding.etMaxQty));
                fieldsData.put("mue", localFunctions.getTrimmedText(binding.etMue));
                fieldsData.put("lambda", localFunctions.getTrimmedText(binding.etlambda));
                fieldsData.put("location", location);
                fieldsData.put("field", queueModel.getField());
                fieldsData.put("counter", String.valueOf(binding.etNbCounter.getText()));
                fieldsData.put("joiningId", "");
                new SetQueueAsync(fieldsData,queueModel.getQueueId(), success -> {
                    if (success) {
                        Toasty.success(this, "Success! Queue Added/Edited Successfully", Toasty.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toasty.error(this, "Error! something went wrong", Toasty.LENGTH_SHORT).show();
                });
            } else {
                String UUIDStr = UUID.randomUUID().toString();
                fieldsData.put("queueName", localFunctions.getTrimmedText(binding.etQueueName));
                fieldsData.put("isFinished", String.valueOf(getIntent().getBooleanExtra("isFinished", false)));
                fieldsData.put("createdAt", LocalDateTime.now().toString());
                fieldsData.put("endedAt", "");
                fieldsData.put("queueId", UUIDStr);
                fieldsData.put("ownerId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                fieldsData.put("maxSize", localFunctions.getTrimmedText(binding.etMaxQty));
                fieldsData.put("mue", localFunctions.getTrimmedText(binding.etMue));
                fieldsData.put("lambda", localFunctions.getTrimmedText(binding.etlambda));
                fieldsData.put("location", location);
                fieldsData.put("field", binding.spFields.getText().toString());
                fieldsData.put("counter", String.valueOf(binding.etNbCounter.getText()));
                fieldsData.put("joiningId", "");
                new SetQueueAsync(fieldsData,UUIDStr, success -> {
                    if (success) {
                        Toasty.success(this, "Success! Queue Added/Edited Successfully", Toasty.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toasty.error(this, "Error! something went wrong", Toasty.LENGTH_SHORT).show();
                });
            }

        }
    }

    private void Toast(String message) {
        Toasty.error(this, message, Toasty.LENGTH_SHORT).show();
    }
}