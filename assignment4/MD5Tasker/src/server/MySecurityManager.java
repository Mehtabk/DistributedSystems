package server;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;
import java.util.Arrays;

public class MySecurityManager extends SecurityManager {
	
	public MySecurityManager(){
		super();
	}

	@Override
	public void checkAccept(String host, int port) {
		return;
	}

	@Override
	public void checkAccess(Thread t) {
		return;
	}

	@Override
	public void checkAccess(ThreadGroup g) {
		return;
	}

	@Override
	public void checkConnect(String host, int port, Object context) {
		return;
	}

	@Override
	public void checkConnect(String host, int port) {
		return;
	}

	@Override
	public void checkCreateClassLoader() {
		return;
	}

	@Override
	public void checkDelete(String file) {
		return;
	}

	@Override
	public void checkExec(String cmd) {
		return;
	}

	@Override
	public void checkExit(int status) {
		return;
	}

	@Override
	public void checkLink(String lib) {
		return;
	}

	@Override
	public void checkListen(int port) {
		return;
	}

	@Override
	public void checkMulticast(InetAddress maddr, byte ttl) {
		return;
	}

	@Override
	public void checkMulticast(InetAddress maddr) {
		return;
	}

	@Override
	public void checkPackageAccess(String pkg) {
		return;
	}

	@Override
	public void checkPackageDefinition(String pkg) {
		return;
	}

	@Override
	public void checkPermission(Permission perm, Object context) {
		return;
	}

	@Override
	public void checkPermission(Permission perm) {
		return;
	}

	@Override
	public void checkPrintJobAccess() {
		return;
	}

	@Override
	public void checkPropertiesAccess() {
		return;
	}

	@Override
	public void checkPropertyAccess(String key) {
		return;
	}

	@Override
	public void checkRead(FileDescriptor fd) {
		return;
	}

	@Override
	public void checkRead(String file, Object context) {
		return;
	}

	@Override
	public void checkRead(String file) {
		return;
	}

	@Override
	public void checkSecurityAccess(String target) {
		return;
	}

	@Override
	public void checkSetFactory() {
		return;
	}

	@Override
	public void checkWrite(FileDescriptor fd) {
		return;
	}

	@Override
	public void checkWrite(String file) {
		return;
	}
}
