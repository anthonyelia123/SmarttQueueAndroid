package me.queue.smartqueue.signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import me.queue.smartqueue.main.ui.activity.MainActivity;
import me.queue.smartqueue.databinding.ActivitySignupBinding;
import me.queue.smartqueue.login.LoginActivity;
import me.queue.utils.LocalFunctions;

public class SignupActivity extends AppCompatActivity {
    //components
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fStore;
    private LocalFunctions localFunctions;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ActivitySignupBinding binding;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        role = getIntent().getStringExtra("role");
        binding.signupTitleTextView.setText("Signup - "+role);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        localFunctions = new LocalFunctions();
        createAuthStateListener();
        binding.signUpButton.setOnClickListener(v -> {
            if (binding.signUpNameEditText.getText().length() == 0 || binding.lastName.getText().length() == 0 || binding.signUpEmailEditText.getText().length() == 0
                    || binding.phone.getText().length() == 0 || binding.age.getText().length() == 0 || binding.signUpPasswordEditText.getText().length() == 0 ||
                    binding.signUpConfirmPasswordEditText.getText().length() == 0) {
                localFunctions.errorToast(this,"Please fill all the fields");

            } else {
                validateAndRegisterUser();
            }
        });
        binding.loginReturnTextView.setOnClickListener(v -> {
            Intent i2 = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(i2);
            finish();
        });
    }

    private void createAuthStateListener() {
        firebaseAuthListener = firebaseAuth -> {
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        };
    }

    //remove firebase listener when we stop the app
    @Override
    public void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    //validate all field and add a client to the database
    public void validateAndRegisterUser() {

        //check if first name is not empty
        if (binding.signUpNameEditText.getText().equals("")) {
            binding.signUpNameEditText.setError("First name is required");
            binding.signUpNameEditText.requestFocus();
            return;
        }
        //check if last name is not empty
        if (binding.lastName.getText().equals("")) {
            binding.lastName.setError("Last name is required");
            binding.lastName.requestFocus();
            return;
        }
        //check if email is not empty
        if (binding.signUpEmailEditText.getText().equals("")) {
            binding.signUpEmailEditText.setError("Email is required");
            binding.signUpEmailEditText.requestFocus();
            return;
        }
        //check if phone is not empty
        if (binding.phone.getText().equals("")) {
            binding.phone.setError("Phone number is required");
            binding.phone.requestFocus();
            return;
        }
        //check if age is not empty
        if (binding.age.getText().equals("")) {
            binding.age.setError("Age is required");
            binding.age.requestFocus();
            return;
        }
        //check if password and confirmPass are not empty
        if (binding.signUpPasswordEditText.getText().equals("") || binding.signUpPasswordEditText.getText().equals("")) {
            binding.signUpPasswordEditText.setError("Password is required");
            binding.signUpPasswordEditText.requestFocus();
            return;
        }
        //check if password and confirmPass are equal
        if (!binding.signUpPasswordEditText.getText().toString().equals(binding.signUpConfirmPasswordEditText.getText().toString())) {
            Log.d("log", "password: " + binding.signUpPasswordEditText.getText().toString());
            Log.d("log", "password confirm: " + binding.signUpConfirmPasswordEditText.getText().toString());

            binding.signUpConfirmPasswordEditText.setError("Password does not match");
            binding.signUpConfirmPasswordEditText.requestFocus();
            return;
        }

        //show progress bar
        binding.progressbar.setVisibility(View.VISIBLE);
        //create user with email and password into firebase
        firebaseAuth.createUserWithEmailAndPassword(binding.signUpEmailEditText.getText().toString().trim(), binding.signUpPasswordEditText.getText().toString().trim()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser userID = FirebaseAuth.getInstance().getCurrentUser();
                assert userID != null;
                DocumentReference documentReference = fStore.collection("users").document(userID.getUid());
                Map<String, Object> user = new HashMap<>();
                user.put("firstname", localFunctions.getTrimmedText(binding.signUpNameEditText));
                user.put("lastname", localFunctions.getTrimmedText(binding.lastName));
                user.put("email", localFunctions.getTrimmedText(binding.signUpEmailEditText));
                user.put("password", localFunctions.getTrimmedText(binding.signUpPasswordEditText));
                user.put("age", localFunctions.getTrimmedText(binding.age));
                user.put("mobile", localFunctions.getTrimmedText(binding.phone));
                user.put("type", role);
                documentReference.set(user).addOnSuccessListener(aVoid ->
                        Log.d("TAG", "onSuccess: user profile is created"));

                startActivity(new Intent(SignupActivity.this, MainActivity.class));


            } else {
                localFunctions.errorToast(getParent(),"Error! "+task.getException().getMessage());
            }
            //hide the progress bar
            binding.progressbar.setVisibility(View.GONE);
        });

    }
}