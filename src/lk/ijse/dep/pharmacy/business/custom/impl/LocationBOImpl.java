package lk.ijse.dep.pharmacy.business.custom.impl;

import lk.ijse.dep.pharmacy.business.custom.LocationBO;
import lk.ijse.dep.pharmacy.dao.DAOFactory;
import lk.ijse.dep.pharmacy.dao.DAOTypes;
import lk.ijse.dep.pharmacy.dao.custom.DrugItemDAO;
import lk.ijse.dep.pharmacy.dao.custom.LocationDAO;
import lk.ijse.dep.pharmacy.dto.LocationDTO;
import lk.ijse.dep.pharmacy.entity.Location;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class LocationBOImpl implements LocationBO {

    private LocationDAO locationDAO = DAOFactory.getInstance().getDAO(DAOTypes.LOCATION);
    private DrugItemDAO drugItemDAO = DAOFactory.getInstance().getDAO(DAOTypes.DRUG_ITEM);


    @Override
    public boolean saveLocation(LocationDTO location) throws Exception {
        return locationDAO.save(new Location(location.getId(), location.getDes(), location.isStatus()));
    }

    @Override
    public boolean updateLocation(LocationDTO location) throws Exception {
        return locationDAO.update(new Location(location.getId(), location.getDes(), location.isStatus()));
    }

    @Override
    public boolean deleteLocation(String locationId) throws Exception {
        //        if (orderDAO.existsByCustomerId(customerId)){
//            throw new AlreadyExistsInOrderException("Customer already exists in an order, hence unable to delete");
//        }
        return locationDAO.delete(locationId);
    }

    @Override
    public List<LocationDTO> findAllLocation() throws Exception {
        List<Location> alLocation = locationDAO.findAll();
        List<LocationDTO> dtos = new ArrayList<>();

        for (Location location : alLocation) {
            dtos.add(new LocationDTO(location.getLocationId(),location.getLocationDes(),location.isLocationStatus()));
        }
        return dtos;
    }

    @Override
    public String getLastLocationId() throws Exception {
        return locationDAO.getLastLocation();
    }

    @Override
    public LocationDTO findLocation(String locationId) throws Exception {
        Location location = locationDAO.find(locationId);
        return new LocationDTO(location.getLocationId(), location.getLocationDes(), location.isLocationStatus());
    }

    @Override
    public List<String> getAllLocationIds() throws Exception {
        List<Location> locations = locationDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (Location location : locations) {
            ids.add(location.getLocationId());
        }
        return ids;
    }

    @Override
    public List<LocationDTO> searchLocation(String query) throws Exception {
        List<Location> search = locationDAO.search(query + "%");
        List<LocationDTO> dtos = new ArrayList<>();

        for (Location searchLocation : search) {
            dtos.add(new LocationDTO(searchLocation.getLocationId(), searchLocation.getLocationDes(), searchLocation.isLocationStatus()));
        }
        return dtos;
    }

    @Override
    public List<String> getAllAvailableLocationIds() throws Exception {
        List<Location> locations = locationDAO.findAvailableLocations();
        List<String> ids = new ArrayList<>();
        for (Location location : locations) {
            ids.add(location.getLocationId());
        }
        return ids;
    }
}
