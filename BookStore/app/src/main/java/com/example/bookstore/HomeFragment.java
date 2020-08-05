package com.example.bookstore;

import android.app.Person;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


/* *
 *Author: Goat Chen
 */

public class HomeFragment extends Fragment {
    DatabaseHelper db;
    List<Book> booksList;
    ListView lvBooks;

    EditText editName;
    Button buttonRefresh;
    Button buttonQuery;
    Button buttonAdd;
    Button buttonDelete;
    Button buttonUpdate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editName = getActivity().findViewById(R.id.etHName);
        buttonRefresh = getActivity().findViewById(R.id.btnHRefresh);
        buttonQuery = getActivity().findViewById(R.id.btnHQuery);
        buttonAdd = getActivity().findViewById(R.id.btnHAdd);
        buttonDelete = getActivity().findViewById(R.id.btnHDelete);
        buttonUpdate = getActivity().findViewById(R.id.btnHUpdate);

        lvBooks = getActivity().findViewById(R.id.lvHome);
        booksList = new ArrayList<>();
        db = new DatabaseHelper(getContext());
        Cursor cursor = db.refreshBook();
        while (cursor.moveToNext()){
            booksList.add(new Book(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
        }
        ArrayAdapter<Book> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, booksList);
        lvBooks.setAdapter(arrayAdapter);

        refreshLv(); // Refresh list view
        queryByName();

        addBook();
        deleteBook();
        updateBook();

    }

    private void queryByName() {
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booksList.clear();
                String name = editName.getText().toString().trim();
                if(name.isEmpty()){
                    Toast.makeText(getContext(),"Please fill in the book name for searching",Toast.LENGTH_LONG).show();
                    return;
                }
                Cursor cursor = db.findBookByName(name);
                if(cursor.getCount()<1){
                    Toast.makeText(getContext(),"No such item, please check!",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    while (cursor.moveToNext()){
                        booksList.add(new Book(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
                    }
                    ArrayAdapter<Book> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, booksList);
                    lvBooks.setAdapter(arrayAdapter);

                }
            }
        });
    }

    private void refreshLv() {
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booksList.clear();
                Cursor cursor = db.refreshBook();
                while (cursor.moveToNext()){
                    booksList.add(new Book(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
                }
                ArrayAdapter<Book> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, booksList);
                lvBooks.setAdapter(arrayAdapter);
                Toast.makeText(getContext(),"Refresh done!",Toast.LENGTH_LONG);

            }
        });
    }

    private void updateBook() {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),UpdateBookActivity.class);
                startActivity(i);

            }
        });
    }

    private void deleteBook() {
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                if (name.isEmpty()){
                    Toast.makeText(getContext(),"Please fill in the name.",Toast.LENGTH_LONG).show();
                    return;
                }
                Integer i = db.deleteBook(name);
                if(i > 0){
                    Toast.makeText(getContext(),"Delete book successfully.",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Toast.makeText(getContext(),"Error to delete book.",Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });
    }

    private void addBook() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),AddBookActivity.class);
                startActivity(i);
            }
        });
    }
}
