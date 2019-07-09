package de.thb.fbi.msr.maus.einkaufsliste.remote;

import de.thb.fbi.msr.maus.einkaufsliste.model.LoginAccessor;
import de.thb.fbi.msr.maus.einkaufsliste.model.LoginItem;
import org.apache.log4j.Logger;

public class RemoteLoginAccessor implements LoginAccessor {
    protected static Logger logger = Logger
            .getLogger(RemoteLoginAccessor.class);

    @Override
    public boolean login(LoginItem login) {
        logger.info(login.getUsername());
        logger.info(login.getPassword());
        return (login.getUsername().equals("Tobi@me.de"));
    }
}