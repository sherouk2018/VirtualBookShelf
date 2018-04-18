package csed.edu.alexu.eg.virtualbookshelf.models;

import android.util.Log;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volumes;
import java.io.IOException;
import csed.edu.alexu.eg.virtualbookshelf.utility.Constants;

public class FilterDataByLocation implements FilterDataContext{

    private static final String TAG = FilterDataByLocation.class.getSimpleName();

    @Override
    public Volumes filterData(String location) {
        Log.d(TAG, "Begin Filtering data by location");
        Volumes volumes = null;
        try {
            String userId = LocationToUserId.getUserId(location);
            // Todo initialize books based on a static class.
            Books books = null;
            Books.Bookshelves.Volumes.List listBooksInst = books.bookshelves().volumes().list(userId, "0");
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
