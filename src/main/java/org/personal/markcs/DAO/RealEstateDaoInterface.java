package org.personal.markcs.DAO;

import org.personal.markcs.Model.Plot;
import org.personal.markcs.Model.RealEstate;

import java.util.List;

public interface RealEstateDaoInterface {
    public boolean addRealEstate(RealEstate realEstate);
    public RealEstate getRealEstateById(int id);
    public List<RealEstate> getAllRealEstates();
}
