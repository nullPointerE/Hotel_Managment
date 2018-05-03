package com.example.yiming.hotelmanagment.view.fragment;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Customer;
import com.malinkang.rxvalidator.RxValidator;
import com.malinkang.rxvalidator.ValidationFail;
import com.malinkang.rxvalidator.ValidationResult;
import com.malinkang.rxvalidator.annotations.NotEmpty;
import com.malinkang.rxvalidator.annotations.RegExp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscription;
import rx.functions.Action1;

public class PersonalInformationFragment extends Fragment implements View.OnClickListener {
    @NotEmpty(order = 1, message = "FirstName is Empty")
    EditText firstName;
    TextInputLayout firstNameTextInputLayout;

     EditText middleName;

    @NotEmpty(order = 2, message = "FirstName is Empty")
    EditText lastName;
    TextInputLayout lastNameTextInputLayout;

    @NotEmpty(order = 3, message = "Email is empty")
    @RegExp(order = 4, message = "Invalid Email", regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    EditText emailAddress;
    TextInputLayout emailTextInputLayout;

    EditText emailAddressConfirmation;
    private Spinner title, gender;
    private CheckBox createNewAccount;
    private Button continueButton;
    private Customer customer;
    private Subscription subscription;
    private boolean isValid;
    Map<EditText, TextInputLayout> inputLayoutMap = new HashMap<>();

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
        firstNameTextInputLayout = view.findViewById(R.id.personal_info_fname_inputLayout);

        middleName = view.findViewById(R.id.personal_info_middleNameET);

        lastName = view.findViewById(R.id.personal_info_lnameET);
        lastNameTextInputLayout = view.findViewById(R.id.personal_info_lname_inputLayout);

        emailAddress = view.findViewById(R.id.personal_info_emailET);
        emailTextInputLayout = view.findViewById(R.id.personal_info_email_inputLayout);

        emailAddressConfirmation = view.findViewById(R.id.personal_info_emailET_confirmation);
        title = view.findViewById(R.id.personal_info_titleSpinner);

        ArrayAdapter<CharSequence> titleAdapter =  ArrayAdapter.createFromResource(getActivity(), R.array.title, android.R.layout.simple_spinner_item);
        titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        title.setAdapter(titleAdapter);
        title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customer.setTitle(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gender = view.findViewById(R.id.personal_info_genderSpinner);
        ArrayAdapter<CharSequence> genderAdapter =  ArrayAdapter.createFromResource(getActivity(), R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                customer.setGender(adapterView.getItemAtPosition(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });
        createNewAccount = view.findViewById(R.id.personal_info_createNewAccoutCB);
        continueButton = view.findViewById(R.id.personal_info_continue_button);
        inputLayoutMap.put(firstName, firstNameTextInputLayout);
        inputLayoutMap.put(lastName, lastNameTextInputLayout);
        inputLayoutMap.put(emailAddress, emailTextInputLayout);
        continueButton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        //call Validations..
       if(subscription == null || subscription.isUnsubscribed()){
           subscription = RxValidator.validate(this).subscribe(new Action1<ValidationResult>() {
               @Override
               public void call(ValidationResult validationResult) {
                   isValid = validationResult.isValid;
                   for (EditText editText : inputLayoutMap.keySet()) {
                       TextInputLayout textInputLayout = inputLayoutMap.get(editText);
                       textInputLayout.setErrorEnabled(false);
                   }
                   if (!validationResult.isValid) {
                       ArrayList<ValidationFail> errors = validationResult.getFails();
                       for (ValidationFail fail : errors) {
                           TextInputLayout textInputLayout = inputLayoutMap.get(fail.getView());
                           textInputLayout.setErrorEnabled(true);
                           textInputLayout.setError(fail.getMessage());
                       }
                   }
               }
           });
       }

       if(isValid) {
           customer.setFirstName(firstName.getText().toString());
           customer.setMiddleName(middleName.getText().toString());
           customer.setLastName(lastName.getText().toString());
           customer.setEmailAddress(emailAddress.getText().toString());
           customer.setCreateNewAccount(createNewAccount.isChecked());
           AddressInformationFragment addressInformationFragment = new AddressInformationFragment();
           Bundle bundle = new Bundle();
           bundle.putParcelable(Constants.GUEST_INFO_BUNDLE_KEY, (Parcelable) customer);
           addressInformationFragment.setArguments(bundle);
           getFragmentManager().beginTransaction().replace(R.id.guest_information_frameLayout, addressInformationFragment).commit();
       }


    }


}