package com.example.yiming.hotelmanagment.view.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Room;
import com.example.yiming.hotelmanagment.data.local.TasksLocalDataSource;

public class AddRoomDialog extends DialogFragment {
    Button addRoom;
    EditText roomNumber;
    TextView price;
    Spinner beds;

    int Int_beds;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_room_dialogfragment, container, false);
        roomNumber = v.findViewById(R.id.roomNumber);
        addRoom = v.findViewById(R.id.addRoom_btn);
        price = v.findViewById(R.id.addprice);
        beds = v.findViewById(R.id.addbeds);
        String[] mlist=new String[]{"1","2","3","5"};
        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mlist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        beds.setAdapter(arrayAdapter);
        beds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Int_beds = Integer.valueOf((String) parent.getItemAtPosition(position));
                switch (Int_beds){
                    case 1:
                        price.setText(String.valueOf(Constants.PRRICE[0]));
                        break;
                    case 2:
                        price.setText(String.valueOf(Constants.PRRICE[1]));
                        break;
                    case 3:
                        price.setText(String.valueOf(Constants.PRRICE[2]));
                        break;
                    case 5:
                        price.setText(String.valueOf(Constants.PRRICE[3]));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                }
        });

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Room room = new Room();
                room.setBeds(Int_beds);
                room.setPrice(Double.valueOf(price.getText().toString()));
                room.setRoomNumber(Integer.valueOf(roomNumber.getText().toString()));
                TasksLocalDataSource.getInstance(getActivity()).addRoom(room);
                dismiss();
            }
        });
        return v;
    }
}
