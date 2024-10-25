package com.example.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.adapter.ImageAdapter;
import com.example.project.R;
import com.example.project.SpaceItemDecoration;
import com.example.project.ui.Ebook;
import com.example.project.ui.VanHoc;
import com.example.project.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends Fragment {
    private RecyclerView recyclerTrending, recyclerDexuat;
    private ImageAdapter trendingAdapter, dexuatAdapter;
    private List<Book> trendingBooks, dexuatBooks;
    private DatabaseReference databaseReference;
    private ImageButton btnVanHoc, btnEbook;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);


        recyclerTrending = view.findViewById(R.id.recycler_trending);
        LinearLayoutManager trendingLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTrending.setLayoutManager(trendingLayoutManager);

        trendingBooks = new ArrayList<>();
        trendingAdapter = new ImageAdapter(trendingBooks);
        recyclerTrending.setAdapter(trendingAdapter);


        recyclerDexuat = view.findViewById(R.id.recycler_dexuat);
        LinearLayoutManager dexuatLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerDexuat.setLayoutManager(dexuatLayoutManager);

        dexuatBooks = new ArrayList<>();
        dexuatAdapter = new ImageAdapter(dexuatBooks);
        recyclerDexuat.setAdapter(dexuatAdapter);


        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.item_space);
        recyclerTrending.addItemDecoration(new SpaceItemDecoration(spaceInPixels));
        recyclerDexuat.addItemDecoration(new SpaceItemDecoration(spaceInPixels));


        databaseReference = FirebaseDatabase.getInstance().getReference("books");
        fetchBooksFromFirebase();


        btnVanHoc = view.findViewById(R.id.ic_van_hoc);
        btnEbook = view.findViewById(R.id.ic_ebook);
        btnVanHoc.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VanHoc.class);
            startActivity(intent);
        });
        btnEbook.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ebook.class);
            startActivity(intent);
        });

        return view;
    }

    private void fetchBooksFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trendingBooks.clear();
                dexuatBooks.clear();
                int trendingCount = 0;
                int dexuatCount = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Book book = dataSnapshot.getValue(Book.class);
                    if (book != null) {
                        if (trendingCount < 10) {
                            trendingBooks.add(book);
                            trendingCount++;
                        } else if (dexuatCount < 10) {
                            dexuatBooks.add(book);
                            dexuatCount++;
                        }
                    }
                }
                trendingAdapter.notifyDataSetChanged();
                dexuatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load books.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


