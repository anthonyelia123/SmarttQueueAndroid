package me.queue.smartqueue.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import me.queue.smartqueue.databinding.ActivityLoginBinding;
import me.queue.smartqueue.main.ui.activity.MainActivity;
import me.queue.smartqueue.signup.SignupActivity;
import me.queue.utils.LocalFunctions;

public class LoginActivity extends AppCompatActivity {

    //components
    private ActivityLoginBinding binding;

    private FirebaseAuth firebaseAuth;
    private LocalFunctions localFunctions = new LocalFunctions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize components by id
        firebaseAuth = FirebaseAuth.getInstance();
        binding.loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        Intent login = new Intent(LoginActivity.this, SignupActivity.class);
        binding.adminSignup.setOnClickListener(v -> {
            login.putExtra("role", "admin");
            startActivity(login);
        });
        binding.clientSignup.setOnClickListener(v -> {
            login.putExtra("role", "client");
            startActivity(login);
        });


    }


    // takes email and password and logins the user
    public void loginUser() {
        binding.progressbar.setVisibility(View.VISIBLE);

        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        // check if email is not empty
        if (TextUtils.isEmpty(email)) {
            localFunctions.errorToast(this,"Please enter email");
            binding.progressbar.setVisibility(View.GONE);
            return;
        }
        //check if password is not empty
        if (TextUtils.isEmpty(password)) {
            localFunctions.errorToast(this,"Please enter password");
            binding.progressbar.setVisibility(View.GONE);
            return;
        }


        //sign-in user with email and password
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //if successful => move to main activity
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                            binding.progressbar.setVisibility(View.GONE);
                        } else {
                            binding.progressbar.setVisibility(View.GONE);

                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e) {
                                // show error toast ot user ,user already exist

                            } catch (FirebaseNetworkException e) {
                                //show error tost network exception

                            } catch (Exception e) {
                                Log.e("log", e.getMessage());
                            }


                            // updateUI(null);
                        }


                    }
                });


    }

}