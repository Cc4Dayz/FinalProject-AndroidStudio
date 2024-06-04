package com.example.finalproject_androidstudio.activities;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject_androidstudio.R;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

    }
}