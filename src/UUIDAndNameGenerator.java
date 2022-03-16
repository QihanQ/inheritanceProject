import java.util.UUID;

public class UUIDAndNameGenerator {
    private String UUIDName;
    private String name;

    public UUIDAndNameGenerator(String name)
    {
        UUID u = UUID.randomUUID();
        UUIDName = u.toString();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUUIDName() {
        return UUIDName;
    }
}
