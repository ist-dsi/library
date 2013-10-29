package pt.ist.library.domain;

import pt.ist.fenixframework.Atomic;

public class Library extends Library_Base {

    public Library() {
        super();
    }

    @Atomic
    public void setLockersCapacity(int newLockersCapacity) {
        if (getLockerSet().size() < newLockersCapacity) {
            for (int i = getLockerSet().size(); i < newLockersCapacity; i++) {
                Locker l = new Locker();
                l.setNumber(i + 1);
                l.setLibrary(this);
            }
        } else if (getLockerSet().size() > newLockersCapacity) {
            Object[] lockersArray = getLockerSet().toArray();
            for (int i = newLockersCapacity; i < getLockerSet().size(); i++) {
                Locker locker = (Locker) lockersArray[i];
                locker.delete();
            }
        }
    }
}
