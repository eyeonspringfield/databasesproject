package org.personal.markcs.DAO;

import org.personal.markcs.Model.Ownership;

import java.util.List;

public interface OwnershipDaoInterface {
    public boolean addOwnership(Ownership ownership);
    public Ownership getOwnershipByTaxId(String TaxId);
    public List<Ownership> getAllOwnerships();
}
