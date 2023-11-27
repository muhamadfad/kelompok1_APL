package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.LocationDAO;
import lk.ijse.dep.pharmacy.entity.Location;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class LocationDAOImpl implements LocationDAO {

    @Override
    public String getLastLocation() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT locationId FROM Location ORDER BY locationId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    @Override
    public List<Location> findAvailableLocations() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Location WHERE locationStatus=TRUE");
        List<Location> locations = new ArrayList<>();
        while (rst.next()) {
            locations.add(new Location(rst.getString(1),
                    rst.getString(2),
                    rst.getBoolean(3)
            ));
        }
        return locations;
    }

    @Override
    public List<Location> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Location");
        List<Location> locations = new ArrayList<>();
        while (rst.next()) {
            locations.add(new Location(rst.getString(1),
                    rst.getString(2),
                    rst.getBoolean(3)
            ));
        }
        return locations;
    }

    @Override
    public Location find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Location WHERE locationId=?", s);
        if (rst.next()) {
            return new Location(rst.getString(1),
                    rst.getString(2),
                    rst.getBoolean(3)
            );
        }
        return null;
    }

    @Override
    public boolean save(Location entity) throws Exception {
        return CrudUtil.execute("INSERT INTO Location VALUES (?,?,?)",
                entity.getLocationId(), entity.getLocationDes(), entity.isLocationStatus());
    }

    @Override
    public boolean update(Location location) throws Exception {
        return CrudUtil.execute("UPDATE Location SET locationDes=?, locationStatus=? WHERE locationId=?",
                location.getLocationDes(), location.isLocationStatus(), location.getLocationId());

    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Location WHERE locationId=?", s);
    }

    @Override
    public List<Location> search(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Location " +
                        "WHERE locationId LIKE ? OR locationDes LIKE ? OR locationStatus LIKE ?",
                query,query,query);
        List<Location> locations = new ArrayList<>();
        while (rst.next()) {
            locations.add(new Location(rst.getString(1),
                    rst.getString(2),
                    rst.getBoolean(3)
            ));
        }
        return locations;
    }
}
