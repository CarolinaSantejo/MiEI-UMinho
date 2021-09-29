import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HostInfo {
    int port;
    String address;
    List<String> files;

    public HostInfo() {
        files = null;
    }

    public HostInfo(int port, String address) {
        this.port = port;
        this.address = address;
        this.files = null;
    }
    public HostInfo(int port, String address,List<String> filesS) {
        this.port = port;
        this.address = address;
        this.files = new ArrayList<>(filesS);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public void fromBytes(byte[] b) throws IOException {
        final ByteArrayInputStream byteIn = new ByteArrayInputStream(b);
        final DataInputStream dataIn = new DataInputStream(byteIn);
        port = dataIn.readInt();
        address = dataIn.readUTF();
        boolean fileList = dataIn.readBoolean();
        if (fileList) {
            int length = dataIn.readInt();
            files = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                files.add(dataIn.readUTF());
            }
        }
        dataIn.close();
        byteIn.close();

    }
    public byte[] toBytes() throws IOException {
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        final DataOutputStream dataOut = new DataOutputStream(byteOut);
        dataOut.writeInt(port);
        dataOut.writeUTF(address);
        dataOut.writeBoolean(files != null);
        if (files != null) {
            dataOut.writeInt(files.size());
            for (String f : files) dataOut.writeUTF(f);
        }
        dataOut.close();
        byteOut.flush();
        return byteOut.toByteArray();
    }

    public HostInfo clone(){
	List<String> list = new ArrayList<>(files);
	return new HostInfo(port,address,list);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostInfo hostInfo = (HostInfo) o;
        return port == hostInfo.port && address.equals(hostInfo.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, address);
    }

    @Override
    public String toString() {
        return "HostInfo{" +
                "port=" + port +
                ", address='" + address + '\'' +
                ", files=" + files.toString() +
                '}';
    }
}
