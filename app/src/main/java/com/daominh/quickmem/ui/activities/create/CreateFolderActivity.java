package com.daominh.quickmem.ui.activities.create;

import android.annotation.SuppressLint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.daominh.quickmem.R;
import com.daominh.quickmem.data.dao.FolderDAO;
import com.daominh.quickmem.data.model.Folder;
import com.daominh.quickmem.databinding.ActivityCreateFolderBinding;
import com.daominh.quickmem.preferen.UserSharePreferences;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CreateFolderActivity extends AppCompatActivity {
    private ActivityCreateFolderBinding binding;
    private UserSharePreferences userSharePreferences;
    private FolderDAO folderDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateFolderBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);


        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tick, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.done) {
            final String folderName = binding.folderEt.getText().toString().trim();
            final String description = binding.descriptionEt.getText().toString().trim();
            if (folderName.isEmpty()) {
                binding.folderTil.setError("");
                binding.folderTil.setHelperText("Folder name cannot be empty");
                binding.folderEt.requestFocus();
                return false;
            } else {
                final String folderId = genUUID();
                final String userId = getUser_id();
                final String createdAt = getCurrentDate();
                final String updatedAt = getCurrentDate();

                Folder folder = new Folder(folderId, folderName, description, userId, createdAt, updatedAt);
                folderDAO = new FolderDAO(this);
                if (folderDAO.insertFolder(folder) > 0) {
                    Toast.makeText(this, "Folder created", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(this, "Folder not created", Toast.LENGTH_SHORT).show();
                }

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private String getCurrentDate() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return currentDate.format(formatter);
        } else {
            // Handle case for Android versions less than Oreo
            // Here we're using SimpleDateFormat which is available on all Android versions
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(new Date());
        }
    }

    private String genUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    private String getUser_id() {
        userSharePreferences = new UserSharePreferences(this);
        return userSharePreferences.getId();
    }

}