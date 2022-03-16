public class LogicalVolume extends UUIDAndNameGenerator{
    private int size;
    private VolumeGroup VG;

    public LogicalVolume(String name, int size, VolumeGroup VG) {
        super(name);
        this.size = size;
        this.VG = VG;
    }


}
