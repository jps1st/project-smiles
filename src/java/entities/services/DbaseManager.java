/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.services;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public class DbaseManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private final static EntityManagerFactory entityManagerFactory 
            = Persistence.createEntityManagerFactory("smilespu");

    public static EntityManager getNewEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

}
