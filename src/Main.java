import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        /*File file = new File("info.txt");    //work in progress
        FileWriter fw = new FileWriter(file,true);
        PrintWriter pw = new PrintWriter(fw);*/

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the LVM system! Enter your commands:");
        System.out.print("cmd# ");
        String i = input.nextLine();
        boolean end = false;

        ArrayList<HardDrive> hd = new ArrayList<HardDrive>();
        ArrayList<PhysicalVolume>  pv = new ArrayList<PhysicalVolume>();
        ArrayList<VolumeGroup> vg = new ArrayList<VolumeGroup>();
        ArrayList<LogicalVolume> lv = new ArrayList<LogicalVolume>();

        while(!end)
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
                for(int n = 0; n < pv.size(); n++)
                {
                    String f = pv.get(n).getName() + ": [" + pv.get(n).getSize() + "G] ";
                    if(pv.get(n).isAssigned())
                    {
                        f += "[" + pv.get(n).getVolumeGroup() + "] ";
                    }
                    f += "[" + pv.get(n).getUUIDName() + "]";
                    System.out.println(f);
                }
                System.out.println("");
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

            else if(i.indexOf("vgextend") == 0)
            {
                i = i.substring(i.indexOf(" ") + 1);
                String vgName = i.substring(0, i.indexOf(" "));
                i = i.substring(i.indexOf(" ") + 1);;
                String pvName = i;
                boolean error = true;
                int vgNum = -1;
                for(int n = 0; n < vg.size(); n++)
                {
                    if(vg.get(n).getName().equals(vgName))
                    {
                        vgNum = n;
                        error = false;
                        break;
                    }
                }
                if(error)
                {
                    System.out.println("Volume group does not exist\n");
                }
                else
                {
                    error = true;
                    int pvNum = -1;
                    for(int n = 0; n < pv.size(); n++)
                    {
                        if(pv.get(n).getName().equals(pvName) && pv.get(n).isAssigned() == false)
                        {
                            pvNum = n;
                            error = false;
                            break;
                        }
                    }
                    if(error)
                    {
                        System.out.println("Physical volume does not exist or is already part of a Volume group\n");
                    }
                    else
                    {
                        pv.get(pvNum).setVolumeGroup(vg.get(vgNum));
                        vg.get(vgNum).addPhysicalVolume(pv.get(pvNum));
                        System.out.println("Physical Volume " + pv.get(pvNum).getName() + " has been added to " + vg.get(vgNum).getName());
                        System.out.println("");
                    }
                }
            }

            else if(i.indexOf("vglist") == 0)
            {
                for(int n = 0; n < vg.size(); n++)
                {
                    String f = vg.get(n).getName() + ": total:[" + vg.get(n).getSize() + "G] ";
                    f += "Available:[" + vg.get(n).getAvailableSpace() + "G] [";
                    ArrayList<PhysicalVolume> phys = vg.get(n).getPhysVol();
                    for(PhysicalVolume p : phys)
                    {
                        f += p.getName() + ",";
                    }
                    f = f.substring(0, f.length() - 1) + "] ";
                    f += "[" + vg.get(n).getUUIDName() + "]";
                    System.out.println(f);
                }
                System.out.println("");
            }

            else if(i.indexOf("lvcreate") == 0)
            {
                i = i.substring(i.indexOf(" ") + 1);
                String name = i.substring(0, i.indexOf(" "));
                i = i.substring(i.indexOf(" ") + 1);
                int size = Integer.parseInt(i.substring(0, i.indexOf(" ") - 1));
                i = i.substring(i.indexOf(" ") + 1);
                String vgName = i;
                boolean error = false;
                for(int n = 0; n < lv.size(); n++)
                {
                    if(lv.get(n).getName().equals(name))
                    {
                        error = true;
                        break;
                    }
                }
                if(error)
                {
                    System.out.println("Logical Volume name already exists\n");
                }
                else
                {
                    error = true;
                    int vgNum = -1;
                    for(int n = 0; n < vg.size(); n++)
                    {
                        if(vg.get(n).getName().equals(vgName) && vg.get(n).getAvailableSpace() >= size)
                        {
                            vgNum = n;
                            error = false;
                            break;
                        }
                    }
                    if(error)
                    {
                        System.out.println("Volume group name does not exists or Volume group does not have enough available space\n");
                    }
                    else
                    {
                        LogicalVolume logVol = new LogicalVolume(name, size, vg.get(vgNum));
                        lv.add(logVol);
                        vg.get(vgNum).addLogicalVolume(logVol);
                        System.out.println("logical volume " + logVol.getName() + " created\n");
                    }
                }
            }

            else if(i.indexOf("lvlist") == 0)
            {
                for(int n = 0; n < vg.size(); n++)
                {
                    if(vg.get(n).hasLogicalVolume())
                    {
                        for(int j = 0; j < vg.get(n).getLogVol().size(); j++)
                        {
                            LogicalVolume logV = vg.get(n).getLogVol().get(j);
                            System.out.println(logV.getName() + ": [" + logV.getSize() + "G] [" + logV.getVG().getName() + "] [" + logV.getUUIDName() + "]");
                        }
                    }
                }
                System.out.println("");
            }

            else if(i.indexOf("exit") == 0)
            {
                end = true;
                //pw.close();
                System.out.println("Saving data. Goodbye!");
                break;
            }

            else
            {
                System.out.println("Not a command or wrong format\n");
            }

            System.out.print("cmd# ");
            i = input.nextLine();
            /*if(i.indexOf("exit") == -1)
            {
                pw.println(i);
            }*/
        }
    }
}
