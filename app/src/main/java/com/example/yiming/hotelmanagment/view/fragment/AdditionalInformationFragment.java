package com.example.yiming.hotelmanagment.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Customer;

public class AdditionalInformationFragment extends Fragment implements View.OnClickListener {
private RadioButton bussinessRadioButton, leisureRadioButton;
private Button submitButton;
private EditText commentEt;
private CheckBox termsAndConditions;
private Customer customer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = (Customer) getArguments().getParcelable(Constants.GUEST_INFO_BUNDLE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.additional_information_fragment, null );
        bussinessRadioButton = view.findViewById(R.id.additional_info_bussiness_radioButton);
        leisureRadioButton = view.findViewById(R.id.additional_info_leisure_radioButton);
        submitButton = view.findViewById(R.id.additional_info_submitButton);
        commentEt = view.findViewById(R.id.additional_info_commentsET);
        termsAndConditions = view.findViewById(R.id.additional_info_terms_checkbox);
        submitButton.setOnClickListener(this);
        return view;
    }
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.additional_info_bussiness_radioButton:
                if (checked)
                    customer.setPurposeOfVisit(((RadioButton) view).getText().toString());
                    break;
            case R.id.additional_info_leisure_radioButton:
                if (checked)
                    customer.setPurposeOfVisit(((RadioButton) view).getText().toString());
                    break;
        }
    }
    @Override
    public void onClick(View view) {
    customer.setComments(commentEt.getText().toString());
    if(!termsAndConditions.isChecked()){
        termsAndConditions.setTextColor(getResources().getColor(R.color.red));
    }
    else {
        
    }
    }


}
