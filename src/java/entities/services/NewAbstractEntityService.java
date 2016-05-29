/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.services;

import java.io.Serializable;
import javax.persistence.EntityManager;

public abstract class NewAbstractEntityService<G> implements Serializable{

    private static final long serialVersionUID = 1L;

    public abstract Class getEntityClass();

    public G refresh(G object) {
        EntityManager m = getNewEntityManager();
        object = m.merge(object);
        m.refresh(object);
        m.close();
        return object;
    }

    public G find(Object id) {
        G found;
        try {
            EntityManager m = getNewEntityManager();
            found = (G) m.find(getEntityClass(), id);
            m.close();
        } catch (Exception e) {
            return null;
        }
        return found;
    }

    public void remove(G toRemove) {
        EntityManager m = getNewEntityManager();
        m.getTransaction().begin();
        m.remove(m.merge(toRemove));
        m.getTransaction().commit();
        m.close();
    }

    public G update(G updatedInstance) {

        EntityManager m = getNewEntityManager();
        m.getTransaction().begin();
        G merge = m.merge(updatedInstance);
        m.getTransaction().commit();
        m.close();
        return merge;
    }

    public G insert(G toInsert) {
        return update(toInsert);
    }

    public EntityManager getNewEntityManager() {
        return DbaseManager.getNewEntityManager();
    }

}
