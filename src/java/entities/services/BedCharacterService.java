 
package entities.services;

import entities.BedCharacterTraits;

public final class BedCharacterService extends NewAbstractEntityService<BedCharacterTraits> {
    
    private static final long serialVersionUID = 1L;

    private BedCharacterService() {}

    @Override
    public Class getEntityClass() {
        return BedCharacterTraits.class;
    }

    private static BedCharacterService instance;

    public static BedCharacterService getInstance() {
        if (instance == null) {
            instance = new BedCharacterService();
        }
        return instance;
    }

}
