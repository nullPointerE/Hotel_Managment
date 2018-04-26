package com.example.yiming.hotelmanagment.view.fragment;

import android.app.Fragment;
import android.content.ContentValues;
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

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Customer;
import com.example.yiming.hotelmanagment.data.local.TasksDbHelper;
import com.example.yiming.hotelmanagment.data.local.TasksPersistenceContract;

public class AdditionalInformationFragment extends Fragment implements View.OnClickListener {
private RadioButton bussinessRadioButton, leisureRadioButton;
private Button submitButton;
private EditText commentEt;
private CheckBox termsAndConditions;
private Customer customer;
private SQLiteDatabase sqLiteDatabase;
private TasksDbHelper tasksDbHelper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = (Customer) getArguments().getParcelable(Constants.GUEST_INFO_BUNDLE_KEY);
        tasksDbHelper = new TasksDbHelper(getActivity());
        sqLiteDatabase = tasksDbHelper.getWritableDatabase();
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
        ContentValues values=new ContentValues();
        values.put(TasksPersistenceContract.CustomerTable.TITLE, customer.getTitle());
        values.put(TasksPersistenceContract.CustomerTable.firstName, customer.getFirstName());
        values.put(TasksPersistenceContract.CustomerTable.lastName, customer.getLastName());
        values.put(TasksPersistenceContract.CustomerTable.EMAILADDRESS, customer.getEmailAddress());
        values.put(TasksPersistenceContract.CustomerTable.GENDER, customer.getGender());
        values.put(TasksPersistenceContract.CustomerTable.COMPANYNAME, customer.getCompanyName());
        values.put(TasksPersistenceContract.CustomerTable.ADDRESS, customer.getAddress());
        values.put(TasksPersistenceContract.CustomerTable.CITY, customer.getCity());
        values.put(TasksPersistenceContract.CustomerTable.POSTALCODE, customer.getPostalCode());
        values.put(TasksPersistenceContract.CustomerTable.COUNTRY, customer.getCountry());
        values.put(TasksPersistenceContract.CustomerTable.phoneNumber, customer.getMobilePhone());
        values.put(TasksPersistenceContract.CustomerTable.PURPOSEOFVISIT, customer.getPurposeOfVisit());
        values.put(TasksPersistenceContract.CustomerTable.COMMENTS, customer.getComments());
        sqLiteDatabase.insert(TasksPersistenceContract.CustomerTable.TABLE_NAME, null, values);
    }
    }


}
