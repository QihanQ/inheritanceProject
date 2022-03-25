import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the LVM system! Enter your commands:");
        System.out.print("cmd# ");
        String i = input.nextLine();

        ArrayList<HardDrive> hd = new ArrayList<HardDrive>();
        ArrayList<PhysicalVolume>  pv = new ArrayList<PhysicalVolume>();
        ArrayList<VolumeGroup> vg = new ArrayList<VolumeGroup>();
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
                        System.out.println("This hard drive name already exists.\n");
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
                i = i.substring(i.indexOf(" ") + 1);
                String name = i.substring(0, i.indexOf(" "));
                i = i.substring(i.indexOf(" ") + 1);
                String driveName = i;
                int hdNum = -1;
                for(int n = 0; n < hd.size(); n++)
                {
                    if(hd.get(n).getName().equals(driveName))
                    {
                        hdNum = n;
                        error = false;
                        break;
                    }
                }
                if(error == true)
                {
                    System.out.println("This hard drive does not exist\n");
                }
                else
                {
                    error = false;
                    for(int n = 0; n < pv.size(); n++)
                    {
                        if(pv.get(n).getName().equals(name))
                        {
                            System.out.println("Physical Volume name already exists.\n");
                            error = true;
                            break;
                        }

                        if(pv.get(n).getHD().getName().equals(driveName))
                        {
                            System.out.println(hd.get(hdNum).getName() + " is associated with an already created PV\n");
                            error = true;
                            break;
                        }
                    }
                    if(!error)
                    {
                        PhysicalVolume p = new PhysicalVolume(name, hd.get(hdNum).getSize(), hd.get(hdNum));
                        pv.add(p);
                        System.out.println("Physical drive " + p.getName() + " created\n");
                    }

                }
            }

            else if(i.indexOf("pvlist") == 0)
            {

            }

            else if(i.indexOf("vgcreate") == 0) {
                i = i.substring(i.indexOf(" ") + 1);
                String name = i.substring(0, i.indexOf(" "));
                i = i.substring(i.indexOf(" ") + 1);
                String pvName = i;
                boolean error = true;
                int pvNum = -1;
                for (int n = 0; n < pv.size(); n++) {
                    if (pv.get(n).getName().equals(pvName)) {
                        pvNum = n;
                        error = false;
                        break;
                    }
                }
                if (error == true) {
                    System.out.println("This physical volume does not exist\n");
                } else {
                    error = false;
                    for (int n = 0; n < vg.size(); n++) {
                        if (vg.get(n).getName().equals(name)) {
                            System.out.println("Volume group name already exists.\n");
                            error = true;
                            break;
                        }

                        if (pv.get(n).getName().equals(pvName)) {
                            if(pv.get(n).isAssigned())
                            {
                                System.out.println(pv.get(n).getName() + " is associated with an already created VG\n");
                                error = true;
                                break;
                            }
                        }
                    }
                    if (!error) {
                        VolumeGroup v = new VolumeGroup(name, pv.get(pvNum));
                        vg.add(v);
                        pv.get(pvNum).setVolumeGroup(v);
                        System.out.println("Volume Group " + v.getName() + " created\n");
                    }
                }
            }
            System.out.print("cmd# ");
            i = input.nextLine();
        }
    }
}
