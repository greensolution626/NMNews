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

public class FragmentState extends Fragment {
    View view;
    RecyclerView recyclerView;
    LocationAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    static String country;
    TextView txt_loc_country;
    ImageView img_close_loc,iamge_back_state;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_select_state, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            country = bundle.getString("key");
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_loc);
        txt_loc_country = (TextView) view.findViewById(R.id.txt_loc_country);
        img_close_loc = (ImageView) view.findViewById(R.id.img_close_loc);
        iamge_back_state = (ImageView) view.findViewById(R.id.iamge_back_state);
        arrayList = new ArrayList<>();
        inItListData(country);
        inItItemRecycle();
        img_close_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        }); iamge_back_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        });
        return view;
    }

    private void inItListData(String country) {
        txt_loc_country.setText(country);
        if (country.equalsIgnoreCase("india")) {
            arrayList.add("Andhra Pradesh");
            arrayList.add("Arunchal Pradesh");
            arrayList.add("Assam");
            arrayList.add("Bihar");
            arrayList.add("Chhattisgarh");
            arrayList.add("Goa");
            arrayList.add("Gujarat");
            arrayList.add("Haryana");
            arrayList.add("Himachal Pradesh");
            arrayList.add("Jharkhand");
            arrayList.add("Karnataka");
            arrayList.add("Kerala");
            arrayList.add("Madhya Pradesh");
            arrayList.add("Maharashtra");
            arrayList.add("Manipur");
            arrayList.add("Meghalaya");
            arrayList.add("Mizoram");
            arrayList.add("Odisha");
            arrayList.add("Punjab");
            arrayList.add("Rajasthan");
            arrayList.add("Sikkim");
            arrayList.add("Tamil Nadu");
            arrayList.add("Telangana");
            arrayList.add("Tripura");
            arrayList.add("Uttar Pradesh");
            arrayList.add("Uttarakhand");
            arrayList.add("West Bengal");
        } else if (country.equalsIgnoreCase("Bangladesh")) {
            arrayList.add("Dhaka");
            arrayList.add("Chittagong");
            arrayList.add("Khulna");
            arrayList.add("Rajshahi");
            arrayList.add("Gazipur");
            arrayList.add("Sylhet");
            arrayList.add("Mymensingh");
            arrayList.add("Barisal");
            arrayList.add("Rangpur");
            arrayList.add("Comilla");
        } else if (country.equalsIgnoreCase("Nepal")) {
            arrayList.add("nepal");
            arrayList.add("Chittagong");
            arrayList.add("Khulna");
            arrayList.add("Rajshahi");
            arrayList.add("Gazipur");
            arrayList.add("Sylhet");
            arrayList.add("Mymensingh");
            arrayList.add("Barisal");
            arrayList.add("Rangpur");
            arrayList.add("Comilla");
            arrayList.add("Jharkhand");
        } else if (country.equalsIgnoreCase("Sri Lanka")) {
            arrayList.add("Anuradhapura");
            arrayList.add("Badulla");
            arrayList.add("Batticaloa");
            arrayList.add("Colombo");
            arrayList.add("Comilla");
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
                if (country.equalsIgnoreCase("Bangladesh") ||
                        country.equalsIgnoreCase("Nepal") ||
                        country.equalsIgnoreCase("Sri Lanka")) {
                    Toast.makeText(getActivity(),"No city  available at this movement! Please select Rajasthan or Maharashtra",Toast.LENGTH_SHORT).show();
                }else if (position==13 || position==19) {
                    Bundle bundle = new Bundle();
                    bundle.putString("country", country); // Put anything what you want
                    bundle.putInt("state", position); // Put anything what you want

                    FragmentCity fragment2 = new FragmentCity();
                    fragment2.setArguments(bundle);

                    getFragmentManager()
                            .beginTransaction().addToBackStack("")
                            .add(R.id.frame_loc, fragment2)
                            .commit();

                } else {
                    Toast.makeText(getActivity(),"No city  available at this movement! Please select Rajasthan or Maharashtra",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
