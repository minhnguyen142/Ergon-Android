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

import com.example.project.Adapter.ImageAdapter;
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
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<Book> bookList;
    private DatabaseReference databaseReference;
    private ImageButton btnVanHoc, btnEbook;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        recyclerView = view.findViewById(R.id.recycler_trending);
        btnVanHoc = view.findViewById(R.id.ic_van_hoc);
        btnEbook = view.findViewById(R.id.ic_ebook);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        bookList = new ArrayList<>();
        imageAdapter = new ImageAdapter(bookList);
        recyclerView.setAdapter(imageAdapter);

        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.item_space);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        databaseReference = FirebaseDatabase.getInstance().getReference("books");
        fetchBooksFromFirebase();

        btnVanHoc.setOnClickListener(v-> {
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
                bookList.clear();
                int count = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (count >= 10) break;

                    Book book = dataSnapshot.getValue(Book.class);
                    if (book != null) {
                        bookList.add(book);
                        count++;
                    }
                }
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load books.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
