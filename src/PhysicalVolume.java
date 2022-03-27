public class PhysicalVolume extends UUIDAndNameGenerator{
    private int size;
    private HardDrive HD;
    private VolumeGroup VG;
    private boolean assigned;

    public PhysicalVolume(String name, int size, HardDrive HD) {
        super(name);
        this.size = size;
        this.HD = HD;
        assigned = false;
    }

    public int getSize() {
        return size;
    }

    public void setVolumeGroup(VolumeGroup volumeGroup)
    {
        assigned = true;
        VG = volumeGroup;
    }

    public String getVolumeGroup()
    {
        return VG.getName();
    }

    public boolean isAssigned()
    {
        return assigned;
    }

    public HardDrive getHD() {
        return HD;
    }
}
