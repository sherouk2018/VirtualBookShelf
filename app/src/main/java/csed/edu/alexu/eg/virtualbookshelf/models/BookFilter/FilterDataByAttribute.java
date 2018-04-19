package csed.edu.alexu.eg.virtualbookshelf.models.BookFilter;

import android.util.Log;

import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volumes;

import java.io.IOException;

import csed.edu.alexu.eg.virtualbookshelf.utility.Constants;

public class FilterDataByAttribute implements FilterDataContext {

    private static final String TAG = FilterDataByLocation.class.getSimpleName();

    @Override
    public Volumes filterData(String query, Books books) {
        Log.d(TAG, "Begin Filtering data by attribute: " + query);
        Volumes volumes = null;
        try {

            Books.Volumes.List listBooksInst = books.volumes().list(query);
            listBooksInst.setMaxResults(Constants.MAX_RESULTS);
            volumes = listBooksInst.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Finish Filtering data by location and number of returned volumes = "
                + (volumes == null ? -1 : volumes.getTotalItems()));
        return volumes;
    }
}
