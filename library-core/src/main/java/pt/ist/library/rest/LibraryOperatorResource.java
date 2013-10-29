package pt.ist.library.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pt.ist.bennu.core.domain.Bennu;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.groups.Group;
import pt.ist.bennu.core.rest.BennuRestResource;
import pt.ist.fenixframework.Atomic;
import pt.ist.library.domain.LibrarySystem;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Path("libraryOperators")
public class LibraryOperatorResource extends BennuRestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listOperators() {
        accessControl("#libraryOperators");
        Group operators = Bennu.getInstance().getLibrarySystem().getOperators();
        JsonArray operatorsJSon = new JsonArray();
        for (User user : operators.getMembers()) {
            JsonObject userJson = new JsonObject();
            userJson.addProperty("id", user.getUsername());
            userJson.addProperty("name", user.getPresentationName());
            operatorsJSon.add(userJson);
        }
        return operatorsJSon.toString();
    }

    @DELETE
    @Path("{id}")
    @Atomic
    public void removeOperator(@PathParam("id") String userName) {
        accessControl("#libraryOperators");
        LibrarySystem system = Bennu.getInstance().getLibrarySystem();
        if (system.getOperators().getMembers().size() == 1) {
            throw new RuntimeException();
        }
        system.getOperators().setGroup(system.getOperators().revoke(User.findByUsername(userName)));

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String findPerson(@PathParam("id") String userName) {
        accessControl("#libraryOperators");
        JsonObject userJson = new JsonObject();
        User user = User.findByUsername(userName);
        if (user != null) {
            userJson.addProperty("id", userName);
            userJson.addProperty("name", user.getUsername());
            userJson.addProperty("exists", true);
            if (Bennu.getInstance().getLibrarySystem().getOperators().isMember(user)) {
                userJson.addProperty("isOperator", true);
            }
        } else {
            userJson.addProperty("exists", false);
        }
        return userJson.toString();
    }

    @PUT
    @Path("{id}")
    @Atomic
    public void addNewOperator(@PathParam("id") String userName) {
        accessControl("#libraryOperators");
        User user = User.findByUsername(userName);
        if (user != null) {
            LibrarySystem system = Bennu.getInstance().getLibrarySystem();
            system.getOperators().setGroup(system.getOperators().grant(user));
        } else {
            throw new RuntimeException();
        }
    }
}
