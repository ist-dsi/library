package pt.ist.library.rest.json;

import pt.ist.bennu.core.annotation.DefaultJsonAdapter;
import pt.ist.bennu.core.domain.Bennu;
import pt.ist.bennu.json.JsonAdapter;
import pt.ist.bennu.json.JsonBuilder;
import pt.ist.fenixframework.Atomic;
import pt.ist.library.domain.Library;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(Library.class)
public class LibraryAdapter implements JsonAdapter<Library> {

    @Override
    @Atomic
    public Library create(JsonElement json, JsonBuilder ctx) {
        // TODO Auto-generated method stub
        Library library = new Library();
        library.setName("Nova Biblioteca");
        library.setLibrarySystem(Bennu.getInstance().getLibrarySystem());
        return library;
    }

    @Override
    public Library update(JsonElement json, Library obj, JsonBuilder ctx) {
        int capacity = json.getAsJsonObject().get("capacity").getAsInt();
        int lockers = json.getAsJsonObject().get("lockers").getAsInt();
        String name = json.getAsJsonObject().get("name").getAsString();

        obj.setMaxOccupation(capacity);
        obj.setLockersCapacity(lockers);
        obj.setName(name);
        return obj;
    }

    @Override
    public JsonElement view(Library obj, JsonBuilder ctx) {
        JsonObject view = new JsonObject();
        view.addProperty("id", obj.getExternalId());
        view.addProperty("name", obj.getName());
        view.addProperty("lockers", obj.getLockerSet().size());
        view.addProperty("capacity", obj.getMaxOccupation());
        return view;
    }

}
