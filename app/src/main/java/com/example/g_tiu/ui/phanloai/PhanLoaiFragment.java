//package com.example.g_tiu.ui.phanloai;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.g_tiu.R;
//import com.example.g_tiu.adapter.phanLoaiAdapter;
//import com.example.g_tiu.addPhanLoai;
//import com.example.g_tiu.databinding.FragmentPhanloaiBinding;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
//
//import java.util.ArrayList;
//
//public class PhanLoaiFragment extends Fragment {
//
//    private FragmentPhanloaiBinding binding;
//    private loaiGD_DBHelper db;
//    private RecyclerView recyclerView;
//    private phanLoaiAdapter adapter;
//    private ArrayList<loaiGD> list;
//    private BottomNavigationView navView;
//    private ActivityResultLauncher<Intent> launcher;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        PhanLoaiViewModel phanLoaiViewModel =
//                new ViewModelProvider(this).get(PhanLoaiViewModel.class);
//
//        binding = FragmentPhanloaiBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        // use getActivity, not getContext!
//        db = new loaiGD_DBHelper(getActivity());
//        list = db.getAll();
//
//        navView = root.findViewById(R.id.nav_view);
//
//        // recyclerView can be scrollable, no need to put in scrollView
//        recyclerView = root.findViewById(R.id.recyclerView_phanLoai);
//        setUpRecyclerView();
//        swipeToRemove();
//
//        launcher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    // OMG I HAD TO ALSO reloadTodoList for resultCode = 1 !!!
//                    if (result.getResultCode() == 1) {
//                        prepareData();
//                    }
//                }
//        );
//
//        FloatingActionButton fab_phanLoai = root.findViewById(R.id.fab_phanLoai);
//        fab_phanLoai.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), addPhanLoai.class);
//            launcher.launch(intent);
//        });
//
//        return root;
//    }
//
//    private void setUpRecyclerView() {
//        recyclerView.setHasFixedSize(true);
//
//        // use getActivity, not getContext!
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        adapter = new phanLoaiAdapter(list);
//        recyclerView.setAdapter(adapter);
//
//        prepareData();
//    }
//
//    private void swipeToRemove() {
//        // on below line we are creating a method to create item touch helper
//        // method for adding swipe to delete functionality.
//        // in this we are specifying drag direction and position to right
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                // this method is called
//                // when the item is moved.
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                // this method is called when we swipe our item to right direction.
//                // on below line we are getting the item at a particular position.
//                loaiGD deletedItem = list.get(viewHolder.getAdapterPosition());
//
//                // below line is to get the position
//                // of the item at that position.
//                int position = viewHolder.getAdapterPosition();
//
//                // this method is called when item is swiped.
//                // below line is to remove item from our array list.
//                list.remove(viewHolder.getAdapterPosition());
//
//                // below line is to notify our item is removed from adapter.
//                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//
//                // below line is to display our snackbar with action.
//                Snackbar snackbar = Snackbar
//                        .make(recyclerView, "Deleted " + deletedItem.getTenLGD(), Snackbar.LENGTH_SHORT)
//                        .setAction("Undo", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                // adding on click listener to our action of snack bar.
//                                // below line is to add our item to array list with a position.
//                                list.add(position, deletedItem);
//
//                                // below line is to notify item is
//                                // added to our adapter class.
//                                adapter.notifyItemInserted(position);
//                            }
//                        });
//                snackbar.setAnchorView(navView);
//
//                snackbar.show();
//            }
//            // at last we are adding this
//            // to our recycler view.
//        }).attachToRecyclerView(recyclerView);
//    }
//
//    private void prepareData() {
//        list.clear();
//        list = db.getAll();
//        adapter.update(list);
//    }
//
//    @Override
//    public void onDestroyView() {
//        db.close();
//        super.onDestroyView();
//        binding = null;
//    }
//}