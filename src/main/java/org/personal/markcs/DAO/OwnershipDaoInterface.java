package org.personal.markcs.DAO;

import org.personal.markcs.Model.Ownership;
import org.personal.markcs.Model.OwnershipType;

import java.util.List;

public interface OwnershipDaoInterface {
    public boolean addOwnership(Ownership ownership, OwnershipType type);
    public Ownership getOwnershipByTaxId(String TaxId);
    public List<Ownership> getAllOwnerships();
}
