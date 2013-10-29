package pt.ist.library.presentationTier.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.bennu.core.domain.Bennu;
import pt.ist.bennu.core.domain.groups.DynamicGroup;
import pt.ist.bennu.core.domain.groups.NobodyGroup;
import pt.ist.bennu.core.servlets.BennuCoreContextListener;
import pt.ist.fenixframework.Atomic;
import pt.ist.library.domain.LibrarySystem;

@WebListener
public class LibraryInitializer implements ServletContextListener {

    @Override
    @Atomic
    public void contextInitialized(ServletContextEvent event) {
        if (Bennu.getInstance().getLibrarySystem() == null) {
            LibrarySystem system = new LibrarySystem();
            Bennu.getInstance().setLibrarySystem(system);
        }

        Logger logger = LoggerFactory.getLogger(BennuCoreContextListener.class);
        logger.info("Initializing Library Module");

        if (Bennu.getInstance().getLibrarySystem().getOperators() == null) {
            DynamicGroup.initialize("libraryOperators", NobodyGroup.getInstance());
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }
}
