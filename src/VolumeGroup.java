import java.util.ArrayList;

public class VolumeGroup extends UUIDAndNameGenerator{
    private int size;
    private PhysicalVolume PV;
    private ArrayList<PhysicalVolume> physVol = new ArrayList<>();
    private ArrayList<LogicalVolume> logVol = new ArrayList<>();

    public VolumeGroup(String name, PhysicalVolume PV)
    {
        super(name);
        this.PV = PV;
        physVol.add(PV);
    }

    public int getSize() {
        size = 0;
        for(int n = 0; n < physVol.size(); n++)
        {
            size += physVol.get(n).getSize();
        }
        return size;
    }

    public void addPhysicalVolume(PhysicalVolume pv)
    {
        physVol.add(pv);
    }

    public void addLogicalVolume(LogicalVolume lv)
    {
        logVol.add(lv);
    }

    public boolean hasLogicalVolume()
    {
        return logVol.size() > 0;
    }
    public int getAvailableSpace()
    {
        int used = 0;
        size = getSize();
        for(int n = 0; n < logVol.size(); n++)
        {
            used += logVol.get(n).getSize();
        }
        return size - used;
    }

    public ArrayList<PhysicalVolume> getPhysVol() {
        return physVol;
    }

    public ArrayList<LogicalVolume> getLogVol() {
        return logVol;
    }
}
