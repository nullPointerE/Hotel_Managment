package com.example.yiming.hotelmanagment.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Customer;

public class PersonalInformationFragment extends Fragment implements View.OnClickListener {
private TextView firstName, middleName, lastName, emailAddress, emailAddressConfirmation;
private Spinner title, gender;
private CheckBox createNewAccount;
private Button continueButton;
private Customer customer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = new Customer();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_information_fragment, null);
        firstName = view.findViewById(R.id.personal_info_fnameET);
        middleName = view.findViewById(R.id.personal_info_middleNameET);
        lastName = view.findViewById(R.id.personal_info_lnameET);
        emailAddress = view.findViewById(R.id.personal_info_emailET);
        emailAddressConfirmation = view.findViewById(R.id.personal_info_emailET_confirmation);
        title = view.findViewById(R.id.personal_info_titleSpinner);

        ArrayAdapter<CharSequence> titleAdapter =  ArrayAdapter.createFromResource(getActivity(), R.array.title, android.R.layout.simple_spinner_item);
        titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                customer.setTitle(adapterView.getItemAtPosition(pos).toString());

            }
        });
        gender = view.findViewById(R.id.personal_info_genderSpinner);
        ArrayAdapter<CharSequence> genderAdapter =  ArrayAdapter.createFromResource(getActivity(), R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                customer.setGender(adapterView.getItemAtPosition(pos).toString());
            }
        });
        createNewAccount = view.findViewById(R.id.personal_info_createNewAccoutCB);
        continueButton = view.findViewById(R.id.personal_info_continue_button);
        continueButton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        //call Validations..
        customer.setFirstName(firstName.getText().toString());
        customer.setMiddleName(middleName.getText().toString());
        customer.setLastName(lastName.getText().toString());
        customer.setEmailAddress(emailAddress.getText().toString());
        customer.setCreateNewAccount(createNewAccount.isChecked());
        AddressInformationFragment addressInformationFragment = new AddressInformationFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.GUEST_INFO_BUNDLE_KEY, customer);
        addressInformationFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.guest_information_frameLayout, addressInformationFragment);
    }


}
