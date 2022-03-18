import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the LVM system! Enter your commands:");
        String i = input.nextLine();

        ArrayList<HardDrive> hd = new ArrayList<HardDrive>();
        ArrayList<PhysicalVolume>  pv = new ArrayList<PhysicalVolume>();

        while(i != "exit")
        {
            if(i.indexOf("install-drive ") == 0)
            {
                boolean exists = false;
                i = i.substring(14);
                String n = i.substring(0, i.indexOf(" "));
                for(int num = 0; num < hd.size(); num++)
                {
                    if(hd.get(num).getName().equals(n))
                    {
                        System.out.println("This hard drive name already exists.");
                        exists = true;
                        break;
                    }
                }
                if(!exists)
                {
                    i = i.substring(i.indexOf(" ") + 1);
                    int s = Integer.parseInt(i.substring(0, i.length() - 1));
                    HardDrive h = new HardDrive(n,s);
                    hd.add(h);
                    System.out.println("Drive " + n + " installed.\n");
                }
            }

            else if(i.indexOf("list-drives") == 0)
            {
                for(int n = 0; n < hd.size(); n++)
                {
                    System.out.println(hd.get(n).getName() + " [" + hd.get(n).getSize() + "G]");
                }
                System.out.println("");
            }

            else if(i.indexOf("pvcreate ") == 0)
            {
                boolean error = true;
                i = i.substring(i.indexOf(" " + 1));
                String name = i.substring(0, i.indexOf(" "));
                i = i.substring(i.indexOf(" ") + 1);
                String driveName = i;
                for(int n = 0; n < hd.size(); n++)
                {
                    if(hd.get(n).getName().equals(driveName))
                    {
                        error = false;
                        break;
                    }
                }
                if(error == false)
                {
                    System.out.println("This hard drive already exists");
                }
                else
                {
                    error = false;
                    for(int n = 0; n < pv.size(); n++)
                    {
                        if(pv.get(n).getName().equals(name))
                        {
                            System.out.println("Physical Volume name already exists.");
                            error = true;
                            break;
                        }

                        if(pv.get(n).getHD().equals(driveName))
                        {
                            System.out.println(driveName + " is associated with an already created PV");
                            error = true;
                            break;
                        }
                    }
                    if(!error)
                    {
                        PhysicalVolume p = new PhysicalVolume(name, driveName);
                        pv.add()
                    }
                }
            }
        }
    }
}
