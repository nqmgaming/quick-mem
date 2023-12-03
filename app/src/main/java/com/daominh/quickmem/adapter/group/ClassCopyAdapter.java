package com.daominh.quickmem.adapter.group;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.daominh.quickmem.data.dao.GroupDAO;
import com.daominh.quickmem.data.model.Group;
import com.daominh.quickmem.databinding.ItemClassCopyBinding;
import com.daominh.quickmem.ui.activities.classes.ViewClassActivity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ClassCopyAdapter extends RecyclerView.Adapter<ClassCopyAdapter.ClassViewHolder> {
    GroupDAO groupDAO;
    private final Context context;
    private final ArrayList<Group> classes;

    public ClassCopyAdapter(Context context, ArrayList<Group> classes) {
        this.context = context;
        this.classes = classes;
    }

    @NonNull
    @NotNull
    @Override
    public ClassCopyAdapter.ClassViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemClassCopyBinding binding = ItemClassCopyBinding.inflate(inflater, parent, false);
        return new ClassViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ClassCopyAdapter.ClassViewHolder holder, int position) {
        Group group = classes.get(position);
        holder.binding.classNameTv.setText(group.getName());
        groupDAO = new GroupDAO(context);
        int numberMember = groupDAO.getNumberMemberInClass(group.getId()) + 1;
        int numberSet = groupDAO.getNumberFlashCardInClass(group.getId());
        holder.binding.numberUserTv.setText(numberMember + " members");
        holder.binding.numberSetTv.setText(numberSet + " sets");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewClassActivity.class);
            intent.putExtra("id", group.getId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {
        ItemClassCopyBinding binding;

        public ClassViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            binding = ItemClassCopyBinding.bind(itemView);
        }
    }
}
