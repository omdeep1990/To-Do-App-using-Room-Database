package com.omdeep.todoroomdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.omdeep.todoroomdatabase.databinding.ActivityAddTaskBinding;

public class AddTaskActivity extends AppCompatActivity {
    ActivityAddTaskBinding binding;
    //private EditText editTextTask, editTextDesc, editTextFinishBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

//        binding.btn

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveTask();
            }
        });

    }

    private void saveTask() {
        final String sTask =  binding.editTextTask.getText().toString().trim();
        final String sDesc =  binding.editTextDesc.getText().toString().trim();
        final String sFinishBy =  binding.editTextFinishBy.getText().toString().trim();

        if (sTask.isEmpty()) {
            binding.editTextTask.setError("Task required");
            binding.editTextTask.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            binding.editTextDesc.setError("Desc required");
            binding.editTextDesc.requestFocus();
            return;
        }

        if (sFinishBy.isEmpty()) {
            binding.editTextFinishBy.setError("Finish by required");
            binding.editTextFinishBy.requestFocus();
            return;
        }
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Task task = new Task();
                task.setTask(sTask);
                task.setDesc(sDesc);
                task.setFinishBy(sFinishBy);
                task.setFinished(false);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }
}

