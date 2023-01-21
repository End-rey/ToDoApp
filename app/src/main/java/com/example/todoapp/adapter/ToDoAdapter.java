package com.example.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.entity.ToDoItem;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private Context parent;

    private int numberItems;
    private List<ToDoItem> toDoList;

    public ToDoAdapter(int numberOfItems, Context parent, List<ToDoItem> toDoList) {
        this.parent = parent;
        this.numberItems = numberOfItems;
        this.toDoList = toDoList;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.todo_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        ToDoViewHolder viewHolder = new ToDoViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.bind(toDoList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder {

        TextView toDoItem;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);

            toDoItem = itemView.findViewById(R.id.tv_todo_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    int positionIndex = getAbsoluteAdapterPosition();
//
//                    Toast toast = Toast.makeText(parent, "Element " + positionIndex
//                            + " was clicked!", Toast.LENGTH_SHORT);
//
//                    toast.show();
                }
            });
        }

        void bind(String description) {
            toDoItem.setText(String.valueOf(description));
        }
    }
}
