package com.along.outboundmanage.utill;
import java.io.File;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class MemDisk {
	public static void main(String[] args) {
		getMemInfo();
		System.out.println();
		getDiskInfo();

		getMemRate();
	}

	public static void getDiskInfo() {
		File[] disks = File.listRoots();
		for(File file : disks) {
			System.out.print(file.getPath() + "    ");
			System.out.print("空闲未使用 = " + file.getFreeSpace() / 1024 / 1024 + "M" + "    ");// 空闲空间
			System.out.print("已经使用 = " + file.getUsableSpace() / 1024 / 1024 + "M" + "    ");// 可用空间
			System.out.print("总容量 = " + file.getTotalSpace() / 1024 / 1024 + "M" + "    ");// 总空间
			System.out.println();
		}
	}

	public static void getMemInfo() {
		OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		System.out.println("Total RAM：" + mem.getTotalPhysicalMemorySize() / 1024 / 1024 + "MB");
		System.out.println("Available　RAM：" + mem.getFreePhysicalMemorySize() / 1024 / 1024 + "MB");
	}

	public static float getMemRate() {
		OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		float total=mem.getTotalPhysicalMemorySize() / 1024 / 1024;
		float free=mem.getFreePhysicalMemorySize() / 1024 / 1024;
		System.out.println("Total RAM：" +total + "MB");
		System.out.println("Available　RAM：" + free + "MB");
		System.out.println(100-(free/total)*100);
		return 100-(free/total)*100;
	}

}