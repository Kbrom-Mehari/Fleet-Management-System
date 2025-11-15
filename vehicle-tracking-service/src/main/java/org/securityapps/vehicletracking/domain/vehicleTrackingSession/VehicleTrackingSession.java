package org.securityapps.vehicletracking.domain.vehicleTrackingSession;

import lombok.Getter;
import org.securityapps.vehicletracking.common.dto.VehicleLocationPoint;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceId;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.dto.SessionAnalytics;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class VehicleTrackingSession {
    private final VehicleTrackingSessionId sessionId;
    private final TrackerDeviceId trackerDeviceId;
    private final List<VehicleLocationPoint> locationPoints=new ArrayList<>();
    private Instant lastUpdate;
    private final Instant startedAt;
    private Instant endedAt;
    private double totalDistanceCovered;

    private static final double SPEED_LIMIT=90;

    private VehicleTrackingSession(TrackerDeviceId trackerDeviceId) {
        this.sessionId = VehicleTrackingSessionId.newId();
        this.trackerDeviceId =Objects.requireNonNull(trackerDeviceId,"Tracker Device ID must not be null");
        this.startedAt = Instant.now();
    }
    public static VehicleTrackingSession start(TrackerDeviceId trackerDeviceId) {
        return new VehicleTrackingSession(trackerDeviceId);
    }

    public void addLocationPoint(VehicleLocationPoint locationPoint) {
        if(!locationPoints.isEmpty()){
            var last=locationPoints.getLast();
            totalDistanceCovered+=haversine(last.latitude(), last.longitude(),
                    locationPoint.latitude(), locationPoint.longitude());
        }
        locationPoints.add(locationPoint);
        lastUpdate=locationPoint.recordedAt();
    }

    public SessionAnalytics endSession() {
        endedAt = Instant.now();
        return new SessionAnalytics(
                Duration.between(startedAt,endedAt),
                totalDistanceCovered,
                computeAverageSpeed(),
                computeMaximumSpeed(),
                computeSpeedLimitViolations()
                );
    }
    public boolean isEnded(){return endedAt!=null;}

    public double computeMaximumSpeed(){
        double maxSpeed=0;
        for (var locationPoint : locationPoints) {
            if (locationPoint.speed()!=null&&locationPoint.speed() > maxSpeed) {
                maxSpeed = locationPoint.speed();
            }
        }
        return maxSpeed;
    }
    public double computeAverageSpeed(){
        if(locationPoints.isEmpty()) return 0;
       double totalSpeed=0;
       int count=0;
        for(var locationPoint : locationPoints) {
            if(locationPoint.speed()!=null){
                totalSpeed+=locationPoint.speed();
                count++;
            }
        }
        return count==0?0:totalSpeed/count;  //prevent division by zero
    }
    public int computeSpeedLimitViolations(){
        int violations=0;
        for(var locationPoint : locationPoints) {
            if(locationPoint.speed()!=null&&locationPoint.speed()>SPEED_LIMIT){
                violations++;
            }
        }
        return violations;
    }

    private double haversine(double lat1,double lon1,double lat2,double lon2) {
        final int EARTH_RADIUS = 6371000; // radius of the earth in meters
        double dLat=Math.toRadians(lat2-lat1);
        double dLon=Math.toRadians(lon2-lon1);
        double rLat1=Math.toRadians(lat1);
        double rLat2=Math.toRadians(lat2);

        double a =Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(rLat1) * Math.cos(rLat2)
                * Math.sin(dLon/2) * Math.sin(dLon/2);

        double c= 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return EARTH_RADIUS * c; // returns distance in meters
    }

}
