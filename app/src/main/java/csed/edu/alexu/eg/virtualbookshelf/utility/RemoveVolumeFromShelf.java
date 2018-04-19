package csed.edu.alexu.eg.virtualbookshelf.utility;

import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volumes;
import csed.edu.alexu.eg.virtualbookshelf.models.UserFunctionality.EditShelf;
import csed.edu.alexu.eg.virtualbookshelf.utility.UserUtils;

public class RemoveVolumeFromShelf extends UserUtils {

    public RemoveVolumeFromShelf(Books books) {
        super(books);
    }

    @Override
    protected Volumes doInBackground(String... params) {
        //params[0]: ShelfID, params[1]: volumeID
        EditShelf shelf = new EditShelf();
        if(params.length == 1) shelf.clearShelf(params[0], books);
        else shelf.deleteFromShelf(params[0], params[1], books);
        return null;
    }


}
