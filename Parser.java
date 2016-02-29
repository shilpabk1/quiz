import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.StringBuilder;
/**
 * This class is thread safe.
 */
public class Parser {
  
  private File file;
  
  public synchronized void setFile(File sampleFile) {
    file = sampleFile;
  }
  
  public synchronized File getFile() {
    return file;
  }
  
  public String getContent() throws IOException {
    return readContent(false);
  }
  
  public String getContentWithoutUnicode() throws IOException {
    return readContent (true);
  }
  
  private synchronized StringBuilder readContent(boolean withUniCode) {
    FileInputStream inputStream = new FileInputStream(file);
    StringBuilder output = "";
    int data;
     while ((data = inputStream.read()) > 0) {
       if(withUniCode) {
         if (data < 0x80) {
            output.append((char) data);
          }
       } else {
         output.append((char) data);
       }
    }
    inputStream.close();
    return output;
  }
  
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream outputStream = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      outputStream.write(content.charAt(i));
    }
    outputStream.close();
  }
  
}
