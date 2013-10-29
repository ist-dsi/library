package pt.ist.library.domain;

import org.apache.commons.lang.StringUtils;

import pt.ist.library.utils.Verhoeff;

public class LibrarySystem extends LibrarySystem_Base {

    private static final String MILLENIUM_INSTITUTION_PREFIX = "0710";

    private static final int COUNTER_SIZE = 5;

    private static final String CODE_FILLER = "0";

    public LibrarySystem() {
        super();
    }

    public String generateVarhoeffCode() {
        String baseCode =
                MILLENIUM_INSTITUTION_PREFIX
                        + StringUtils.leftPad(Integer.toString(getMilleniumCodeCounter()), COUNTER_SIZE, CODE_FILLER);
        setMilleniumCodeCounter(getMilleniumCodeCounter() + 1);
        return baseCode + Verhoeff.generateVerhoeff(baseCode);

    }
}
