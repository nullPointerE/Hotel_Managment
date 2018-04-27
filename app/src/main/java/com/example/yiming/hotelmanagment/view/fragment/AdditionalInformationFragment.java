package com.example.yiming.hotelmanagment.view.fragment;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Customer;
import com.example.yiming.hotelmanagment.data.local.TasksLocalDataSource;
import com.example.yiming.hotelmanagment.data.local.TasksPersistenceContract;
import com.example.yiming.hotelmanagment.view.MainActivity;

public class AdditionalInformationFragment extends Fragment implements View.OnClickListener {
    private RadioButton bussinessRadioButton, leisureRadioButton;
    private RadioGroup radioGroup;
    private Button submitButton;
    private EditText commentEt;
    private CheckBox termsAndConditions;
    private Customer customer;
    private TasksLocalDataSource tasksLocalDataSource;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = (Customer) getArguments().getParcelable(Constants.GUEST_INFO_BUNDLE_KEY);
        tasksLocalDataSource = TasksLocalDataSource.getInstance(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.additional_information_fragment, null);
        bussinessRadioButton = view.findViewById(R.id.additional_info_bussiness_radioButton);
        leisureRadioButton = view.findViewById(R.id.additional_info_leisure_radioButton);
        submitButton = view.findViewById(R.id.additional_info_submitButton);
        commentEt = view.findViewById(R.id.additional_info_commentsET);
        termsAndConditions = view.findViewById(R.id.additional_info_terms_checkbox);
        radioGroup = view.findViewById(R.id.raidoGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.additional_info_bussiness_radioButton:
                        customer.setPurposeOfVisit(bussinessRadioButton.getText().toString());
                        break;
                    case R.id.additional_info_leisure_radioButton:
                        customer.setPurposeOfVisit(leisureRadioButton.getText().toString());
                        break;
                }
            }
        });
        submitButton.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view) {
        customer.setComments(commentEt.getText().toString());
        if (!termsAndConditions.isChecked()) {
            termsAndConditions.setTextColor(getResources().getColor(R.color.red));
        } else {
            tasksLocalDataSource.addCustomer(customer);
            for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {
                getFragmentManager().popBackStack();
            }
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }


}