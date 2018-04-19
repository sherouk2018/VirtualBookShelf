package csed.edu.alexu.eg.virtualbookshelf.utility;

import android.util.Log;

import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volumes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import csed.edu.alexu.eg.virtualbookshelf.models.BookFilter.FilterDataByAttribute;
import csed.edu.alexu.eg.virtualbookshelf.models.BookFilter.FilterDataContext;

public class FilterData extends UserUtils {
    private String path = "csed.edu.alexu.eg.virtualbookshelf.models.BookFilter.";

    public FilterData(Books books) {
        super(books);
    }

    @Override
    protected Volumes doInBackground(String... params) {

        try {
            Log.d("Soso in filter", "params: "+ params[0] + " "+params[1]);
            FilterDataContext filter = (FilterDataContext) Class.forName(path + params[0]).newInstance();
            return filter.filterData(params[1], books);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Invalid parameters for filter");

    }
}
