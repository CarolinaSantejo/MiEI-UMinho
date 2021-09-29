import java.io.*;
import java.time.LocalDateTime;

public class FileInfo {
    int fileSize = 0;
    String fileName;
    short packetNumber = 0;
    long modificationDate = 0;


    public FileInfo() {}

    public FileInfo(String fileName) {
        this.fileName = fileName;
    }

    public FileInfo(int filesize, String fileName, short packetN) {
        this.fileSize = filesize;
        this.fileName = fileName;
        this.packetNumber = packetN;
        this.modificationDate = 0;
    }
    public byte[] readFile() throws IOException {
        File f = new File(fileName);
        fileSize = (int) f.length();
        // work only for 2GB file, because array index can only up to Integer.MAX
        modificationDate = f.lastModified();
        byte[] buffer = new byte[(int)f.length()];
        FileInputStream is = new FileInputStream(fileName);
        is.read(buffer);
        is.close();
        return buffer;
    }

    public void updateMetadataByName(String filesPath) throws FileNotFoundException {
        File f = new File(filesPath+fileName);
        if (f.exists()) {
            modificationDate = f.lastModified();
            fileSize = (int) f.length();
        }
        else throw new FileNotFoundException("Ficheiro não encontrado!");
    }

    public void fromBytes(DataInputStream dataIn) throws IOException {
        fileSize = dataIn.readInt();
        fileName = dataIn.readUTF();
        packetNumber = dataIn.readShort();
        modificationDate = dataIn.readLong();
    }

    public void toBytes(DataOutputStream dataOut) throws IOException {
        dataOut.writeInt(fileSize);
        dataOut.writeUTF(fileName);
        dataOut.writeShort(packetNumber);
        dataOut.writeLong(modificationDate);
    }

    public int size(){
        return Integer.BYTES + fileName.length() + Short.BYTES + Long.BYTES;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileSize=" + fileSize +
                ", fileName='" + fileName + '\'' +
                ", packetNumber=" + packetNumber +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
