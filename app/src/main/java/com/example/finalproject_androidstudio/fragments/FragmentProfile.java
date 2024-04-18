package com.example.finalproject_androidstudio.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalproject_androidstudio.R;
import com.example.finalproject_androidstudio.activities.MainActivity;
import com.example.finalproject_androidstudio.activities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {

    private FirebaseAuth mAuth;
    boolean isBabysitter = false;
    private TextInputEditText fullNameEditText, emailEditText, phoneNumberEditText, socialLinkEditText, salaryEditText, descriptionEditText;
    private TextInputLayout dateEditText, passwordEditText, rePasswordEditText, genderSpinner;
    private AutoCompleteTextView locationSpinner, experienceSpinner, kidsAgeSpinner;
//    private CheckBox babysitterCheckBox;
//    private LinearLayout babysitterForm;
    private Button updateButton, cancelButton, addBabysitter;
    CheckBox babysitterCheckBox;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        loadUserData();
        setupListeners();
    }

    private void initializeViews(View view) {
        fullNameEditText = view.findViewById(R.id.regEditTextFullName);
        emailEditText = view.findViewById(R.id.regEditTextEmailAddress);
        dateEditText = view.findViewById(R.id.regTextSelectedDate);
        dateEditText.setVisibility(View.GONE);

        passwordEditText = view.findViewById(R.id.regTextPassword);
        rePasswordEditText = view.findViewById(R.id.regTextRePassword);
        passwordEditText.setVisibility(View.GONE);
        rePasswordEditText.setVisibility(View.GONE);

        phoneNumberEditText = view.findViewById(R.id.regPhoneNumberEditText);
        locationSpinner = view.findViewById(R.id.regLocationSpinner);
        genderSpinner = view.findViewById(R.id.regGender);
        genderSpinner.setVisibility(View.GONE);

        babysitterCheckBox = view.findViewById(R.id.regBabysitterCheckBox);
        babysitterCheckBox.setVisibility(View.GONE);

//        babysitterForm = view.findViewById(R.id.regBabysitterForm);
        socialLinkEditText = view.findViewById(R.id.regEditTextSocialLink);
        salaryEditText = view.findViewById(R.id.regEditTextSalary);
        descriptionEditText = view.findViewById(R.id.regEditTextDescription);
        experienceSpinner = view.findViewById(R.id.regSpinnerExperience);
        kidsAgeSpinner = view.findViewById(R.id.regSpinnerKidsAge);

        updateButton = view.findViewById(R.id.updateButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        addBabysitter = view.findViewById(R.id.buttonAddBabysitter);
    }

    private void loadUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Reference to the user's data in Firebase
            DatabaseReference userRef = null;
//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            if (userEmail != null) {
                String sanitizedEmail = sanitizeEmail(userEmail);
                userRef = FirebaseDatabase.getInstance().getReference("users").child(sanitizedEmail);
                // Now use 'ref' to read or write data
            }
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Assuming user data has a specific structure. Adjust according to your database structure.
                        String fullName = dataSnapshot.child("fullName").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                        String location = dataSnapshot.child("location").getValue(String.class);

                        fullNameEditText.setText(fullName);
                        emailEditText.setText(email);
                        phoneNumberEditText.setText(phoneNumber);
                        locationSpinner.setText(location);

                        isBabysitter = dataSnapshot.child("whoAmI").getValue(User.WhoAmI.class) == User.WhoAmI.BABYSITTER ? true : false;
                        Log.d("WhoAmI", String.valueOf(isBabysitter));

                        if(isBabysitter) {
                            String experience = dataSnapshot.child("experience").getValue(String.class);
                            String kidsAgeRange = dataSnapshot.child("kidsAgeRange").getValue(String.class);
                            String socialLink = dataSnapshot.child("socialLink").getValue(String.class);
                            Long salary = dataSnapshot.child("salary").getValue(Long.class);
                            String description = dataSnapshot.child("description").getValue(String.class);

                            experienceSpinner.setVisibility(View.VISIBLE);
                            experienceSpinner.setText(experience);

                            kidsAgeSpinner.setVisibility(View.VISIBLE);
                            kidsAgeSpinner.setText(kidsAgeRange);

                            socialLinkEditText.setVisibility(View.VISIBLE);
                            socialLinkEditText.setText(socialLink);

                            salaryEditText.setVisibility(View.VISIBLE);
                            salaryEditText.setText(salary != null ? String.valueOf(salary) : null);

                            descriptionEditText.setVisibility(View.VISIBLE);
                            descriptionEditText.setText(description);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Failed to load user data: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "No user logged in.", Toast.LENGTH_LONG).show();
        }
    }

    private void setupListeners() {
        updateButton.setOnClickListener(v -> updateUserProfile(v));
        cancelButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_fragmentProfile_to_fragmentMain));
    }

    private void updateUserProfile(View view) {
        String email = sanitizeEmail(((TextInputEditText) view.findViewById(R.id.regEditTextEmailAddress)).getText().toString().trim());
        String fullName = ((TextInputEditText) view.findViewById(R.id.regEditTextFullName)).getText().toString().trim();
        String phoneNumber = ((TextInputEditText) view.findViewById(R.id.regPhoneNumberEditText)).getText().toString().trim();
        String location = ((AutoCompleteTextView) view.findViewById(R.id.regLocationSpinner)).getText().toString();

        // Additional fields if the user is a babysitter
        String experience = null;
        String kidsAgeRange = null;
        String socialLink = null;
        String salary = null;
        String description = null;

        if(isBabysitter){
            experience = ((AutoCompleteTextView) view.findViewById(R.id.regSpinnerExperience)).getText().toString();
            kidsAgeRange = ((AutoCompleteTextView) view.findViewById(R.id.regSpinnerKidsAge)).getText().toString();
            socialLink = ((TextInputEditText) view.findViewById(R.id.regEditTextSocialLink)).getText().toString().trim();
            salary = ((TextInputEditText) view.findViewById(R.id.regEditTextSalary)).getText().toString().trim();
            description = ((TextInputEditText) view.findViewById(R.id.regEditTextDescription)).getText().toString().trim();
        }

        // Create a user object or a map to update
        Map<String, Object> userData = new HashMap<>();
        userData.put("fullName", fullName);
        userData.put("phoneNumber", phoneNumber);
        userData.put("location", location);

        if (isBabysitter) {
            userData.put("experience", experience);
            userData.put("kidsAgeRange", kidsAgeRange);
            userData.put("socialLink", socialLink);
            userData.put("salary", salary);
            userData.put("description", description);
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(email).updateChildren(userData)
                .addOnSuccessListener(aVoid -> {
                    // Successfully updated the user
                    Toast.makeText(getContext(), "User updated successfully!", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    // Failed to update user
                    Toast.makeText(getContext(), "Failed to update user: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });

        Navigation.findNavController(view).navigate(R.id.action_fragmentProfile_to_fragmentMain);
    }

    private String sanitizeEmail(String email) {
        if (email == null) return null;
        return email.replace(".", ",");
    }

}