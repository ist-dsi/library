package pt.ist.library.rest;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import pt.ist.bennu.core.domain.Bennu;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.rest.DomainObjectResource;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.library.domain.Library;
import pt.ist.library.domain.LibrarySystem;
import pt.ist.library.domain.LibraryUser;
import pt.ist.library.domain.Locker;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Path("/libraries")
public class LibraryResource extends DomainObjectResource<Library> {

    @Override
    @Atomic
    public Collection<Library> all() {

        LibrarySystem system = Bennu.getInstance().getLibrarySystem();

        //TODO remove these libraries initialization
        if (system.getLibrarySet().isEmpty()) {
            Library l1 = new Library();
            l1.setName("Central");
            system.addLibrary(l1);
            l1 = new Library();
            l1.setName("Informática");
            system.addLibrary(l1);
            l1 = new Library();
            l1.setName("Física");
            system.addLibrary(l1);
        }
        return system.getLibrarySet();
    }

    @Override
    public String collectionKey() {
        return "libraries";
    }

    @Override
    public Class<Library> type() {
        return Library.class;
    }

    @Override
    @Atomic
    public boolean delete(Library obj) {
        obj.getLibrarySystem().addRemovedLibrary(obj);
        obj.getLibrarySystem().removeLibrary(obj);
        return true;
    }

    @Override
    public String getAccessExpression() {
        return "#libraryOperators";
    }

    @GET
    @Path("{id}/students")
    @Atomic
    public String getUsers(@PathParam("id") String externalID) {
        JsonObject obj = new JsonObject();

        JsonArray studentsJsonArray = new JsonArray();
        Library library = FenixFramework.getDomainObject(externalID);

        for (LibraryUser user : library.getUserSet()) {
            JsonObject studentJsonObject = new JsonObject();
            studentJsonObject.addProperty("id", user.getUser().getUsername());
            studentJsonObject.addProperty("name", user.getUser().getPresentationName());
            studentJsonObject.addProperty("library", externalID);
            if (user.getLocker() != null) {
                studentJsonObject.addProperty("locker", user.getLocker().getNumber());
            }
            studentsJsonArray.add(studentJsonObject);
        }
        obj.add("collection", studentsJsonArray);

        if (library.getMaxOccupation() != 0) {
            obj.addProperty("percentage", library.getUserSet().size() * 100 / library.getMaxOccupation());
        } else {
            obj.addProperty("percentage", 0);
        }
        obj.addProperty("maxCapacity", library.getMaxOccupation());
        return obj.toString();
    }

    @PUT
    @Path("{id}/students/{userId}")
    @Atomic
    public void putUser(@PathParam("id") String externalID, @PathParam("userId") String userID) {
        Library library = FenixFramework.getDomainObject(externalID);
        User user = User.findByUsername(userID);
        if (library != null && user != null) {
            if (user.getLibraryUser() != null) {
                library.addUser(user.getLibraryUser());
            } else {
                //TODO throw any error
            }
        }
    }

    @GET
    @Path("{id}/availableLockers")
    @Atomic
    public String getAvailableLockers(@PathParam("id") String externalID) {
        Library library = FenixFramework.getDomainObject(externalID);
        JsonArray lockersJson = new JsonArray();
        JsonObject lockerJson = new JsonObject();
        lockerJson.addProperty("number", "Sem Cacifo");
        lockersJson.add(lockerJson);

        for (Locker locker : library.getLockerSet()) {
            if (locker.getUser() == null) {
                lockerJson = new JsonObject();
                lockerJson.addProperty("number", locker.getNumber());
                lockersJson.add(lockerJson);
            }
        }
        return lockersJson.toString();
    }
}
