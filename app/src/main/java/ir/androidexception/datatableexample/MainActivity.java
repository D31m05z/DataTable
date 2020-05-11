package ir.androidexception.datatableexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class MainActivity extends AppCompatActivity {
    private DataTable dataTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTable = findViewById(R.id.dt);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/iran_sans.ttf");
        DataTableHeader header = new DataTableHeader.Builder()
                .item("Product Name" , 3)
                .item("Quantity", 1)
                .item("Price", 2)
                .item("Discount", 2)
                .build();

        dataTable.setTypeface(tf);
        dataTable.setHeader(header);

        dataTable.inflate(this, new DataTable.OnItemClickListener() {
            @Override
            public void onItemClick(DataTableRow row, int position) {
                String item = row.getValues().isEmpty() ? "" : row.getValues().get(0);
                Toast.makeText(getApplicationContext(),  item + " click " + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongItemClick(DataTableRow row, int position) {
                String item = row.getValues().isEmpty() ? "" : row.getValues().get(0);
                Toast.makeText(getApplicationContext(), item + " long " + position, Toast.LENGTH_LONG).show();
            }
        });

        new Task().execute();
    }

    class Task extends AsyncTask<String, Integer, ArrayList<DataTableRow>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<DataTableRow> result) {

            dataTable.setRows(result);

            super.onPostExecute(result);
        }

        @Override
        protected ArrayList<DataTableRow> doInBackground(String... params) {
            ArrayList<DataTableRow> rows = new ArrayList<>();
            for(int i=0;i<200;i++){
                Random r = new Random();
                int random = r.nextInt(i+1);
                int randomDiscount = r.nextInt(20);
                DataTableRow row = new DataTableRow.Builder()
                        .value("Product #" + i)
                        .value(String.valueOf(random))
                        .value(String.valueOf(random*1000).concat("$"))
                        .value(String.valueOf(randomDiscount).concat("%"))
                        .build();
                rows.add(row);
            }

            return rows;
        }
    }
}
