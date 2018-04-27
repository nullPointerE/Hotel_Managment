package com.example.yiming.hotelmanagment.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class AddressInformationFragment extends Fragment implements View.OnClickListener {
private TextView companyName, city, postalCode, dayTimePhone, mobilePhone, address;
private Spinner country;
private Button continueButton;
private Customer customer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = (Customer) getArguments().getParcelable(Constants.GUEST_INFO_BUNDLE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_information_fragment, null);
        companyName = view.findViewById(R.id.address_info_companyNameET);
        city = view.findViewById(R.id.address_info_cityET);
        postalCode = view.findViewById(R.id.address_info_postalCodeET);
        dayTimePhone = view.findViewById(R.id.address_info_datTimePhoneEt);
        mobilePhone = view.findViewById(R.id.address_info_mobilePhoneET);
        address = view.findViewById(R.id.address_info_addressET);
        country = view.findViewById(R.id.address_info_countrySpinner);
        continueButton = view.findViewById(R.id.address_info_continue_button);
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(countryAdapter);
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customer.setCountry(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        continueButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        //callValidations
        customer.setCompanyName(companyName.getText().toString());
        customer.setAddress(address.getText().toString());
        customer.setCity(city.getText().toString());
        customer.setPostalCode(postalCode.getText().toString());
        customer.setDaytimePhone(dayTimePhone.getText().toString());
        customer.setMobilePhone(mobilePhone.getText().toString());
        AdditionalInformationFragment additionalInformationFragment = new AdditionalInformationFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.GUEST_INFO_BUNDLE_KEY, (Parcelable) customer);
        additionalInformationFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.guest_information_frameLayout, additionalInformationFragment).commit();
    }
}
