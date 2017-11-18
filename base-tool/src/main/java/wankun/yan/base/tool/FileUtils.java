package wankun.yan.base.tool;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  0:49
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
    }

    public static long deleteFolderFiles(String path) {
        return deleteFolderFiles(new File(path));
    }

    public static long deleteFolderFiles(File folder) {
        if(folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if(files != null && files.length != 0) {
                long deletedFileLength = 0L;

                for(int i = 0; i < files.length; ++i) {
                    if(files[i].isFile()) {
                        files[i].delete();
                        deletedFileLength += files[i].length();
                    }
                }

                return deletedFileLength;
            } else {
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    public static long deleteAllFolderFiles(String path) {
        return deleteAllFolderFiles(new File(path));
    }

    public static long deleteAllFolderFiles(File folder) {
        if(folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if(files != null && files.length != 0) {
                long deletedFileLength = 0L;

                for(int i = 0; i < files.length; ++i) {
                    if(files[i].isDirectory()) {
                        deletedFileLength += deleteAllFolderFiles(files[i]);
                    } else if(files[i].isFile()) {
                        deletedFileLength += files[i].length();
                    }

                    files[i].delete();
                }

                return deletedFileLength;
            } else {
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    public static long deleteFolder(String path) {
        return deleteFolder(new File(path));
    }

    public static long deleteFolder(File folder) {
        if(folder.exists() && folder.isDirectory()) {
            long deletedFileLength = deleteAllFolderFiles(folder);
            folder.delete();
            return deletedFileLength;
        } else {
            return 0L;
        }
    }

    public static void createFileIfNotExists(String path) throws IOException {
        if(!StrUtils.isEmpty(path)) {
            File file = new File(path);
            if(!file.exists()) {
                createParentFolder(file);
                file.createNewFile();
            }

        }
    }

    public static boolean deleteFile(String path) {
        if(StrUtils.notEmpty(path)) {
            File file = new File(path);
            return file.exists()?file.delete():true;
        } else {
            return false;
        }
    }

    public static boolean deleteFile(File file) {
        return file != null?file.delete():false;
    }

    public static boolean createFolder(String path) {
        if(StrUtils.notEmpty(path)) {
            File file = new File(path);
            return file.exists() && file.isDirectory()?true:file.mkdirs();
        } else {
            return false;
        }
    }

    public static boolean isFileExists(String path) {
        if(StrUtils.notEmpty(path)) {
            File file = new File(path);
            return file.exists();
        } else {
            return false;
        }
    }

    public static String getFileNameExtension(String fileName) {
        if(StrUtils.notEmpty(fileName)) {
            int index = fileName.lastIndexOf(46);
            if(index != -1) {
                return fileName.substring(index + 1, fileName.length());
            }
        }

        return null;
    }

    public static String getFileName(String fileName) {
        String extension = getFileNameExtension(fileName);
        return extension != null?fileName.replace("." + extension, ""):fileName;
    }

    public static boolean renameFile(String oldFilePath, String newFilePath) {
        return renameFile(new File(oldFilePath), new File(newFilePath));
    }

    public static boolean renameFile(File oldFile, File newFile) {
        return oldFile.renameTo(newFile);
    }

    public static byte[] getBytesFromFile(String path) {
        return getBytesFromFile(new File(path));
    }

    public static byte[] getBytesFromFile(File file) {
        if(file != null && file.exists() && file.isFile()) {
            FileInputStream fis = null;
            ByteArrayOutputStream baos = null;

            try {
                baos = new ByteArrayOutputStream(8192);
                fis = new FileInputStream(file);
//                int length = false;
                byte[] buffer = new byte[1024];

                int length;
                while((length = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                byte[] var5 = baos.toByteArray();
                return var5;
            } catch (Exception var9) {
                logger.error(var9.getMessage(), var9);
                return null;
            } finally {
                IOUtils.close(fis);
                IOUtils.close(baos);
            }
        } else {
            return null;
        }
    }

    public static String getStringFromFile(String savePath) {
        return getStringFromFile(savePath, "UTF-8");
    }

    public static String getStringFromFile(String savePath, String charset) {
        return getStringFromFile(new File(savePath), charset);
    }

    public static String getStringFromFile(File file) {
        return getStringFromFile(file, "UTF-8");
    }

    public static String getStringFromFile(File file, String charset) {
        byte[] bytes = getBytesFromFile(file);
        if(bytes != null) {
            try {
                return new String(bytes, charset);
            } catch (Exception var4) {
                logger.error(var4.getMessage(), var4);
            }
        }

        return null;
    }

    public static boolean createParentFolder(File file) {
        File parent = file.getParentFile();
        return !parent.exists()?parent.mkdirs():true;
    }

    public static boolean outputDataToFileSafe(byte[] data, String filePath) {
        if(data != null && filePath != null) {
            File tempFile = new File(filePath + ".temp");

            try {
                createFileIfNotExists(tempFile.getAbsolutePath());
                outputDataToFile(data, tempFile.getAbsolutePath());
                File file = new File(filePath);
                if(file.exists()) {
                    file.delete();
                }

                return tempFile.renameTo(file);
            } catch (Exception var4) {
                logger.error(var4.getMessage(), var4);
                tempFile.delete();
            }
        }

        return false;
    }

    public static boolean outputDataToFile(byte[] data, String filePath) {
        if(data != null && filePath != null) {
            BufferedOutputStream bos = null;

            boolean var4;
            try {
                createFileIfNotExists(filePath);
                FileOutputStream fos = new FileOutputStream(new File(filePath));
                bos = new BufferedOutputStream(fos, 8192);
                bos.write(data);
                bos.flush();
                var4 = true;
            } catch (Exception var8) {
                logger.error(var8.getMessage(), var8);
                return false;
            } finally {
                IOUtils.close(bos);
            }

            return var4;
        } else {
            return false;
        }
    }

    public static boolean outputStringToFile(String savePath, String data, boolean append) {
        return outputStringToFile(savePath, data, "UTF-8", append);
    }

    public static boolean outputStringToFile(String savePath, String data) {
        return outputStringToFile(savePath, data, "UTF-8");
    }

    public static boolean outputStringToFile(String savePath, String data, String charset) {
        return outputStringToFile(savePath, data, charset, false);
    }

    public static boolean outputStringToFile(String savePath, String data, String charset, boolean append) {
        if(savePath != null && data != null) {
            BufferedWriter bw = null;

            boolean var5;
            try {
                bw = getBufferedWriter(savePath, charset, append);
                if(append && !isEmptyFile(savePath)) {
                    bw.newLine();
                }

                bw.write(data);
                bw.flush();
                var5 = true;
            } catch (IOException var9) {
                logger.error(var9.getMessage(), var9);
                return false;
            } finally {
                IOUtils.close(bw);
            }

            return var5;
        } else {
            return false;
        }
    }

    public static boolean isEmptyFile(String savePath) {
        File file = new File(savePath);
        return file.exists()?file.length() == 0L:true;
    }

    public static boolean copyFile(String srcPath, String destPath) {
        return srcPath != null && destPath != null?copyFile(new File(srcPath), new File(destPath)):false;
    }

    public static boolean copyFile(File srcFile, File destFile) {
        if(srcFile != null && destFile != null && srcFile.exists()) {
            try {
                return copyStream(new FileInputStream(srcFile), new FileOutputStream(destFile));
            } catch (FileNotFoundException var3) {
                logger.error(var3.getMessage(), var3);
            }
        }

        return false;
    }

    public static void copyFolder(String srcPath, String destPath) {
        copyFolder(new File(srcPath), new File(destPath), (f) -> {
            return true;
        });
    }

    public static void copyFolder(String srcPath, String destPath, Predicate<File> predicate) {
        copyFolder(new File(srcPath), new File(destPath), predicate);
    }

    public static void copyFolder(File srcFolder, File destFolder) {
        copyFolder(srcFolder, destFolder, (f) -> {
            return true;
        });
    }

    public static void copyFolder(File srcFolder, File destFolder, Predicate<File> predicate) {
        if(predicate.test(srcFolder)) {
            if(srcFolder.isFile()) {
                copyFile(srcFolder, destFolder);
            } else if(isParentFolder(srcFolder, destFolder)) {
                copyFolder2ChildFolder(srcFolder, destFolder);
            } else {
                destFolder.mkdirs();
                String[] files = srcFolder.list();
                if(files != null && files.length != 0) {
                    for(int i = 0; i < files.length; ++i) {
                        File srcFile = new File(srcFolder.getAbsolutePath() + File.separator + files[i]);
                        if(predicate.test(srcFile)) {
                            File destFile = new File(destFolder.getAbsolutePath() + File.separator + files[i]);
                            if(srcFile.isFile()) {
                                copyFile(srcFile, destFile);
                            } else if(srcFile.isDirectory()) {
                                copyFolder(srcFile, destFile, predicate);
                            }
                        }
                    }

                }
            }
        }
    }

    private static boolean isParentFolder(File parent, File child) {
        return child.getAbsolutePath().startsWith(parent.getAbsolutePath() + File.separator);
    }

    private static void copyFolder2ChildFolder(File srcFolder, File destFolder) {
        if(isParentFolder(srcFolder, destFolder)) {
            File tempFolder = new File(srcFolder.getParentFile() + File.separator + destFolder.getName() + "-temp");
            copyFolder(srcFolder, tempFolder);
            deleteFolder(srcFolder);
            copyFolder(tempFolder, destFolder);
            deleteFolder(tempFolder);
        }

    }

    public static void exploreFolder(File folder, Consumer<File> consumer) {
        consumer.accept(folder);
        if(folder.isDirectory()) {
            File[] files = folder.listFiles();
            if(files != null && files.length != 0) {
                File[] var3 = files;
                int var4 = files.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    File file = var3[var5];
                    exploreFolder(file, consumer);
                }

            }
        }
    }

    public static boolean copyStream(InputStream is, OutputStream os) {
        if(is != null && os != null) {
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(os);
//                int length = false;
                byte[] buffer = new byte[1024];

                int length;
                while((length = bis.read(buffer)) > 0) {
                    bos.write(buffer, 0, length);
                }

                bos.flush();
                boolean var6 = true;
                return var6;
            } catch (Exception var10) {
                logger.error(var10.getMessage(), var10);
                return false;
            } finally {
                IOUtils.close(bis);
                IOUtils.close(bos);
            }
        } else {
            return false;
        }
    }

    public static String formatFileSize(Long fileSize) {
        long size = NumberUtils.valueOf(fileSize).longValue();
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeText;
        if(size < 1024L) {
            fileSizeText = df.format((double)size) + "B";
        } else if(size < 1048576L) {
            fileSizeText = df.format((double)size / 1024.0D) + "K";
        } else if(size < 1073741824L) {
            fileSizeText = df.format((double)size / 1048576.0D) + "M";
        } else {
            fileSizeText = df.format((double)size / 1.073741824E9D) + "G";
        }

        return fileSizeText;
    }

    public static BufferedReader getBufferedReader(String path, String charset) {
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(path), charset));
        } catch (Exception var3) {
            logger.error(var3.getMessage(), var3);
            return null;
        }
    }

    public static BufferedReader getBufferedReader(InputStream is, String charset) {
        try {
            return new BufferedReader(new InputStreamReader(is, charset));
        } catch (Exception var3) {
            logger.error(var3.getMessage(), var3);
            return null;
        }
    }

    public static BufferedWriter getBufferedWriter(String path, String charset, boolean append) {
        try {
            createFileIfNotExists(path);
            return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, append), charset));
        } catch (Exception var4) {
            logger.error(var4.getMessage(), var4);
            return null;
        }
    }

    public static BufferedWriter getBufferedWriter(String path, String charset) {
        return getBufferedWriter(path, charset, false);
    }

    public static String getFileMD5(File file) {
        if(!file.isFile()) {
            return null;
        } else {
            MessageDigest digest = null;
            FileInputStream in = null;
            byte[] buffer = new byte[1024];

            label57: {
                Object var6;
                try {
                    digest = MessageDigest.getInstance("MD5");
                    in = new FileInputStream(file);

                    while(true) {
                        int len;
                        if((len = in.read(buffer, 0, 1024)) == -1) {
                            break label57;
                        }

                        digest.update(buffer, 0, len);
                    }
                } catch (Exception var10) {
                    logger.error(var10.getMessage(), var10);
                    var6 = null;
                } finally {
                    IOUtils.close(in);
                }

                return (String)var6;
            }

            BigInteger var5 = new BigInteger(1, digest.digest());
            return var5.toString(16);
        }
    }

    public static List<String> getStringList(String path) {
        return getStringList(path, "UTF-8");
    }

    public static List<String> getStringList(String path, String charset) {
        return getStringList(path, charset, true);
    }

    public static List<String> getStringList(String path, String charset, boolean ignoreEmptyLine) {
        try {
            BufferedReader br = getBufferedReader(path, charset);
            ArrayList list = new ArrayList();

            while(true) {
                String line;
                do {
                    if((line = br.readLine()) == null) {
                        return list;
                    }
                } while(ignoreEmptyLine && !StrUtils.notEmpty(line));

                list.add(line.trim());
            }
        } catch (IOException var6) {
            logger.error(var6.getMessage(), var6);
            return null;
        }
    }

    public static String[] getStringArray(String path) {
        return getStringArray(path, "UTF-8");
    }

    public static String[] getStringArray(String path, String charset) {
        return getStringArray(path, charset, true);
    }

    public static String[] getStringArray(String path, String charset, boolean ignoreEmptyLine) {
        List<String> list = getStringList(path, charset, ignoreEmptyLine);
        return list != null?(String[])list.toArray(new String[list.size()]):null;
    }

    public static boolean downloadFile(InputStream is, String filePath) throws IOException {
        try {
            createFileIfNotExists(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[8192];

            while(bis.read(buffer) != -1) {
                bos.write(buffer);
            }

            bos.flush();
            bos.close();
            boolean var5 = true;
            return var5;
        } catch (IOException var9) {
            deleteFile(filePath);
            throw new IOException(var9);
        } finally {
            is.close();
        }
    }
}

