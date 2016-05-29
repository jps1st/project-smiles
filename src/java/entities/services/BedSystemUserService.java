/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.services;

import entities.BedSystemUser;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedSystemUserService extends NewAbstractEntityService<BedSystemUser> {

    private static final long serialVersionUID = 1L;

    private BedSystemUserService() {
    }

    @Override
    public Class getEntityClass() {
        return BedSystemUser.class;
    }

    private static BedSystemUserService instance;

    public static BedSystemUserService getInstance() {
        if (instance == null) {
            instance = new BedSystemUserService();
        }
        return instance;
    }

    public BedSystemUser checkForLogin(String username, String password) {
        EntityManager em = getNewEntityManager();
        List<BedSystemUser> c = em.createQuery("SELECT s FROM BedSystemUser s "
                + "WHERE s.active = true "
                + "AND s.username like ?1 "
                + "AND s.password LIKE ?2", BedSystemUser.class)
                .setParameter(1, username).setParameter(2, password).getResultList();
        em.close();
        if (c.isEmpty()) {
            return null;
        }

        return c.get(0);

    }

}
