package org.securityapps.vehicletracking.domain.trackerDevice;

import java.util.Objects;

public class TrackerDevice {
    private final TrackerDeviceId trackerDeviceId;
    private String serialNumber;
    private String imei;
    private String simNumber;
    private boolean isActive=true;
    private boolean isDamaged=false;

    private TrackerDevice(TrackerDeviceId trackerDeviceId, String serialNumber, String imei, String simNumber) {
        this.trackerDeviceId = trackerDeviceId;
        this.serialNumber = serialNumber;
        this.imei = imei;
        this.simNumber = simNumber;
    }

    public static TrackerDevice create(TrackerDeviceId trackerDeviceId,String serialNumber, String imei, String simNumber) {
        return new TrackerDevice(trackerDeviceId,serialNumber,imei,simNumber);
    }
    public TrackerDeviceId getTrackerDeviceId() {return trackerDeviceId;}
    public String getSerialNumber() {return serialNumber;}
    public String getImei() {return imei;}
    public String getSimNumber() {return simNumber;}

    @Override
    public boolean equals(Object object) {
        if(object==this)return true;
        if(!(object instanceof TrackerDevice trackerDevice))return false;
        return Objects.equals(trackerDeviceId,trackerDevice.trackerDeviceId);
    }
    @Override
    public int hashCode() {return Objects.hash(trackerDeviceId);}
    @Override
    public String toString() {
        return "TrackerDeviceId: %s, serialNumber: %s, imei: %s, simNumber: %s, isActive: %s, isDamaged: %s".formatted(trackerDeviceId.toString(),serialNumber,imei,simNumber,isActive,isDamaged);
    }


}
