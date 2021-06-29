package com.android.nmnewsagency.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.LocationAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentCountry extends Fragment {
    View view;
    RecyclerView recyclerView;
    LocationAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    ImageView iamge_back_country;
    EditText search_edit;
    String apiKey = "AIzaSyAlVPWqpH2XbSPRBAuxYmlCougC0k5-stA";
    LinearLayout lin_recycled;
    FrameLayout frame_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_select_location, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_loc);
        iamge_back_country = (ImageView) view.findViewById(R.id.iamge_back_country);
        search_edit = (EditText) view.findViewById(R.id.search_edit);
        frame_search = (FrameLayout) view.findViewById(R.id.frame_search);
        lin_recycled = (LinearLayout) view.findViewById(R.id.lin_recycled);
        arrayList = new ArrayList<>();
        inItListData();
        inItItemRecycle();
        iamge_back_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        });
        search_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // iniLizedSearch();
               // frame_search.setVisibility(View.VISIBLE);
               // lin_recycled.setVisibility(View.GONE);
            }
        });
        return view;
    }

    private void inItListData() {
        arrayList.add("India");
        arrayList.add("Bangladesh");
        arrayList.add("Nepal");
        arrayList.add("Sri Lanka");
    }

    private void inItItemRecycle() {
        locationAdapter = new LocationAdapter(getActivity(),arrayList);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Movie movie = movieList.get(position);
               // Toast.makeText(getActivity(), arrayList.get(position) + " is selected!", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("key",arrayList.get(position) ); // Put anything what you want

                FragmentState fragment2 = new FragmentState();
                fragment2.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().addToBackStack("")
                        .add(R.id.frame_loc, fragment2)
                        .commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    private void closefragment() {
        getActivity().finish();
    }


    public void iniLizedSearch(){
        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), apiKey);
        }
        PlacesClient placesClient = Places.createClient(getActivity());

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("searchcountry", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("searchcountry error", "An error occurred: " + status);
            }
        });
    }
    }

