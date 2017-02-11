package com.demonoteapp.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.demonoteapp.Adapter.NoteAdapter;
import com.demonoteapp.Database.DatabaseAdapter;
import com.demonoteapp.Model.Note;
import com.demonoteapp.Model.Setting;
import com.demonoteapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_NOTE = 1;
    private static final int REQUEST_CODE_EDIT_NOTE = 1;
    DatabaseAdapter db;
    NoteAdapter adapter;
    List<Note> noteList;
    int selectedPos = -1;

    //Widget
    ListView lvNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseAdapter(this);
        Toast.makeText(getApplicationContext(),""+db.getNotesCount(),Toast.LENGTH_LONG).show();
        db.addNote(new Note("test","Demo"));

        checkIfNoteEmpty();
        initListViewNote();
        initEvents();
    }

    private void initEvents() {
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNoteIntent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(addNoteIntent, REQUEST_CODE_ADD_NOTE);
            }
        });

        lvNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                selectedPos = pos;
                return false;
            }
        });
    }

    public void checkIfNoteEmpty() {
        if (db.getNotesCount() == 0)
            findViewById(R.id.lnInfor).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.lnInfor).setVisibility(View.GONE);
    }

    public void showDialogDeleteNote() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteNote(noteList.get(selectedPos));
                        noteList.remove(noteList.get(selectedPos));
                        adapter.notifyDataSetChanged();
                        checkIfNoteEmpty();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void initListViewNote() {
        lvNote = (ListView) findViewById(R.id.lvNote);
        noteList = new ArrayList<>();
        noteList = db.getAllNotes();
        adapter = new NoteAdapter(getApplicationContext(), 0, noteList);
        lvNote.setAdapter(adapter);
        registerForContextMenu(lvNote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuSetting) {
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Option");
        menu.add(0, v.getId(), 0, "Edit");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Edit") {
            Intent editNoteIntent = new Intent(MainActivity.this, EditNoteActivity.class);
            Note note = noteList.get(selectedPos);
            editNoteIntent.putExtra("NOTE", note);
            startActivityForResult(editNoteIntent, REQUEST_CODE_EDIT_NOTE);

        } else if (item.getTitle() == "Delete") {
            showDialogDeleteNote();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD_NOTE) {
            checkIfNoteEmpty();
            noteList.clear();
            noteList.addAll(db.getAllNotes());
            adapter.notifyDataSetChanged();
        }

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_EDIT_NOTE) {
            noteList.clear();
            noteList.addAll(db.getAllNotes());
            adapter.notifyDataSetChanged();
        }
    }
}
