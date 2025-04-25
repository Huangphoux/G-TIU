package com.example.g_tiu.ui.phanloai;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_tiu.R;
import com.example.g_tiu.adapter.phanLoaiAdapter;
import com.example.g_tiu.databinding.FragmentPhanloaiBinding;
import com.example.g_tiu.db_helper.loaiGD_DBHelper;
import com.example.g_tiu.item.loaiGD;

import java.util.ArrayList;
import java.util.List;


public class PhanLoaiFragment extends Fragment {

    private FragmentPhanloaiBinding binding;
    private loaiGD_DBHelper db;
    private RecyclerView recyclerView;
    private phanLoaiAdapter adapter;
    private ArrayList<loaiGD> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PhanLoaiViewModel phanLoaiViewModel =
                new ViewModelProvider(this).get(PhanLoaiViewModel.class);

        binding = FragmentPhanloaiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        phanLoaiViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // use getActivity, not getContext!
        db = new loaiGD_DBHelper(getActivity());
        list = db.getAll();

        if(list.isEmpty()) {
            Log.d("Error", "The list is empty son!");
        }

        // recyclerView can be scrollable, no need to put in scrollView
        recyclerView = root.findViewById(R.id.recyclerView_phanLoai);
        recyclerView.setHasFixedSize(true);

        // use getActivity, not getContext!
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new phanLoaiAdapter(list);
        recyclerView.setAdapter(adapter);

        prepareData();

        return root;
    }

    private void prepareData() {
        list.clear();
        list = db.getAll();
        adapter.update(list);
    }

    @Override
    public void onDestroyView() {
        db.close();
        super.onDestroyView();
        binding = null;
    }
}