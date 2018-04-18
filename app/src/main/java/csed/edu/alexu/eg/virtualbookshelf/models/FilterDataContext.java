package csed.edu.alexu.eg.virtualbookshelf.models;

import com.google.api.services.books.model.Volumes;

public interface FilterDataContext {

    public Volumes filterData (String query);
}
