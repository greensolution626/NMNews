package com.android.nmnewsagency.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.LocationAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentCity extends Fragment {
    View view;
    static int state;
    static String country;
    RecyclerView recyclerView;
    LocationAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    List<String> arrayList1;
    TextView txt_loc_state, txt_loc_country;
    ImageView img_closestate, img_closecount,iamge_back_city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_select_city, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            country = bundle.getString("country");
            state = bundle.getInt("state");
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_loc);
        txt_loc_state = (TextView) view.findViewById(R.id.txt_loc_state);
        txt_loc_country = (TextView) view.findViewById(R.id.txt_loc_country);
        img_closestate = (ImageView) view.findViewById(R.id.img_closestate);
        img_closecount = (ImageView) view.findViewById(R.id.img_closecount);
        iamge_back_city = (ImageView) view.findViewById(R.id.iamge_back_city);
        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
        inItListData(state);
        img_closecount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        });
        img_closestate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        });iamge_back_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        });
        return view;
    }

    private void inItListData(int state) {
        if (state == 13) {
            txt_loc_state.setText("Maharashtra");
        } else {
            txt_loc_state.setText("Rajasthan");
        }
        txt_loc_country.setText(country);

        if (state == 19) {
            arrayList.add("Ajmer");
            arrayList.add("Alwer");
            arrayList.add("Bikaner");
            arrayList.add("Barmer");
            arrayList.add("Jaipur");/*4*/
            arrayList.add("Bundi");
            arrayList.add("Churu");
            arrayList.add("Dausa");
            arrayList.add("Ganganagar");
            arrayList.add("Hanumangarh");
            arrayList.add("Jaisalmer");
            arrayList.add("Jodhpur");
            arrayList.add("Karauli");
            arrayList.add("Kota");
            arrayList.add("Mount Abu");
            arrayList.add("Nagaur");
            arrayList.add("Nawalgarh");
            arrayList.add("Bharatpur");
            arrayList.add("Baran");
            arrayList.add("Bhilwara");
            arrayList.add("Chittorgarh");
            arrayList.add("Dholpur");
            arrayList.add("Jhalawar");
            arrayList.add("Tonk");
            arrayList.add("Udaipur");
            inItItemRecycle();
        } else if (state == 13) {
            arrayList.add("Mumbai");/*0*/
            arrayList.add("Pune");/*1*/
            arrayList.add("Nagpur");
            arrayList.add("Thane");
            arrayList.add("Nashik");
            arrayList.add("Kalyan");
            arrayList.add("Vasai");
            arrayList.add("Aurangabad");
            arrayList.add("Navi Mumbai");
            arrayList.add("Solapur");
            arrayList.add("Latur");
            arrayList.add("Amravati");
            arrayList.add("Thane");
            arrayList.add("Sangli");/*13*/
            inItItemRecycle();
        } else {
            Toast.makeText(getActivity(), state + "else", Toast.LENGTH_SHORT).show();
        }
    }

    private void inItItemRecycle() {
        locationAdapter = new LocationAdapter(getActivity(),arrayList);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String state1 = arrayList.get(position);
                if (state1.equals("Jaipur")) {
                    sgowFragmentByCon(position);
                } else if (state1.equals("Mumbai")) {
                    sgowFragmentByCon(position);
                } else if (state1.equals("Pune")) {
                    sgowFragmentByCon(position);
                } else if (state1.equals("Sangli")) {
                    sgowFragmentByCon(position);

                } else if(state == 13){
                    Toast.makeText(getActivity(), "No Tahsil available at this movement! Please select Mumbai , Sangli and Pune", Toast.LENGTH_SHORT).show();
                } else if(state == 19){
                    Toast.makeText(getActivity(), "No tahsil available at this movement! Please select Jaipur", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void sgowFragmentByCon(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("country", country); // Put anything what you want
        bundle.putString("state", txt_loc_state.getText().toString()); // Put anything what you want
        bundle.putString("city", arrayList.get(position)); // Put anything what you want
        FragmentTahsil fragment2 = new FragmentTahsil();
        fragment2.setArguments(bundle);

        getFragmentManager()
                .beginTransaction().addToBackStack("")
                .add(R.id.frame_loc, fragment2)
                .commit();
    }

    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
