import java.util.ArrayList;

public class VolumeGroup extends UUIDAndNameGenerator{
    private int size;
    private ArrayList<PhysicalVolume> physVol;
    private ArrayList<LogicalVolume> logVol;

    public VolumeGroup(String name, int size, ArrayList<PhysicalVolume> pv, ArrayList<LogicalVolume> lv)
    {
        super(name);
        this.size = size;
        physVol = pv;
        logVol = lv;
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
