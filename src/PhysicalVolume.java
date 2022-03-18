public class PhysicalVolume extends UUIDAndNameGenerator{
    private int size;
    private String UUID;
    private HardDrive HD;

    public PhysicalVolume(String name, int size, HardDrive HD) {
        super(name);
        this.size = size;
        this.HD = HD;
    }

    public int getSize() {
        return size;
    }

    public String getUUID() {
        return UUID;
    }

    public HardDrive getHD() {
        return HD;
    }
}
