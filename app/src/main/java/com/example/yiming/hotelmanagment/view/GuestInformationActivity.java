package com.example.yiming.hotelmanagment.view;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.view.fragment.PersonalInformationFragment;

public class GuestInformationActivity extends AppCompatActivity {
private PersonalInformationFragment personalInformationFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_information);
        personalInformationFragment = new PersonalInformationFragment();
        getFragmentManager().beginTransaction().replace(R.id.guest_information_frameLayout, personalInformationFragment)
                .addToBackStack("GuestInfo")
                .commit();
    }
}
