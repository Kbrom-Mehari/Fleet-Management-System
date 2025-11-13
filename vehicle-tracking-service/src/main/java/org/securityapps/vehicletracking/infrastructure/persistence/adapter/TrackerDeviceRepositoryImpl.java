package org.securityapps.vehicletracking.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDevice;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceId;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceRepository;
import org.securityapps.vehicletracking.infrastructure.persistence.repository.JpaTrackerDeviceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrackerDeviceRepositoryImpl implements TrackerDeviceRepository {
    private final JpaTrackerDeviceRepository jpaTrackerDeviceRepository;

    @Override
    public void save(TrackerDevice trackerDevice){

    }
    @Override
    public Optional<TrackerDevice> findById(TrackerDeviceId id){
        return Optional.empty();
    }
    @Override
    public Optional<TrackerDevice> findByImei(String imei){
        return Optional.empty();
    }
    @Override
    public List<TrackerDevice> findInactive(){
        return null;
    }

}
