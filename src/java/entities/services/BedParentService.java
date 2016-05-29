/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.services;

import entities.BedParent;
import entities.BedSubject;
import entities.JdStudent;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedParentService extends NewAbstractEntityService<BedParent> {

    private static final long serialVersionUID = 1L;
    
    private BedParentService() {
    }

    @Override
    public Class getEntityClass() {
        return BedParent.class;
    }

    private static BedParentService instance;

    public static BedParentService getInstance() {
        if (instance == null) {
            instance = new BedParentService();
        }
        return instance;
    }

    public boolean parentRecordAlreadyExists(BedParent parent) {
        boolean newItem = parent.getId() == null;
        EntityManager m = getNewEntityManager();
        List<BedParent> rs = m.createQuery("SELECT c FROM BedParent c WHERE c.firstName LIKE ?1 AND c.middleName LIKE ?2 AND c.lastName LIKE ?3", BedParent.class).setParameter(1, parent.getFirstName()).setParameter(2, parent.getMiddleName()).setParameter(3, parent.getLastName()).getResultList();
        if (!rs.isEmpty()) {
            if (!newItem) {
                for (BedParent bedParent : rs) {
                    boolean equals = bedParent.getId().equals(parent.getId());
                    if (equals) {
                        m.close();
                        return false;
                    }
                }
            } else {

                return true;
            }
        }
        return false;
    }

    public BedParent findByParentId(Integer id) {
        EntityManager m = getNewEntityManager();
        List<BedParent> rs
                = m.createQuery("SELECT c FROM BedParent c "
                        + "WHERE c.parentId = ?1", BedParent.class)
                .setParameter(1, id)
                .getResultList();
        m.close();
        if (rs.isEmpty()) {
            return null;
        }
        return rs.get(0);
    }

    public boolean hasDependents(Integer id) {
        EntityManager m = getNewEntityManager();
        long rs
                = m.createQuery("SELECT COUNT(c) FROM JdStudent c "
                        + "WHERE c.parentFather.id = ?1 OR c.parentMother.id = ?1 "
                        + "OR c.parentGuardian.id = ?1", Long.class)
                .setParameter(1, id)
                .getSingleResult();
        m.close();
        return rs > 0;
    }

    public List<BedParent> search(String keyword) {

        if (keyword == null) {
            keyword = "";
        }

        EntityManager m = getNewEntityManager();
        List<BedParent> parentList
                = m.createQuery("SELECT s FROM BedParent s "
                        + "WHERE s.lastName like ?1 "
                        + "OR s.firstName like ?1 "
                        + "OR s.middleName LIKE ?1 "
                        + "ORDER BY s.lastName", BedParent.class)
                .setParameter(1, "%" + keyword + "%")
                .setMaxResults(50).getResultList();
        m.close();
        return parentList;
    }

    public List<BedParent> completeParent(String key) {
        if (key == null) {
            key = "";
        }

        EntityManager m = getNewEntityManager();

        List<BedParent> parentList;

        if (key.contains(",")) {
            String[] split = key.split(",");
            String lastname = split[0];
            if (split.length == 1) {
                parentList
                        = m.createQuery("SELECT s FROM BedParent s "
                                + "WHERE s.lastName like ?1 "
                                + "ORDER BY s.lastName", BedParent.class)
                        .setParameter(1, "%" + lastname + "%")
                        .setMaxResults(300)
                        .getResultList();
            } else {
                String rest = split[1];
                parentList
                        = m.createQuery("SELECT s FROM BedParent s "
                                + "WHERE s.lastName like ?1 "
                                + "AND (s.firstName like ?2 "
                                + "OR s.middleName LIKE ?2) "
                                + "ORDER BY s.lastName", BedParent.class)
                        .setParameter(1, "%" + lastname + "%")
                        .setParameter(1, "%" + rest + "%")
                        .setMaxResults(300)
                        .getResultList();
            }
        } else {
            parentList
                    = m.createQuery("SELECT s FROM BedParent s "
                            + "WHERE s.lastName like ?1 "
                            + "OR s.firstName like ?1 "
                            + "OR s.middleName LIKE ?1 "
                            + "ORDER BY s.lastName", BedParent.class)
                    .setParameter(1, "%" + key + "%")
                    .setMaxResults(300)
                    .getResultList();
        }

        m.close();

        return parentList;
    }

}
