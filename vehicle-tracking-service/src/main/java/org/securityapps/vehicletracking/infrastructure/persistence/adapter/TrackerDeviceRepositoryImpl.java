package org.securityapps.vehicletracking.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDevice;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceId;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceRepository;
import org.securityapps.vehicletracking.infrastructure.persistence.mapper.TrackerDeviceMapper;
import org.securityapps.vehicletracking.infrastructure.persistence.repository.JpaTrackerDeviceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrackerDeviceRepositoryImpl implements TrackerDeviceRepository {
    private final TrackerDeviceMapper mapper;
    private final JpaTrackerDeviceRepository jpaTrackerDeviceRepository;

    @Override
    public void save(TrackerDevice trackerDevice){
        var entity=mapper.toEntity(trackerDevice);
        jpaTrackerDeviceRepository.save(entity);

    }
    @Override
    public Optional<TrackerDevice> findById(TrackerDeviceId id){
        var entity=jpaTrackerDeviceRepository.findById(id.toString()).orElseThrow(()->new RuntimeException("Device not found"));
        return Optional.ofNullable(mapper.toDomain(entity));
    }
    @Override
    public Optional<TrackerDevice> findByImei(String imei){
        var entity=jpaTrackerDeviceRepository.findByImei(imei).orElseThrow(()->new RuntimeException("Device not found"));
        return Optional.ofNullable(mapper.toDomain(entity));
    }
    @Override
    public List<TrackerDevice> findInactive(){
        return null;
    }

}
