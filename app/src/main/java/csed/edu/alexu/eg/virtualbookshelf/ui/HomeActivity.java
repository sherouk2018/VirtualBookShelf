package csed.edu.alexu.eg.virtualbookshelf.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import com.google.api.services.books.model.Volumes;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import csed.edu.alexu.eg.virtualbookshelf.models.BookFilter.FilterDataByAttribute;
import csed.edu.alexu.eg.virtualbookshelf.utility.BookListAdapter;
import csed.edu.alexu.eg.virtualbookshelf.R;
import csed.edu.alexu.eg.virtualbookshelf.models.BookFilter.FilterDataByAttribute;
import csed.edu.alexu.eg.virtualbookshelf.models.BookFilter.FilterDataByLocation;
import csed.edu.alexu.eg.virtualbookshelf.models.BookFilter.FilterDataContext;
import csed.edu.alexu.eg.virtualbookshelf.utility.EditFactory;
import csed.edu.alexu.eg.virtualbookshelf.utility.FilterData;
import csed.edu.alexu.eg.virtualbookshelf.utility.UserUtils;


public class HomeActivity extends AppCompatActivity {
    // Filter
    String[] search_fields = {"all", "title", "category", "Location"};
    public static final int NO_FILTER = 0;
    public static final int TITLE = 1;
    public static final int SUBJECT = 2;
    public static final int LOCATION = 3;
    private static int filterOption = 0;

    ListView booksListView;
    BookListAdapter adapter;
    Button filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // books
        booksListView = findViewById(R.id.books_list);

//        // run async task
//        new LoadBooksTask().execute(new Filter(0, null));

        // filter text field
        final EditText filter_String = findViewById(R.id.search_text);

        // filter fields menu
        final Spinner spinner = findViewById(R.id.search_choices);
        ArrayAdapter spinner_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, search_fields);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                  filterOption = i;
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> adapterView) {
                                                  filterOption = 0;
                                              }
                                          }
        );

        // books filtering
        filter = findViewById(R.id.do_filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Mirnaaaa", "Filtering" + filterOption + "  " + filter_String.getText().toString());
                if (adapter != null) {
                    Log.d("Mirnaaa 222", "Filtering" + filterOption + "  " + filter_String.getText().toString());
                    // clear data
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    // according to filter
                }
                    new LoadBooksTask().execute(new Filter(filterOption, filter_String.getText().toString()));

            }
        });
    }


//    private void dog(ArrayList<Volume> volumes) {
//        Log.d("Mirnaaaa in post ", volumes.size()+"");
//
//        adapter = new BookListAdapter(HomeActivity.this, R.layout.books_list_item, volumes);
//        booksListView.setAdapter(adapter);
//
//        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d(this.getClass().getName(), "open indivual book number " + i + " in list");
//                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
//                Volume volume = (Volume) booksListView.getAdapter().getItem(i);
//                intent.putExtra("book_id", volume.getId());
//                startActivity(intent);
//            }
//        });
//    }

    /**
     *
     */
    private class LoadBooksTask extends AsyncTask<Filter, Void, ArrayList<Volume>> {

        @Override
        protected ArrayList<Volume> doInBackground(Filter... f) {
            EditFactory factory = EditFactory.getInstance();
//            UserUtils utils = factory.getEditFun("FilterData");
            FilterData filterData = new FilterData();
            Log.d("HomeActivity Mirna" , "filtering "+ f[0].getFilterField() + "  " + f[0].getFilterString());
            Volumes volumes = null;
            String query;
           // try {
                switch (f[0].getFilterField()) {
                    case TITLE:
                        Log.d("HomeActivity1 " , "filtering with title in home activity " + filterData.getClass().toString());
                        query = "intitle:" + f[0].getFilterString();
                        Log.d("HomeActivity2" , "filtering with title in home activity + geury " + query);

                        volumes = filterData.getVolumesBasedOnFilter("FilterDataByAttribute", query);
                        Log.d("HomeActivity3" , "filtering with title in home activity + geury " + query);
                        break;
                    case SUBJECT:
                        Log.d("HomeActivity" , "filtering with subject in home activity");
                        query = "subject:" + f[0].getFilterString();
                        volumes = filterData.getVolumesBasedOnFilter("FilterDataByAttribute", query);
                        Log.d("HomeActivity3" , "filtering with title in home activity + geury " + query);
                        break;
                    case LOCATION:
                        Log.d("HomeActivity" , "filtering with location in home activity");
                        query = f[0].getFilterString();
                        volumes = filterData.getVolumesBasedOnFilter("FilterDataByAttribute", query);
                        Log.d("HomeActivity3" , "filtering with title in home activity + geury " + query);
                        break;
                    case 0:
                        Log.d("HomeActivity" , "filtering with 0 in home activity");
                        query = "intitle:harry";
                        volumes = filterData.getVolumesBasedOnFilter("FilterDataByAttribute", query);
                        Log.d("HomeActivity3" , "filtering with title in home activity + geury " + query);
                        break;
                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }

           /* try {
                Books books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(), JacksonFactory.getDefaultInstance(), null)
                        .setApplicationName("VirtualBookShelf")
                        //.setHttpRequestInitializer((GoogleAccountCredential)this.getIntent().getSerializableExtra("token"))
                        .setBooksRequestInitializer(new BooksRequestInitializer("AIzaSyCik4DYGQfsRcBp1a-cB6seTwuA6cqxCKY"))
                        .build();
                List volumesList = books.volumes().list("title:harry").setMaxResults(new Long(20));
                volumes = volumesList.execute();
                for (Volume volume : volumes.getItems()) {
                    onProgressUpdate(volume);
                }
            } catch (Exception e) {
                Log.e("Mirna ", e.getMessage());
            }*/
            if (volumes == null || volumes.isEmpty()) {
                Log.d("Mirnaaaa", "volumes is empty");
            }
            return volumes != null ? (ArrayList<Volume>) volumes.getItems() : new ArrayList<Volume>();
        }

        @Override
        protected void onPostExecute(ArrayList<Volume> volumes) {
            super.onPostExecute(volumes);
            Log.d("Mirnaaaa in post ", volumes.size()+"");

            adapter = new BookListAdapter(HomeActivity.this, R.layout.books_list_item, volumes);
            booksListView.setAdapter(adapter);

            booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d(this.getClass().getName(), "open indivual book number " + i + " in list");
                    Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                    Volume volume = (Volume) booksListView.getAdapter().getItem(i);
                    intent.putExtra("book_id", volume.getId());
                    startActivity(intent);
                }
            });
        }

    }


    /**
     *
     */
    public class Filter {
        private int filterField;
        private String filterString;

        Filter(int filterField, String filterString) {
            this.filterField = filterField;
            this.filterString = filterString;
        }

        public int getFilterField() {
            return filterField;
        }

        public String getFilterString() {
            return filterString;
        }
    }


}

