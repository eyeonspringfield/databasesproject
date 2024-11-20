package org.personal.markcs.DAO;

import org.personal.markcs.Model.Plot;

import java.util.List;

public interface PlotDaoInterface {
    public boolean addPlot(Plot plot);
    public Plot getPlotById(int id);
    public List<Plot> getAllPlots();
}
