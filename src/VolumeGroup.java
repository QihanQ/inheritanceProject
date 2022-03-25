import java.util.ArrayList;

public class VolumeGroup extends UUIDAndNameGenerator{
    private int size;
    private PhysicalVolume PV;
    private ArrayList<PhysicalVolume> physVol;
    private ArrayList<LogicalVolume> logVol;

    public VolumeGroup(String name, PhysicalVolume PV)
    {
        super(name);
        this.PV = PV;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<PhysicalVolume> getPhysVol() {
        return physVol;
    }

    public ArrayList<LogicalVolume> getLogVol() {
        return logVol;
    }
}
