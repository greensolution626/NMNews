package com.android.nmnewsagency.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.activity.LocationReqActivity;
import com.android.nmnewsagency.activity.MainActivity;
import com.android.nmnewsagency.activity.SettingActivity;
import com.android.nmnewsagency.adapter.LocationAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;
import com.android.nmnewsagency.pref.Prefrence;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FragmentTahsil extends Fragment {
    View view;
    static String state, country;
    static String city;
    RecyclerView recyclerView;
    LocationAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    TextView txt_loc_country, txt_loc_state, txt_loc_city;
    ImageView img_cou, img_sta, img_city,iamge_back_tahsil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_select_tahsil, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            country = bundle.getString("country");
            state = bundle.getString("state");
            city = bundle.getString("city");
        }
        iniIT();
        return view;
    }

    private void iniIT() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_loc);
        txt_loc_country = (TextView) view.findViewById(R.id.txt_loc_country);
        txt_loc_state = (TextView) view.findViewById(R.id.txt_loc_state);
        txt_loc_city = (TextView) view.findViewById(R.id.txt_loc_city);
        img_cou = (ImageView) view.findViewById(R.id.img_cou);
        iamge_back_tahsil = (ImageView) view.findViewById(R.id.iamge_back_tahsil);
        img_sta = (ImageView) view.findViewById(R.id.img_sta);
        img_city = (ImageView) view.findViewById(R.id.img_city);
        arrayList = new ArrayList<>();
        inItListData(city);
        img_cou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        });
        img_sta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        });
        img_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        });iamge_back_tahsil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        });
    }

    private void inItListData(String city) {
        txt_loc_city.setText(city);
        txt_loc_country.setText(country);
        txt_loc_state.setText(state);

        if (city.equals("Jaipur")) {
            arrayList.add("Sanganer");
            arrayList.add("Kotputli ");
            arrayList.add("Choumu ");
            arrayList.add("Bassie ");
            arrayList.add("Chaksu ");
            arrayList.add("Pavta ");
            arrayList.add("Phagi ");
            arrayList.add("Jaipur ");
            arrayList.add("Shahpura");
            arrayList.add("Pavta");
            arrayList.add("Viratnagar ");
            inItItemRecycle();
        } else if (city.equals("Mumbai")) {
            arrayList.add("Andheri");
            arrayList.add("Borivali ");
            arrayList.add("Kurla ");
            arrayList.add("Ambivali ");
            arrayList.add("Bandra ");
            arrayList.add("Juhu ");
            arrayList.add("Kondivita ");
            arrayList.add("Varsova ");
            arrayList.add("Vileparle");
            arrayList.add("Vyaravali");
            arrayList.add("Mogara ");
            inItItemRecycle();
        } else if (city.equals("Pune")) {
            arrayList.add("Ambegaon");
            arrayList.add("Baramati ");
            arrayList.add("Bhor ");
            arrayList.add("Daund ");
            arrayList.add("Haveli ");
            arrayList.add("Indapur ");
            arrayList.add("Junnar ");
            arrayList.add("Khed ");
            arrayList.add("Mawal");
            arrayList.add("Mulshi");
            arrayList.add("Pune City ");
            arrayList.add("Purandhar ");
            arrayList.add("Shirur ");
            arrayList.add("Velhe ");
            inItItemRecycle();
        } else if (city.equals("Sangli")) {
            arrayList.add("Miraj");
            arrayList.add("Tasgaon ");
            arrayList.add("Kavthe Mahankal ");
            arrayList.add("Jat ");
            arrayList.add("Khanapur ");
            arrayList.add("Atpadi ");
            arrayList.add("Palus ");
            arrayList.add("Kadegaon ");
            arrayList.add("Walwa ");
            arrayList.add("Shirala ");
            inItItemRecycle();
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
                Prefrence.settahsil(arrayList.get(position));
                Intent intent = new Intent(getActivity(),
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(FragmentTahsil.this).commit();
    }
}
