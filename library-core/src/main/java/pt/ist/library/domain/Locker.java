package pt.ist.library.domain;

public class Locker extends Locker_Base {

    public Locker() {
        super();
    }

    public void delete() {
        getLibrary().removeLocker(this);
        this.setUser(null);
        deleteDomainObject();
    }
}
