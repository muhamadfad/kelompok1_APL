package lk.ijse.dep.pharmacy.business.custom;

import lk.ijse.dep.pharmacy.business.SuperBO;
import lk.ijse.dep.pharmacy.dto.DoctorDTO;
import lk.ijse.dep.pharmacy.dto.LocationDTO;

import java.util.List;

public interface LocationBO extends SuperBO {
    boolean saveLocation(LocationDTO location)throws Exception;

    boolean updateLocation(LocationDTO location)throws Exception;

    boolean deleteLocation(String locationId) throws Exception;

    List<LocationDTO> findAllLocation() throws Exception;

    String getLastLocationId()throws Exception;

    LocationDTO findLocation(String locationId) throws Exception;

    List<String> getAllLocationIds() throws Exception;

    List<LocationDTO> searchLocation(String query) throws Exception;

    List<String> getAllAvailableLocationIds() throws Exception;

}
