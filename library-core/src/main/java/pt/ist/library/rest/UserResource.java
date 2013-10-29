package pt.ist.library.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import pt.ist.bennu.core.domain.Bennu;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.rest.BennuRestResource;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.library.domain.Library;
import pt.ist.library.domain.LibrarySystem;
import pt.ist.library.domain.LibraryUser;
import pt.ist.library.domain.Locker;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("user")
public class UserResource extends BennuRestResource {

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchUser(@PathParam("id") String studentId) {
        accessControl("#libraryOperators");
        JsonObject studentJsonObject = new JsonObject();
        User user = User.findByUsername(studentId);
        if (user != null) {
            studentJsonObject.addProperty("exists", 1);
            studentJsonObject.addProperty("id", user.getUsername());
            studentJsonObject.addProperty("name", user.getPresentationName());

            //studentJsonObject.addProperty("photo", );
            LibraryUser libraryUser = user.getLibraryUser();
            if (libraryUser != null) {
                studentJsonObject.addProperty("milleniumNumber", libraryUser.getMilleniumCode());
                if (libraryUser.getLibrary() != null) {
                    JsonObject library = new JsonObject();
                    library.addProperty("name", libraryUser.getLibrary().getName());
                    library.addProperty("id", libraryUser.getLibrary().getExternalId());
                    studentJsonObject.add("onLibrary", library);
                    if (libraryUser.getLocker() != null) {
                        studentJsonObject.addProperty("locker", libraryUser.getLocker().getNumber());
                    }
                }
            }
        } else {
            studentJsonObject.addProperty("exists", 0);
        }

        return studentJsonObject.toString();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Atomic(mode = TxMode.WRITE)
    public void removeUser(@PathParam("id") String studentId) {
        accessControl("#libraryOperators");
        User user = User.findByUsername(studentId);
        if (user != null && user.getLibraryUser() != null) {
            LibraryUser libraryUser = user.getLibraryUser();
            if (libraryUser.getLibrary() != null) {
                libraryUser.getLibrary().removeUser(libraryUser);
            }
            if (libraryUser.getLocker() != null) {
                libraryUser.getLocker().setUser(null);
                libraryUser.setLocker(null);
            }
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Atomic(mode = TxMode.WRITE)
    public void addUser(@PathParam("id") String studentId, String json) {
        accessControl("#libraryOperators");
        removeUser(studentId);
        User user = User.findByUsername(studentId);
        if (user != null && user.getLibraryUser() != null) {
            Library library = FenixFramework.getDomainObject(getAttributeFromJSON(json, "library"));
            if (library.getUserSet().size() < library.getMaxOccupation()) {
                user.getLibraryUser().setLibrary(library);
            } else {
                throw new WebApplicationException();
            }
            String lockerNumberString = getAttributeFromJSON(json, "locker");
            int lockerNumber = -1;
            try {
                lockerNumber = Integer.parseInt(lockerNumberString);
            } catch (Exception e) {
                //this means either the received value wasn't a number or didn't exist
            }
            if (lockerNumber != -1) {
                for (Locker locker : library.getLockerSet()) {
                    if (locker.getNumber() == lockerNumber && locker.getUser() == null) {
                        locker.setUser(user.getLibraryUser());
                        break;
                    }
                }
            }
        } else if (user != null && user.getLibraryUser() == null && getAttributeFromJSON(json, "generateCode") != null) {
            LibrarySystem system = Bennu.getInstance().getLibrarySystem();
            String milleniumCode = system.generateVarhoeffCode();
            LibraryUser libraryUser = new LibraryUser();
            libraryUser.setUser(user);
            libraryUser.setMilleniumCode(milleniumCode);
        }
    }

    String getAttributeFromJSON(String json, String attribute) {
        accessControl("#libraryOperators");
        JsonParser parser = new JsonParser();
        JsonElement object = parser.parse(json);
        return object.getAsJsonObject().get(attribute).getAsString();
    }
}
